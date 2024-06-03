package com.census.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.JPEG
import android.graphics.Bitmap.CompressFormat.PNG
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns.*
import android.provider.OpenableColumns.DISPLAY_NAME
import com.google.common.io.ByteStreams
import timber.log.Timber
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

// -------------------------------------------File Extensions---------------------------------------
const val PDF_EXT = ".pdf"
const val JPG_EXT = ".jpg"
const val MP3_EXT = ".mp3"
const val MP4_EXT = ".mp4"
const val PNG_EXT = ".png"

// --------------------------------------------Mime Types-------------------------------------------
const val IMAGE_MIME_TYPE = "image/jpeg"
const val VIDEO_MIME_TYPE = "video/mp4"
const val AUDIO_MIME_TYPE = "audio/mpeg"
const val PDF_MIME_TYPE = "application/pdf"

// -------------------------------------------Folder Name-------------------------------------------


const val DEFAULT_CAMERA_FOLDER = "DCIM"

// Folder for Saving Media in Shared Storage.
const val APP_MEDIA_FOLDER = "RACETOFREEDOMMedia"

const val APP_CROPPED_IMAGE = "RACETOFREEDOMCroppedImage"
const val APP_OVERLAY_IMAGE = "RACETOFREEDOMOverlayImage"
const val APP_OVERLAY_VIDEO = "RACETOFREEDOMOverlayVideo"
const val APP_CAPTURED_IMAGE = "RACETOFREEDOMCapturedImage"
const val APP_CAPTURED_VIDEO = "RACETOFREEDOMCapturedVideo"

// Folder for Saving Temporary Files Cache Storage.
const val APP_TEMP_MEDIA_FOLDER = "RACETOFREEDOMTempMedia"


// -----------------------------------------Methods for File Names----------------------------------
fun generateOverlayImageFileName() = APP_OVERLAY_IMAGE + '_' + Date().time
fun generateOverlayVideoFileName() = APP_OVERLAY_VIDEO + '_' + Date().time
fun generateCroppedImageFileName() = APP_CROPPED_IMAGE + '_' + Date().time
fun generateCaptureImageFileName() = APP_CAPTURED_IMAGE + '_' + Date().time
fun generateCaptureVideoFileName() = APP_CAPTURED_VIDEO + '_' + Date().time

/**
 * Create a Folder if Not Exist in External Storage Directory.
 * */
fun String.createFolderExternalStorage() {
    val file = File(
        Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DCIM
        ).toString() + File.separator + this
    )
    if (!file.exists()) {
        if (!file.mkdir()) {
            Timber.d("Folder Create -> Failure")
        } else {
            Timber.d("Folder Create -> Success")
        }
    }
}

/**
 * Create a Folder if Not Exist in Internal Storage Cache.
 * */
fun String.createFolderInternalCache(context: Context) {
    val file = File(
        context.cacheDir.toString()
                + File.separator
                + this
    )
    if (!file.exists()) {
        if (!file.mkdir()) {
            Timber.d("Folder Create -> Failure")
        } else {
            Timber.d("Folder Create -> Success")
        }
    }
}

/**
 * @return Return true or false if directory deleted.
 * @param directory The File or Folder to be deleted.
 * */
fun deleteDirectory(directory: File): Boolean {
    if (directory.isDirectory) {
        val children = directory.list()
        if (children != null) {
            for (aChildren in children) {
                val success = deleteDirectory(
                    File(directory, aChildren)
                )
                if (!success) {
                    return false
                }
            }
        }
    }
    return directory.delete()
}

/**
 * @param parentDir The directory that need to be listed.
 * @return the list of all files in the provided directory.
 * */
fun getListFiles(parentDir: File): List<File> {

    val fileArrayList = ArrayList<File>()
    val files = parentDir.listFiles()

    files?.forEach { file ->
        if (file.isDirectory) {
            fileArrayList.addAll(
                getListFiles(file)
            )
        } else {
            fileArrayList.add(file)
        }
    }

    return fileArrayList
}

/**
 * @return Returns the Collection Uri According to Mime Type.
 * */
fun getCollectionUri(mimeType: String): Uri {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        when (mimeType) {
            VIDEO_MIME_TYPE -> {
                MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            }
            IMAGE_MIME_TYPE -> {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            }
            else -> {
                MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            }
        }
    } else {
        when (mimeType) {
            VIDEO_MIME_TYPE -> {
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            }
            IMAGE_MIME_TYPE -> {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }
            else -> {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
        }
    }
}

/**
 * @return Whether the Uri authority is MediaProvider.
 */
fun Uri.isMediaDocument(): Boolean {
    return "com.android.providers.media.documents" == authority
}

/**
 * @return Whether the Uri authority is DownloadsProvider.
 */
fun Uri.isDownloadsDocument(): Boolean {
    return "com.android.providers.downloads.documents" == authority
}

/**
 * @return Whether the Uri authority is ExternalStorageProvider.
 */
