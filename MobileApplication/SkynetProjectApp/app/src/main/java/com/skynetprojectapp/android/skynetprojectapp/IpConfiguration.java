package com.skynetprojectapp.android.skynetprojectapp;

import android.os.StrictMode;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

public  class IpConfiguration {

    private final static String ip = "192.168.1.252";


    public static String getIp(){
        return ip;
    }


    public static boolean logIn(String studentId, String password)
    {
        Boolean success=false;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = "http://"+getIp() +":8080/login?username="+studentId+"&password="+password;
        RestTemplate restTemplate = new RestTemplate();

        try {

            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
//        multiValueMap.add("username", studentId);
//        multiValueMap.add("password", password);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(multiValueMap, headers);

           success = restTemplate.getForObject(url, Boolean.class, entity);
            System.out.println("Login is " + success);
        } catch (HttpServerErrorException e) {
            System.out.print("The server crashed" + e.getMessage() + "" + e.getCause());
        }
        return success;
    }
}
