package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TaskDetailsPage.this, MyTask.class);
                startActivity(intent);
            }
        });
// ----------------------- task 1 ----------------- got it when the user click to button task 1 and changed the main name in page from get and set
//        Intent intent1 = getIntent();
//        String task1 = intent1.getExtras().getString("task");
//
//        TextView text1 = findViewById(R.id.taskName);
//        text1.setText(task1);


        //---------------------------------------
//        Intent intent4 = new Intent(MyTask.this , TaskDetailsPage.class);
//                    String task3 = "Umm After You Finshed From Task 1 and 2 You Come To Me ? , Please Get Out And Don't let Me See U Again :)";
//                    intent4.putExtra("task" , task3);
//                    startActivity(intent4);
//
        Intent intent = getIntent();
//    this is title render
        String data1 = intent.getStringExtra("title");
        TextView title = findViewById(R.id.title1);
        title.setText(data1);
        //    this is body render
        String data2 = intent.getStringExtra("body");
        TextView body = findViewById(R.id.body2);
        body.setText(data2);
        //    this is state render
        String data3 = intent.getStringExtra("state");
        TextView state = findViewById(R.id.state3);
        state.setText(data3);



    }


}