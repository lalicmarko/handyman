package com.example.handyman.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

data class Post(
    val id: String,
    val title: String,
    val description: String,
    val dateCreated: Timestamp?,
    val imgBase64: String,
    val author: String
) {
    companion object {
        fun from(doc: DocumentSnapshot): Post {
            return Post(
                id = doc.id,
                author = doc.getString("author").orEmpty(),
                title = doc.getString("title").orEmpty(),
                description = doc.getString("description").orEmpty(),
                dateCreated = doc.getTimestamp("dateCreated"),
                imgBase64 = doc.getString("imgBase64").orEmpty(),
            )
        }
    }
}
