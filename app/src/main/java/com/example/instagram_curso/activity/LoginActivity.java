package com.example.instagram_curso.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagram_curso.R;
import com.example.instagram_curso.config.ConfiguracaoFirebase;
import com.example.instagram_curso.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button botaoEntrar;
    private ProgressBar progressBar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        verificarUsuarioLogado();
        inicializarComponentes();

        progressBar.setVisibility(View.GONE);
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();


                if (!textoEmail.isEmpty()) {
                    if (!textoSenha.isEmpty()) {

                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);
                        validarLogin(usuario);

                    } else {
                        Toast.makeText(LoginActivity.this, "Preencha a Senha", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Preencha o Email", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    public void validarLogin(Usuario usuario) {
        progressBar.setVisibility(View.VISIBLE);
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Erro ao Fazer Login", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public void abrirCadastro(View view) {

        Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(i);
    }

    public void inicializarComponentes() {

        campoEmail = findViewById(R.id.editLoginEmail);
        campoSenha = findViewById(R.id.editLoginSenha);
        botaoEntrar = findViewById(R.id.buttonLoginEntrar);
        progressBar = findViewById(R.id.progressLogin);

        campoEmail.requestFocus();

    }
}