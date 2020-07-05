package com.mazur.stationsdistance.api

import com.mazur.stationsdistance.model.Station
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v1/stations")
    fun getStations(): Single<Response<ArrayList<Station>>>
}