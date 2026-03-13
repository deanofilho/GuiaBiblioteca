package com.deano.guiabiblioteca;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ConteudosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ConteudoAdapter adapter; //Conectar a interface
    ArrayList<Conteudo> lista;
    TextView txtAvisoVazio = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteudos);


        recyclerView = findViewById(R.id.recyclerConteudos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //Vertical

        lista = new ArrayList<>(); //Lista vazia

        //Recebendo dados da tela anterior e salvando
        String secao = getIntent().getStringExtra("SECAO");
        String tituloTela = getIntent().getStringExtra("TITULO");

        setTitle(tituloTela); //Carrega Titulo da tela
        carregarConteudos(secao); //Carrega Conteúdo da seção da tela

        //adapter salva a lista
        adapter = new ConteudoAdapter(lista, this); //Injeção de Dependência via construtor
        recyclerView.setAdapter(adapter); //conecta a interface

        // No onCreate, depois de configurar o recycler:
        SearchView searchView = findViewById(R.id.searchView); //captura e salva o xml do searchView

        //Tratando o Evento do Teclado no Buscador
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus(); // Fecha o teclado quando aperta o Enter
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtrarLista(newText); //Excuta quando a pessoa digita no teclado
                return true;
            }
        });
        txtAvisoVazio = findViewById(R.id.txtAvisoVazio);
    }

    private void carregarConteudos(String secao) {
        int titulosId = 0;
        int textosId = 0;

        if ("faq".equals(secao)){ //Capturando Arrays do String.xml
            titulosId = R.array.faq_titulos; //primeiro em negrito na ui
            textosId = R.array.faq_textos; //segundo em negrito na ui
        }

        //Convertendo Arrays xml em Arrays java
        String[] titulos = getResources().getStringArray(titulosId);
        String[] textos = getResources().getStringArray(textosId);

        //Colocando Arrays Java na lista
        for (int i = 0; i < titulos.length; i++) {
            lista.add(new Conteudo(titulos[i], textos[i]));
        }
    }

    // Método que faz a mágica da busca fora do OnCreate:
    private void filtrarLista(String texto) {
        ArrayList<Conteudo> filtrados = new ArrayList<>(); //Não altera a lista original

        for (Conteudo item : lista) {
            if (item.getTitulo().toLowerCase().contains(texto.toLowerCase())) { //Verifica na lista
                filtrados.add(item);
            }
        }

        // MÁGICA AQUI: Mostra ou esconde o aviso se não achar na busca
        if (filtrados.isEmpty()) {
            txtAvisoVazio.setVisibility(View.VISIBLE);
        } else {
            txtAvisoVazio.setVisibility(View.GONE);
        }
        //Atualizar a lista ConteudoAdapter
        adapter.filtrar(filtrados);
    }

}
