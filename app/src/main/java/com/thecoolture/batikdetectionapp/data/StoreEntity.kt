package com.thecoolture.batikdetectionapp.data

import com.google.firebase.firestore.GeoPoint

data class StoreEntity(
    var batiks: List<String>?,
    var storeName: String,
    var region: String,
    var location: GeoPoint
)
