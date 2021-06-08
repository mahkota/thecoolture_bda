package com.thecoolture.batikdetectionapp.ui.history

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thecoolture.batikdetectionapp.data.database.History
import com.thecoolture.batikdetectionapp.data.database.HistoryDao
import com.thecoolture.batikdetectionapp.data.database.HistoryRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryViewModel(application: Application) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is history Fragment"
    }
    val text: LiveData<String> = _text

    private val _history = MutableLiveData<List<History>>()
    var history: LiveData<List<History>>

    private val mHistoryDao: HistoryDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = HistoryRoomDatabase.getDatabase(application)
        mHistoryDao = db.historyDao()
        history = mHistoryDao.getAllHistory()
    }
}