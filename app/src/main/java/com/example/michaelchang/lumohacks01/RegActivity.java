package com.example.michaelchang.lumohacks01;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegActivity extends AppCompatActivity {
    
    public static final String USERNAME = "Username";
    public static final String CONFIRMATION = "Confirmation";
    public static final String PASSWORD = "Password";
    public static final String TAG = "MedChat";
    public static final String TAG1 = "MedChat";
    private DocumentReference mDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        btnSubmitClicked();
    }

    public static Intent getIntent(Context srcContext) {
        return new Intent(srcContext, RegActivity.class);
    }
    
    private void btnSubmitClicked() {
        Button btnSubmit = findViewById(R.id.submit_button);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameView = (EditText) findViewById(R.id.enter_username);
                EditText confirmationView = (EditText) findViewById(R.id.enter_confirmation);
                EditText passwordView = (EditText) findViewById(R.id.enter_password);
                EditText password2View = (EditText) findViewById(R.id.enter_password2);

                final String usernameText = usernameView.getText().toString();
                String confirmationText = confirmationView.getText().toString();
                String passwordText = passwordView.getText().toString();
                String password2Text = password2View.getText().toString();

                if(usernameText.isEmpty() || confirmationText.isEmpty() || passwordText.isEmpty() || password2Text.isEmpty()) { //check for not empty
                    Toast.makeText(RegActivity.this, "Please fill out all text boxes", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!passwordText.equals(password2Text)) { //check that password matches
                    Toast.makeText(RegActivity.this, "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                }

                Map<String, Object> dataToSave = new HashMap<String, Object>();
                dataToSave.put(USERNAME, usernameText);
                dataToSave.put(CONFIRMATION, confirmationText);
                dataToSave.put(PASSWORD, passwordText);

                DocumentReference mDocRef = FirebaseFirestore.getInstance().document("Users/" + usernameText);
                mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Registration success");
                        Log.d(TAG, "Logging in user");
                        CurrentUser.getInstance().setUsername(usernameText);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG1, "Registration failed", e);
                    }
                });
            }
        });
    }
}
