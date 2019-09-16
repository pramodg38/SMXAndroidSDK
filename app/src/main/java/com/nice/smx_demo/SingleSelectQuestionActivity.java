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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import static com.nice.smx_demo.SMXSdkConstants.PROVIDER_URL_LOCAL;
import static com.nice.smx_demo.SMXSdkConstants.QUESTION_NUMBER;

public class SingleSelectQuestionActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mButton;
    private int questionNUmber = 0;
    private Gson gson = new Gson();
    private SurveyResponseModel mSurveyResponseModel = new SurveyResponseModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_select_question);
        Context context = this;
        getQuestionLocal(context, mSurveyResponseModel);
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
                PROVIDER_URL_LOCAL + "" + (++QUESTION_NUMBER),
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
                Log.i("SMX-SDK-SS", "button clicked");
                getQuestion(this, mSurveyResponseModel);
                break;
        }
    }

}
