/*
 * Copyright 2015-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.census.utils.s3

import android.content.Context
import android.util.Log
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.services.s3.AmazonS3Client
import com.census.data.local.prefs.SecureSharedPreference
import java.util.*

/*
 * Handles basic helper functions used throughout the app.
 */
class FileTransferUtil {
    private var sS3Client: AmazonS3Client? = null
    private val sCredProvider: CognitoCachingCredentialsProvider? = null
    private var sTransferUtility: TransferUtility? = null
    private var credentials: BasicAWSCredentials? = null
    /**
     * Gets an instance of CognitoCachingCredentialsProvider which is
     * constructed using the given Context.
     *
     * @param context An Context instance.
     * @return A default credential provider.
     */
    /*private CognitoCachingCredentialsProvider getCredProvider(Context context) {
        if (sCredProvider == null) {
            sCredProvider = new CognitoCachingCredentialsProvider(
                    context.getApplicationContext());
        }
        return sCredProvider;
    }*/
    /**
     * Gets an instance of a S3 client which is constructed using the given
     * Context.
     *
     * @param context An Context instance.
     * @return A default S3 client.
     */
    private fun getS3Client(
        context: Context
    ): AmazonS3Client {

        val preference = SecureSharedPreference(context)
        TransferNetworkLossHandler.getInstance(context)
        val region = Region.getRegion(
            preference[SecureSharedPreference.PREF_REGION_NAME]
        )
        if (sS3Client == null) {

            credentials = BasicAWSCredentials(
                preference[SecureSharedPreference.PREF_ACCESS_KEY],
                preference[SecureSharedPreference.PREF_SECRET_KEY],
            )

            sS3Client = AmazonS3Client(credentials, region)

        }
        return sS3Client as AmazonS3Client
    }

    /**
     * Gets an instance of the TransferUtility which is constructed using the
     * given Context
     *
     * @param context
     * @return a TransferUtility instance
     */
    // The method from sample
    fun getTransferUtility(context: Context): TransferUtility {
        if (sTransferUtility == null) {

            val preference = SecureSharedPreference(context)

            sTransferUtility = TransferUtility.builder()
                .context(context.applicationContext)
                .s3Client(getS3Client(context.applicationContext))
                .defaultBucket(preference[SecureSharedPreference.PREF_BUCKET_NAME] + "/mobileUploads")
                .build()
        }
        return sTransferUtility!!
    }
    // The method from doc
    /*public TransferUtility getTransferUtility_2(Context context) {
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(context.getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();

        return transferUtility;
    }*/
    /**
     * Converts number of bytes into proper scale.
     *
     * @param bytes number of bytes to be converted.
     * @return A string that represents the bytes in a proper scale.
     */
    private fun getBytesString(bytes: Long): String {
        val quantifiers = arrayOf(
            "KB", "MB", "GB", "TB"
        )
        var speedNum = bytes.toDouble()
        var i = 0
        while (true) {
            if (i >= quantifiers.size) {
                return ""
            }
            speedNum /= 1024.0
            if (speedNum < 512) {
                return String.format(Locale.getDefault(), "%.2f", speedNum) + " " + quantifiers[i]
            }
            i++
        }
    }

    /*
     * Fills in the map with information in the observer so that it can be used
     * with a SimpleAdapter to populate the UI
     */
    fun fillMap(map: MutableMap<String?, Any?>, observer: TransferObserver, isChecked: Boolean) {
        val progress = (observer.bytesTransferred.toDouble() * 100 / observer
            .bytesTotal).toInt()
        map["id"] = observer.id
        map["checked"] = isChecked
        map["fileName"] = observer.absoluteFilePath
        map["progress"] = progress
        map["bytes"] = (getBytesString(observer.bytesTransferred) + "/"
                + getBytesString(observer.bytesTotal))
        map["state"] = observer.state
        map["percentage"] = "$progress%"
        Log.d("usm_s3_transfer_item", "map= $map")
    }
}