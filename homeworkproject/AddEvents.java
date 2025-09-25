package com.example.homeworkproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.metrics.Event;
import android.graphics.ImageDecoder;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddEvents extends AppCompatActivity {
    EditText Location,Description,Name,Date;
    Button Create;
    FloatingActionButton Upload,Back;
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    getContentResolver().takePersistableUriPermission(
                            imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }
    );



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        Location=findViewById(R.id.eventlocation);
        Description=findViewById(R.id.eventdescription);
        Name=findViewById(R.id.eventname);
        Date=findViewById(R.id.eventdate);
        Upload=findViewById(R.id.upload_image);
        Create=findViewById(R.id.create);
        Back=findViewById(R.id.go_back);
        String username = getIntent().getStringExtra("username"); //Retrieve the username
        Back.setOnClickListener(view->{
            Intent intent = new Intent(getApplicationContext(), MenuRepresentative.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
        Upload.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");
            imagePickerLauncher.launch(Intent.createChooser(intent, "Select Event Flyer"));
        });
        Create.setOnClickListener(view -> {
            String name = Name.getText().toString();
            String location = Location.getText().toString();
            String description = Description.getText().toString();
            String date = Date.getText().toString();
            if (TextUtils.isEmpty(name)){
                Toast.makeText(AddEvents.this,"Enter name",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(location)){
                Toast.makeText(AddEvents.this,"Enter location",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(date)){
                Toast.makeText(AddEvents.this,"Enter date",Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                String regex="(0[1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[0,1,2])\\/(19|20)\\d{2}";
                if (!date.matches(regex))
                {
                    Toast.makeText(AddEvents.this,"Date should have the format DD/MM/YYYY",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
            if (TextUtils.isEmpty(description)){
                Toast.makeText(AddEvents.this,"Enter description",Toast.LENGTH_SHORT).show();
                return;
            }
            else
                if(description.length()>200) {
                Toast.makeText(AddEvents.this,"Description should be less than 100 characters",Toast.LENGTH_SHORT).show();
                return;
            }
            if (imageUri == null) {
                Toast.makeText(this, "You must select an image first", Toast.LENGTH_SHORT).show();
                return;
            }
            String imagePath = imageUri.toString();
            EventDatabaseHelper db =new EventDatabaseHelper(this);
            db.addEvent(name,username,date,location,description,imagePath);
            Location.setText("");
            Name.setText("");
            Description.setText("");
            Date.setText("");
        });



    }
}