fun Uri.isExternalStorageDocument(): Boolean {
    return "com.android.externalstorage.documents" == authority
}

/**
 * @param context The Context Required for Content Resolver.
 * @return Get The File Name of Provided File Uri or null.
 * */
fun Uri.getFileName(context: Context): String? {

    var displayName: String? = null

    val cursor = context.contentResolver.query(
        this, null, null,
        null, null
    )

    cursor?.let {

        if (it.moveToFirst()) {

            try {

                displayName = it.getString(
                    it.getColumnIndexOrThrow(DISPLAY_NAME)
                )

            } catch (exception: Exception) {

                Timber.d("Error -> %s", exception.message)
            }

        }

        it.close()
    }

    return displayName
}

/**
 * @return Returns Saved Path of the File from Uri Provided.
 * */
fun ContentResolver.getSavedPath(uri: Uri): String? {
    var savedPath: String? = null
    query(
        uri, null, null,
        null, null
    )?.run {
        if (moveToFirst()) {
            savedPath = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                getString(getColumnIndexOrThrow(RELATIVE_PATH)) + getString(
                    getColumnIndexOrThrow(
                        MediaStore.MediaColumns.DISPLAY_NAME
                    )
                )
            } else {
                getString(getColumnIndexOrThrow(DATA))
            }
        }
        close()
    }
    return savedPath
}

/**
 * @return Returns External Storage Path of the File.
 * */
private fun getDataPath(fileName: String, directory: String): String {
    return Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_DCIM
    ).toString() + File.separator + directory + File.separator + fileName
}

/**
 * @return Returns the Bitmap Object of the File Provided.
 * */
fun File.createBitmapFromFile(): Bitmap? = BitmapFactory.decodeFile(absolutePath)

/**
 * @return Returns the Bitmap Object of the Path Provided.
 * */
fun String.createBitmapFromPath(): Bitmap? = BitmapFactory.decodeFile(this)

/**
 * @param context The Context Required for Content Resolver.
 * @param extension The Extension of the File to be Created.
 * @return Returns the File to be Created from Uri or null.
 * */
fun Uri.createFileFromUri(context: Context, extension: String): File? {

    try {

        val fileName = "${UUID.randomUUID()}$extension"

        Timber.d("File Name -> %s", fileName)

        val tempFile = File(context.cacheDir, fileName)

        val inputStream = context
            .contentResolver.openInputStream(this)

        val outputStream = FileOutputStream(tempFile)

        inputStream?.apply {
            ByteStreams.copy(this, outputStream)
        }

        inputStream?.close()
        outputStream.close()

        Timber.d("File Size -> %s", tempFile.length())

        return tempFile

    } catch (exception: Exception) {

        Timber.d("Error -> %s", exception.message)

        return null
    }

}


/**
 * @param mimeType video/mp4, audio/mpeg, application/pdf
 * @param uri The uri of the File to be written in storage
 * @param fileName The Display Name of the File to written
 * @return Path of Saved File if successfully saved or null
 * */
fun Context.saveFileToStorage(fileName: String, uri: Uri, mimeType: String): String? {

    try {

        val collectionUri = getCollectionUri(mimeType)

        val values = getContentValues(fileName, mimeType, APP_MEDIA_FOLDER)

        val newUri = contentResolver.insert(collectionUri, values)

        newUri?.let { savedUri ->
            return contentResolver.saveOneUriToAnother(
                uriToBeRead = uri, uriToBeWrite = savedUri
            )
        }

    } catch (exception: Exception) {
        Timber.d("Error -> %s", exception.message)
    }

    return null
}

/**
 * @param mimeType video/mp4, audio/mpeg, application/pdf
 * @param path The path of the File to be written in storage.
 * @param fileName The Display Name of the File to written.
 * @return Path of Saved File if successfully saved or null
 * */
fun Context.saveFileToStorage(fileName: String, path: String, mimeType: String): String? {

    try {

        val fileUri = Uri.fromFile(File(path))

        val collectionUri = getCollectionUri(mimeType)

        val contentValues = getContentValues(
            fileName, mimeType, APP_MEDIA_FOLDER
        )

        val destinationUri = contentResolver.insert(
            collectionUri, contentValues
        )

        destinationUri?.apply {
            return contentResolver.saveOneUriToAnother(
                uriToBeRead = fileUri,
                uriToBeWrite = this@apply
            )
        }

    } catch (exception: Exception) {
        Timber.d("Error -> %s", exception.message)
    }

    return null
}

/**
 * @param mimeType video/mp4, audio/mpeg, application/pdf
 * @param file The File to be written in the shared storage.
 * @param fileName The Display Name of the File to written.
 * @return Path of Saved File if successfully saved or null
 * */
