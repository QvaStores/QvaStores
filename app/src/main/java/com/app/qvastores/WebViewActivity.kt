package com.app.qvastores

import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_webview)

        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = object : WebChromeClient() {
        }
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url?.startsWith("mailto:") == true) {
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
                    startActivity(intent)
                    return true
                } else if (url?.startsWith("https://www.facebook.com/") == true) {
                    try {
                        view?.context?.packageManager?.getPackageInfo("com.facebook.katana", 0)
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        intent.setPackage("com.facebook.katana")
                        startActivity(intent)
                        return true
                    } catch (e: PackageManager.NameNotFoundException) {
                        // Aplicación de Facebook no instalada, abrir en el navegador
                        view?.loadUrl(url)
                        return true
                    }
                } else if (url?.startsWith("https://www.instagram.com/") == true) {
                    try {
                        view?.context?.packageManager?.getPackageInfo("com.instagram.android", 0)
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        intent.setPackage("com.instagram.android")
                        startActivity(intent)
                        return true
                    } catch (e: PackageManager.NameNotFoundException) {
                        // Aplicación de Instagram no instalada, abrir en el navegador
                        view?.loadUrl(url)
                        return true
                    }
                } else if (url?.startsWith("https://twitter.com/") == true) {
                    try {
                        view?.context?.packageManager?.getPackageInfo("com.twitter.android", 0)
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        intent.setPackage("com.twitter.android")
                        startActivity(intent)
                        return true
                    } catch (e: PackageManager.NameNotFoundException) {
                        // Aplicación de Twitter no instalada, abrir en el navegador
                        view?.loadUrl(url)
                        return true
                    }
                } else if (url?.startsWith("https://t.me/") == true) {
                    try {
                        view?.context?.packageManager?.getPackageInfo("org.telegram.messenger", 0)
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        intent.setPackage("org.telegram.messenger")
                        startActivity(intent)
                        return true
                    } catch (e: PackageManager.NameNotFoundException) {
                        // Aplicación de Telegram no instalada, abrir en el navegador
                        view?.loadUrl(url)
                        return true
                    }
                }

                // Otros enlaces, abrir en el WebView
                return false
            }
        }
        webView.loadUrl("https://qvastores.com")
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
