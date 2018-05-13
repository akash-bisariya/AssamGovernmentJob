package com.assamgovernmentjob.webPage

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.webkit.*
import com.assamgovernmentjob.R
import kotlinx.android.synthetic.main.activity_web.*
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.app_bar_main.*


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class WebActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        setSupportActionBar(web_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        web_toolbar.findViewById<TextView>(R.id.tv_web_toolbar_title).text = intent.getStringExtra("text")
        pb_progress.visibility = View.VISIBLE
        web_view.settings.builtInZoomControls = true
        web_view.settings.javaScriptEnabled=true

        btn_retry.setOnClickListener {
            if (isOnline()) {
                btn_retry.visibility = View.GONE
                pb_progress.visibility = View.VISIBLE
                web_view.loadUrl(intent.getStringExtra("url"))
            } else {
                Toast.makeText(this, "Network not available, Please check internet connection.", Toast.LENGTH_SHORT).show()
                pb_progress.visibility = View.GONE
            }
        }

        web_view.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                pb_progress.visibility = View.GONE
                web_view.visibility = View.VISIBLE
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                pb_progress.visibility = View.GONE
            }
        }
        if (isOnline())
            web_view.loadUrl(intent.getStringExtra("url"))
        else {
            Toast.makeText(this, "Network not available, Please check internet connection.", Toast.LENGTH_SHORT).show()
            pb_progress.visibility = View.GONE
            btn_retry.visibility = View.VISIBLE
        }


    }

    private fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }


}
