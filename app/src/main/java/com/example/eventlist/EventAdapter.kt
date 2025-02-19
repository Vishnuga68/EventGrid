package com.example.eventlist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

// EventAdapter class to bind event data to RecyclerView items
class EventAdapter(
    private val context: Context,  // Context for starting activities and accessing resources
    private val eventList: MutableList<EventModel>   // List of events to be displayed
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    // ViewHolder class holds references to views for each event item in the RecyclerView
    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // UI components that represent the event's image, name, date, and time
        val eventImage: ImageView? = itemView.findViewById(R.id.eventImage)
        val eventName: TextView? = itemView.findViewById(R.id.eventName)
        val eventDate: TextView? = itemView.findViewById(R.id.eventDate)
        val eventTime: TextView? = itemView.findViewById(R.id.eventTime)
        val linearLayout: LinearLayout? = itemView.findViewById(R.id.linearLayout) // Root layout

        init {
            // Handle item click to navigate to the event details page
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val event = eventList[position]

                    // Open detail activity only if not in selection mode
                    if (!event.isSelected) {
                        val intent = Intent(context, DetailActivity::class.java).apply {
                            putExtra("event_name", event.eventName)
                            putExtra("event_description", event.eventDescription)
                            putExtra("event_date", event.eventDate)
                            putExtra("event_time", event.eventTime)
                            putExtra("event_image", event.eventImage)
                        }
                        context.startActivity(intent)
                    }
                }
            }

            // Handle long-click to toggle selection
            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val event = eventList[position]
                    event.isSelected = !event.isSelected // Toggle selection

                    // Notify MainActivity to update selection menu visibility
                    (context as? MainActivity)?.updateSelectionMenu()

                    // Refresh the item to reflect the updated selection state
                    notifyItemChanged(position) // Refresh item
                    return@setOnLongClickListener true
                }
                false
            }
        }
    }

    // Create a new ViewHolder when a new item is created for the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        // Inflate the layout for each event item
        val view = LayoutInflater.from(context).inflate(R.layout.item_events, parent, false)
        return EventViewHolder(view)
    }

    // Bind event data to the corresponding views in the ViewHolder
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]

        try {
            // Set image, name, date, and time for the event item
            holder.eventImage?.setImageResource(event.eventImage)
            holder.eventName?.text = event.eventName
            holder.eventDate?.text = "Date: ${event.eventDate}"
            holder.eventTime?.text = "Time: ${event.eventTime}"

            // Update background color based on selection
            if (event.isSelected) {
                holder.linearLayout?.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_item_background))
            } else {
                holder.linearLayout?.setBackgroundColor(ContextCompat.getColor(context, R.color.white)) // Default color
            }


        } catch (e: NullPointerException) {
            // Log any errors related to null pointer exceptions for debugging
            Log.e("EventAdapter", "NullPointerException: ${e.message}")
        }
    }

    // Return the total number of events in the list
    override fun getItemCount(): Int = eventList.size

    // Add a new event to the list and notify the adapter
    fun addEvent(event: EventModel) {
        eventList.add(event)
        notifyItemInserted(eventList.size - 1)
    }

    // Remove all selected events from the list and refresh the RecyclerView
    fun removeSelectedEvents() {
        eventList.removeAll { it.isSelected }
        notifyDataSetChanged()
    }
}
