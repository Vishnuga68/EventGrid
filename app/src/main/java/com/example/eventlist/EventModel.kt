package com.example.eventlist

// Data class to represent an event with its details
data class EventModel(
    val eventName: String, // Name of the event
    val eventDescription: String, // Description of the event
    val eventDate: String, // Date when the event takes place
    val eventTime: String, // Time when the event starts
    val eventImage: Int, // Resource ID for the event's image (default image)
    var isSelected: Boolean = false // Boolean flag to track if the event is selected (default is false)
)
