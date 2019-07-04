package com.serge.moneyme.service

import kotlin.math.pow


class PMTHelper {
    companion object {
        fun calculatePMT(R: Double, n: Int, Pv: Double): Double {
            val pvTimesR = (Pv * R)
            val onePlusR = 1 + R
            val negativeN = n * -1

            val denominator = (1 - (onePlusR.pow(negativeN)))

            return pvTimesR / denominator
        }
    }
}