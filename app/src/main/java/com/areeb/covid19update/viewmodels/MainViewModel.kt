package com.areeb.covid19update.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.areeb.covid19update.models.DailyReport
import com.areeb.covid19update.models.DailyReportCountryParent
import com.areeb.covid19update.models.LatestTotals
import com.areeb.covid19update.models.ListOfCountries
import com.areeb.covid19update.network.Covid19DataApi
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val logTag = "LOG_TAG"

    //mutable live data variables
    private val _latestTotal = MutableLiveData<LatestTotals>()
    private val _dailyReportTotal = MutableLiveData<DailyReport>()
    private val _dailyReportCountry = MutableLiveData<DailyReportCountryParent>()

    //live data variables
    val latestTotal: LiveData<LatestTotals> = _latestTotal
    val dailyReportTotal: LiveData<DailyReport> = _dailyReportTotal
    val dailyReportCountry: LiveData<DailyReportCountryParent> = _dailyReportCountry

    init {
        getLatestTotals()
    }

    private fun getLatestTotals() {
        viewModelScope.launch {
            try {
                //get latest totals
                val latestTotals = Covid19DataApi.retrofitService.getLatestTotals()
                _latestTotal.value = latestTotals[0]
                Log.d(logTag, latestTotals.toString())

                //get daily report total
//                val dailyReportTotals = Covid19DataApi.retrofitService.getDailyReportTotal("2020-07-21")
//                _dailyReportTotal.value = dailyReportTotals[0]
//                Log.d(logTag, dailyReportTotals.toString())

                //get daily report by country name
//                val dailyReportCountries = Covid19DataApi.retrofitService.getDailyReportByCountryName("2020-04-01", "Italy")
//                _dailyReportCountry.value = dailyReportCountries[0]
//                Log.d(logTag, dailyReportCountries.toString())

            } catch (e: Exception) {
                Log.d(logTag, e.toString())
            }
        }
    }
}