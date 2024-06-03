package com.census.ui.base.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiError {
    var errorCode: Int
        private set

    @Expose
    @SerializedName("message")
    var message: String?
        private set

    @Expose
    @SerializedName("status_code")
    var statusCode: String?
        private set

    constructor(errorCode: Int, statusCode: String?, message: String?) {
        this.errorCode = errorCode
        this.statusCode = statusCode
        this.message = message
    }

    constructor(errorCode: Int, message: String?) {
        this.errorCode = errorCode
        statusCode = ""
        this.message = message
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val apiError = other as ApiError
        if (errorCode != apiError.errorCode) {
            return false
        }
        if (if (statusCode != null) statusCode != apiError.statusCode else apiError.statusCode != null) {
            return false
        }
        return if (message != null) message == apiError.message else apiError.message == null
    }

    override fun hashCode(): Int {
        var result = errorCode
        result = 31 * result + if (statusCode != null) statusCode.hashCode() else 0
        result = 31 * result + if (message != null) message.hashCode() else 0
        return result
    }
}
