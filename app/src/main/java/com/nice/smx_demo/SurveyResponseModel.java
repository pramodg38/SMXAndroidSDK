package com.nice.smx_demo;

/**
 * @author pramodgai
 * @date 16-07-2019
 */
public final class SurveyResponseModel {

    String questionText;
    String questiontype;
    String personIdentifier;
    String answerToQuestion;
    boolean isMandatoryQuestion;
    boolean isThankyouQuestion;

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    String questionType;

    public boolean isThankyouQuestion() {
        return isThankyouQuestion;
    }

    public void setThankyouQuestion(boolean thankyouQuestion) {
        isThankyouQuestion = thankyouQuestion;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getQuestiontype() {
        return questiontype;
    }

    public String getPersonIdentifier() {
        return personIdentifier;
    }

    public String getAnswerToQuestion() {
        return answerToQuestion;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setQuestiontype(String questiontype) {
        this.questiontype = questiontype;
    }

    public void setPersonIdentifier(String personIdentifier) {
        this.personIdentifier = personIdentifier;
    }

    public void setAnswerToQuestion(String answerToQuestion) {
        this.answerToQuestion = answerToQuestion;
    }

    public boolean isMandatoryQuestion() {
        return isMandatoryQuestion;
    }

    public void setMandatoryQuestion(boolean mandatoryQuestion) {
        isMandatoryQuestion = mandatoryQuestion;
    }

    @Override
    public String toString() {
        return "SurveyResponseModel{" +
                "questionText='" + questionText + '\'' +
                ", questiontype='" + questiontype + '\'' +
                ", personIdentifier='" + personIdentifier + '\'' +
                ", answerToQuestion='" + answerToQuestion + '\'' +
                ", isMandatoryQuestion=" + isMandatoryQuestion +
                ", isThankyouQuestion=" + isThankyouQuestion +
                '}';
    }

}
