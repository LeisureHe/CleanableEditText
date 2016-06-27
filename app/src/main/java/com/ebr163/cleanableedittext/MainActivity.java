package com.ebr163.cleanableedittext;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ebr163.cleanableeditview.CleanableEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    CleanableEditText text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (CleanableEditText) findViewById(R.id.tex1);
    }


    @Override
    public void onClick(View v) {
        text1.setError("xaxassadas");
        text1.setErrorEnabled(true);
    }
}
