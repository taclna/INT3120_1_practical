package com.example.unit2_pathway1_thuchanhcoban

fun main() {
    val sontungmtpSong = Song("Chung ta khong thuoc ve nhau", "Son tung mtp", 2015, 4_000_000)
    println(sontungmtpSong.isPopular)
    sontungmtpSong.printDescription()
}


class Song(
    val title: String,
    val artist: String,
    val yearPublished: Int,
    val playCount: Int
){
    val isPopular: Boolean
        get() = playCount >= 1000

    fun printDescription() {
        println("$title, performed by $artist, was released in $yearPublished.")
    }
}