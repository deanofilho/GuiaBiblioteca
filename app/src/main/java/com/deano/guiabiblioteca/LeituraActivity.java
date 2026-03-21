package com.deano.guiabiblioteca;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity responsável por exibir o conteúdo completo selecionado pelo usuário,
 * incluindo título e texto formatado em HTML.
 */
public class LeituraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitura);

        TextView titulo = findViewById(R.id.txtTitulo);
        TextView texto = findViewById(R.id.txtTexto);

        // Recupera os dados enviados pela Activity anterior
        String tituloRecebido = getIntent().getStringExtra("titulo");
        String textoRecebido = getIntent().getStringExtra("texto");

        // Define o título do conteúdo
        titulo.setText(tituloRecebido);

        // Aplica formatação HTML ao texto (suporte a <b>, <br>, etc.)
        if (textoRecebido != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                texto.setText(Html.fromHtml(textoRecebido, Html.FROM_HTML_MODE_COMPACT));
            } else {
                texto.setText(Html.fromHtml(textoRecebido));
            }
        }

        // Ativa o botão de navegação (voltar) na barra superior
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Finaliza a Activity e retorna para a anterior
        finish();
        return true;
    }
}