package com.rodda.detailmodule.model.network

import com.rodda.detailmodule.model.response.ReportResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiServices {
    @FormUrlEncoded
    @POST("predict")
    fun postReport (
        @Field("fullName") fullName : String,
        @Field("location") location : String,
        @Field("time") time : String,
        @Field("images") images : List<String>
    ) : Call<ReportResponse>
}