package com.example.sekolahkuapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    AppCompatEditText etuserName , etPassword ;
    AppCompatButton btnLogin ;
    int chance = 3 ;

    private  void login(){
        String userName = etuserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (userName.equals("admin") && password.equals("admin")){
            Intent intent = new Intent(LoginActivity.this , ListMainActivity.class);
            startActivity(intent);
        } else {
            chance --;
            if (chance == 0){
                Toast.makeText(this, "Kesempatan Anda Habis", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Password dan username Salah", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etuserName = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }
}
