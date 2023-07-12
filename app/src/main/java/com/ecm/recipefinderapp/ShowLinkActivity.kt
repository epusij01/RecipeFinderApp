package com.ecm.recipefinderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class ShowLinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_link)
        var extras = intent.extras
        if (extras != null){
            var link = extras.getString("link")

            var webView = findViewById<WebView>(R.id.webViewId)
            webView.loadUrl(link.toString())
        }
    }
}