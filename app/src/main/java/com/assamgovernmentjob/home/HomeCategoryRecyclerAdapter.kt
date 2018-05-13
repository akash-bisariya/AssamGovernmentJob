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
class HomeCategoryRecyclerAdapter(private val context: Context, private val categoryModel: CategoryModel?, private val onItemClick: IOnRecycleItemClick) : RecyclerView.Adapter<HomeCategoryRecyclerAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewHolderBind(context, categoryModel as CategoryModel, onItemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.home_row_layout, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun getItemCount(): Int {
        return if (categoryModel!!.userData.catData.post_content.size > 0) categoryModel.userData.catData.post_content.size else 0
    }

    class ViewHolder(itemView: View?, private var onItemClick: IOnRecycleItemClick) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val tvUrl = itemView!!.findViewById(R.id.tv_url) as TextView
        private val tvDate = itemView!!.findViewById(R.id.tv_date) as TextView

        override fun onClick(view: View?) {
            onItemClick.onRecycleItemClick(view, adapterPosition,true)
        }

        fun viewHolderBind( context: Context, categoryModel: CategoryModel, listener: IOnRecycleItemClick) {
            onItemClick = listener
            tvUrl.text = categoryModel.userData.catData.post_content[adapterPosition].str
            tvDate.text = categoryModel.userData.catData.post_date
            itemView.setOnClickListener(this)
        }
    }
}