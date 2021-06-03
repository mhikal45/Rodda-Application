package com.rodda.roddaapplication.ui.dataform

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataFormViewModel : ViewModel() {

    private val _fullName = MutableLiveData<String>()
    private val _location = MutableLiveData<String>()
    private val _time = MutableLiveData<String>()
    private val _image = MutableLiveData<List<String>>()


    fun postReport (fullName : String,location : String,time : String,image : List<String>) {
        val client = com.rodda.roddaapplication.model.network.ApiConfig()
            .getApiServices().postReport(fullName,location,time,image)
        client.enqueue(object : Callback<com.rodda.roddaapplication.model.response.ReportResponse> {
            override fun onResponse(
                call: Call<com.rodda.roddaapplication.model.response.ReportResponse>,
                response: Response<com.rodda.roddaapplication.model.response.ReportResponse>
            ) {
                if (response.isSuccessful) {
                    _fullName.value = response.body()?.fullName
                    _location.value = response.body()?.location
                    _time.value = response.body()?.time
                    _image.value = response.body()?.images
                }
            }

            override fun onFailure(call: Call<com.rodda.roddaapplication.model.response.ReportResponse>, t: Throwable) {
                Log.d ("OnFailure :",t.message.toString())
            }

        })
    }
}