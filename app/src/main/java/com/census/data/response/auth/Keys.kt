package com.census.data.response.auth


import com.google.gson.annotations.SerializedName

data class Keys(
    @SerializedName("access_key")
    var accessKey: String?,
    @SerializedName("aws_bucket_name")
    var awsBucketName: String?,
    @SerializedName("cloud_key")
    var cloudKey: String?,
    @SerializedName("region")
    var region: String?,
    @SerializedName("secret_key")
    var secretKey: String?
)