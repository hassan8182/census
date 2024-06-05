package com.census.data.response.census

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {
    @Expose
    @SerializedName("id")
    var id: Int? = null

    @Expose
    @SerializedName("province_name")
    var provinceName: String? = null

    @Expose
    @SerializedName("district_code")
    var districtCode: Int? = null

    @Expose
    @SerializedName("district_name")
    var districtName: String? = null

    @Expose
    @SerializedName("tehsil_code")
    var tehsilCode: Int? = null

    @Expose
    @SerializedName("tehsil_name")
    var tehsilName: String? = null

    @Expose
    @SerializedName("cv_code")
    var cvCode: Int? = null

    @Expose
    @SerializedName("urbanity")
    var urbanity: String? = null

    @Expose
    @SerializedName("cv_name")
    var cvName: String? = null

    @Expose
    @SerializedName("ar_tehsil")
    var arTehsil: String? = null

    @Expose
    @SerializedName("block_id")
    var blockId: Int? = null

    @Expose
    @SerializedName("unique_id")
    var uniqueId: String? = null

    @Expose
    @SerializedName("univ_2012")
    var univ2012: String? = null

    @Expose
    @SerializedName("univ_2022")
    var univ2022: String? = null

    @Expose
    @SerializedName("field_executive")
    var fieldExecutive: String? = null

    @Expose
    @SerializedName("exec_id")
    var execId: String? = null

    @Expose
    @SerializedName("field_sup")
    var fieldSup: String? = null

    @Expose
    @SerializedName("sup_id")
    var supId: String? = null

    @Expose
    @SerializedName("iqc_sup")
    var iqcSup: String? = null

    @Expose
    @SerializedName("iqcsup_id")
    var iqcsupId: String? = null

    @Expose
    @SerializedName("field_auditor")
    var fieldAuditor: String? = null

    @Expose
    @SerializedName("auditor_id")
    var auditorId: String? = null

    @Expose
    @SerializedName("iqc_auditor")
    var iqcAuditor: String? = null

    @Expose
    @SerializedName("iqcauditor_id")
    var iqcauditorId: String? = null

    @Expose
    @SerializedName("Assigned")
    var assigned: String? = null

    @Expose
    @SerializedName("Flagged")
    var flagged: String? = null

    @Expose
    @SerializedName("Resolved")
    var resolved: String? = null

    @Expose
    @SerializedName("csv_file_name")
    var csvFileName: String? = null

    @Expose
    @SerializedName("ar_geo_file_name")
    var arGeoFileName: String? = null

    @Expose
    @SerializedName("fk_map")
    var fkMap: Int? = null

    @Expose
    @SerializedName("status")
    var status: Int? = null

    @Expose
    @SerializedName("created_at")
    var createdAt: String? = null

    @Expose
    @SerializedName("updated_at")
    var updatedAt: String? = null

    @Expose
    @SerializedName("file_name")
    var fileName: String? = null

    @Expose
    @SerializedName("blockId")
    var blockIdAlt: Int? = null

    @Expose
    @SerializedName("json")
    var json: String? = null

    @Expose
    @SerializedName("active")
    var active: Int? = null

    @Expose
    @SerializedName("city")
    var city: String? = null

}