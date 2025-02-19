package com.example.eventlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity responsible for displaying the details of a selected event.
 * It receives event data via Intent and updates the UI accordingly.
 */
class DetailActivity : AppCompatActivity() {

    /**
     * Called when the activity is first created.
     * Retrieves event details from Intent extras and updates the UI components.
     */
    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Retrieve event details from the Intent
        val eventName = intent.getStringExtra("event_name") ?: "Unknown Event"
        val eventDescription = intent.getStringExtra("event_description") ?: "No description available"
        val eventDate = intent.getStringExtra("event_date") ?: "No date provided"
        val eventTime = intent.getStringExtra("event_time") ?: "No time provided"
        val eventImage = intent.getIntExtra("event_image", R.drawable.default_image)

        // Bind UI components to corresponding views in the layout
        val eventTitle = findViewById<TextView>(R.id.event_detail_name)
        val eventImageView = findViewById<ImageView>(R.id.event_detail_image)
        val eventDesc = findViewById<TextView>(R.id.event_detail_description)
        val eventDateView = findViewById<TextView>(R.id.event_detail_date)
        val eventTimeView = findViewById<TextView>(R.id.event_detail_time)

        // Populate the UI components with retrieved event details
        eventTitle.text = eventName
        eventImageView.setImageResource(eventImage)
        eventDesc.text = eventDescription
        eventDateView.text = eventDate
        eventTimeView.text = eventTime
    }
}
