package com.tosh.poolassistant.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.tosh.poolassistant.R
import com.tosh.poolassistant.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        login_btn.setOnClickListener {
            val phone = phone_login.text.toString().trim()
            val password = password_login.text.toString().trim()

            if (phone.isEmpty()){
                phone_login.error = "Enter Phone"
                return@setOnClickListener
            }

            if (password.isEmpty()){
                password_login.error = "Enter password"
                return@setOnClickListener
            }

            instantiateLoginViewModel(phone, password)

        }
    }

    private fun instantiateLoginViewModel(phone:String,  password:String) {
        val mainViewModel: MainViewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        mainViewModel.userLogin(phone, password).observe(this, Observer {
            if (it == "successful"){
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val contextView: View = findViewById(R.id.loginLayout)
                val snackbar = Snackbar
                    .make(contextView, it, Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        })
    }
}
