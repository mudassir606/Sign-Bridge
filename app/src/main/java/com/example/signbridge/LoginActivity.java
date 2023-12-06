package com.example.signbridge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView loginText;
    CardView login_btn,create_Btn;
    EditText email_edit, password_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        create_Btn = findViewById(R.id.signUp_btn);
        login_btn = findViewById(R.id.login_btn);
        email_edit = findViewById(R.id.Edit_Email);
        password_edit = findViewById(R.id.Edit_Password);
        loginText=findViewById(R.id.login_text_id);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_edit.getText().toString().trim();
                String password = password_edit.getText().toString().trim();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (email.isEmpty() && password.isEmpty()){
                            Toast.makeText(LoginActivity.this,"Fields are empty",Toast.LENGTH_SHORT).show();

                        }else{

                                        // Email exists in the database
                                        String savedPassword = "123456";
                                        String savedEmail = "admin123@gmail.com";

                                        // Check if the entered password matches the stored password
                                        if (email.equals(savedEmail)&&password.equals(savedPassword)) {
                                            Toast.makeText(LoginActivity.this, "User Log In", Toast.LENGTH_SHORT).show();
                                            //startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            intent.putExtra("USER_EMAIL", email);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            // Password doesn't match
                                            Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }




                },2000);

            }
        });
    }
}