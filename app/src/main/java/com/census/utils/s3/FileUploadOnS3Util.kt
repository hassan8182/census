package com.census.utils.s3

import android.content.Context
import android.util.Log
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.census.R
import com.census.data.local.prefs.SecureSharedPreference
import java.io.File

class FileUploadOnS3Util {

    companion object {
        const val TAG = "FileUploadOnS3Util"
    }

    private var context: Context? = null
    private var filePath: String? = null
    private var type: String? = null
    private var position: Int? = null
    private var documentType: Int? = null

    constructor(context: Context, filePath: String, position: Int, documentType: Int) {
        this.context = context
        this.filePath = filePath
        this.position = position
        this.documentType = documentType
    }

    constructor(context: Context, filePath: String, type: String) {
        this.context = context
        this.filePath = filePath
        this.type = type
    }

    private var fileListener: FileUploadListener? = null
    private var documentListener: DocumentUploadListener? = null

    fun setFileListener(listener: FileUploadListener?) {
        this.fileListener = listener
    }

    fun setDocumentListener(listener: DocumentUploadListener?) {
        this.documentListener = listener
    }

    fun upload() {


        val fileTransferUtil = FileTransferUtil()
        val file = File(filePath)

        val secureSharedPreference = SecureSharedPreference(context!!)
        val transferUtility = fileTransferUtil.getTransferUtility(context!!)

        val bucketKey = file.name.replace("\\s".toRegex(), String())

        val uploadObserver = transferUtility.upload(
            secureSharedPreference[SecureSharedPreference.PREF_BUCKET_NAME] + "/mobileUploads",
            bucketKey,
            file
        )
        uploadObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                when (state) {

                    TransferState.WAITING_FOR_NETWORK -> {
                        Log.d(TAG, "TransferState -> Waiting for Network!")
                        fileListener?.onFileUploadError(
                            context?.getString(R.string.internet_error), type
                        )
                        if (documentListener != null) {
                            documentListener?.onDocumentUploadError(
                                context?.getString(R.string.internet_error),
                                position,
                                documentType
                            )
                        }
                    }

                    TransferState.WAITING -> {
                        Log.d(TAG, "TransferState -> $state")
                    }

                    TransferState.CANCELED -> {
                        Log.d(TAG, "TransferState -> $state")

                    }

                    TransferState.IN_PROGRESS -> {
                        Log.d(TAG, "TransferState -> $state")

                    }

                    TransferState.PAUSED -> {
                        Log.d(TAG, "TransferState -> $state")
                    }

                    TransferState.RESUMED_WAITING -> {
                        Log.d(TAG, "TransferState -> $state")

                    }

                    TransferState.FAILED -> if (fileListener != null) {
                        fileListener?.onFileUploadError(
                            context!!.getString(R.string.internet_error), type
                        )
                    } else if (documentListener != null) {
                        Log.d(TAG, "TransferState -> $state")

                        documentListener?.onDocumentUploadError(
                            context?.getString(R.string.internet_error),
                            position, documentType
                        )
                    }
                    TransferState.COMPLETED -> if (fileListener != null) {
                        fileListener?.onFileUploadSuccess(bucketKey, type!!)
                    } else if (documentListener != null) {
                        Log.d(TAG, "TransferState -> $state")
                        documentListener?.onDocumentUploadSuccess(
                            bucketKey,
                            filePath,
                            position,
                            documentType
                        )
                    }
                    else -> Unit
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                if (fileListener != null) {
                    val currentPercent = (100 * bytesCurrent / bytesTotal).toInt()
                    fileListener?.onFileUploadProgressChange(currentPercent, type)
                }
            }

            override fun onError(id: Int, ex: Exception) {
                if (fileListener != null) fileListener?.onFileUploadError(ex.message, type)
                else if (documentListener != null) documentListener?.onDocumentUploadError(
                    ex.message,
                    position,
                    documentType
                )
            }
        })
    }
}