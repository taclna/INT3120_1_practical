package com.example.unit2_pathway1_thuchanhcoban

fun main() {
    val morningNotification = 51
    val eveningNotification = 135

    printNotificationSummary(morningNotification)
    printNotificationSummary(eveningNotification)
}

fun printNotificationSummary(numberOfMessages: Int) {
    // Fill in the code.
    if (numberOfMessages < 100) {
        print("You have $numberOfMessages notifications.\n")
    } else {
        print("Your phone is blowing up! You have 99+ notifications.\n")
    }
}