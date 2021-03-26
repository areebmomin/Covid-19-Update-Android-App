package com.areeb.covid19update.models

data class LatestTotals(
    val confirmed: Long,
    val critical: Long,
    val deaths: Long,
    val lastChange: String,
    var lastUpdate: String,
    val recovered: Long
)
