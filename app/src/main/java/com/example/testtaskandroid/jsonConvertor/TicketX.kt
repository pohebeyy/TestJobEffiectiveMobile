package com.example.testtaskandroid.jsonConvertor

data class TicketX(
    val arrival: Arrival,
    val badge: String,
    val company: String,
    val departure: Departure,
    val hand_luggage: HandLuggage,
    val has_transfer: Boolean,
    val has_visa_transfer: Boolean,
    val id: Int,
    val is_exchangable: Boolean,
    val is_returnable: Boolean,
    val luggage: Luggage,
    val price: PriceX,
    val provider_name: String
)