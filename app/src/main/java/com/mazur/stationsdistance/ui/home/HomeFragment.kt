package com.mazur.stationsdistance.ui.home

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.lifecycle.ViewModelProviders
import com.mazur.stationsdistance.R
import com.mazur.stationsdistance.extensions.round
import com.mazur.stationsdistance.helpers.Preferences
import com.mazur.stationsdistance.ui.BaseFragment
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*

class HomeFragment : BaseFragment(), HomeView {
    private lateinit var presenter: HomePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        presenter = ViewModelProviders.of(this).get(HomePresenter::class.java)
        presenter.view = this

        view.show_btn.setOnClickListener {
            presenter.getStationsFromFields(view.txt_from, view.txt_destination)
        }

        val lastCachedStationTime: Calendar? = Hawk.get(Preferences.LAST_STATIONS_CACHE)
        val isActual =
            ((lastCachedStationTime != null) && (DateUtils.isToday(lastCachedStationTime.timeInMillis)))
        if (!Hawk.contains(Preferences.CACHED_STATIONS) || !isActual)
            getStationsList()

        insertStationsIntoSelect(view.txt_from, view.txt_destination)

        return view
    }

    override fun insertStationsIntoSelect(
        txtFrom: AutoCompleteTextView?,
        txtDestination: AutoCompleteTextView?
    ) {
        presenter.fillStationsFields(context, txtFrom, txtDestination)
    }

    override fun getCurrentContext() = context

    override fun getStationsList() {
        presenter.requestForStations()
    }

    @SuppressLint("SetTextI18n")
    override fun showDistance(
        firstLatitude: Double?, firstLongitude: Double?,
        lastLatitude: Double?, lastLongitude: Double?
    ) {
        if (firstLatitude != null && firstLongitude != null
            && lastLatitude != null && lastLongitude != null
        ) {
            val fromStation = Location("fromStation")
            fromStation.latitude = firstLatitude
            fromStation.longitude = firstLongitude

            val destinationStation = Location("destinationStation")
            destinationStation.latitude = lastLatitude
            destinationStation.longitude = lastLongitude

            val distanceDiff: Float = fromStation.distanceTo(destinationStation) / 1000
            view?.distance_number?.text = "${distanceDiff.round()}km"
        }
    }

}