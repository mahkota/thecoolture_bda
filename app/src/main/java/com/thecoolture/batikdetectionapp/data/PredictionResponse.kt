package com.thecoolture.batikdetectionapp.data

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

	@field:SerializedName("prediction")
	val prediction: List<PredictionItem>
)

data class PredictionItem(

	@field:SerializedName("probability")
	val probability: Double,

	@field:SerializedName("label")
	val label: String
)