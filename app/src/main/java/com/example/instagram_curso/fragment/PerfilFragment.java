package com.example.instagram_curso.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.instagram_curso.R;
import com.example.instagram_curso.activity.EditarPerfilActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class PerfilFragment extends Fragment {

    private ProgressBar progressBar;
    private CircleImageView imagePerfil;
    public GridView gridViewPerfil;
    private TextView textPublicacoes, textSeguidores, textSeguindo;
    private Button buttonEditarPerfil;

    public PerfilFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        gridViewPerfil = view.findViewById(R.id.grifViewPerfil);
        progressBar = view.findViewById(R.id.progressBarPerfil);
        imagePerfil = view.findViewById(R.id.imagePerfil);
        textPublicacoes = view.findViewById(R.id.textPublicacoes);
        textSeguidores = view.findViewById(R.id.textSeguidores);
        textSeguindo = view.findViewById(R.id.textSeguindo);
        buttonEditarPerfil = view.findViewById(R.id.buttonEditarPerfil);

        buttonEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EditarPerfilActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
}