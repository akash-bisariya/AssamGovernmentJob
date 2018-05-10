package com.assamgovernmentjob.home

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.assamgovernmentjob.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener , IHomeView{
    override fun showProgress() {
        pb_progress.visibility= View.VISIBLE
    }

    override fun hideProgress() {
        pb_progress.visibility=View.GONE
    }

    override fun setHomeData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDataFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
            }
            R.id.nav_assam_government_job -> {

            }
            R.id.nav_all_india_job -> {

            }
            R.id.nav_answer_key -> {

            }
            R.id.nav_latest_result -> {

            }
            R.id.nav_syllabus -> {

            }
            R.id.nav_important -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
