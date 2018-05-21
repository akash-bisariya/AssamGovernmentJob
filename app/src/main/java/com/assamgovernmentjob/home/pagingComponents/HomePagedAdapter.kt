package com.assamgovernmentjob.home.pagingComponents

import android.arch.paging.PagedListAdapter
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.assamgovernmentjob.R
import com.assamgovernmentjob.home.HomeData
import com.assamgovernmentjob.home.IOnRecycleItemClick
import com.assamgovernmentjob.home.Link
import kotlinx.android.synthetic.main.home_row_layout.view.*

/**
 * Created by akash
 * on 18/5/18.
 */
class HomePagedAdapter(private val onItemClick: IOnRecycleItemClick) : PagedListAdapter<Link, HomePagedAdapter.HomeViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.create(parent, onItemClick)
    }

    class HomeViewHolder(private var view: View, var onItemClick: IOnRecycleItemClick) : RecyclerView.ViewHolder(view), View.OnClickListener {
        override fun onClick(p0: View?) {
            onItemClick.onRecycleItemClick(view, adapterPosition, false)
        }

        fun bindTo(link: Link?) {
            itemView.tv_url.text = link!!.str
            itemView.setOnClickListener(this)
        }

        companion object {
            fun create(parent: ViewGroup, onItemClick: IOnRecycleItemClick): HomeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.home_row_layout, parent, false)
                return HomeViewHolder(view, onItemClick)
            }
        }

    }
}

object DIFF_CALLBACK : DiffCallback<Link>() {
    override fun areItemsTheSame(oldItem: Link, newItem: Link): Boolean {
        return oldItem.href == newItem.href
    }

    override fun areContentsTheSame(oldItem: Link, newItem: Link): Boolean {
        return oldItem == newItem
    }
}
