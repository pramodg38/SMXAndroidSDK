package com.nice.smx_demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author pramodgai
 * @date 16-07-2019
 */
public class SMXApiHandler {


    public static List<ProviderModel>  generateProvider() {


        ProviderModel providerModel = new ProviderModel();
        Random rand = new Random(1);
        providerModel.setSurvey_id("NTTPRAVIN_133");
        providerModel.setContact_id(System.currentTimeMillis() + "");
        providerModel.setEmail(rand.nextInt(500) + "and_sdk_test@nice.com");
        providerModel.setFirst_name("android_sdk");
        providerModel.setLast_name("android_api");
        providerModel.setPhone(System.currentTimeMillis() + "");
        providerModel.setExternal_contact_record_id(System.currentTimeMillis() + "");

        List<ProviderModel> providerList = new ArrayList<ProviderModel>();

        providerList.add(providerModel);


        return providerList;
    }

}
