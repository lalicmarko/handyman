package com.example.handyman.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.handyman.data.Handyman
import com.example.handyman.data.Profession
import com.example.handyman.ui.viewmodels.auth.AuthState
import com.example.handyman.util.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.concurrent.Executors

class AuthViewModel : StateViewModel<AuthState>(AuthState()) {

    private var auth: FirebaseAuth = Firebase.auth

    private val _errorEvent: MutableSharedFlow<String?> = MutableSharedFlow()
    val errorEvent = _errorEvent.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = auth.currentUser
            if (currentUser == null) {
                Log.e(TAG, "login: user not signed")
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    Log.d(TAG, "login:success")
                    setState {
                        copy(isAuthorized = true)
                    }
                } catch (e: Exception) {
                    // If sign up fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", e)
                    setState {
                        copy(isAuthorized = false)
                    }
                    _errorEvent.emit(e.message)
                }
            } else {
                Log.i(TAG, "login: already logged.")
                pullProfessions()
                pullHandymen()
            }
        }
    }

    private suspend fun <T> pullDocuments(
        collectionPath: String,
        mapFun: (DocumentSnapshot) -> T
    ): List<T> {
        val db = Firebase.firestore
        val collectionRef = db.collection(collectionPath)
        return try {
            val result = collectionRef.get().await().documents.map { mapFun(it) }
            Log.d("BOBAN", "AuthViewModel -> pullDocuments -> success $result")
            result
        } catch (e: Exception) {
            Log.e("BOBAN", "AuthViewModel -> pullDocuments -> ", e)
            emptyList()
        }
    }

    private suspend fun pullHandymen() {
        pullDocuments("handymen") { Handyman.from(it) }
    }

    private suspend fun pullProfessions() {
        pullDocuments("profession") { Profession.from(it) }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                Log.d(TAG, "createUserWithEmail:success")
                setState {
                    copy(isAuthorized = true)
                }
            } catch (e: Exception) {
                // If sign up fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", e)
                setState {
                    copy(isAuthorized = false)
                }
                _errorEvent.emit(e.message)
            }
        }
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }
}