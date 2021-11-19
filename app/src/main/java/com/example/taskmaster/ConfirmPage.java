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

public class ConfirmPage extends AppCompatActivity {

    private static final String TAG = "CONFIRM PAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_page);
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i(TAG, "onCreate: initialized Amplify");
        } catch (Exception e) {
            Log.i(TAG, "checkedConfirmUp: " + e);
        }

            Button confirmButton = findViewById(R.id.confirmButton);

            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    TextView email = findViewById(R.id.Email);
                    TextView confirmCode = findViewById(R.id.code_confirm);

                    String setEmail = email.getText().toString();
                    String setConfirmCode = confirmCode.getText().toString();
                    Amplify.Auth.confirmSignUp(setEmail
                            , setConfirmCode,
                            result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                            error -> Log.e("AuthQuickstart", error.toString())
                    );



                    Intent intent = new Intent(ConfirmPage.this, SignIn.class);

                    startActivity(intent);
                    MyTask.analyticsEvent();


                }
            });

    }
}