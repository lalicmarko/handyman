package com.example.handyman.data.repo

import com.example.handyman.data.model.Handyman
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultHandymanRepository @Inject constructor() {

    private val ref = Firebase.firestore.collection(COLLECTION_NAME)

    private val _handyManFlow = ref.snapshots().map { snapshot ->
        snapshot.documents.map { Handyman.from(it) }
    }
    val handyManFlow get() = _handyManFlow

    companion object {
        private const val COLLECTION_NAME = "handymen"
    }
}