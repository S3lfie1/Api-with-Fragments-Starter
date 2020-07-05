package com.mazur.stationsdistance.adapters;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.mazur.stationsdistance.R
import com.mazur.stationsdistance.model.Station
import kotlinx.android.synthetic.main.item_station_layout.view.*

class StationsAdapter(context: Context, private var stations: ArrayList<Station>) :
    ArrayAdapter<Station>(context, R.layout.item_station_layout, stations) {

    var filtered = ArrayList<Station>()

    override fun getCount() = filtered.size

    override fun getItem(position: Int) = filtered[position]

    override fun getFilter() = filter

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return convertView ?: createView(position, parent)
    }

    private fun createView(position: Int, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_station_layout, parent, false)
        view.target_name.text = filtered[position].name

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_station_layout, parent, false)
        convertView?.target_name?.text = filtered[position].name

        return super.getDropDownView(position, convertView, parent)
    }

    private var filter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()

            val query =
                if (constraint != null && constraint.isNotEmpty()) autocomplete(constraint.toString())
                else arrayListOf()

            results.values = query
            results.count = query.size

            return results
        }

        private fun autocomplete(input: String): ArrayList<Station> {
            val results = arrayListOf<Station>()

            for (station in stations) {
                if (station.name.toLowerCase().contains(input.toLowerCase())) results.add(station)
            }

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            filtered = results.values as ArrayList<Station>
            notifyDataSetInvalidated()
        }

        override fun convertResultToString(result: Any) = (result as Station).name
    }
}