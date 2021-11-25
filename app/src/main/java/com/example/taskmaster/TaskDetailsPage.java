package com.example.taskmaster;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class TaskDetailsPage extends AppCompatActivity  implements OnMapReadyCallback {
    private GoogleMap googleMap;

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

        String url = intent.getExtras().getString("image");
        String url1 = "https://img3.hulu.com/user/v3/artwork/9c91ffa3-dc20-48bf-8bc5-692e37c76d88?base_image_bucket_name=image_manager&base_image=747157b1-4581-414a-959f-c4956ebc3349&size=1200x630&format=jpeg";
        Log.i("url", "onCreate: "+ url);
        ImageView image = findViewById(R.id.s_image);
        Log.i("url", "onCreate: "+ image.getId());
        Picasso.get().load(url).into(image);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);


    }




    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        Intent intent = getIntent();
        LatLng myLocation = new LatLng(getIntent().getDoubleExtra("lat", intent.getFloatExtra("lat",0)),
                getIntent().getDoubleExtra("lon", intent.getFloatExtra("lon",0)));
        googleMap.addMarker(new MarkerOptions().position(myLocation).title("My Location In Jordan"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
    }
}


