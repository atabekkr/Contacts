package com.example.contacts.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.contacts.R
import com.example.contacts.data.Contact
import com.example.contacts.databinding.ItemContactBinding

class ContactAdapter: Adapter<ContactAdapter.ContactViewHolder>() {
    inner class ContactViewHolder(private var binding: ItemContactBinding): ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.apply {
                tvName.text = contact.name
                tvPhone.text = contact.phone

                ivDelete.setOnClickListener {
                    onDeleteClick.invoke(contact)
                    contacts.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }

                ivCall.setOnClickListener {
                    onCallClick.invoke(contact)
                }
            }
        }
    }

    var contacts = mutableListOf<Contact>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = contacts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        val binding = ItemContactBinding.bind(v)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    private var onDeleteClick: (contact: Contact) -> Unit = {}
    fun setOnDeleteClickListener(onDeleteClick: (contact: Contact) -> Unit) {
        this.onDeleteClick = onDeleteClick
    }

    private var onCallClick: (contact: Contact) -> Unit = {}
    fun setOnCallClickListener(onCallClick: (contact: Contact) -> Unit) {
        this.onCallClick = onCallClick
    }
}