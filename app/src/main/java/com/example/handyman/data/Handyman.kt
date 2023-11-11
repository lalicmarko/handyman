package com.example.handyman.data

import android.util.Log
import com.example.handyman.util.orZero
import com.google.firebase.firestore.DocumentSnapshot

data class Handyman(
    val id: String,
    val name: String,
    val surname: String,
    val rating: Int,
    val professions: List<String>
) {
    companion object {
        fun from(doc: DocumentSnapshot): Handyman {
            return Handyman(
                id = doc.id,
                name = doc.getString("name").orEmpty(),
                surname = doc.getString("surname").orEmpty(),
                rating = doc.getLong("rating")?.toInt().orZero(),
                professions = (doc.get("professions") as? ArrayList<String>).orEmpty()
            )
        }
    }
}