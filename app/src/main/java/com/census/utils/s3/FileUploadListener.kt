package com.census.utils.s3

interface FileUploadListener {
    fun onFileUploadSuccess(path: String?, type: String)
    fun onFileUploadProgressChange(progress: Int, type: String?)
    fun onFileUploadError(error: String?, type: String?)
}