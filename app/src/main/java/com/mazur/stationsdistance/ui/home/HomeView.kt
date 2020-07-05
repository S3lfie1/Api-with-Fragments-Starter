package com.mazur.stationsdistance.ui.home;

import android.content.Context
import android.widget.AutoCompleteTextView

interface HomeView {
    fun getCurrentContext(): Context?
    fun getStationsList()
    fun showDistance(
        firstLatitude: Double?,
        firstLongitude: Double?,
        lastLatitude: Double?,
        lastLongitude: Double?
    )
    fun insertStationsIntoSelect(
        txtFrom: AutoCompleteTextView?,
        txtDestination: AutoCompleteTextView?
    )
}