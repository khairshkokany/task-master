package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SIGNUP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
            Log.i(TAG, "onCreate: initialized Amplify");


        }catch (Exception e) {
            Log.i(TAG, "checkedSignUp: " + e);
        }



        Button signUpButton = findViewById(R.id.signupButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView email = findViewById(R.id.Email);
                TextView password = findViewById(R.id.Password);
                String setEmail = email.getText().toString();
                String setPassword = password.getText().toString();


                AuthSignUpOptions options = AuthSignUpOptions.builder()
                        .userAttribute(AuthUserAttributeKey.email(), setEmail)
                        .build();
                Amplify.Auth.signUp(setEmail, setPassword, options,
                        result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
                        error -> Log.e("AuthQuickStart", "Sign up failed", error)
                );

                Intent intent = new Intent(SignUp.this, ConfirmPage.class);
                startActivity(intent);
                MyTask.analyticsEvent();


            }

        });
        Button signinButton = findViewById(R.id.signin_in_signup);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this , SignIn.class);
                startActivity(intent);
                MyTask.analyticsEvent();

            }
        });
    }

}