package com.deano.guiabiblioteca;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity responsável por exibir o site de busca do acervo da biblioteca
 * dentro de uma WebView, com tratamento de carregamento, erros e navegação.
 */
public class WebViewActivity extends AppCompatActivity {

    private ProgressBar progressBar; // Indica o progresso de carregamento da página
    private LinearLayout layoutErro; // Layout exibido em caso de erro de carregamento
    private WebView myWebView; // Componente que renderiza o conteúdo web

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        // 1. Inicialização das Views do layout
        progressBar = findViewById(R.id.progressBar);
        layoutErro = findViewById(R.id.layoutErro);
        myWebView = findViewById(R.id.webview);

        // 2. Configuração da WebView para suportar páginas modernas
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Permite execução de JavaScript
        webSettings.setDomStorageEnabled(true); // Habilita armazenamento local (HTML5)

        // Define comportamento de navegação e eventos da WebView
        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                // Trata erro apenas da página principal (evita interferência de recursos internos)
                if (request.isForMainFrame()) {
                    layoutErro.setVisibility(View.VISIBLE);
                    myWebView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // Esconde erro e garante exibição da WebView ao iniciar carregamento
                layoutErro.setVisibility(View.GONE);
                myWebView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                // Script JavaScript para remover elementos indesejados da página original
                String scriptLimpeza = "javascript:(function() { " +
                        "function esconder(selector, isId) { " +
                        "   var el = isId ? document.getElementById(selector) : document.getElementsByClassName(selector)[0]; " +
                        "   if (el) { el.style.display = 'none'; el.style.height = '0'; } " +
                        "} " +
                        "esconder('instituicao', true);" +
                        "esconder('botaoDownloadAcervo', true);" +
                        "esconder('label', true);" +
                        "esconder('rodape', true);" +
                        "esconder('menu-botao', false);" +
                        "})();";

                // Executa o script após o carregamento da página
                view.evaluateJavascript(scriptLimpeza, null);
            }
        });

        // 3. Controle da barra de progresso durante o carregamento
        myWebView.setWebChromeClient(new android.webkit.WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        // Carrega a página do sistema de busca da biblioteca
        myWebView.loadUrl("https://portal.ufnt.edu.br/biblioteca/pesquisa/pesquisar.action");
        setTitle("Pesquisar no Acervo");

        // 4. Botão "Tentar Novamente" em caso de erro
        findViewById(R.id.btnTentarNovamente).setOnClickListener(v -> myWebView.reload());

        // 5. Controle do botão voltar usando o Dispatcher moderno
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (myWebView.canGoBack()) {
                    // Navega para a página anterior dentro da WebView
                    myWebView.goBack();
                } else {
                    // Sai da Activity caso não haja histórico
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        // 6. Ativa botão de voltar na barra superior (ActionBar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Retorna para o menu principal do aplicativo
        return true;
    }
}