package com.thecoolture.batikdetectionapp.ui.store

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.thecoolture.batikdetectionapp.data.StoreEntity

class StoreViewModel : ViewModel() {
    private val _store = MutableLiveData<ArrayList<StoreEntity>>()
    val store: LiveData<ArrayList<StoreEntity>> = _store

/*
    private val _retrieveError = MutableLiveData<Int>()
    val retrieveError: LiveData<Int> = _retrieveError
*/

    companion object {
        private const val TAG = "StoreViewModel"
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text


    init {
        getFirebase()

    }

    private fun getFirebase() {
        val db = Firebase.firestore
        val firestoreResult = ArrayList<StoreEntity>()

        db.collection("tokobatik")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    Log.d(TAG, "getStoreData: ${document.data["batiks"]}")
                    Log.d(TAG, "getStoreData: ${document.data["nama_toko"]}")
                    Log.d(TAG, "getStoreData: ${document.data["region"]}")
//                    getResult.add(document.data["batiks"], document.data["nama_toko"] ,document.data["region"])

                    firestoreResult.add(
                        StoreEntity(
                            document.data["batiks"] as List<String>?,
                            document.data["nama_toko"].toString(),
                            document.data["region"].toString(),
                            document.data["location"] as GeoPoint
                        )
                    )

                    Log.d(TAG, "getStoreData: ${firestoreResult.toString()}")
                    _store.value = firestoreResult
                }

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

//        Log.d(TAG, "getStoreData: ${a.toString()}")
        Log.d(TAG, "getStoreData: ${firestoreResult.toString()}")
    }
}