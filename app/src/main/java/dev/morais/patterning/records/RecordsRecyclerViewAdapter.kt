package dev.morais.patterning.records

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.morais.patterning.R
import java.text.SimpleDateFormat

class RecordsRecyclerViewAdapter(
    private val context: Context?,
    private val records: List<Record>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listeners = mutableListOf<(record: Record) -> Unit>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(context).inflate(R.layout.list_item_record, parent, false).let {
            RecyclerViewViewHolder(it)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        records[position].let { record ->
            if (holder is RecyclerViewViewHolder) {
                holder.recordIcon.setImageResource(R.drawable.ic_menu_gallery)
                holder.recordType.text = record.type

                val dateFormat = SimpleDateFormat("yyyy-MM-dd @ hh:mm:ss")
                holder.recordDate.text = dateFormat.format(record.date)
                holder.recordDelete.setOnClickListener {
                    listeners.forEach { it(record) }
                }
            }
        }
    }

    fun onDeleteClickListener(block: (record: Record) -> Unit) {
        listeners.add(block)
    }

    fun deleteAllListeners() = listeners.clear()

    override fun getItemCount() = records.size

    class RecyclerViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recordIcon: ImageView = itemView.findViewById(R.id.record_icon)
        var recordDate: TextView = itemView.findViewById(R.id.record_date)
        var recordType: TextView = itemView.findViewById(R.id.record_type)
        var recordDelete: ImageButton = itemView.findViewById(R.id.record_delete)
    }
}