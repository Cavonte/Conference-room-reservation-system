package com.examples.android.examples;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


public class ThirdActivityFragment extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = fm.findFragmentById(R.id.fragment);

        if(fragment == null){
            fragment = new Fragment();
            fm.beginTransaction().add(R.id.fragment, fragment).commit();
        }
    }

}
