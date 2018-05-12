package com.assamgovernmentjob.home

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.assamgovernmentjob.R
import com.assamgovernmentjob.constants.AppConstants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener , IHomeView , IOnRecycleItemClick{
    override fun onRecycleItemClick(view: View?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val homePresenterImpl=HomePresenterImpl(this,HomeInteracterImpl())
    override fun showProgress() {
        pb_progress.visibility= View.VISIBLE
    }

    override fun hideProgress() {
        pb_progress.visibility=View.GONE
    }

    override fun setHomeData(categoryModel:CategoryModel?) {
        pb_progress.visibility= View.GONE
        rv_home.layoutManager=LinearLayoutManager(this)
        rv_home.adapter=HomeRecyclerAdapter(this,categoryModel ,this)
//        val category: CategoryModel =responseBody as CategoryModel
//        category.userData.catData.post_content[0]


    }

    override fun getDataFailed() {
        pb_progress.visibility=View.GONE
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
                homePresenterImpl.getHomeData(0)
            }
            R.id.nav_latest_jobs -> {
                homePresenterImpl.getHomeData(AppConstants.Latest_Jobs)
            }
            R.id.nav_latest_admit_card -> {
                homePresenterImpl.getHomeData(AppConstants.Admit_Card)
            }
            R.id.nav_all_india_job -> {
                homePresenterImpl.getHomeData(AppConstants.All_India_Jobs)
            }
            R.id.nav_answer_key -> {
                homePresenterImpl.getHomeData(AppConstants.Answer_Key)
            }
            R.id.nav_latest_result -> {
                homePresenterImpl.getHomeData(AppConstants.Latest_Results)


            }
            R.id.nav_syllabus -> {
                homePresenterImpl.getHomeData(AppConstants.Syllabus)
            }
            R.id.nav_important -> {
                homePresenterImpl.getHomeData(AppConstants.Important)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