fun Context.saveFileToStorage(fileName: String, file: File, mimeType: String): String? {

    try {

        val fileUri = Uri.fromFile(file)

        val collectionUri = getCollectionUri(mimeType)

        val contentValues = getContentValues(
            fileName, mimeType, APP_MEDIA_FOLDER
        )

        val destinationUri = contentResolver.insert(
            collectionUri, contentValues
        )

        destinationUri?.apply {
            return contentResolver.saveOneUriToAnother(
                uriToBeRead = fileUri,
                uriToBeWrite = this@apply
            )
        }

    } catch (exception: Exception) {
        Timber.d("Error -> %s", exception.message)
    }

    return null
}

/**
 * @return Returns the File Object of the Bitmap Provided.
 * */
fun Bitmap.createFileFromBitmap(context: Context, extension: String): File? {

    try {

        val fileName = "${UUID.randomUUID()}$extension"

        Timber.d("File Name -> %s", fileName)

        val tempFile = File(context.cacheDir, fileName)

        val outputStream = FileOutputStream(tempFile)

        when (extension) {
            JPG_EXT -> {
                this.compress(JPEG, 100, outputStream)
            }
            else -> {
                this.compress(PNG, 100, outputStream)
            }
        }

        outputStream.close()

        Timber.d("File Size -> %s", tempFile.length())

        return tempFile

    } catch (exception: Exception) {

        Timber.d("Error -> %s", exception.message)

        return null

    }

}

/**
 * @return Saved Path of File or null in case of error.
 * @param uriToBeWrite Path at which file to be written.
 * @param uriToBeRead Uri Path of the file to be read.
 * */
fun ContentResolver.saveOneUriToAnother(uriToBeRead: Uri, uriToBeWrite: Uri): String? {
    return try {
        var byteStream: ByteArray? = null
        openInputStream(uriToBeRead)?.use {
            byteStream = it.readBytes()
        }
        byteStream?.let { byteArray ->
            val outputStream = openOutputStream(uriToBeWrite)
            outputStream?.write(byteArray)
            outputStream?.close()
        }
        getSavedPath(uriToBeWrite)
    } catch (exception: Exception) {
        Timber.d("Error -> %s", exception.message)
        null
    }
}

/**
 * @return Returns Content Values to be insert with file
 * @param mimeType video/mp4, audio/mpeg, application/pdf
 * @param directory Directory Name of File to be written
 * */
fun getContentValues(fileName: String, mimeType: String, directory: String): ContentValues {

    return ContentValues().apply {
        put(TITLE, fileName)
        put(MIME_TYPE, mimeType)
        put(DISPLAY_NAME, fileName)
        put(DATE_ADDED, Date().time)
        put(DATE_MODIFIED, Date().time)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            put(DATE_TAKEN, Date().time)
            put(
                RELATIVE_PATH,
                Environment.DIRECTORY_DCIM + File.separator + directory
            )
        } else {
            directory.createFolderExternalStorage()
            put(
                DATA, getDataPath(fileName, directory)
            )
        }
    }
}

/**
 * @return Returns the file object if Saved successful or null.
 * */
fun saveByteArray(fileBytes: ByteArray?, file: File?): File? {
    return if (file != null) try {
        val bos = BufferedOutputStream(
            FileOutputStream(file)
        )
        bos.write(fileBytes)
        bos.flush()
        bos.close()
        file
    } catch (exception: Exception) {
        null
    } else null
}

fun getFilesCountExternalStorage(directoryName: String): Int {
    return File(
        Environment.getExternalStorageDirectory()
            .toString() + File.separator + directoryName + File.separator
    ).listFiles()?.size ?: 0

}

fun cleanInternalCache(context: Context, folderName: String) {
    try {
        val cacheDirectory = File(
            context.cacheDir.toString()
                    + File.separator
                    + folderName
                    + File.separator
        )
        deleteDirectory(cacheDirectory)
    } catch (exception: Exception) {
        Timber.d("Error -> %s", exception.message)
    }
}

fun getFilesCountInternalCache(context: Context, directoryName: String): Int {

    return File(
        context.cacheDir.toString()
                + File.separator
                + directoryName
                + File.separator
    ).listFiles()?.size ?: 0

}

fun createExternalStorageFile(fileName: String? = null, extension: String): File? {

    return try {
        val displayName = "${fileName ?: UUID.randomUUID()}$extension"
        Timber.d("File Name -> %s", displayName)

        APP_MEDIA_FOLDER.createFolderExternalStorage()

        File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM
            ).toString() + File.separator + APP_MEDIA_FOLDER + File.separator + displayName
        )
    } catch (exception: Exception) {
        Timber.d("Error -> %s", exception.message)
        null
    }
}

fun createTemporaryFile(context: Context, fileName: String? = null, extension: String): File? {

    APP_TEMP_MEDIA_FOLDER.createFolderInternalCache(context)

    return try {
        val displayName = "${fileName ?: UUID.randomUUID()}$extension"
        Timber.d("File Name -> %s", displayName)
        File(
            context.cacheDir.toString()
                    + File.separator
                    + APP_TEMP_MEDIA_FOLDER
                    + File.separator,
            displayName
        )
    } catch (exception: Exception) {
        Timber.d("Error -> %s", exception.message)
        null
    }
}