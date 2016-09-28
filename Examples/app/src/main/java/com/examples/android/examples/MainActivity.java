package com.examples.android.examples;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Coding style convention in Android.
    private Button mLuckyButton;
    private Button mNextButton;
    private Button mFragmentButton;
    private TextView mTextView;
    private Context context;
    private Toast toast;
    private int count;


    //Messaged passed in order to create an instance of activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Instantiates the layout
        setContentView(R.layout.activity_main);

        mLuckyButton = (Button) findViewById(R.id.btn1);
        mNextButton = (Button) findViewById(R.id.btn2);
        mFragmentButton = (Button) findViewById(R.id.btn3);
        mTextView = (TextView)findViewById(R.id.txt1);
        context = getApplicationContext();
        toast = Toast.makeText(context,"Feeling lucky is not available",Toast.LENGTH_SHORT);
        count = 0;


        mLuckyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(R.string.response);
                count++;
                if(count >= 2){
                    toast.show();
                }
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });

        mFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ThirdActivityFragment.class);
                startActivity(i);
            }
        });


    }
}
