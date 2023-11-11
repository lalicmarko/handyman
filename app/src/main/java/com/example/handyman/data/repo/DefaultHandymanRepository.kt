package com.example.handyman.data.repo

import android.util.Log
import com.example.handyman.data.model.Handyman
import com.example.handyman.util.await
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultHandymanRepository @Inject constructor() {

    private val _handyManFlow = flow {
        Log.d("BOBAN", "DefaultHandymanRepository -> flow started -> ")
        emit(ref.get().await().documents.map { Handyman.from(it) })

        emitAll(ref.snapshots().map { snapshot ->
            Log.d("BOBAN", "DefaultHandymanRepository -> snapshot listener -> ")
            snapshot.documents.map { Handyman.from(it) }
        })
    }

    private val ref = Firebase.firestore.collection(COLLECTION_NAME)

    val handyManFlow get() = _handyManFlow

    companion object {
        private const val COLLECTION_NAME = "handymen"
    }
}