package com.example.motivationapp.infra

class MotivationAppConstants private constructor() {

    /**
     * The private constructor does not allow this class can be instantiate
     */

    object KEY {
        val USER_NAME = "username"
    }

    object PHRASES_FILTER {
        val ALL = 1
        val HAPPY = 2
        val MORNING = 3
    }
}