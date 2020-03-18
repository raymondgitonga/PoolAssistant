package com.tosh.poolassistant.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tosh.poolassistant.R
import com.tosh.poolassistant.util.getSharedPreferencesValue

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val value = getSharedPreferencesValue(baseContext, "phone")

        Thread(Runnable {
            runProgressBar()

            if (value == "default") {
                startApp()
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }).start()
    }

    private fun runProgressBar() {
        var progress = 0
        while (progress < 100) {
            try {
                Thread.sleep(300)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            progress += 10
        }
    }

    private fun startApp() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
