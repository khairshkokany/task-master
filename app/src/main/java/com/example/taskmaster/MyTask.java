package com.example.taskmaster;

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

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

import java.util.ArrayList;
import java.util.List;

public class MyTask extends AppCompatActivity {
    public static final String TAG = "Main Activity";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);

        Button button = findViewById(R.id.AddTask);
// toolbar jamal
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyTask.this , AddTask.class);
                startActivity(intent);
            }
        });

            Button button1 = findViewById(R.id.AllTasks);

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(MyTask.this , AllTask.class);
                    startActivity(intent1);
                }
            });

//        Button button2 = findViewById(R.id.task1);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MyTask.this , TaskDetailsPage.class);
//                String task1 = "This Is Task 1 Nothing Is Here Go To Task 2 Please .! ";
//                intent.putExtra("task" , task1);
//                startActivity(intent);
//            }
//        });
//
//        Button button3 = findViewById(R.id.task2);
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MyTask.this , TaskDetailsPage.class);
//                String task2 = "Umm Can You Go Out Please I'm So Busy Ty :) ";
//                intent.putExtra("task" , task2);
//                startActivity(intent);
//            }
//        });
//            Button button4 = findViewById(R.id.task3);
//            button4.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent4 = new Intent(MyTask.this , TaskDetailsPage.class);
//                    String task3 = "Umm After You Finshed From Task 1 and 2 You Come To Me ? , Please Get Out And Don't let Me See U Again :)";
//                    intent4.putExtra("task" , task3);
//                    startActivity(intent4);
//                }
//            });
            Button button5 = findViewById(R.id.Settings);
            button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent5 = new Intent(MyTask.this , Settings.class);
                    startActivity(intent5);
                }
            });

        ArrayList<Details> detailsArrayList = new ArrayList<Details>();

        detailsArrayList.add(new Details("New Data " , "Welcome To Our Application" , "NEW"));
        detailsArrayList.add(new Details("Error1" , "Please Make Sure You Put a Correct Data Ty :) " , "Assigned"));
        detailsArrayList.add(new Details("Test" , "Please Waiting We Are Checking If You Have A Error Or Not !" , "in Progress "));
        detailsArrayList.add(new Details("Information" , "Nice Everything Is Good You Can Discover Our Application Thank You For Choosing Us " , "Complete"));

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
            Amplify.configure(getApplicationContext());
            Log.i(TAG, "onCreate: initialized Amplify");

        } catch (AmplifyException e) {
            Log.e(TAG, "onCreate: Colud not initialize Amplify ",e );
        }

            List<Details> detailsList = new ArrayList<>();
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
                        Details details1 = new Details(details.getTitle(), details.getBody(), details.getState());
                        Log.i("graph of khair is here for testing ", details.getTitle());
                        detailsList.add(details1);
                    }
                    handler.sendEmptyMessage(1);

                },
                error -> Log.e(TAG, "onCreate: Query failure",error )
                );

        try {

            Amplify.addPlugin(new AWSApiPlugin());
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
}