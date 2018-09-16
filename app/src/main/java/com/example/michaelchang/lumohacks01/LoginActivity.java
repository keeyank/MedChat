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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MedChat";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupBtnLogin();
    }

    private void setupBtnLogin() {
        Button btnLogin = findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameView = (EditText) findViewById(R.id.username);
                EditText passwordView = (EditText) findViewById(R.id.password);

                final String usernameText = usernameView.getText().toString();
                final String passwordText = passwordView.getText().toString();

                if (usernameText.isEmpty() || passwordText.isEmpty()) { //check for not empty
                    Toast.makeText(LoginActivity.this, "Please fill out all text boxes", Toast.LENGTH_LONG).show();
                    return;
                }

                // Pull user data from server
                DocumentReference docRef = db.collection("Users").document(usernameText);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                // Check password
                                if (isPasswordValid(Objects.requireNonNull(document.getData()), passwordText)) {
                                    Log.d(TAG, "Login successful!");
                                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT)
                                            .show();
                                    updateUserSingleton(usernameText);
                                }
                                else {
                                    Log.d(TAG, "Login unsuccessful - Incorrect password");
                                    Toast.makeText(LoginActivity.this, "Your password is incorrect", Toast.LENGTH_SHORT)
                                            .show();
                                }

                            } else {
                                Log.d(TAG, "Login Unsuccessful - No such document found");
                                Toast.makeText(LoginActivity.this, "Your username doesn't exist! " +
                                        "Go to the Register page to create a new account.", Toast.LENGTH_LONG)
                                        .show();
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                            Toast.makeText(LoginActivity.this, "get failed with " + task.getException(), Toast.LENGTH_LONG)
                                    .show();
                        }

                    }
                });

            }
        });
    }

    private boolean isPasswordValid(Map<String, Object> data, String passwordText) {
        Log.d(TAG, "Correct password: " + data.get("Password") +
                "\nInputted password: " + passwordText);
        return data.get("Password").equals(passwordText);
    }

    private void updateUserSingleton(String username) {
        CurrentUser currentUser = CurrentUser.getInstance();
        currentUser.setUsername(username);
    }

    public static Intent getIntent(Context srcContext) {
        return new Intent(srcContext, LoginActivity.class);
    }
    
    
}
