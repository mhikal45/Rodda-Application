package com.rodda.roddaapplication.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.rodda.roddaapplication.MainActivity
import com.rodda.roddaapplication.R
import com.rodda.roddaapplication.databinding.ActivityDetailTrialBinding
import com.rodda.roddaapplication.model.ResultModel

class DetailTrialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTrialBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var docID: String

    companion object{
        const val DOC_ID = "doc_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTrialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        docID = intent.getStringExtra(DOC_ID).toString()
        Log.d("Doc ID", "Doc Id nya = $docID")

        val documentReference = firestore.collection("results").document(docID)
        documentReference.get()
            .addOnSuccessListener {
                Log.d("SUCCESS", "${it.getString("images")}")
                binding.locationTVV.text = it.getString("location")

            }.addOnFailureListener{
                Log.e("FAIL", it.toString())
            }
    }
}