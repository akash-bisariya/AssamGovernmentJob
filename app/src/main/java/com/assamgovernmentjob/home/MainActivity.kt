package com.assamgovernmentjob.home

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.assamgovernmentjob.R
import com.assamgovernmentjob.constants.AppConstants
import com.assamgovernmentjob.home.model.CategoryModel
import com.assamgovernmentjob.home.model.HomeModel
import com.assamgovernmentjob.home.model.Link
import com.assamgovernmentjob.home.pagingComponents.HomePagedAdapter
import com.assamgovernmentjob.home.pagingComponents.HomeViewModel
import com.assamgovernmentjob.home.pagingComponents.ViewModelFactory
import com.assamgovernmentjob.utils.Utils
import com.assamgovernmentjob.webPage.WebActivity
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, IHomeView, IOnRecycleItemClick {
    private var categoryModelData: CategoryModel? = null
    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userAdapter: HomePagedAdapter
    private val homePresenterImpl: IHomePresenter = HomePresenterImpl(this)

    @SuppressLint("LogNotTimber")
    override fun onRecycleItemClick(view: View?, position: Int, isCategory: Boolean) {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
            mInterstitialAd.loadAd(AdRequest.Builder().build())
            getToWepPage(position, isCategory)
        }

        mInterstitialAd.adListener = object : AdListener() {

            override fun onAdClosed() {
                super.onAdClosed()
                getToWepPage(position, isCategory)
            }
        }
    }

    override fun showProgress() {
        pb_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pb_progress.visibility = View.GONE
        if (Utils.isOnline(this@MainActivity)) {
            rl_retry.visibility = View.GONE
        } else {
            if (categoryModelData == null && userAdapter.currentList == null) {
                rl_retry.visibility = View.VISIBLE
                Toast.makeText(this, getString(R.string.txt_network_error_message), Toast.LENGTH_SHORT).show()
            } else {
                if (userAdapter.currentList!!.size <= 0) {
                    rl_retry.visibility = View.VISIBLE
                    Toast.makeText(this, getString(R.string.txt_network_error_message), Toast.LENGTH_SHORT).show()
                } else
                    rl_retry.visibility = View.GONE
            }
        }
    }

    override fun setHomeData(homeModel: HomeModel?) {
    }

    fun getToWepPage(position: Int, isCategory: Boolean) {
        val intent = Intent(this@MainActivity, WebActivity::class.java)
        if (isCategory) {
            intent.putExtra("url", categoryModelData!!.userData.catData.post_content[position].href)
            intent.putExtra("text", categoryModelData!!.userData.catData.post_content[position].str)
        } else {
            intent.putExtra("url", userAdapter.currentList!![position]?.href)
            intent.putExtra("text", userAdapter.currentList!![position]?.str)
        }
        startActivity(intent)
    }


    override fun onResume() {
        super.onResume()
        if (Utils.isOnline(this@MainActivity)) {
            rl_retry.visibility = View.GONE
        } else {
            if (categoryModelData == null && userAdapter.currentList == null) {
                rl_retry.visibility = View.VISIBLE
            } else {
                if (userAdapter.currentList!!.size <= 0) {
                    rl_retry.visibility = View.VISIBLE
                } else
                    rl_retry.visibility = View.GONE
            }
        }
    }

    override fun setHomeData(categoryModel: CategoryModel?) {
        categoryModelData = categoryModel as CategoryModel
        pb_progress.visibility = View.GONE
        rv_home.layoutManager = LinearLayoutManager(this)
        rv_home.adapter = HomeCategoryRecyclerAdapter(this, categoryModel, this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("onNewIntent", "onNewIntent")
        homeViewModel.refresh()
    }

    override fun getDataFailed() {
        pb_progress.visibility = View.GONE
        if (categoryModelData == null && userAdapter.currentList == null)
            rl_retry.visibility = View.VISIBLE
        Toast.makeText(this, getString(R.string.txt_network_error_message), Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeAds()
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Home"
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val sharedPreferences = getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("deviceId", androidId)
        editor.apply()
        nav_view.setNavigationItemSelectedListener(this)
        homeViewModel = ViewModelProviders.of(this@MainActivity, ViewModelFactory(this.application)).get(HomeViewModel::class.java)
        initAdapter()
        onNavigationItemSelected(nav_view.menu.getItem(0))
        if (Utils.isOnline(this)) pb_progress.visibility = View.VISIBLE
        btn_retry_home.setOnClickListener({
            if (Utils.isOnline(this)) {
                onNavigationItemSelected(nav_view.menu.getItem(0))
                rl_retry.visibility = View.GONE
            } else {
                Toast.makeText(this, getString(R.string.txt_network_error_message), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initializeAds() {
        MobileAds.initialize(this, getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        adView_home_activity.loadAd(adRequest)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.txt_interstitial_ad_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun initAdapter() {
        rv_home.layoutManager = LinearLayoutManager(this)
        userAdapter = HomePagedAdapter(this)
        rv_home.adapter = userAdapter
        homeViewModel.homeDataList.observe(this, Observer<PagedList<Link>> {
            userAdapter.submitList(it)
            Handler().postDelayed({
                pb_progress.visibility = View.GONE
            }, 1000)

        })

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                if (Utils.isOnline(this))
                    pb_progress.visibility = View.VISIBLE
                initAdapter()
                if (homeViewModel.homeDataList.value != null && Utils.isOnline(this))
                    homeViewModel.refresh()
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
            R.id.nav_privacy_policy, R.id.nav_about_us -> {
                item.isChecked = true
                val intent = Intent(this@MainActivity, WebActivity::class.java)
                if (item.itemId == R.id.nav_about_us) {
                    intent.putExtra("url", getString(R.string.txt_about_us_url))
                    intent.putExtra("text", getString(R.string.txt_about_us))
                } else {
                    intent.putExtra("url", getString(R.string.txt_privacy_url))
                    intent.putExtra("text", getString(R.string.txt_privacy_policy))
                }
                startActivity(intent)
            }
            R.id.nav_share -> {
                val share = Intent(Intent.ACTION_SEND)
                share.type = "text/plain"
                share.putExtra(Intent.EXTRA_TEXT, getString(R.string.txt_share_text))
                startActivity(Intent.createChooser(share, getString(R.string.txt_share_title)))
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
