package com.areeb.covid19update.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.areeb.covid19update.models.DailyReport
import com.areeb.covid19update.models.DailyReportCountryParent
import com.areeb.covid19update.models.LatestTotals
import com.areeb.covid19update.network.Covid19DataApi
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {

    private val logTag = "LOG_TAG"

    //mutable live data variables
    private val _latestTotal = MutableLiveData<LatestTotals>()
    private val _dailyReportTotal = MutableLiveData<DailyReport>()
    private val _dailyReportCountry = MutableLiveData<DailyReportCountryParent>()
    private val _latestTotalConfirmed = MutableLiveData<String>()
    private val _latestTotalLastUpdate = MutableLiveData<String>()
    private val _latestTotalCritical = MutableLiveData<String>()
    private val _latestTotalDeaths = MutableLiveData<String>()
    private val _latestTotalRecovered = MutableLiveData<String>()

    //live data variables
    val latestTotal: LiveData<LatestTotals> = _latestTotal
    val dailyReportTotal: LiveData<DailyReport> = _dailyReportTotal
    val dailyReportCountry: LiveData<DailyReportCountryParent> = _dailyReportCountry
    val latestTotalConfirmed: LiveData<String> = _latestTotalConfirmed
    val latestTotalLastUpdate: LiveData<String> = _latestTotalLastUpdate
    val latestTotalCritical: LiveData<String> = _latestTotalCritical
    val latestTotalDeaths: LiveData<String> = _latestTotalDeaths
    val latestTotalRecovered: LiveData<String> = _latestTotalRecovered

    init {
        getLatestTotals()
    }

    private fun getLatestTotals() {
        viewModelScope.launch {
            try {
                //get latest totals
                val latestTotals = Covid19DataApi.retrofitService.getLatestTotals()
                val tempLatestTotals = latestTotals[0]

                //get updated time
                val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(tempLatestTotals.lastUpdate)
                _latestTotalLastUpdate.value = "\u2022 Updated At: " + SimpleDateFormat("HH:mm", Locale.US).format(date!!)

                //get confirmed cases
                _latestTotalConfirmed.value = NumberFormat.getNumberInstance(Locale.US).format(tempLatestTotals.confirmed)

                //get critical cases
                _latestTotalCritical.value = NumberFormat.getNumberInstance(Locale.US).format(tempLatestTotals.critical)

                //get death cases
                _latestTotalDeaths.value = NumberFormat.getNumberInstance(Locale.US).format(tempLatestTotals.deaths)

                //get recovered cases
                _latestTotalRecovered.value = NumberFormat.getNumberInstance(Locale.US).format(tempLatestTotals.recovered)

                _latestTotal.value = tempLatestTotals
                Log.d(logTag, latestTotals.toString())

            } catch (e: Exception) {
                Log.d(logTag, e.toString())
            }
        }
    }

    private fun getDailyReportTotals() {
        viewModelScope.launch {
            try {
                //get daily report total
                val dailyReportTotals =
                    Covid19DataApi.retrofitService.getDailyReportTotal("2020-07-21")
                _dailyReportTotal.value = dailyReportTotals[0]
                Log.d(logTag, dailyReportTotals.toString())
            } catch (e: Exception) {
                Log.d(logTag, e.toString())
            }
        }
    }

    private fun getDailyReportByCountryName() {
        viewModelScope.launch {
            try {
                //get daily report by country name
                val dailyReportCountries =
                    Covid19DataApi.retrofitService.getDailyReportByCountryName("2020-04-01", "Italy")
                _dailyReportCountry.value = dailyReportCountries[0]
                Log.d(logTag, dailyReportCountries.toString())
            } catch (e: Exception) {
                Log.d(logTag, e.toString())
            }
        }
    }
}