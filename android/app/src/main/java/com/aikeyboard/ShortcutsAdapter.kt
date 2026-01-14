package com.aikeyboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShortcutsAdapter(
    private val shortcuts: List<Shortcut>,
    private val onAction: (Shortcut, String) -> Unit
) : RecyclerView.Adapter<ShortcutsAdapter.ShortcutViewHolder>() {
    
    inner class ShortcutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val triggerText: TextView = itemView.findViewById(R.id.trigger_text)
        val expansionText: TextView = itemView.findViewById(R.id.expansion_text)
        val deleteButton: Button = itemView.findViewById(R.id.delete_button)
        val editButton: Button = itemView.findViewById(R.id.edit_button)
        
        fun bind(shortcut: Shortcut) {
            triggerText.text = shortcut.trigger
            expansionText.text = shortcut.expansion
            
            deleteButton.setOnClickListener {
                onAction(shortcut, "delete")
            }
            
            editButton.setOnClickListener {
                onAction(shortcut, "edit")
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortcutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shortcut, parent, false)
        return ShortcutViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ShortcutViewHolder, position: Int) {
        holder.bind(shortcuts[position])
    }
    
    override fun getItemCount(): Int = shortcuts.size
}