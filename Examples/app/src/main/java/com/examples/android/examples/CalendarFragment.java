package com.examples.android.examples;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

public class CalendarFragment extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.calendar, null);
        return new AlertDialog.Builder(getActivity()).setView(v).setPositiveButton("ok", null).create();
    }

}
