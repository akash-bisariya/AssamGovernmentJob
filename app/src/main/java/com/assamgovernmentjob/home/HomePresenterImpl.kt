package com.assamgovernmentjob.home

/**
 * Created by akash bisariya on 11-05-2018.
 */
class HomePresenterImpl(var homeView: IHomeView?) : IHomePresenter{
    private val mHomeInteractor:IHomeInteracter = HomeInteracterImpl(this)
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
            mHomeInteractor.requestDataAPI(category,"","")
        }
    }

    override fun getHomeData(deviceId:String,token:String) {
        homeView.let {
            homeView!!.showProgress()
            mHomeInteractor.requestDataAPI(0,deviceId,token)
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