package com.nice.smx_demo;

/**
 * @author pramodgai
 * @date 16-07-2019
 */
public final class ProviderModel {

private String survey_id;

    private String isInAppSurvey;
    private String follow_up_owner_1;
    private String contact_id;
    private String email;
    private String phone;
    private String first_name;
    private String last_name;
    private String external_contact_record_id;

    public String getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(String survey_id) {
        this.survey_id = survey_id;
    }

    public String getIsInAppSurvey() {
        return isInAppSurvey;
    }

    public void setIsInAppSurvey(String isInAppSurvey) {
        this.isInAppSurvey = isInAppSurvey;
    }

    public String getFollow_up_owner_1() {
        return follow_up_owner_1;
    }

    public void setFollow_up_owner_1(String follow_up_owner_1) {
        this.follow_up_owner_1 = follow_up_owner_1;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getExternal_contact_record_id() {
        return external_contact_record_id;
    }

    public void setExternal_contact_record_id(String external_contact_record_id) {
        this.external_contact_record_id = external_contact_record_id;
    }
}
