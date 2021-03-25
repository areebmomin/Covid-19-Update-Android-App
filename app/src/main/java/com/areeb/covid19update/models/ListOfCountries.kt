package com.areeb.covid19update.models

data class ListOfCountries(
    val name: String,
    val alpha2code: String,
    val alpha3code: String,
    val latitude: Double,
    val longitude: Double
)
