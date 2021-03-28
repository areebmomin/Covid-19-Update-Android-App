package com.areeb.covid19update.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.areeb.covid19update.network.Covid19DataApi
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {

    private val logTag = "LOG_TAG"

    //mutable live data variables
    private val _latestTotalConfirmed = MutableLiveData<String>()
    private val _latestTotalLastUpdate = MutableLiveData<String>()
    private val _latestTotalCritical = MutableLiveData<String>()
    private val _latestTotalDeaths = MutableLiveData<String>()
    private val _latestTotalRecovered = MutableLiveData<String>()
    private val _dailyReportTotalCases = MutableLiveData<String>()
    private val _dailyReportRecovered = MutableLiveData<String>()
    private val _dailyReportCritical = MutableLiveData<String>()
    private val _dailyReportDeaths = MutableLiveData<String>()
    private val _dailyReportCountryTotalCases = MutableLiveData<String>()
    private val _dailyReportCountryRecovered = MutableLiveData<String>()
    private val _dailyReportCountryCritical = MutableLiveData<String>()
    private val _dailyReportCountryDeaths = MutableLiveData<String>()

    //live data variables
    val latestTotalConfirmed: LiveData<String> = _latestTotalConfirmed
    val latestTotalLastUpdate: LiveData<String> = _latestTotalLastUpdate
    val latestTotalCritical: LiveData<String> = _latestTotalCritical
    val latestTotalDeaths: LiveData<String> = _latestTotalDeaths
    val latestTotalRecovered: LiveData<String> = _latestTotalRecovered
    val dailyReportTotalCases: LiveData<String> = _dailyReportTotalCases
    val dailyReportRecovered: LiveData<String> = _dailyReportRecovered
    val dailyReportCritical: LiveData<String> = _dailyReportCritical
    val dailyReportDeaths: LiveData<String> = _dailyReportDeaths
    val dailyReportCountryTotalCases: LiveData<String> = _dailyReportCountryTotalCases
    val dailyReportCountryRecovered: LiveData<String> = _dailyReportCountryRecovered
    val dailyReportCountryCritical: LiveData<String> = _dailyReportCountryCritical
    val dailyReportCountryDeaths: LiveData<String> = _dailyReportCountryDeaths

    init {
        getLatestTotals()

        //initialize daily report live data
        resetDailyReportValues()

        //initialize daily report country live data
        resetDailyReportCountryValues()
    }

    private fun getLatestTotals() {
        viewModelScope.launch {
            try {

                //reset latest total values
                resetLatestTotalValues()

                //get latest totals
                val latestTotals = Covid19DataApi.retrofitService.getLatestTotals()
                val tempLatestTotals = latestTotals[0]

                //get updated time
                val date = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss",
                    Locale.US
                ).parse(tempLatestTotals.lastUpdate)
                _latestTotalLastUpdate.value =
                    "\u2022 Updated at: " + SimpleDateFormat("HH:mm", Locale.US).format(date!!)

                //get confirmed cases
                _latestTotalConfirmed.value =
                    NumberFormat.getNumberInstance(Locale.US).format(tempLatestTotals.confirmed)

                //get critical cases
                _latestTotalCritical.value =
                    NumberFormat.getNumberInstance(Locale.US).format(tempLatestTotals.critical)

                //get death cases
                _latestTotalDeaths.value =
                    NumberFormat.getNumberInstance(Locale.US).format(tempLatestTotals.deaths)

                //get recovered cases
                _latestTotalRecovered.value =
                    NumberFormat.getNumberInstance(Locale.US).format(tempLatestTotals.recovered)
            } catch (e: Exception) {
                Log.d(logTag, e.toString())

                //reset latest total values
                resetLatestTotalValues()
            }
        }
    }

    fun getDailyReportTotals(date: String) {
        viewModelScope.launch {
            try {
                //reset daily report values
                resetDailyReportValues()

                //get daily report total
                val dailyReportTotals =
                    Covid19DataApi.retrofitService.getDailyReportTotal(date)

                val tempDailyReport = dailyReportTotals[0]

                _dailyReportTotalCases.value =
                    NumberFormat.getNumberInstance(Locale.US).format(tempDailyReport.confirmed)

                _dailyReportRecovered.value =
                    NumberFormat.getNumberInstance(Locale.US).format(tempDailyReport.recovered)

                _dailyReportCritical.value =
                    NumberFormat.getNumberInstance(Locale.US).format(tempDailyReport.critical)

                _dailyReportDeaths.value =
                    NumberFormat.getNumberInstance(Locale.US).format(tempDailyReport.deaths)

                Log.d(logTag, tempDailyReport.toString())
            } catch (e: Exception) {
                Log.d(logTag, e.toString())

                //reset daily report values
                resetDailyReportValues()
            }
        }
    }

    fun getDailyReportByCountryName(date: String, countryName: String) {
        viewModelScope.launch {
            try {

                //reset daily report country values
                resetDailyReportCountryValues()

                //get daily report by country name
                val dailyReportCountries =
                    Covid19DataApi.retrofitService.getDailyReportByCountryName(
                        date,
                        countryName
                    )

                val dailyReportCountry = dailyReportCountries[0]

                Log.d(logTag, dailyReportCountry.toString())

                _dailyReportCountryTotalCases.value =
                    NumberFormat.getNumberInstance(Locale.US)
                        .format(dailyReportCountry.provinces[0].confirmed)

                _dailyReportCountryRecovered.value =
                    NumberFormat.getNumberInstance(Locale.US)
                        .format(dailyReportCountry.provinces[0].recovered)

                _dailyReportCountryCritical.value =
                    NumberFormat.getNumberInstance(Locale.US)
                        .format(dailyReportCountry.provinces[0].active)

                _dailyReportCountryDeaths.value =
                    NumberFormat.getNumberInstance(Locale.US)
                        .format(dailyReportCountry.provinces[0].deaths)

            } catch (e: Exception) {
                Log.d(logTag, e.toString())

                //reset daily report country values
                resetDailyReportCountryValues()
            }
        }
    }

    private fun resetLatestTotalValues() {
        _latestTotalConfirmed.value = "0"
        _latestTotalCritical.value = "0"
        _latestTotalDeaths.value = "0"
        _latestTotalRecovered.value = "0"
    }

    private fun resetDailyReportValues() {
        _dailyReportTotalCases.value = "0"
        _dailyReportRecovered.value = "0"
        _dailyReportCritical.value = "0"
        _dailyReportDeaths.value = "0"
    }

    private fun resetDailyReportCountryValues() {
        _dailyReportCountryTotalCases.value = "0"
        _dailyReportCountryRecovered.value = "0"
        _dailyReportCountryCritical.value = "0"
        _dailyReportCountryDeaths.value = "0"
    }
}