package com.example.taskmaster;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.analytics.pinpoint.AWSPinpointAnalyticsPlugin;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Details;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;


import java.util.ArrayList;
import java.util.List;

public class MyTask extends AppCompatActivity {
    private static PinpointManager pinpointManager;


    public static final String TAG = "MainActivity";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        getPinpointManager(getApplicationContext());
        createNotificationChannel();



        Button button = findViewById(R.id.AddTask);
// toolbar jamal
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyTask.this , AddTask.class);

                startActivity(intent);
                analyticsEvent();
            }
        });

            Button button1 = findViewById(R.id.AllTasks);

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(MyTask.this , AllTask.class);
                    startActivity(intent1);
                    analyticsEvent();
                }
            });

            Button button5 = findViewById(R.id.Settings);
            button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent5 = new Intent(MyTask.this , Settings.class);
                    startActivity(intent5);
                    analyticsEvent();

                }
            });

//        ArrayList<com.amplifyframework.datastore.generated.model.Details> detailsArrayList = new ArrayList<>();
//
//        detailsArrayList.add(new Details("New Data " , "Welcome To Our Application" , "NEW"));
//        detailsArrayList.add(new Details("Error1" , "Please Make Sure You Put a Correct Data Ty :) " , "Assigned"));
//        detailsArrayList.add(new Details("Test" , "Please Waiting We Are Checking If You Have A Error Or Not !" , "in Progress "));
//        detailsArrayList.add(new Details("Information" , "Nice Everything Is Good You Can Discover Our Application Thank You For Choosing Us " , "Complete"));

//        // here I Will Take The Recycler View
//        RecyclerView taskRec = findViewById(R.id.recyclerViewMain);
//
//        // here we will set the layout manger
//
//        taskRec.setLayoutManager(new LinearLayoutManager(this));
//
//        // here we will set the  adapter for this recycler view
//        taskRec.setAdapter(new DetailsAdapter(detailsArrayList));





        try {

            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSPinpointAnalyticsPlugin(getApplication()));

            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i(TAG, "onCreate: initialized Amplify");

        } catch (AmplifyException e) {
            Log.e(TAG, "onCreate: Colud not initialize Amplify ", e);
        }

        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickstart", result.toString()),
                error -> Log.e("AmplifyQuickstart", error.toString())
        );



        Button logoutButton = findViewById(R.id.log_out_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amplify.Auth.signOut(
                        () -> Log.i("AuthQuickstart", "Signed out successfully"),
                        error -> Log.e("AuthQuickstart", error.toString())

                );

                Intent intent = new Intent(MyTask.this , SignIn.class);
                startActivity(intent);
                analyticsEvent();
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sharedPreferences.getString("username","hello user");

        TextView user = findViewById(R.id.username);
        user.setText("Welcome "  +  username);

        recycleView();

// ---------START---------- this is for room database locally -------------------
        // this is to get the database and render it in my task with recyclerView
//        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "database-name").allowMainThreadQueries().build();
//        TaskDao taskDao = db.taskDao();
//        List<Details> detailsList = taskDao.getAll();

//        RecyclerView taskRec = findViewById(R.id.recyclerViewMain);

        // here we will set the layout manger

//        taskRec.setLayoutManager(new LinearLayoutManager(this));
//
//        // here we will set the  adapter for this recycler view
//        taskRec.setAdapter(new DetailsAdapter(detailsList));

// ------------------- this is for room database locally ---------END----------




    }

    private void recycleView () {

        List<com.amplifyframework.datastore.generated.model.Details> detailsList = new ArrayList<>();
        RecyclerView alltasks = findViewById(R.id.recyclerViewMain);
        alltasks.setLayoutManager(new LinearLayoutManager(this));
        alltasks.setAdapter(new DetailsAdapter(detailsList));

        Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                alltasks.getAdapter().notifyDataSetChanged();
                return false;
            }
        });
        Amplify.API.query(ModelQuery.list(com.amplifyframework.datastore.generated.model.Details.class) ,
                response -> {
                    for (com.amplifyframework.datastore.generated.model.Details details : response.getData()) {
//                        Details details1 = new Details(details.getTitle(), details.getBody(), details.getState());
                        Log.i("graph of khair is here for testing ", details.getTitle());
                        detailsList.add(details);
                    }
                    handler.sendEmptyMessage(1);

                },
                error -> Log.e(TAG, "onCreate: Query failure",error )
        );
        analyticsEvent();
    }


    public static PinpointManager getPinpointManager(final Context applicationContext) {
        if (pinpointManager == null) {
            final AWSConfiguration awsConfig = new AWSConfiguration(applicationContext);
            AWSMobileClient.getInstance().initialize(applicationContext, awsConfig, new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    Log.i("INIT", userStateDetails.getUserState().toString());
                }

                @Override
                public void onError(Exception e) {
                    Log.e("INIT", "Initialization error.", e);
                }
            });

            PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                    applicationContext,
                    AWSMobileClient.getInstance(),
                    awsConfig);

            pinpointManager = new PinpointManager(pinpointConfig);

            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }
                            final String token = task.getResult();
                            Log.d(TAG, "Registering push notifications token: " + token);
                            pinpointManager.getNotificationClient().registerDeviceToken(token);
                        }
                    });
        }
        return pinpointManager;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(PushListenerService.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void analyticsEvent () {
        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("Main Activity ")
                .addProperty("Add Task", "Text Option")
                .addProperty("Successful", true)
                .build();
        Amplify.Analytics.recordEvent(event);

    }
}