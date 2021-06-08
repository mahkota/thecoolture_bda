package com.thecoolture.batikdetectionapp

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thecoolture.batikdetectionapp.data.ApiConfig
import com.thecoolture.batikdetectionapp.data.PredictionItem
import com.thecoolture.batikdetectionapp.data.PredictionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class MainActivityViewModel : ViewModel() {

    private val _predictionResult = MutableLiveData<List<PredictionItem>>()
    val predictionResult: LiveData<List<PredictionItem>> = _predictionResult

    private val _onProcess = MutableLiveData<Int>()
    val onProcess: LiveData<Int> = _onProcess

    var errorDetails = ""

    companion object{
        private const val TAG = "MainActivityViewModel"
    }

    fun runPrediction(imageString: String, path: File) {
        var endResult: String? = ""

        Log.d("TAG", "MainActivity: $path")

        val file = File(path, "imstring.txt")
        val stream = FileOutputStream(file)
        stream.use { stream ->
            stream.write(imageString.toByteArray())
        }

        val client = ApiConfig.getApiService().getPrediction(imageString)
        client.enqueue(object : Callback<PredictionResponse> {
            init {
                _onProcess.value = 0
            }

            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body()?.prediction?.sortedByDescending { it.probability }}")
                    _onProcess.value = 1
                    _predictionResult.value = response.body()?.prediction?.sortedByDescending { it.probability }
                    endResult = response.body()?.prediction?.sortedByDescending { it.probability }?.get(0)?.label

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    errorDetails = response.message()
                    _onProcess.value = 2
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                errorDetails = t.message.toString()
                _onProcess.value = 2
            }
        })
    }
}