package com.deano.guiabiblioteca;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Activity responsável por exibir a lista de conteúdos da biblioteca,
 * permitindo a busca dinâmica por meio de um campo de pesquisa.
 */
public class ConteudosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ConteudoAdapter adapter;
    private ArrayList<Conteudo> lista;
    private TextView txtAvisoVazio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteudos);

        // Inicialização do RecyclerView
        recyclerView = findViewById(R.id.recyclerConteudos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lista = new ArrayList<>();

        // Recebe dados da tela anterior
        String secao = getIntent().getStringExtra("SECAO");
        String tituloTela = getIntent().getStringExtra("TITULO");

        setTitle(tituloTela);

        // Carrega os conteúdos conforme a seção selecionada
        carregarConteudos(secao);

        // Configuração do Adapter
        adapter = new ConteudoAdapter(lista, this);
        recyclerView.setAdapter(adapter);

        // Inicialização do campo de busca
        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Remove o foco para fechar o teclado
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Atualiza a lista conforme o texto digitado
                filtrarLista(newText);
                return true;
            }
        });

        txtAvisoVazio = findViewById(R.id.txtAvisoVazio);
    }

    /**
     * Carrega os conteúdos com base na seção recebida.
     */
    private void carregarConteudos(String secao) {
        int titulosId = 0;
        int textosId = 0;

        if ("faq".equals(secao)) {
            titulosId = R.array.faq_titulos;
            textosId = R.array.faq_textos;
        }

        // Obtém os dados do arquivo de recursos
        String[] titulos = getResources().getStringArray(titulosId);
        String[] textos = getResources().getStringArray(textosId);

        // Popula a lista com os conteúdos
        for (int i = 0; i < titulos.length; i++) {
            lista.add(new Conteudo(titulos[i], textos[i]));
        }
    }

    /**
     * Filtra a lista de conteúdos com base no texto digitado pelo usuário.
     */
    private void filtrarLista(String texto) {
        ArrayList<Conteudo> filtrados = new ArrayList<>();

        for (Conteudo item : lista) {
            if (item.getTitulo().toLowerCase().contains(texto.toLowerCase())) {
                filtrados.add(item);
            }
        }

        // Exibe ou oculta mensagem caso não haja resultados
        if (filtrados.isEmpty()) {
            txtAvisoVazio.setVisibility(View.VISIBLE);
        } else {
            txtAvisoVazio.setVisibility(View.GONE);
        }

        // Atualiza o adapter com a lista filtrada
        adapter.filtrar(filtrados);
    }
}