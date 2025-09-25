package com.example.homeworkproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    Button register_button,login_button,forgot_pass_button;
    EditText editTextPassword,editTextUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        editTextPassword=findViewById(R.id.enterpassword);
        editTextUsername=findViewById(R.id.enterusername);
        register_button = findViewById(R.id.register);
        login_button=findViewById(R.id.login);
        forgot_pass_button=findViewById(R.id.forgotpassbutton);
        register_button.setOnClickListener(view->{
            try{
                Intent intent=new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
       forgot_pass_button.setOnClickListener(view->{
            try{
                Intent intent=new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(intent);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String username, password;
                username = String.valueOf(editTextUsername.getText());
                password = String.valueOf(editTextPassword.getText());
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(Login.this, "Enter username", Toast.LENGTH_SHORT).show();
                    return;
                }
                DatabaseHelper databaseHelper = new DatabaseHelper(Login.this);
                int login_nr = databaseHelper.login(username, password);
                if (login_nr == -1) {
                    Toast.makeText(Login.this, "Unsuccessful login", Toast.LENGTH_SHORT).show();
                    return;
                } else if (login_nr == 0) {
                    try {
                        Intent intent = new Intent(getApplicationContext(), MenuAttendee.class);
                        intent.putExtra("username", username); //put the username
                        startActivity(intent);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (login_nr == 1)
                {
                    try{
                        Intent intent=new Intent(getApplicationContext(),MenuRepresentative.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });
    }
}