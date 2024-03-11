package com.bdenney.modtwo

class Config {
    companion object {
        // The amount of time between attempts allowed in minutes
        const val MINUTE_DURATION_BETWEEN_ATTEMPTS = 1L
        // How often we should remind the user in minutes
        const val MINUTE_REMINDER_PERIOD = 1L
        // The minimum value of the range presented to the user
        const val MIN_NUM_RANGE = 1
        // The maximum value of the range presented to the user
        const val MAX_NUM_RANGE = 100
        // The number of numbers presented to the user
        const val NUM_IN_SERIES = 10
    }
}