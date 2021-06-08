package com.thecoolture.batikdetectionapp.data

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @Headers("content-type: image/jpeg")
    @POST("predict/full")
    fun getPrediction(
        @Field("data") data: String
    ): Call<PredictionResponse>
}