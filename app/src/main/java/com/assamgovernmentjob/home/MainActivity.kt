package com.assamgovernmentjob.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.assamgovernmentjob.R
import com.assamgovernmentjob.constants.AppConstants
import com.assamgovernmentjob.webPage.WebActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.provider.Settings.Secure



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, IHomeView, IOnRecycleItemClick {
    lateinit var categoryModelData: CategoryModel
    lateinit var homeModelData: HomeModel
    override fun onRecycleItemClick(view: View?, position: Int, isCategory: Boolean) {
        val intent = Intent(this@MainActivity, WebActivity::class.java)
        if (isCategory) {
            intent.putExtra("url", categoryModelData.userData.catData.post_content[position].href)
            intent.putExtra("text", categoryModelData.userData.catData.post_content[position].str)
        } else {
            intent.putExtra("url", homeModelData.homeData.links[position].href)
            intent.putExtra("text", homeModelData.homeData.links[position].str)
        }
        startActivity(intent)
    }

    private val homePresenterImpl = HomePresenterImpl(this, HomeInteracterImpl())
    override fun showProgress() {
        pb_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pb_progress.visibility = View.GONE
    }

    override fun setHomeData(homeModel: HomeModel?) {
        homeModelData = homeModel!!
        pb_progress.visibility = View.GONE
        rv_home.layoutManager = LinearLayoutManager(this)
        rv_home.adapter = HomeRecyclerAdapter(this, homeModel, this)
    }

    override fun setHomeData(categoryModel: CategoryModel?) {
        categoryModelData = categoryModel as CategoryModel
        pb_progress.visibility = View.GONE
        rv_home.layoutManager = LinearLayoutManager(this)
        rv_home.adapter = HomeCategoryRecyclerAdapter(this, categoryModel, this)
    }

    override fun getDataFailed() {
        pb_progress.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Home"
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        onNavigationItemSelected(nav_view.menu.getItem(0))
        val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val sharedPreferences = getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("deviceId", androidId)
        editor.apply()


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
                homePresenterImpl.getHomeCategoryData(0)
                supportActionBar?.title = item.title
                item.isChecked = true
            }
            R.id.nav_latest_jobs -> {
                homePresenterImpl.getHomeCategoryData(AppConstants.Latest_Jobs)
                supportActionBar?.title = item.title
                item.isChecked = true
            }
            R.id.nav_latest_admit_card -> {
                homePresenterImpl.getHomeCategoryData(AppConstants.Admit_Card)
                supportActionBar?.title = item.title
                item.isChecked = true
            }
            R.id.nav_all_india_job -> {
                homePresenterImpl.getHomeCategoryData(AppConstants.All_India_Jobs)
                supportActionBar?.title = item.title
                item.isChecked = true
            }
            R.id.nav_answer_key -> {
                homePresenterImpl.getHomeCategoryData(AppConstants.Answer_Key)
                supportActionBar?.title = item.title
                item.isChecked = true
            }
            R.id.nav_latest_result -> {
                homePresenterImpl.getHomeCategoryData(AppConstants.Latest_Results)
                supportActionBar?.title = item.title
                item.isChecked = true
            }
            R.id.nav_syllabus -> {
                homePresenterImpl.getHomeCategoryData(AppConstants.Syllabus)
                supportActionBar?.title = item.title
                item.isChecked = true
            }
            R.id.nav_important -> {
                homePresenterImpl.getHomeCategoryData(AppConstants.Important)
                supportActionBar?.title = item.title
                item.isChecked = true
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
