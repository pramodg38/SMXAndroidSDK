package com.nice.smx_demo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nice.smx_demo.SMXSdkConstants.PROVIDER_URL;
import static com.nice.smx_demo.SMXSdkConstants.PROVIDER_URL_LOCAL;
import static com.nice.smx_demo.SMXSdkConstants.QUESTION_NUMBER;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mButton;

    private Gson gson = new Gson();
    private SurveyResponseModel mSurveyResponseModel = new SurveyResponseModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Context context = this;
        //showQuestion(null);


        List<ProviderModel> data = SMXApiHandler.generateProvider();
        //createProvider(context, data);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("data", data);
        createProviderLocal(context, params);
       // getQuestion(context);

    }

    private void createProvider(Context context,  List<ProviderModel> data) {
        JSONObject jsonReq = new JSONObject();
        try {
            String jsonString = gson.toJson(data);
            Log.i("SMX-SDK", "final data model");
            //jsonReq.optString(jsonString);
            jsonReq.putOpt("data", jsonString);
        } catch (Exception e) {
            Log.e("SMX-SDk", String.valueOf(e.fillInStackTrace()));
            e.printStackTrace();
        }

        Log.i("SMX-SDK", "Printing params from modelL, jsonReq :: "+ jsonReq);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                PROVIDER_URL_LOCAL,
                jsonReq,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("Success: ", response.toString());
                            Gson g = new Gson();
                            SurveyResponseModel surveyResponseModel = g.fromJson
                                    (response.toString(), SurveyResponseModel.class);
                            showQuestion(surveyResponseModel);
                        } catch (Exception e) {
                            showError(true);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error: ", String.valueOf(error.fillInStackTrace()));
                        showError(true);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("domain", "nttpravin");
                params.put("username", "pravin.shaw@nice.com");
                params.put("password", "Password1");

                return params;
            }
        };

        MySingleton.getInstance(context).addToRequestQue(jsonObjectRequest);

        //remove this after test
        /*mSurveyResponseModel.setQuestionText("Dummy question");
        mSurveyResponseModel.setQuestionType("scale");
        showQuestion(mSurveyResponseModel);*/
    }

    private void createProviderLocal(Context context,  Map<String, Object> params) {
        Log.i("smx-sdk","Calling provider req");
       JSONObject jsonReq = new JSONObject();
        try {
            jsonReq.putOpt("data", params.get("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("SMX-SDK", "Printing params from modelL, jsonReq :: "+ jsonReq);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                PROVIDER_URL+ "" + (++QUESTION_NUMBER),
                jsonReq,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("Success: ", response.toString());
                            Gson g = new Gson();
                            SurveyResponseModel surveyResponseModel = g.fromJson
                                    (response.toString(), SurveyResponseModel.class);
                            showQuestion(surveyResponseModel);
                        } catch (Exception e) {
                            showError(true);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error: ", String.valueOf(error.fillInStackTrace()));
                        showError(true);
                    }
                }
        );

        MySingleton.getInstance(context).addToRequestQue(jsonObjectRequest);
    }

    private void showError(boolean isError) {

        String questionText = "Sorry. Not able to load survey";
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.take_survey);
        textView.setText(questionText);
        mButton = findViewById(R.id.nextButton);
        mButton.setVisibility(View.GONE);
    }

    private void getQuestion(Context context, SurveyResponseModel surveyResponseModel) {

        JSONObject jsonReq = new JSONObject();
        try {
            //jsonReq =
            String jsonString = gson.toJson(surveyResponseModel);
            jsonReq.optString(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                PROVIDER_URL_LOCAL+ "" + (++QUESTION_NUMBER),
                jsonReq,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("Success: ", response.toString());
                            Gson g = new Gson();
                            SurveyResponseModel surveyResponseModel = g.fromJson
                                    (response.toString(), SurveyResponseModel.class);
                            showQuestion(surveyResponseModel);
                        } catch (Exception e) {
                            e.printStackTrace();
                            showQuestion(null);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error: ", String.valueOf(error.fillInStackTrace()));
                        showQuestion(null);
                    }
                }
        );
        MySingleton.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

    private void getQuestionLocal(Context context, SurveyResponseModel surveyResponseModel) {
        surveyResponseModel.setAnswerToQuestion("4");

        if (surveyResponseModel.getQuestionType().equals("single-select") ) {
            surveyResponseModel.setAnswerToQuestion("it was a good experience");
        }
        JSONObject jsonReq = new JSONObject();
        try {
            //jsonReq =
            String jsonString = gson.toJson(surveyResponseModel);
            jsonReq.optString(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                PROVIDER_URL_LOCAL+ "" + (++QUESTION_NUMBER),
                jsonReq,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("Success: ", response.toString());
                            Gson g = new Gson();
                            SurveyResponseModel surveyResponseModel = g.fromJson
                                    (response.toString(), SurveyResponseModel.class);
                            showQuestion(surveyResponseModel);
                        } catch (Exception e) {
                            e.printStackTrace();
                            showQuestion(null);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error: ", String.valueOf(error.fillInStackTrace()));
                        showQuestion(null);
                    }
                }
        );
        MySingleton.getInstance(context).addToRequestQue(jsonObjectRequest);

    }

    private void showQuestion(SurveyResponseModel surveyResponseModel) {
        String questionText = "";
        this.mSurveyResponseModel = surveyResponseModel;
        if (null == surveyResponseModel  || null == (surveyResponseModel.getQuestionType())) {
            questionText = "Thank you for your valuable feedback !!!";
            setContentView(R.layout.activity_main);
           disableAllComponents();
        } else if (surveyResponseModel.getQuestionType().equals("scale")) {
            Log.i("SMX-SDK", "scale question");
            questionText = surveyResponseModel.getQuestionText();
            setContentView(R.layout.activity_main);
            //setContentView(R.layout.activity_single_select_question);
        } else if (surveyResponseModel.getQuestionType().equals("single-select")) {
            Log.i("SMX-SDK", "single select question");
            questionText = surveyResponseModel.getQuestionText();
            setContentView(R.layout.activity_single_select_question);
        }

        TextView textView = findViewById(R.id.take_survey);
        textView.setText(questionText);

        mButton = findViewById(R.id.nextButton);
        mButton.setOnClickListener(this);

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        if (ratingBar != null) {
            ratingBar.setOnClickListener(this);
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    //ratingBar.getRating();
                    Log.i("Rating bar change", "rating ar cleicked");
                }
            });
        }

    }

    private void disableAllComponents() {
        mButton = findViewById(R.id.nextButton);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setVisibility(View.GONE);
        mButton.setVisibility(View.GONE);

        /*RadioGroup radioGroup = findViewById(R.id.radioGroup);
        if (radioGroup != null) {
            radioGroup.setVisibility(View.GONE);
        }*/

        EditText commentTest = findViewById(R.id.commentText);

        if (commentTest != null) {
            commentTest.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.nextButton:
            Log.i("SMX-SDK", "button clicked");
                mSurveyResponseModel.setAnswerToQuestion("4");
            getQuestionLocal(this, mSurveyResponseModel);
            break;

            case R.id.ratingBar:
                Log.i("SMX-SDK", "rating bar");
                mSurveyResponseModel.setAnswerToQuestion("3");
                getQuestion(this, mSurveyResponseModel);
            break;
        }
    }



}
