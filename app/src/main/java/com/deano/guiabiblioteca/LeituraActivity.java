package com.deano.guiabiblioteca;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.text.Html;
import androidx.appcompat.app.AppCompatActivity;

public class LeituraActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_leitura);

            TextView titulo = findViewById(R.id.txtTitulo);
            TextView texto = findViewById(R.id.txtTexto);

            // 1. Pegamos os dados vindos da Intent
            String tituloRecebido = getIntent().getStringExtra("titulo");
            String textoRecebido = getIntent().getStringExtra("texto");

            // 2. Definimos o título normalmente
            titulo.setText(tituloRecebido);

            // 3. Aplicamos a formatação HTML no conteúdo (para reconhecer <b> e <br>)
            // Dentro do onCreate da LeituraActivity
            if (textoRecebido != null) {
                // O segredo está aqui: o texto agora vem com as tags reais
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    texto.setText(Html.fromHtml(textoRecebido, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    texto.setText(Html.fromHtml(textoRecebido));
                }
            }

            // Ativa o botão de voltar na barra superior
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        @Override
        public boolean onSupportNavigateUp() {
            finish();
            return true;
        }
    }