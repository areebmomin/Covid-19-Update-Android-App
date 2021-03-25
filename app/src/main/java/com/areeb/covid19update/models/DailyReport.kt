package com.areeb.covid19update.models

data class DailyReport(
    val active: Long,
    val confirmed: Long,
    val critical: Long,
    val date: String,
    val deaths: Long,
    val recovered: Long
)