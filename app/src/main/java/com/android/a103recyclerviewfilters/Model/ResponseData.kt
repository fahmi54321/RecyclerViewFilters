package com.android.a103recyclerviewfilters.Model

import com.google.gson.annotations.SerializedName

data class ResponseData(

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("lokasi")
	val lokasi: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("nama_tempat")
	val namaTempat: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null
)
