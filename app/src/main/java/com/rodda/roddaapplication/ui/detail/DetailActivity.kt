package com.rodda.roddaapplication.ui.detail

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.rodda.roddaapplication.R
import com.rodda.roddaapplication.databinding.ActivityDetailBinding
import com.rodda.roddaapplication.model.ResultModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: DetailAdapter
    private lateinit var firestore: FirebaseFirestore

    companion object{
        const val IMG_ID = "img_id"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()


        setUpRecyclerView()
    }

    fun setUpRecyclerView() {
        val imgID:String = intent.getStringExtra(IMG_ID).toString()
        val query : Query = firestore.collection("results").whereArrayContains("images", imgID)
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<ResultModel> = FirestoreRecyclerOptions.Builder<ResultModel>()
            .setQuery(query, ResultModel::class.java)
            .build()

        adapter = DetailAdapter(firestoreRecyclerOptions)
        binding.rvDetailImage.setHasFixedSize(true)
        binding.rvDetailImage.layoutManager = GridLayoutManager(this, 3)
        binding.rvDetailImage.adapter = adapter
    }

}