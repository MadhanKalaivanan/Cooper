package com.cooper.firebasesdk.module.addMeeting.dto

import java.io.Serializable

data class ResultData(var title: String = "",
                      var date: String = "",
                      var description: String = ""): Serializable
