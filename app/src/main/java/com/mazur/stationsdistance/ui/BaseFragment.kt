package com.mazur.stationsdistance.ui

import android.content.Context
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment(){
    var navigator : Navigator? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = context as? Navigator
    }
}