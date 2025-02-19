package com.example.eventlist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

// MainActivity class to manage the event list and interactions in the main screen
class MainActivity : AppCompatActivity() {

    private lateinit var eventList: RecyclerView // RecyclerView to display the list of events
    private lateinit var adapter: EventAdapter // Adapter to bind event data to RecyclerView
    private val eventData = arrayListOf<EventModel>() // List to hold event data
    private var menuDelete: MenuItem? = null // Menu item for delete option

    // Launcher to handle the result from AddEventActivity when a new event is added
    private val addEventLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null) {
                    // Get event details from the returned data
                    val eventName = data.getStringExtra("event_name") ?: return@registerForActivityResult
                    val eventDescription = data.getStringExtra("event_description") ?: getString(R.string.desc_default)
                    val eventDate = data.getStringExtra("event_date") ?: "No Date"
                    val eventTime = data.getStringExtra("event_time") ?: "No Time"

                    // Create a new EventModel object and add it to the adapter
                    val newEvent = EventModel(eventName, eventDescription, eventDate, eventTime, R.drawable.default_image)
                    adapter.addEvent(newEvent)
                }
            }
        }

    // onCreate method to initialize the activity and setup views
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView and set its layout manager
        eventList = findViewById(R.id.eventList)
        eventList.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter and set it to the RecyclerView
        adapter = EventAdapter(this, eventData)
        eventList.adapter = adapter

        // Load default events into the list
        loadDefaultEvents()

        // Set up the toolbar and enable title display
        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }

    // Handle selection of options in the app's menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Handle add event action
            R.id.action_add -> {
                val intent = Intent(this, AddEventActivity::class.java)
                addEventLauncher.launch(intent) // Launch the AddEventActivity for result
                true
            }
            // Handle delete selected events action
            R.id.action_delete -> {
                deleteSelectedEvents() // Delete selected events
                true
            }
            else -> super.onOptionsItemSelected(item) // Fallback for other menu items
        }
    }

    // Inflate the options menu and set up the delete menu item visibility
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu) // Inflate menu XML
        menuDelete = menu?.findItem(R.id.action_delete) // Find the delete item in the menu
        updateSelectionMenu() // Update the visibility of the delete icon
        return true
    }

    // Update the visibility of the delete menu item based on event selection
    fun updateSelectionMenu() {
        // Check if any event is selected
        val isAnyEventSelected = eventData.any { it.isSelected }
        menuDelete?.isVisible = isAnyEventSelected // Show delete menu item only if an event is selected
    }

    // Delete all selected events from the list
    private fun deleteSelectedEvents() {
        adapter.removeSelectedEvents() // Remove selected events from the adapter
        updateSelectionMenu() // Hide delete icon after deletion
    }

    // Load a set of default events into the list
    private fun loadDefaultEvents() {
        // Add default event data to the event list
        eventData.addAll(
            listOf(
                EventModel("Sports Tournaments", getString(R.string.desc_sports), "05/12/2025", "02:30 PM", R.drawable.sports),
                EventModel("Music Festivals", getString(R.string.desc_music), "12/11/2025", "06:00 PM", R.drawable.music),
                EventModel("Charity Events", getString(R.string.desc_charity), "20/09/2025", "09:00 AM", R.drawable.charity),
                EventModel("Online Workshops", getString(R.string.desc_webinar), "08/08/2025", "04:00 PM", R.drawable.webinar),
                EventModel("Cultural Festivals", getString(R.string.desc_cultural), "15/10/2025", "07:30 PM", R.drawable.cultural),
                EventModel("Weddings & Engagements", getString(R.string.desc_wedding), "25/06/2025", "05:00 PM", R.drawable.wedding),
                EventModel("Coding Challenges", getString(R.string.desc_coding), "30/07/2025", "11:15 AM", R.drawable.coding),
                EventModel("Business Expos", getString(R.string.desc_business), "10/09/2025", "01:45 PM", R.drawable.business),
                EventModel("Science Fairs", getString(R.string.desc_science), "18/05/2025", "03:30 PM", R.drawable.science),
                EventModel("University Reunions", getString(R.string.desc_university), "22/11/2025", "06:45 PM", R.drawable.university),
                EventModel("Gaming Tournaments", getString(R.string.desc_game), "05/07/2025", "10:30 AM", R.drawable.game),
                EventModel("Movie Premieres", getString(R.string.desc_movie), "29/12/2025", "08:00 PM", R.drawable.movie),
                EventModel("Personal Parties", getString(R.string.desc_parties), "14/04/2025", "09:15 PM", R.drawable.parties),
                EventModel("Swimming", getString(R.string.desc_exercise), "27/08/2025", "07:00 AM", R.drawable.swimming),
                EventModel("Exercise", getString(R.string.desc_swimming), "03/03/2025", "05:30 AM", R.drawable.exersice)
            )
        )
        adapter.notifyItemRangeInserted(0, eventData.size) // Notify adapter about the new items added
    }
}
