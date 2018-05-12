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

    override fun getHomeData(category: Int) {
        homeView.let {
            homeView!!.showProgress()
            homeInteractor.requestDataAPI(this,category)
        }
    }

    override fun onDestroy() {
        homeView=null
    }
}