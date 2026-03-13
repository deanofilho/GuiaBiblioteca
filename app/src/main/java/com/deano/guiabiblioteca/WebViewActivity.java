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

public class WebViewActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private LinearLayout layoutErro;
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        // 1. Inicialização das Views
        progressBar = findViewById(R.id.progressBar);
        layoutErro = findViewById(R.id.layoutErro);
        myWebView = findViewById(R.id.webview);

        // 2. Configurações da WebView
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (request.isForMainFrame()) {
                    layoutErro.setVisibility(View.VISIBLE);
                    myWebView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                layoutErro.setVisibility(View.GONE);
                myWebView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
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
                view.evaluateJavascript(scriptLimpeza, null);
            }
        });

        // 3. Barra de Progresso
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

        myWebView.loadUrl("https://portal.ufnt.edu.br/biblioteca/pesquisa/pesquisar.action");
        setTitle("Pesquisar no Acervo");

        // 4. Botão Tentar Novamente
        findViewById(R.id.btnTentarNovamente).setOnClickListener(v -> myWebView.reload());

        // 5. Novo padrão de botão voltar (Dispatcher)
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (myWebView.canGoBack()) {
                    myWebView.goBack();
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        // 6. Seta de voltar na ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Retorna para o menu do App
        return true;
    }
}