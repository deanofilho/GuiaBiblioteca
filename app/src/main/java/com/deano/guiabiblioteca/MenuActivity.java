package com.deano.guiabiblioteca;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //tudo sobre a tela
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        RecyclerView recyclerView = findViewById(R.id.recyclerMenu); //capturei a tela com esse ID
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //Layout Geraciador será Vertical

        // Cria a linha divisória para separa itens
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        // Aplica a linha no componente xml do RecyclerView
        recyclerView.addItemDecoration(divider);

        //Lista que vai guardar os itens da classe menu, não da tela.
        List<Menu> listaCategorias = new ArrayList<>();

        //Construtor do Menu
        listaCategorias.add(new Menu( //nome, icone, indentificador
                "Pesquisar no Acervo",
                android.R.drawable.ic_menu_search,
                "web_acervo" // Slug único para identificar o clique para o ConteudoActivity e MenuAdapter
        ));

        listaCategorias.add(new Menu(
                getString(R.string.secao_faq),
                android.R.drawable.ic_menu_help, // Ícone de interrogação padrão do Android
                "faq"
        ));

        listaCategorias.add(new Menu(
                "Repositório Solaris",
                android.R.drawable.star_on,
                "solaris" //ID para o MenuAdapter tratar
        ));
        listaCategorias.add(new Menu(
                "Ficha Catalográfica",
                android.R.drawable.ic_menu_agenda,
                "ficha_catalografica" //ID para o MenuAdapter tratar
        ));

        listaCategorias.add(new Menu(
                "Contatos e Localização",
                android.R.drawable.ic_menu_directions,
                "contato_local" //MenuAdapter
        ));

        //Ligar tudo RecyclerView, lista de dados, Adapter
        //MenuAdapter recebe os dados da lista, Cria os layouts de cada item, Controla clique
        MenuAdapter adapter = new MenuAdapter(this, listaCategorias);
        recyclerView.setAdapter(adapter); //MenuAdapter é o intermediário
    }
    //1 - Activity abre
    //2 - Carrega layout XML
    //3 - Pega o RecyclerView
    //4 - Define que será lista vertical
    //5 - Adiciona linhas divisórias
    //6 - Cria lista com itens
    //7 - Envia lista para o Adapter
    //8 - Adapter mostra na tela
}