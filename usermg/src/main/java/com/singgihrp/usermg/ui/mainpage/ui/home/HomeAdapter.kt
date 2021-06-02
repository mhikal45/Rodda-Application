package com.singgihrp.usermg.ui.mainpage.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singgihrp.usermg.R
import com.singgihrp.usermg.databinding.ListReportBinding
import com.singgihrp.usermg.entity.Resulys

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    private val listItem = ArrayList<Resulys>()

    fun setData(items: ArrayList<Resulys>) {
        listItem.clear()
        listItem.addAll(items)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = ListReportBinding.bind(itemView)

        fun bind(item: Resulys) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_report, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = listItem.size
}