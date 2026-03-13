package com.deano.guiabiblioteca;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ConteudoAdapter extends RecyclerView.Adapter<ConteudoAdapter.ViewHolder> {
    //Esse Adapter vai controlar um RecyclerView usando um ViewHolder próprio.
    private List<Conteudo> lista; //Guarda as referencias dos dados recebidos
    private Context context; //Para abrir intent, abrir activity e acessar recursos

    public ConteudoAdapter(List<Conteudo> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Converter xml em view e retorna
        View view = LayoutInflater.from(parent.getContext()) //Usar o parent.getContext() é mais seguro que context direto
                .inflate(R.layout.item_conteudo, parent, false); //Pega o XML do item_conteudo.xml
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Pegando o item da posição atual e colocando o título na TextView.
        Conteudo conteudo = lista.get(position);
        holder.titulo.setText(conteudo.getTitulo());

        // O clique que leva para a tela de leitura final LeituraActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, LeituraActivity.class);
            //Passando dados de Leitura
            intent.putExtra("titulo", conteudo.getTitulo());
            intent.putExtra("texto", conteudo.getTexto());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size(); //Informa quantos itens existem. Obrigatório para o RecyclerView
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo; //Guarda a referência do TextView

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txtTitulo); // Captura o XML com o ID
        }
    }

    //Recebe dados da ConteudosActivity quando o usuário digita na busca.
    public void filtrar(ArrayList<Conteudo> listaFiltrada) {
        //Troca a lista interna, Avisa o RecyclerView que os dados mudaram, A lista na tela é redesenhada
        this.lista = listaFiltrada; //Não altera os objetos antigos.
        notifyDataSetChanged(); // Avisa o motor da lista que os dados mudaram

    }
}