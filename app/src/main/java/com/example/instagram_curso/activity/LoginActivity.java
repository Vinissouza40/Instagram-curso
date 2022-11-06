package com.example.instagram_curso.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.instagram_curso.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmailLogin, editSenhaLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void abrirCadastro(View view){

        Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(i);
    }
}