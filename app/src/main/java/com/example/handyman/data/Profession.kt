package com.example.handyman.data

import com.google.firebase.firestore.DocumentSnapshot

data class Profession(val id: String, val name: String) {
    companion object {
        fun from(doc: DocumentSnapshot): Profession {
            return Profession(id = doc.id, name = doc.getString("name").orEmpty())
        }
    }
}
