package com.cooper.firebasesdk.module.listing.adapter

import com.cooper.firebasesdk.module.addMeeting.dto.ResultData

interface ClickListener {
    fun rowClickListener(resultData: ResultData? = null, id: String? = null)
}