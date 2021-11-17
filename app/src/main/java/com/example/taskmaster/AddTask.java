package com.example.taskmaster;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Details;


public class AddTask extends AppCompatActivity {
public static final String TAG = "ADD TASK";

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        // --------------- database ------------------
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();
        TaskDao taskDao = db.taskDao();

        // ---------------- database ------------------
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent   = new Intent(AddTask.this, MyTask.class);
                startActivity(intent);
            }
        });
        TextView textView = findViewById(R.id.textView3);
        Button button = findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            int counter = 0 ;
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
            // this is to set the data you write it in plan text and insert it in database after you click to save button
                EditText taskTitle = findViewById(R.id.titlePerson1);
                EditText taskBody = findViewById(R.id.bodyPerson);
                EditText taskState = findViewById(R.id.statePerson);

                String setTitle = taskTitle.getText().toString();
                String setBody = taskBody.getText().toString();
                String setState = taskState.getText().toString();
                    // those were for room data base
//                Details details = new Details(setTitle , setBody , setState);
//                taskDao.insertAll(details);
                
                textView.setText("Total Tasks :"+ counter++);
                Toast toast = Toast.makeText(getApplicationContext() , "Osh You Hit Me !! , You Will Get Error If You Hit Me Again  Cya :) " , Toast.LENGTH_SHORT);
                toast.show();
                // add our constructor data to AWS Server we can found this also in schema file "C:\Users\STUDENT\Desktop\AndroidPart\taskmaster\amplify\backend\api\taskmaster\schema.graphql"
                Details details = Details.builder()
                        .title(setTitle)
                        .body(setBody)
                        .state(setState)
                        .build();

                    // Api AWS AMPLIFY // -----------------*****
                Amplify.API.mutate(ModelMutation.create(details) , response ->
                        Log.i(TAG , "Added task with id tag : " + response.getData().getId()) ,
                        error -> Log.e(TAG , "Create failed " , error)

                        );
            }
        });

    }
}