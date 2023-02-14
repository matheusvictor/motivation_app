package com.example.motivationapp.infra

class MotivationAppConstants private constructor() {

    /**
     * The private constructor does not allow this class can be instantiate
     */

    object KEY {
        val USER_NAME = "username"
        val TEMP_USER_NAME = ""
    }

    object PHRASES_FILTER {
        val GOOD_VIBES = 1
        val BAD_VIBES = 2
    }
}