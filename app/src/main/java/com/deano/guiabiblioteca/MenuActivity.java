package com.deano.guiabiblioteca;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Activity responsável por exibir o menu principal do aplicativo,
 * contendo as principais funcionalidades disponíveis ao usuário.
 */
public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Inicializa o RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adiciona separador entre os itens da lista
        DividerItemDecoration divider =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        // Lista de opções do menu
        List<Menu> listaCategorias = new ArrayList<>();

        // Item: Pesquisa no acervo
        listaCategorias.add(new Menu(
                "Pesquisar no Acervo",
                android.R.drawable.ic_menu_search,
                "web_acervo"
        ));

        // Item: FAQ
        listaCategorias.add(new Menu(
                getString(R.string.secao_faq),
                android.R.drawable.ic_menu_help,
                "faq"
        ));

        // Item: Repositório
        listaCategorias.add(new Menu(
                "Repositório Solaris",
                android.R.drawable.star_on,
                "solaris"
        ));

        // Item: Ficha catalográfica
        listaCategorias.add(new Menu(
                "Ficha Catalográfica",
                android.R.drawable.ic_menu_agenda,
                "ficha_catalografica"
        ));

        // Item: Contato
        listaCategorias.add(new Menu(
                "Contatos e Localização",
                android.R.drawable.ic_menu_directions,
                "contato_local"
        ));

        // Configura o adapter para exibir os itens no RecyclerView
        MenuAdapter adapter = new MenuAdapter(this, listaCategorias);
        recyclerView.setAdapter(adapter);
    }
}