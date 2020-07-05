package com.mazur.stationsdistance.extensions

import java.math.BigDecimal
import java.math.RoundingMode

fun Float.round(): Float = BigDecimal(this.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toFloat()