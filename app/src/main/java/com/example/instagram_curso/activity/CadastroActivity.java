package com.example.instagram_curso.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.instagram_curso.R;
import com.example.instagram_curso.config.ConfiguracaoFirebase;
import com.example.instagram_curso.config.UsuarioFirebase;
import com.example.instagram_curso.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastrar;
    private ProgressBar progressBar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializarComponentes();

        progressBar.setVisibility(View.GONE);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoNome = campoNome.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if (!textoNome.isEmpty()) {
                    if (!textoEmail.isEmpty()) {
                        if (!textoSenha.isEmpty()) {
                            usuario = new Usuario();
                            usuario.setNome(textoNome);
                            usuario.setEmail(textoEmail);
                            usuario.setSenha(textoSenha);
                            cadastrar(usuario);

                        } else {
                            Toast.makeText(CadastroActivity.this, "Preencha a Senha", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CadastroActivity.this, "Preencha o email", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CadastroActivity.this, "Preencha o nome", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cadastrar(Usuario usuario) {
        progressBar.setVisibility(View.VISIBLE);
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(
                        this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    try {
                                        String idUsuario = task.getResult().getUser().getUid();
                                        usuario.setId(idUsuario);
                                        usuario.salvar();

                                        UsuarioFirebase.atualizarNomeUsuario(usuario.getNome());

                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!!", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    progressBar.setVisibility(View.GONE);

                                    String erroExcecao = "";

                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthWeakPasswordException e) {
                                        erroExcecao = "Digite uma senha mais forte!";
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        erroExcecao = "Por favor, digite um e-mail v??lido";
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        erroExcecao = "essa conta j?? foi cadastrada";
                                    } catch (Exception e) {
                                        erroExcecao = "ao cadastrar usu??rio: " + e.getMessage();
                                        e.printStackTrace();
                                    }

                                    Toast.makeText(CadastroActivity.this, "Erro " + erroExcecao, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
    }

    public void inicializarComponentes() {
        campoNome = findViewById(R.id.editCadastroNome);
        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);
        progressBar = findViewById(R.id.progressCadastro);
    }
}