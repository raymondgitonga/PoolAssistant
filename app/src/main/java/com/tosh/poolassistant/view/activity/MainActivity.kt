package com.tosh.poolassistant.view.activity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.tosh.poolassistant.R
import com.tosh.poolassistant.util.Constants.SHARED_PHONE
import com.tosh.poolassistant.view.fragment.DashBoardFragment
import com.tosh.poolassistant.view.fragment.ProfileFragment
import com.tosh.poolassistant.viewmodel.MainViewModel

class MainActivity() : AppCompatActivity() {

    private val dashboardFragment = DashBoardFragment()
    private var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        createFormFragment()
    }

    private fun createFormFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.details_fragment, dashboardFragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.drawer_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Handle item selection
        return when (item.itemId) {
            R.id.logout_navigation -> {
                logout()
                true
            }
            R.id.account_navigation -> {
                val fragmentProfile = ProfileFragment()
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.details_fragment, fragmentProfile)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    private fun logout() {
        val settings = PreferenceManager.getDefaultSharedPreferences(this)
        settings.edit().remove(SHARED_PHONE).apply()
        mainViewModel!!.deleteUser()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
