package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class SignIn extends AppCompatActivity {

    private static final String TAG = "SIGNIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i(TAG, "onCreate: initialized Amplify");
        }catch (Exception e) {
            Log.i(TAG, "checkedSignin: " + e);
        }


            Button signUpButton = findViewById(R.id.signinButton);

            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    EditText email = findViewById(R.id.email_signin);
                    EditText password = findViewById(R.id.password_signin);

                    String setEmail = email.getText().toString();
                    String setPassword = password.getText().toString();
                    Amplify.Auth.signIn(setEmail,
                            setPassword,
                            result -> Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete"),
                            error -> Log.e("AuthQuickstart", error.toString())

                    );



                    Intent intent = new Intent(SignIn.this, MyTask.class);
                    startActivity(intent);

                }
            });

    }
}