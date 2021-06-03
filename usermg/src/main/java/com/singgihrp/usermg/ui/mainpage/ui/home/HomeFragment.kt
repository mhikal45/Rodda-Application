package com.singgihrp.usermg.ui.mainpage.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.singgihrp.usermg.databinding.FragmentHomeBinding
import com.singgihrp.usermg.entity.ResultModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    private var adapter: ResultAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseFirestore = FirebaseFirestore.getInstance()

        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        val query : Query = firebaseFirestore.collection("results").orderBy("waktu", Query.Direction.DESCENDING)
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<ResultModel> = FirestoreRecyclerOptions.Builder<ResultModel>()
            .setQuery(query, ResultModel::class.java)
            .build()

        adapter = ResultAdapter((firestoreRecyclerOptions))
        binding.rvListResult.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        binding.rvListResult.setHasFixedSize(true)
        binding.rvListResult.layoutManager = LinearLayoutManager(activity)
        binding.rvListResult.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter!!.stopListening()
    }
}

