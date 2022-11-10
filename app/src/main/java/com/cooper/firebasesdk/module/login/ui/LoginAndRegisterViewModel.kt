package com.cooper.firebasesdk.module.login.ui

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cooper.firebasesdk.common.BaseViewModel
import com.cooper.firebasesdk.module.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginAndRegisterViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    var isLogin = MutableLiveData<Boolean>()

    var isSuccess = MutableLiveData<Boolean>()

    fun doLogin(userName: String, password: String) {

        if (validateEmailAndPassword(userName, password)) {
            viewModelScope.launch {

                isBusy.value = true
                val result = loginRepository.doLogin(userName, password)
                isBusy.value = false

                result?.let {
                    isSuccess.value = true
                } ?: kotlin.run {
                    showToast("Username or password is wrong")
                }
            }
        }
    }

    fun registerUser(userName: String, password: String) {

        if (validateEmailAndPassword(userName, password)) {
            viewModelScope.launch {

                isBusy.value = true
                val result = loginRepository.registerUser(userName, password)
                isBusy.value = false

                result?.let {
                    showToast("Account registered successfully")
                    isSuccess.value = true
                } ?: kotlin.run {
                    showToast("Can't register")
                }
            }
        }
    }

    private fun validateEmailAndPassword(userName: String, password: String): Boolean {
        if (TextUtils.isEmpty(userName)) {
            showToast("Enter email address!")
            return false
        } else if (TextUtils.isEmpty(password)) {
            showToast("Enter password!")
            return false
        } else if (password.length < 6) {
            showToast("Should be greater than 6")
            return false
        }
        return true
    }

}