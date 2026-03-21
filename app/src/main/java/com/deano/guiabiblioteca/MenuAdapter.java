package com.deano.guiabiblioteca;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter responsável por exibir o menu principal
 * e gerenciar as ações de navegação do aplicativo.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<Menu> menuList; // Lista de itens do menu definidos na MenuActivity
    private Context context; // Contexto utilizado para navegação e acesso a recursos

    public MenuAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout de cada item do menu (item_menu.xml)
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Obtém o item correspondente à posição atual
        Menu item = menuList.get(position);

        // Define os dados visuais do item (título e ícone)
        holder.title.setText(item.getTitle());
        holder.icon.setImageResource(item.getIcon());

        // Trata o clique no item e define a navegação correspondente
        holder.itemView.setOnClickListener(v -> {
            if ("solaris".equals(item.getSlug()) || "ficha_catalografica".equals(item.getSlug())) {
                String url;
                if ("solaris".equals(item.getSlug())) {
                    url = context.getString(R.string.url_solaris);
                } else {
                    url = context.getString(R.string.url_ficha_catalografica);
                }

                // Abre o navegador padrão com a URL correspondente
                Intent intentNavegador = new Intent(Intent.ACTION_VIEW);
                intentNavegador.setData(android.net.Uri.parse(url));
                context.startActivity(intentNavegador);

            } else if ("contato_local".equals(item.getSlug())) {
                // Exibe um diálogo com opções de contato e localização
                String[] opcoes = {"Falar no WhatsApp", "Ver localização no Mapa"};

                new android.app.AlertDialog.Builder(context)
                        .setTitle("Como podemos ajudar?")
                        .setItems(opcoes, (dialog, which) -> {
                            if (which == 0) {
                                // Abre conversa no WhatsApp com o número da biblioteca
                                String url = "https://api.whatsapp.com/send?phone=" + context.getString(R.string.whatsapp_biblioteca);
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(android.net.Uri.parse(url));
                                context.startActivity(i);
                            } else {
                                // Abre a localização da biblioteca no Google Maps
                                android.net.Uri gmmIntentUri = android.net.Uri.parse(context.getString(R.string.endereco_biblioteca));
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps"); // Garante abertura no app Maps

                                // Verifica se o Google Maps está disponível antes de abrir
                                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                                    context.startActivity(mapIntent);
                                } else {
                                    // Caso não esteja disponível, abre a localização no navegador
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, gmmIntentUri));
                                }
                            }
                        })
                        .show();
            } else if ("web_acervo".equals(item.getSlug())) {
                // Abre a tela interna com WebView para consulta ao acervo
                Intent intent = new Intent(context, WebViewActivity.class);
                context.startActivity(intent);
            } else {
                // Redireciona para a tela de conteúdos com base na seção selecionada
                Intent intent = new Intent(context, ConteudosActivity.class);

                // Envia os dados necessários para a próxima Activity
                intent.putExtra("SECAO", item.getSlug());
                intent.putExtra("TITULO", item.getTitle());

                context.startActivity(intent);
            }
        });
    }

    @Override // Retorna a quantidade total de itens no menu
    public int getItemCount() {
        return menuList.size();
    }

    /**
     * ViewHolder responsável por armazenar as referências
     * dos elementos visuais de cada item do menu.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Vincula os componentes do layout aos elementos da View
            icon = itemView.findViewById(R.id.iconMenu);
            title = itemView.findViewById(R.id.titleMenu);
        }
    }
}