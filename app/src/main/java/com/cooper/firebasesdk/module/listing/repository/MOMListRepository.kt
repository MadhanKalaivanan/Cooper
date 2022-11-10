package com.cooper.firebasesdk.module.listing.repository

import com.cooper.firebasesdk.utils.ConnectivityObserver
import com.cooper.firebasesdk.utils.await
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject


class LoginRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val connectivityObserver: ConnectivityObserver
) {

    suspend fun doLogin(userName: String, password: String): AuthResult? {
        if (!connectivityObserver.isConnected) {
            return null
        }
        return try {
            auth.signInWithEmailAndPassword(userName, password).await()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun registerUser(userName: String, password: String): AuthResult? {
        if (!connectivityObserver.isConnected) {
            return null
        }
        return try {
            auth.createUserWithEmailAndPassword(userName, password).await()
        } catch (e: Exception) {
            null
        }
    }
}


