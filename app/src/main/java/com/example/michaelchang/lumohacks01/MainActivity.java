package com.example.michaelchang.lumohacks01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupAccountButtons();
    }

    @Override
    public void onResume(){
        super.onResume();

        setupUsernameDisplay();
    }

    private void setupUsernameDisplay() {
        TextView txtCurrentUser = findViewById(R.id.txtCurrentUser);
        CurrentUser currentUser = CurrentUser.getInstance();
        if (currentUser.getUsername() != null) {
            txtCurrentUser.setText("Current User: " + currentUser.getUsername());
            Log.d("MedChat", "User " + currentUser.getUsername() + " is logged in");
        }
        Log.d("MedChat", "User not yet logged in");
    }


    // set up login button
    private void setupAccountButtons() {

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LoginActivity.getIntent(MainActivity.this);
                startActivity(intent);
            }
        });

        Button btnReg = findViewById(R.id.btnReg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RegActivity.getIntent(MainActivity.this);
                startActivity(intent);
            }
        });


    }


    // set up register

}
