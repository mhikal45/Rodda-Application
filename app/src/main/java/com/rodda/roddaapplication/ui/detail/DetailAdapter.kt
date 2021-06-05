package com.rodda.roddaapplication.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.rodda.roddaapplication.R
import com.rodda.roddaapplication.databinding.ListDetailGridBinding
import com.rodda.roddaapplication.model.ResultModel

class DetailAdapter (options: FirestoreRecyclerOptions<ResultModel>) :
FirestoreRecyclerAdapter<ResultModel, DetailAdapter.DetailViewHolder>(options){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_detail_grid, parent, false)
        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int, model: ResultModel) {
        holder.bind(model)
    }

    inner class DetailViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListDetailGridBinding.bind(itemView)

        fun bind(result: ResultModel){
            with(binding) {
                Glide.with(itemView.context)
                    .load(result.images)
                    .into(imgDetailPhoto)
                tvPredictions.text = result.predictions.toString()
                tvPredictionsAcc.text = result.predictions_acc.toString()
            }
        }
    }

}