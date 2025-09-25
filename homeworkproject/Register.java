package com.example.homeworkproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class Register extends AppCompatActivity {
    EditText editTextEmail,editTextPassword,editTextUsername,editTextOrg;
    private static final int PICK_FILE_REQUEST_CODE = 123;
    Button buttonReg;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        editTextEmail=findViewById(R.id.enteremail);
        editTextPassword=findViewById(R.id.enterpassword);
        editTextUsername=findViewById(R.id.enterusername);
        editTextOrg=findViewById(R.id.enterorg);
        buttonReg=findViewById(R.id.button);
        radioGroup = findViewById(R.id.radioGroup);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password,username,organization = null;
                DatabaseHelper databaseHelper = new DatabaseHelper(Register.this);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String roleText = selectedRadioButton.getText().toString();

                int role=0;
                if(roleText.equals("Event representative"))
                {
                    role=1;
                    organization=String.valueOf(editTextOrg.getText());
                    if (TextUtils.isEmpty(organization)){
                        Toast.makeText(Register.this,"Enter organization",Toast.LENGTH_SHORT);
                        return;
                    }
                }
                email=String.valueOf(editTextEmail.getText());
                password=String.valueOf(editTextPassword.getText());
                username=String.valueOf(editTextUsername.getText());
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"Enter email",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    String regex="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
                    if (!email.matches(regex))
                    {
                        Toast.makeText(Register.this,"Incorrect email format",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this,"Enter password",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    if(password.length()<11)
                    {
                        Toast.makeText(Register.this,"Password must be at least 12 characters and should have at least a digit",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        String regex = ".*[0-9].*";
                        if (!password.matches(regex))
                        {
                            Toast.makeText(Register.this,"Password should contain at least a digit",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(Register.this,"Enter username",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    if(databaseHelper.checkUsername(username))
                    {
                        Toast.makeText(Register.this,"Username is already taken",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                databaseHelper.register(username,email,password,role,organization);
                Toast.makeText(Register.this,"Account was succesfully created",Toast.LENGTH_SHORT).show();
                editTextUsername.setText("");
                editTextPassword.setText("");
                editTextEmail.setText("");
                editTextOrg.setText("");
                try{
                    Intent intent=new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButton2) {
                editTextOrg.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.radioButton) {
                editTextOrg.setVisibility(View.GONE);
            }
        });



    }
}