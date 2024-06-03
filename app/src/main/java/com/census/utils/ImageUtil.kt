package com.census.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageUtil private constructor(
    /*private val context: Context,
    imageSaveListener: ImageSaveListener,
    private val imagePath: String?,
    private val filter: Int,
    private val overlayPath: String?,
    isSaveInGallery: Boolean,
    state: Int,
    mediaId: String?*/
) {
    /*private var state: Int
    private val mediaId: String?
    private val isSaveInGallery = false
    private var kfilterProcessor: KfilterProcessor? = null
    private val imageSaveListener: ImageSaveListener?
    private fun processImage() {
        applyFilterOnImage(imagePath)
    }*/

    /*private fun isFilterApply(filter: Int): Boolean {
        return filter > 0
    }*/

    /*private val newFilePath: String
        private get() = PathUtil.getInternalUploadFilePath(
            context,
            PathUtil.OVERLAY_IMAGE.toString() + "_" + System.currentTimeMillis(),
            ".png"
        ).getAbsolutePath()*/

    /*private fun applyFilterOnImage(actualFilePath: String?) {
        if (isFilterApply(filter)) {
            val outPutFilterFilePath = newFilePath
            kfilterProcessor = KfilterProcessor(getSelectedFilter(filter), actualFilePath)
            kfilterProcessor.save(outPutFilterFilePath, object : SaveFile() {
                fun save(filteredFilepath: String) {
                    sendProgress("image filer apply done")
                    if (isEmpty(overlayPath)) {
                        sendProgress("File for upload file = $filteredFilepath")
                        //                        sendStateChange(filteredFilepath, UploadStatus.IS_EDITED);
                        sendOnSave(filteredFilepath)
                    } else {
//                        sendStateChange(filteredFilepath, UploadStatus.IS_FILTER_APPLIED);
                        sendProgress("Video overlay apply start")
                        applyOverlay(filteredFilepath)
                    }
                }

                fun error(s: String) {
                    sendProgress("image filer apply error")
                }
            })
        } else {
            if (isEmpty(overlayPath)) {
                sendProgress("File for upload file = $actualFilePath")
                //                sendStateChange(actualFilePath, UploadStatus.IS_EDITED);
                sendOnSave(actualFilePath)
            } else {
                applyOverlay(actualFilePath)
            }
        }
    }*/

    /*private fun applyOverlay(actualImagePath: String?) {
        try {
            val back = MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                Uri.fromFile(File(actualImagePath))
            )
            val overlayBitmap = MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                Uri.fromFile(File(overlayPath))
            )
            val bitmap: Bitmap = applyOverlayOnImage(back, overlayBitmap)
            sendProgress("overlay image has applied")
            val mediaPath = newFilePath
            val output: String = getOutputFilePath(context, mediaPath)
            val result: String = BitmapUtil.savebitmap(bitmap, output)
            sendProgress("image editing has done")
            sendOnSave(result)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }*/

    /*private fun sendOnSave(filePath: String?) {
        if (imageSaveListener != null) {
            imageSaveListener.onSave(filePath, mediaId)
        }
    }*/

    /*private fun sendStateChange(filePath: String, state: Int) {
        this.state = state
        if (imageSaveListener != null && !isSaveInGallery) {
            imageSaveListener.onStateChange(filePath, state, mediaId)
        }
    }*/

    /*private fun sendProgress(progress: String) {}*/
    /*private fun getSelectedFilter(filterIndex: Int): Kfilter {
        return getFilters().get(filterIndex)
    }*/

    /*private fun isEmpty(string: String?): Boolean {
        return string == null || string.trim { it <= ' ' }.isEmpty()
    }*/

    /*class Builder(private val context: Context, imageSaveListener: ImageSaveListener) {
        private var imagePath: String? = null
        private var filter = 0
        private var overlayPath: String? = null
        private var state = 0
        private var mediaId: String? = null
        private var isSaveInGallery = false
        private val imageSaveListener: ImageSaveListener
        fun setImagePath(imagePath: String?): Builder {
            this.imagePath = imagePath
            return this
        }

        fun setOverlayPath(overlayPath: String?): Builder {
            this.overlayPath = overlayPath
            return this
        }

        fun setFilter(filter: Int): Builder {
            this.filter = filter
            return this
        }

        fun setState(state: Int): Builder {
            this.state = state
            return this
        }

        fun setMediaId(mediaId: String?): Builder {
            this.mediaId = mediaId
            return this
        }

        fun isSaveInGallery(isSaveInGallery: Boolean): Builder {
            this.isSaveInGallery = isSaveInGallery
            return this
        }

        fun build(): ImageUtil {
            return ImageUtil(
                context, imageSaveListener, imagePath, filter,
                overlayPath, isSaveInGallery, state, mediaId
            )
        }

        init {
            this.imageSaveListener = imageSaveListener
        }
    }*/

    companion object {
        /**
         * This method is responsible for solving the rotation issue if exist. Also scale the images to
         * 1024x1024 resolution
         *
         * @param context       The current context
         * @param selectedImage The Image URI
         * @return Bitmap image results
         * @throws IOException
         */
        @Throws(IOException::class)
        fun handleSamplingAndRotationBitmap(
            context: Context,
            file: File,
            outputFile: File? = null,
        ): Bitmap {
            val MAX_HEIGHT = 1024
            val MAX_WIDTH = 1024

            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            val contentUri = Uri.fromFile(file)
            var imageStream = context.contentResolver.openInputStream(contentUri)
            BitmapFactory.decodeStream(imageStream, null, options)
            imageStream!!.close()

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            imageStream = context.contentResolver.openInputStream(contentUri)
            var img = BitmapFactory.decodeStream(imageStream, null, options)
            img = rotateImageIfRequired(context, img, contentUri)

//            file.createNewFile()
//            val outputStream = FileOutputStream(outputFile)
            val outputStream = FileOutputStream(outputFile ?: file.also {  it.createNewFile() })
            img!!.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
            return img
        }

        /**
         * Rotate an image if required.
         *
         * @param img           The image bitmap
         * @param selectedImage Image URI
         * @return The resulted Bitmap after manipulation
         */
        @Throws(IOException::class)
        private fun rotateImageIfRequired(
            context: Context,
            img: Bitmap?,
            selectedImage: Uri
        ): Bitmap? {
            val input = context.contentResolver.openInputStream(selectedImage)
            val ei: ExifInterface = if (Build.VERSION.SDK_INT > 23) ExifInterface(input!!) else ExifInterface(
                    selectedImage.path!!
                )
            return when (ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270)
                else -> img
            }
        }

        private fun rotateImage(img: Bitmap?, degree: Int): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(degree.toFloat())
            val rotatedImg = Bitmap.createBitmap(img!!, 0, 0, img.width, img.height, matrix, true)
            img.recycle()
            return rotatedImg
        }

        /**
         * Calculate an inSampleSize for use in a [BitmapFactory.Options] object when decoding
         * bitmaps using the decode* methods from [BitmapFactory]. This implementation calculates
         * the closest inSampleSize that will result in the final decoded bitmap having a width and
         * height equal to or larger than the requested width and height. This implementation does not
         * ensure a power of 2 is returned for inSampleSize which can be faster when decoding but
         * results in a larger bitmap which isn't as useful for caching purposes.
         *
         * @param options   An options object with out* params already populated (run through a decode*
         * method with inJustDecodeBounds==true
         * @param reqWidth  The requested width of the resulting bitmap
         * @param reqHeight The requested height of the resulting bitmap
         * @return The value to be used for inSampleSize
         */
        private fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int, reqHeight: Int
        ): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {

                // Calculate ratios of height and width to requested height and width
                val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
                val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())

                // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
                // with both dimensions larger than or equal to the requested height and width.
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio

                // This offers some additional logic in case the image has a strange
                // aspect ratio. For example, a panorama may have a much larger
                // width than height. In these cases the total pixels might still
                // end up being too large to fit comfortably in memory, so we should
                // be more aggressive with sample down the image (=larger inSampleSize).
                val totalPixels = (width * height).toFloat()

                // Anything more than 2x the requested pixels we'll sample down further
                val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
                while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                    inSampleSize++
                }
            }
            return inSampleSize
        }
    }

    /*init {
        this.imageSaveListener = imageSaveListener
        this.isSaveInGallery = isSaveInGallery
        this.state = state
        this.mediaId = mediaId
        processImage()
    }*/
}