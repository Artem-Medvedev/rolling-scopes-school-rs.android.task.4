package com.example.anothertask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list.view.*


class AdapterNotes: RecyclerView.Adapter<AdapterNotes.ViewHolder>() {

    private var notes = emptyList<Notes>()
    var OnItemClick: ((Notes)->Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val  currentItem = notes[position]
        holder.itemView.tvNoteName.text = currentItem.noteName
        holder.itemView.tvNoteSurname.text = currentItem.noteSurname
        holder.itemView.tvNoteAge.text = currentItem.noteAge.toString()

        holder.itemView.setOnClickListener {
            OnItemClick?.invoke(notes[position])
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setData(notes: List<Notes>) {
        this.notes = notes
        notifyDataSetChanged()
    }

}
