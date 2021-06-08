package com.thecoolture.batikdetectionapp.ui.detail

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.thecoolture.batikdetectionapp.data.BatikDetailEntity
import com.thecoolture.batikdetectionapp.data.database.History
import com.thecoolture.batikdetectionapp.data.database.HistoryRoomDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScannedBatikDetailViewModel : ViewModel() {
    private val _batikDetail = MutableLiveData<BatikDetailEntity>()
    val batikDetail: LiveData<BatikDetailEntity> = _batikDetail

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    private var batikName: String = ""

    companion object {
        private const val TAG = "BatikDetailViewModel"
    }

    init {
        getBatikDetail()
    }

    fun setBatikName(name: String) {
        batikName = name
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insert(name: String, application: Application) {
        val dbDao = HistoryRoomDatabase.getDatabase(application)
        val mHistoryDao = dbDao.historyDao()
        val currentDateTime = LocalDateTime.now()

        executorService.execute {
            mHistoryDao.insert(
                History(
                    batikName = name,
                    scanDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                )
            )
        }
    }

    private fun getBatikDetail() {
        val db = Firebase.firestore

        db.collection("info_batik")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "getBatikDetail: ${document.id} => ${document.data}")
                    Log.d(TAG, "getBatikDetail: Received batik name: $batikName")

                    if (document.data["name"].toString() == batikName) {
                        Log.d(TAG, "getBatikDetail: Preferred batik found!")

                        _batikDetail.value = BatikDetailEntity(
                            document.data["name"].toString(),
                            document.data["origin"].toString(),
                            document.data["meaning"].toString(),
                            document.data["short_desc"]?.toString(),
                            document.data["history"]?.toString()
                        )
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}