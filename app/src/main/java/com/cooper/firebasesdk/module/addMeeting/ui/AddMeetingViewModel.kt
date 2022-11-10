package com.cooper.firebasesdk.module.addMeeting.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cooper.firebasesdk.common.BaseViewModel
import com.cooper.firebasesdk.module.addMeeting.AddMeetingRepository
import com.cooper.firebasesdk.module.addMeeting.dto.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMeetingViewModel @Inject constructor(
    private val addMeetingRepository: AddMeetingRepository
) : BaseViewModel() {

    var isEdit = MutableLiveData<Boolean>()
    var isSuccess = MutableLiveData<Boolean>()
    var refNo = ""

    fun updateEditValue() {
        isEdit.value = refNo.isNotEmpty()
    }

    fun callApi(resultData: ResultData) {
        if (resultData.title.isEmpty()) {
            showToast("Title is empty")
        } else if (resultData.date.isEmpty()) {
            showToast("Date is empty")
        } else if (resultData.description.isEmpty()) {
            showToast("Description is empty")
        } else if (isEdit.value == true) {
            editData(resultData)
        } else {
            addData(resultData)
        }
    }

    fun addData(resultData: ResultData) {

        viewModelScope.launch {

            isBusy.value = true
            val result = addMeetingRepository.addData(resultData)
            isBusy.value = false

            result?.let {
                isSuccess.value = true
            } ?: kotlin.run {
                showToast("Username or password is wrong")
            }
        }
    }

    fun editData(resultData: ResultData) {

        viewModelScope.launch {

            isBusy.value = true
            val result = addMeetingRepository.editUser(resultData, refNo)
            isBusy.value = false

            result?.let {
                isSuccess.value = true
            } ?: kotlin.run {
                showToast("Can't register")
            }
        }
    }

}