package com.example.unit3_pathway1_lopvabosuutap

data class Event(
    val title: String,
    val description: String? = null,
    val daypart: Daypart,
    val durationInMinutes: Int
) {
    //Nv7
    val durationOfEvent: String
        get() = if (durationInMinutes < 60) "short" else "long"
}
