package com.cooper.firebasesdk.module.addMeeting

import com.cooper.firebasesdk.common.Constants
import com.cooper.firebasesdk.module.addMeeting.dto.ResultData
import com.cooper.firebasesdk.utils.ConnectivityObserver
import com.cooper.firebasesdk.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject


class AddMeetingRepository @Inject constructor(
    private val database: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val connectivityObserver: ConnectivityObserver
) {

    suspend fun addData(resultData: ResultData?): Boolean? {
        if (!connectivityObserver.isConnected) {
            return null
        }
        return try {
            database.reference.child(Constants.CHILD_NAME).child(firebaseAuth.currentUser?.uid ?: "")
                .push().setValue(resultData).await()
            return true
        } catch (e: Exception) {
            null
        }
    }

    suspend fun editUser(resultData: ResultData?, id: String): Boolean? {
        if (!connectivityObserver.isConnected) {
            return null
        }
        return try {
           database.reference.child(Constants.CHILD_NAME).child(firebaseAuth.currentUser?.uid ?: "").
           child(id).updateChildren(mapOf("title" to resultData?.title, "date" to resultData?.date, "description" to resultData?.description)).await()
            return true
        } catch (e: Exception) {
            null
        }
    }
}


