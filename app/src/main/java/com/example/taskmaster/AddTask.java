package com.example.taskmaster;

import static com.amplifyframework.datastore.generated.model.Details.builder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Details;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddTask extends AppCompatActivity {
public static final String TAG = "ADD TASK";
    public String imageName = "";
    public Uri uri;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
    sharePhoto();
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

    try {
        // Add these lines to add the AWSCognitoAuthPlugin and AWSS3StoragePlugin plugins
        Amplify.addPlugin(new AWSCognitoAuthPlugin());
        Amplify.addPlugin(new AWSS3StoragePlugin());
        Amplify.configure(getApplicationContext());

        Log.i("MyAmplifyApp", "Initialized Amplify");
    } catch (AmplifyException error) {
        Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
    }


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
                String imageUrl = sharedPreferences.getString("FileUrlForReal" , "No files");
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
                        .imageName(imageUrl)
                        .build();

                    // Api AWS AMPLIFY // -----------------*****
                Amplify.API.mutate(ModelMutation.create(details) , response ->
                        Log.i(TAG , "Added task with id tag : " + response.getData().getId()) ,
                        error -> Log.e(TAG , "Create failed " , error)

                        );
            }
        });

        Button uploadButton = findViewById(R.id.upload);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadInputStream();

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent = Intent.createChooser(intent,"intent");
                startActivityForResult(intent,44);
            }
        });

    }

    private void uploadInputStream() {
        if (uri!= null) {


            try {
                InputStream exampleInputStream = getContentResolver().openInputStream(uri);

                Amplify.Storage.uploadInputStream(
                        imageName,
                        exampleInputStream,
                        result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                        storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
                );
            } catch (FileNotFoundException error) {
                Log.e("MyAmplifyApp", "Could not find file to open for input stream.", error);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String fileName = sdf.format(new Date());
        File uploadFile = new File(getApplicationContext().getFilesDir(), fileName);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            InputStream exampleInputStream = getContentResolver().openInputStream(data.getData());
            OutputStream outputStream = new FileOutputStream(uploadFile);
            imageName = data.getData().toString();
            byte[] buff = new byte[1024];
            int length;
            while ((length = exampleInputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            exampleInputStream.close();
            outputStream.close();
            Amplify.Storage.uploadFile(
                    fileName + ".jpg",
                    uploadFile,
                    result -> {
                        Log.i("MyAmplifyAppUpload", "Successfully uploaded: " + result.getKey());
                        Amplify.Storage.getUrl(result.getKey(), urlResult -> {
                            sharedPreferences.edit().putString("FileUrlForReal", urlResult.getUrl().toString()).apply();
                        }, urlError -> {
                            Log.e(TAG, "onActivityResult: Error please dont be mad");
                        });
                    },
                    storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sharePhoto() {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        ImageView image = findViewById(R.id.imageShare);
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/*")) {
                Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if (imageUri != null) {
                    image.setImageURI(imageUri);
                    image.setVisibility(View.VISIBLE);

                }
            }
        }
    }


    }

