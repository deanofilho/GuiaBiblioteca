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

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<Menu> menuList; //Lista com os items criados no MenuActivity
    private Context context; //É o “ambiente” da aplicação.

    public MenuAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos o layout de cada item (item_menu.xml) transformando em view
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Menu item = menuList.get(position); //Rodar cada item da lista posição

        //Define os dados visuais titulo e icone
        holder.title.setText(item.getTitle());
        holder.icon.setImageResource(item.getIcon());

        // Tratando o Click
        holder.itemView.setOnClickListener(v -> {
            if ("solaris".equals(item.getSlug()) || "ficha_catalografica".equals(item.getSlug())) {
                String url;
                if ("solaris".equals(item.getSlug())) {
                    url = context.getString(R.string.url_solaris);
                } else {
                    url = context.getString(R.string.url_ficha_catalografica);
                }

                // Abre o Navegador Padrão do Celular
                Intent intentNavegador = new Intent(Intent.ACTION_VIEW);
                intentNavegador.setData(android.net.Uri.parse(url));
                context.startActivity(intentNavegador);

            } else if ("contato_local".equals(item.getSlug())) {
                // Criamos uma janelinha de opções
                String[] opcoes = {"Falar no WhatsApp", "Ver localização no Mapa"};

                new android.app.AlertDialog.Builder(context)
                        .setTitle("Como podemos ajudar?")
                        .setItems(opcoes, (dialog, which) -> {
                             if (which == 0) {
                                // OPÇÃO: WHATSAPP
                                String url = "https://api.whatsapp.com/send?phone=" + context.getString(R.string.whatsapp_biblioteca);
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(android.net.Uri.parse(url));
                                context.startActivity(i);
                            } else {
                                // OPÇÃO: GOOGLE MAPS
                                android.net.Uri gmmIntentUri = android.net.Uri.parse(context.getString(R.string.endereco_biblioteca));
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps"); // Garante que abre no Maps

                                // Verifica se o Maps está instalado antes de abrir
                                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                                    context.startActivity(mapIntent);
                                } else {
                                    // Se não tiver o app do Maps, abre no navegador
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, gmmIntentUri));
                                }
                            }
                        })
                        .show();
            } else if ("web_acervo".equals(item.getSlug())) {
                // Se for o acervo, abre a WebViewActivity
                Intent intent = new Intent(context, WebViewActivity.class);
                context.startActivity(intent);
            } else {
                // Se for qualquer outro é tratado na ConteudosActivity
                Intent intent = new Intent(context, ConteudosActivity.class);
                //Enviar dados a ConteudosActivity
                intent.putExtra("SECAO", item.getSlug());
                intent.putExtra("TITULO", item.getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override //Sobreescrita, Diz ao RecyclerView quantos items existem.
    public int getItemCount() {
        return menuList.size();
    }

    //Guardar referencias dos componentes da linha, titulo e icone
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iconMenu); //do item_menu.xml
            title = itemView.findViewById(R.id.titleMenu);
        }
    }
}