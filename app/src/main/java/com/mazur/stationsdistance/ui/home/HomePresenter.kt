package com.mazur.stationsdistance.ui.home;

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.mazur.stationsdistance.R
import com.mazur.stationsdistance.adapters.StationsAdapter
import com.mazur.stationsdistance.api.ApiClient
import com.mazur.stationsdistance.helpers.Preferences
import com.mazur.stationsdistance.model.Station
import com.orhanobut.hawk.Hawk
import es.dmoral.toasty.Toasty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class HomePresenter : ViewModel() {
    var view: HomeView? = null

    @SuppressLint("CheckResult")
    fun requestForStations() {
        ApiClient.getClient(Preferences.API_URL).getStations()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isSuccessful) {
                    cacheNewStations(it.body())
                    Log.i("COLEO_INFO", "Requested new station list")
                }
            }) {
                Log.d("COLEO_EXCEPTION", "Failed while requesting stations: ${it.localizedMessage}")
            }
    }

    private fun cacheNewStations(stationList: ArrayList<Station>?) {
        val currentTime: Calendar = Calendar.getInstance()

        Hawk.put(Preferences.CACHED_STATIONS, stationList)
        Hawk.put(Preferences.LAST_STATIONS_CACHE, currentTime)
    }

    fun fillStationsFields(
        context: Context?,
        txtFrom: AutoCompleteTextView?,
        txtDestination: AutoCompleteTextView?
    ) {
        if (txtFrom != null && txtDestination != null) {
            val cachedStations: ArrayList<Station>? = Hawk.get(Preferences.CACHED_STATIONS)
            if (cachedStations != null && context != null) {
                val adapter = StationsAdapter(context, cachedStations)

                txtFrom.setAdapter(adapter)
                txtDestination.setAdapter(adapter)
            }
        }
    }

    fun getStationsFromFields(
        txtFrom: AutoCompleteTextView?,
        txtDestination: AutoCompleteTextView?
    ) {
        val stations: ArrayList<Station>? = Hawk.get(Preferences.CACHED_STATIONS)
        var from: Station? = null
        var destination: Station? = null
        if (stations != null)
            for (station in stations) {
                if (station.name == txtFrom?.text.toString())
                    from = station
                if (station.name == txtDestination?.text.toString())
                    destination = station
            }

        if (from != null && destination != null)
            view?.showDistance(
                from.latitude,
                from.longitude,
                destination.latitude,
                destination.longitude
            )
        else
            Toasty.error(
                view?.getCurrentContext()!!,
                R.string.not_found_error,
                Toast.LENGTH_SHORT,
                true
            ).show();
    }


}
