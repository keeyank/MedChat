package com.example.michaelchang.lumohacks01;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
    }

    public static Intent getIntent(Context srcContext) {
        return new Intent(srcContext, RegActivity.class);
    }
}
