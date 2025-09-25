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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ForgotPassword extends AppCompatActivity {
    EditText Username,New_password;
    DatabaseHelper db=new DatabaseHelper(this);
    Button Submit;
    FloatingActionButton GoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        Username = findViewById(R.id.username);
        New_password = findViewById(R.id.new_password);
        Submit = findViewById(R.id.submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, new_password;
                username = String.valueOf(Username.getText());
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(ForgotPassword.this,"Enter username",Toast.LENGTH_SHORT).show();
                    return;
                }
                new_password = String.valueOf(New_password.getText());
                if (TextUtils.isEmpty(new_password)){
                    Toast.makeText(ForgotPassword.this,"Enter new password",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    if(new_password.length()<11)
                    {
                        Toast.makeText(ForgotPassword.this,"Password must be at least 12 characters and should have at least a digit",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        String regex = ".*[0-9].*";
                        if (!new_password.matches(regex))
                        {
                            Toast.makeText(ForgotPassword.this,"Password should contain at least a digit",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                if (!db.checkUsername(username)) {
                    db.updatePassword(username, new_password);
                    Username.setText("");
                    New_password.setText("");
                    try{
                        Intent intent=new Intent(getApplicationContext(),Login.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(ForgotPassword.this, "Incorrect username", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

}
