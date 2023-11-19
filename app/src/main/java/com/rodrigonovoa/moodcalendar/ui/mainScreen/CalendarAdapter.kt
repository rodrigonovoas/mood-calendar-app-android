package com.rodrigonovoa.moodcalendar.ui.mainScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rodrigonovoa.moodcalendar.R
import com.rodrigonovoa.moodcalendar.database.data.MoodWithDayEntity

class CalendarAdapter(private val moodList: List<MoodWithDayEntity>) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>()
{

    var onMoodClick: ((MoodWithDayEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_mood, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = moodList[position]
        val context = holder.ivMood.context

        when (item.moodId) {
            1 -> {
                holder.ivMood.setImageDrawable(context.getDrawable(R.drawable.ic_happy_mood))
            }
            2 -> {
                holder.ivMood.setImageDrawable(context.getDrawable(R.drawable.ic_neutral_mood))
            }
            3 -> {
                holder.ivMood.setImageDrawable(context.getDrawable(R.drawable.ic_sad_mood))
            }
            else -> {
                println("Invalid selection")
            }
        }

        holder.tvMood.text = (position + 1).toString()
        holder.ivMood.setOnClickListener { onMoodClick?.invoke(item) }
    }

    override fun getItemCount(): Int {
        return moodList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val ivMood: ImageView = itemView.findViewById(R.id.imv_mood)
        val tvMood: TextView = itemView.findViewById(R.id.tv_mood)
    }
}