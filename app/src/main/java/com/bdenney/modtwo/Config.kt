package com.bdenney.modtwo

class Config {
    companion object {
        // The amount of time between attempts allowed in minutes
        const val MINUTE_DURATION_BETWEEN_ATTEMPTS = 1L
        // How often we should remind the user in minutes
        const val MINUTE_REMINDER_PERIOD = 1L
    }
}