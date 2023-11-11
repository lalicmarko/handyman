package com.example.handyman.data.repo

import android.util.Log
import com.example.handyman.data.model.Profession
import com.example.handyman.util.await
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultProfessionRepository @Inject constructor() {

    private val ref = Firebase.firestore.collection(COLLECTION_NAME)

    val professionsFlow = flow {
        Log.d("BOBAN", "DefaultProfessionRepository -> flow started -> ")
        emit(ref.get().await().documents.map { Profession.from(it) })

        emitAll(ref.snapshots().map { snapshot ->
            Log.d("BOBAN", "DefaultProfessionRepository -> snapshot listener -> ")
            snapshot.documents.map { Profession.from(it) }
        })
    }

    companion object {
        private const val COLLECTION_NAME = "profession"
    }
}