package com.cooper.firebasesdk.common

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cooper.firebasesdk.utils.StringResource

abstract class BaseViewModel : ViewModel() {
    var isBusy: MutableLiveData<Boolean> = MutableLiveData()
        protected set

    var forceStopLoader: MutableLiveData<Boolean> = MutableLiveData()
        private set

    var toastMessageString: MutableLiveData<StringResource?> = MutableLiveData()
        private set

    protected fun showToast(string: String, duration: Int = Toast.LENGTH_SHORT) {
        toastMessageString.value = StringResource(string, duration)
        toastMessageString.value = null
    }
}