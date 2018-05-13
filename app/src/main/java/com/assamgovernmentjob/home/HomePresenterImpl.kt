package com.assamgovernmentjob.home

/**
 * Created by akash bisariya on 11-05-2018.
 */
class HomePresenterImpl(var homeView: IHomeView?,var homeInteractor: IHomeInteracter) : IHomePresenter,IHomeInteracter.OnFinishedListener{
    override fun onResultFail(strError: String) {
        homeView!!.hideProgress()
        homeView!!.getDataFailed()
    }

    override fun onResultSuccess(categoryModel:CategoryModel?) {
        homeView!!.hideProgress()
        homeView!!.setHomeData(categoryModel)
    }

    override fun getHomeCategoryData(category: Int) {
        homeView.let {
            homeView!!.showProgress()
            homeInteractor.requestDataAPI(this,category)
        }
    }

    override fun getHomeData() {
        homeView.let {
            homeView!!.showProgress()
            homeInteractor.requestDataAPI(this,0)
        }
    }

    override fun onResultSuccess(homeModel: HomeModel?) {
        homeView!!.hideProgress()
        homeView!!.setHomeData(homeModel)
    }

    override fun onDestroy() {
        homeView=null
    }
}