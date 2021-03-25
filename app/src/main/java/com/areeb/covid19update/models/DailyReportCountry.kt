package com.areeb.covid19update.models

data class DailyReportCountry(
    val province: String,
    val confirmed: Long,
    val recovered: Long,
    val deaths: Long,
    val active: Long
)
