package com.example.eventlist

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*

/**
 * Activity responsible for adding a new event.
 * This activity allows users to input event details including name, description, date, and time.
 */
class AddEventActivity : AppCompatActivity() {

    // UI components for event input fields
    private lateinit var eventName: EditText
    private lateinit var eventDesc: EditText
    private lateinit var eventDate: EditText
    private lateinit var eventTime: EditText
    private lateinit var addEventButton: Button

    /**
     * Called when the activity is first created.
     * Initializes UI components and sets up event listeners for date and time pickers.
     */
    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        // Initialize UI elements by binding them to corresponding views in XML
        eventName = findViewById(R.id.eventName)
        eventDesc = findViewById(R.id.eventDesc)
        eventDate = findViewById(R.id.eventDate)
        eventTime = findViewById(R.id.eventTime)
        addEventButton = findViewById(R.id.action_add)

        // Set up event listener to show Date Picker Dialog when eventDate field is clicked
        eventDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Display DatePickerDialog and update eventDate field with selected date
            val datePicker = DatePickerDialog(this, R.style.DarkDatePickerDialogTheme , { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                eventDate.setText(formattedDate)
            }, year, month, day)

            datePicker.show()
        }

        // Set up event listener to show Time Picker Dialog when eventTime field is clicked
        eventTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            // Display TimePickerDialog and update eventTime field with selected time
            val timePicker = TimePickerDialog(this, R.style.DarkTimePickerDialogTheme , { _, selectedHour, selectedMinute ->
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                eventTime.setText(formattedTime)
            }, hour, minute, true)

            timePicker.show()
        }

        // Set up event listener for add event button to send event data back to MainActivity
        addEventButton.setOnClickListener {
            val name = eventName.text.toString().trim()
            val description = eventDesc.text.toString().trim()
            val date = eventDate.text.toString().trim()
            val time = eventTime.text.toString().trim()

            // Ensure all fields are filled before sending data back
            if (name.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
                val resultIntent = Intent().apply {
                    putExtra("event_name", name)
                    putExtra("event_description", description)
                    putExtra("event_date", date)
                    putExtra("event_time", time)
                }
                setResult(RESULT_OK, resultIntent) // Return data to calling activity
                finish() // Close activity
            }
        }
    }
}
