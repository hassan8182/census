package com.census.utils.s3

interface DocumentUploadListener {
    fun onDocumentUploadSuccess(bucketPath: String?, localPath: String?, position: Int?, documentType: Int?)
    fun onDocumentUploadError(error: String?, position: Int?, documentType: Int?)
}