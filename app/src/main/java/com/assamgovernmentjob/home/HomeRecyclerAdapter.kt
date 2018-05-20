package com.assamgovernmentjob.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.assamgovernmentjob.R

/**
 * Created by akash bisariya on 13-02-2018.
 */
class HomeRecyclerAdapter(private val context: Context, private val homeModel: HomeModel?, private val onItemClick: IOnRecycleItemClick) : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewHolderBind(context, homeModel as HomeModel, onItemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.home_row_layout, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun getItemCount(): Int {
        return if (homeModel!!.homeData.links.isNotEmpty()) homeModel.homeData.links.size else 0
    }

    class ViewHolder(itemView: View?, private var onItemClick: IOnRecycleItemClick) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val tvUrl = itemView!!.findViewById(R.id.tv_url) as TextView
        override fun onClick(view: View?) {
            onItemClick.onRecycleItemClick(view, adapterPosition,false)
        }

        fun viewHolderBind( context: Context, homeModel: HomeModel?, listener: IOnRecycleItemClick) {
            onItemClick = listener
            tvUrl.text = homeModel!!.homeData.links[adapterPosition].str
//            tvDate.text = ""
            itemView.setOnClickListener(this)
        }
    }
}