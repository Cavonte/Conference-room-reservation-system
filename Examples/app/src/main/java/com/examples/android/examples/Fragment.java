package com.examples.android.examples;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment extends android.support.v4.app.Fragment{

    private Button mCalendar;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_example, container, false);
        mCalendar = (Button) v.findViewById(R.id.btn5);

        mCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                CalendarFragment calendar = new CalendarFragment();
                calendar.show(manager, "");
            }
        });
        return v;
    }
}
