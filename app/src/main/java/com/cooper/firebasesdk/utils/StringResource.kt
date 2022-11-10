package com.cooper.firebasesdk.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

class StringResource(@StringRes private val resource: Int, val duration: Int = Toast.LENGTH_SHORT) {
    private var string: String = ""
    private var isFromResource: Boolean = true

    constructor(string: String, duration: Int = Toast.LENGTH_SHORT) : this(-1, duration) {
        this.string = string
        isFromResource = false
    }

    fun getContent(context: Context): String {
        return if (isFromResource)
            context.getString(resource)
        else
            string
    }
}