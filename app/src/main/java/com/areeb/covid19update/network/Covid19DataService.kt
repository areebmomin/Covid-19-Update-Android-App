package com.areeb.covid19update.network

import com.areeb.covid19update.models.DailyReport
import com.areeb.covid19update.models.DailyReportCountryParent
import com.areeb.covid19update.models.LatestTotals
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

//Covid-19 Data API Key
private const val apiKey = "6901274071msh6c8ac44298a2561p190b6djsn2662f7e52bae"

//initialize base url
private const val BASE_URL = "https://covid-19-data.p.rapidapi.com"

//initialize Moshi object
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//initialize retrofit
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

//declare api service interface
interface Covid19DataService {
    //method for latest totals
    @Headers(
        "x-rapidapi-key: $apiKey",
        "x-rapidapi-host: covid-19-data.p.rapidapi.com"
    )
    @GET("totals")
    suspend fun getLatestTotals(): List<LatestTotals>

    //method for daily report total
    @Headers(
        "x-rapidapi-key: $apiKey",
        "x-rapidapi-host: covid-19-data.p.rapidapi.com"
    )
    @GET("report/totals")
    suspend fun getDailyReportTotal(@Query("date") date: String): List<DailyReport>

    //method for daily report of given country
    @Headers(
        "x-rapidapi-key: $apiKey",
        "x-rapidapi-host: covid-19-data.p.rapidapi.com"
    )
    @GET("report/country/name")
    suspend fun getDailyReportByCountryName(@Query("date") date: String,
                                            @Query("name") name: String): List<DailyReportCountryParent>
}

//declare api object
object Covid19DataApi {
    val retrofitService: Covid19DataService by lazy {
        retrofit.create(Covid19DataService::class.java)
    }
}