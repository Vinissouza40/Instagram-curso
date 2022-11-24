package com.example.instagram_curso.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram_curso.R;
import com.example.instagram_curso.model.Postagem;
import com.example.instagram_curso.model.Usuario;

import de.hdodenhof.circleimageview.CircleImageView;

public class VisualizarPostagemActivity extends AppCompatActivity {

    private TextView textPerfilPostagem, textQtdCurtidasPostagem, textDescricaoPostagem, textVisualizarComentariosPostagem;
    private ImageView imagePostagemSelecionada;
    private CircleImageView imagePerfilPostagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_postagem);

        Toolbar toolbar = findViewById(R.id.toolbarVisualizarPostagens);
        toolbar.setTitle("Visualizar Postagens");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        inicializarComponentes();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Postagem postagem = (Postagem) bundle.getSerializable("postagem");
            Usuario usuario = (Usuario) bundle.getSerializable("usuario");

            Uri uri = Uri.parse(usuario.getCaminhoFoto());
            Glide.with(VisualizarPostagemActivity.this).load(uri).into(imagePerfilPostagem);
            textPerfilPostagem.setText(usuario.getNome());

            Uri uriPostagem = Uri.parse(postagem.getCaminhoFoto());
            Glide.with(VisualizarPostagemActivity.this).load(uriPostagem).into(imagePostagemSelecionada);
            textDescricaoPostagem.setText(postagem.getDescricao());
        }
    }

    private void inicializarComponentes() {
        textPerfilPostagem = findViewById(R.id.textPerfilPostagem);
        textQtdCurtidasPostagem = findViewById(R.id.textQtdCurtidasPostagem);
        textDescricaoPostagem = findViewById(R.id.textDescricaoPostagem);
        textVisualizarComentariosPostagem = findViewById(R.id.textVisualizarComentatriosPostagem);
        imagePostagemSelecionada = findViewById(R.id.imagePostagemSelecionada);
        imagePerfilPostagem = findViewById(R.id.imagePerfilPostagem);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}