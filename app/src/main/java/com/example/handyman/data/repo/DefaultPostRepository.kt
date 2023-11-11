package com.example.handyman.data.repo

import com.example.handyman.data.model.Post
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPostRepository @Inject constructor() {

    private val ref = Firebase.firestore.collection(COLLECTION_NAME)

    private val _postsFlow = ref.snapshots().map { snapshot ->
        snapshot.documents.map { Post.from(it) }
    }

    val postsFlow get() = _postsFlow

    companion object {
        private const val TAG = "PostRepository"
        private const val COLLECTION_NAME = "posts"
    }
}