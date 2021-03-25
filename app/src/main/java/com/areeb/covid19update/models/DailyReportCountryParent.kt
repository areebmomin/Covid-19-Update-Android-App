package com.areeb.covid19update.models

data class DailyReportCountryParent(
    val country: String,
    val provinces: List<DailyReportCountry>,
    val latitude: Double,
    val longitude: Double,
    val date: String
)
