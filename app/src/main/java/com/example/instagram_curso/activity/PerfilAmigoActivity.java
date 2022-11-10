package com.example.instagram_curso.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram_curso.R;
import com.example.instagram_curso.config.ConfiguracaoFirebase;
import com.example.instagram_curso.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilAmigoActivity extends AppCompatActivity {

    private Usuario usuarioSelecionado;
    private Button buttonAcaoPerfil;
    private TextView textNome;
    private CircleImageView imagePerfil;
    private DatabaseReference usuarioRef;
    private DatabaseReference usuarioAmigoRef;
    private ValueEventListener valueEventListenerPerfilAmigo;
    private TextView textPublicacoes, textSeguidores, textSeguindo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_amigo);

        usuarioRef = ConfiguracaoFirebase.getFirebaseDatabase().child("usuarios");

        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbarPerfilUser);
        toolbar.setTitle("Perfil");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            usuarioSelecionado = (Usuario) bundle.getSerializable("usuarioSelecionado");

            getSupportActionBar().setTitle("@" + usuarioSelecionado.getNome());
            textNome.setText(usuarioSelecionado.getNome());
            String caminhoFoto = usuarioSelecionado.getCaminhoFoto();
            if (caminhoFoto != null) {
                Uri url = Uri.parse(caminhoFoto);
                Glide.with(PerfilAmigoActivity.this).load(url).into(imagePerfil);

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarDadosPerfilAmigo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioAmigoRef.removeEventListener(valueEventListenerPerfilAmigo);
    }

    private void recuperarDadosPerfilAmigo() {

        usuarioAmigoRef = usuarioRef.child(usuarioSelecionado.getId());
        valueEventListenerPerfilAmigo = usuarioAmigoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);

                String postagens = String.valueOf (usuario.getPostagens());
                String seguindo = String.valueOf(usuario.getSeguindo());
                String seguidores = String.valueOf(usuario.getSeguidores());

                textPublicacoes.setText(postagens);
                textSeguidores.setText(seguidores);
                textSeguindo.setText(seguindo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void inicializarComponentes() {
        buttonAcaoPerfil = findViewById(R.id.buttonAcaoPerfil);
        buttonAcaoPerfil.setText("Seguir");
        imagePerfil = findViewById(R.id.imagePerfil);
        textNome = findViewById(R.id.textNome);
        textPublicacoes = findViewById(R.id.textPublicacoes);
        textSeguidores = findViewById(R.id.textSeguidores);
        textSeguindo = findViewById(R.id.textSeguindo);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}