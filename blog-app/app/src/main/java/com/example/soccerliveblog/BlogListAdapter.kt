package com.example.soccerliveblog

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class BlogListAdapter : RecyclerView.Adapter<BlogListAdapter.ViewHolder>() {

    private var blogList = ArrayList<BlogPostModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(blogList[position])

    override fun getItemCount(): Int = blogList.size

    fun addItem(blogItem:BlogPostModel){
        blogList.add(0,blogItem)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val time: TextView = itemView.findViewById(R.id.time)
        private val currentActivity: TextView = itemView.findViewById(R.id.currentActivity)

        fun bind(currentValue: BlogPostModel) = with(itemView) {
            time.text = currentValue.time
            currentActivity.text = currentValue.currentActivity
        }
    }

}