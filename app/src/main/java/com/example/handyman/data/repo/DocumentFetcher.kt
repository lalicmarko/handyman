package com.example.handyman.data.repo

import android.util.Log
import com.example.handyman.util.await
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

object DocumentFetcher {

    suspend fun <T> pullDocuments(
        collectionPath: String,
        mapFun: (DocumentSnapshot) -> T
    ): List<T> {
        val db = Firebase.firestore
        val collectionRef = db.collection(collectionPath)
        return try {
            val result = collectionRef.get().await().documents.map { mapFun(it) }
            Log.d("BOBAN", "DocumentFetcher -> pullDocuments -> success")
            result
        } catch (e: Exception) {
            Log.e("BOBAN", "DocumentFetcher -> pullDocuments -> error", e)
            emptyList()
        }
    }
}