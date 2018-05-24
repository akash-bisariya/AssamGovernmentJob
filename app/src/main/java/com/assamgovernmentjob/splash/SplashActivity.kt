package com.assamgovernmentjob.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.assamgovernmentjob.R
import com.assamgovernmentjob.home.MainActivity

class SplashActivity : AppCompatActivity() {
    lateinit var mHandler:Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mHandler= Handler(Handler.Callback { p0 ->
            if(p0!!.what==101) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
            true
        })
        mHandler.sendEmptyMessageDelayed(101,2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeMessages(101)
    }

    override fun onStop() {
        super.onStop()
        mHandler.removeMessages(101)
    }
}
