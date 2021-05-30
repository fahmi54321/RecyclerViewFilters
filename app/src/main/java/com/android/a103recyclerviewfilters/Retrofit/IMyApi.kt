package com.android.a103recyclerviewfilters.Retrofit

import com.android.a103recyclerviewfilters.Model.DataItem
import com.android.a103recyclerviewfilters.Model.Item
import com.android.a103recyclerviewfilters.Model.ResponseData
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import java.util.*

interface IMyApi {
    @get:GET("photos")
    var getData:Observable<List<Item>>

    @get:GET("api")
    var getApi:Observable<ResponseData>
}