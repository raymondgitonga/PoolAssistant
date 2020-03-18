package com.tosh.poolassistant.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.tosh.poolassistant.R
import com.tosh.poolassistant.util.Constants.SHARED_PHONE
import com.tosh.poolassistant.view.fragment.DashBoardFragment
import com.tosh.poolassistant.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity() : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val dashboardFragment = DashBoardFragment()
    private var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        createFormFragment()
        setupDrawer()
        loadUserDetails()
    }

    private fun createFormFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.details_fragment, dashboardFragment)
        transaction.commit()
    }

    private fun setupDrawer(){
        setSupportActionBar(toolBar)
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, drawerLayout, toolBar,
            R.string.open_drawer,
            R.string.close_drawer
        ){}
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.account_navigation -> {

            }
            R.id.status_navigation -> {

            }
            R.id.order_history_navigation -> {
                Toast.makeText(this, "Share clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.logout_navigation -> {
                logout()
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

    private fun loadUserDetails() {
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0)
        val navName = headerView.findViewById<View>(R.id.navigation_name) as TextView
        val navEmail = headerView.findViewById<View>(R.id.navigation_email) as TextView

        mainViewModel!!.getUserDetails().observe(this, Observer { userEntities ->
            for (i in userEntities.indices) {
                navName.text = userEntities[i].name
                navEmail.text = userEntities[i].phone
            }
        })

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
