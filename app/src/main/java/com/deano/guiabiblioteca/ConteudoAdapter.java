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

/**
 * Adapter responsável por gerenciar e exibir a lista de conteúdos no RecyclerView.
 * Cada item representa um conteúdo que pode ser selecionado para leitura detalhada.
 */
public class ConteudoAdapter extends RecyclerView.Adapter<ConteudoAdapter.ViewHolder> {

    private List<Conteudo> lista;
    private Context context;

    /**
     * Construtor do adapter.
     *
     * @param lista   Lista de conteúdos a serem exibidos
     * @param context Contexto da aplicação, utilizado para navegação entre telas
     */
    public ConteudoAdapter(List<Conteudo> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item da lista
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conteudo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Obtém o item atual da lista
        Conteudo conteudo = lista.get(position);

        // Define o título no item da lista
        holder.titulo.setText(conteudo.getTitulo());

        // Define ação de clique para abrir a tela de leitura
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, LeituraActivity.class);

            // Envia os dados do conteúdo para a próxima Activity
            intent.putExtra("titulo", conteudo.getTitulo());
            intent.putExtra("texto", conteudo.getTexto());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // Retorna a quantidade de itens na lista
        return lista.size();
    }

    /**
     * ViewHolder responsável por armazenar as referências dos elementos do layout.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Vincula o componente do layout ao código
            titulo = itemView.findViewById(R.id.txtTitulo);
        }
    }

    /**
     * Atualiza a lista com base no filtro aplicado pelo usuário.
     *
     * @param listaFiltrada Nova lista de conteúdos filtrados
     */
    public void filtrar(ArrayList<Conteudo> listaFiltrada) {
        this.lista = listaFiltrada;
        notifyDataSetChanged();
    }
}