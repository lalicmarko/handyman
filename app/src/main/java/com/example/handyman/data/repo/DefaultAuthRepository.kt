package com.example.handyman.data.repo

import android.util.Log
import com.example.handyman.util.await
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAuthRepository @Inject constructor() {

    private val auth = Firebase.auth

    suspend fun login(email: String, password: String): Result<String> {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.e(TAG, "login: user not signed")
            return try {
                auth.signInWithEmailAndPassword(email, password).await()
                Log.d(TAG, "login:success")
                Result.success("Success.")
            } catch (e: Exception) {
                // If sign up fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", e)
                Result.failure(e)
            }
        } else {
            Log.i(TAG, "login: already logged.")
            return Result.success("Already logged")
        }
    }

    suspend fun signUp(email: String, password: String): Result<String> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Log.d(TAG, "createUserWithEmail:success")
            Result.success("Success")
        } catch (e: Exception) {
            // If sign up fails, display a message to the user.
            Log.w(TAG, "createUserWithEmail:failure", e)
            Result.failure(e)
        }
    }

    fun getLoggedUser(): String? = auth.currentUser?.email

    fun isLogged(): Boolean = auth.currentUser != null

    companion object {
        private const val TAG = "DefaultAuthRepository"
    }
}