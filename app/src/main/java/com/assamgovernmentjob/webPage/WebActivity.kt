package com.assamgovernmentjob.webPage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.*
import com.assamgovernmentjob.R
import kotlinx.android.synthetic.main.activity_web.*
import android.widget.TextView
import android.widget.Toast
import com.assamgovernmentjob.utils.Utils
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class WebActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        setSupportActionBar(web_toolbar)
        MobileAds.initialize(this, getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        adView_web_activity.loadAd(adRequest)
        web_toolbar.findViewById<TextView>(R.id.tv_web_toolbar_title).text = intent.getStringExtra("text")
        pb_progress_web.visibility = View.VISIBLE
        web_view.settings.builtInZoomControls = true
        web_view.settings.javaScriptEnabled = true

        btn_retry.setOnClickListener {
            if (Utils.isOnline(this@WebActivity)) {
                rl_retry.visibility = View.GONE
                pb_progress_web.visibility = View.VISIBLE
                web_view.loadUrl(intent.getStringExtra("url"))
            } else {
                Toast.makeText(this, getString(R.string.txt_network_error_message), Toast.LENGTH_SHORT).show()
                pb_progress_web.visibility = View.GONE
            }
        }
        iv_back.setOnClickListener({
            onBackPressed()
        })

        web_view.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                pb_progress_web.visibility = View.GONE
                web_view.visibility = View.VISIBLE
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                pb_progress_web.visibility = View.GONE
            }
        }
        if (Utils.isOnline(this@WebActivity))
            web_view.loadUrl(intent.getStringExtra("url"))
        else {
            Toast.makeText(this, getString(R.string.txt_network_error_message), Toast.LENGTH_SHORT).show()
            pb_progress_web.visibility = View.GONE
            rl_retry.visibility = View.VISIBLE
        }
    }
}
