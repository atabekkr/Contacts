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

                if (contact.image.isNotEmpty()) {
                    val id = root.context.resources.getIdentifier(
                        contact.image,
                        "drawable",
                        root.context.packageName
                    )
                    ivImage.setImageResource(id)
                } else {
                    ivImage.setImageResource(R.drawable.ic_no_accounts)
                }

                if (contact.isFav == 1) {
                    ivFav.setImageResource(R.drawable.ic_star_filled)
                } else {
                    ivFav.setImageResource(R.drawable.ic_star_border)
                }

                ivFav.setOnClickListener {
                    onFavoriteClick.invoke(contact, adapterPosition)
                }

                ivDelete.setOnClickListener {
                    onDeleteClick.invoke(contact, adapterPosition)
                }

                ivCall.setOnClickListener {
                    onCallClick.invoke(contact)
                }

                cvContact.setOnClickListener {
                    onItemClick.invoke(contact, adapterPosition)
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

    private var onDeleteClick: (contact: Contact, position: Int) -> Unit = { _, _ ->}
    fun setOnDeleteClickListener(onDeleteClick: (contact: Contact, position: Int) -> Unit) {
        this.onDeleteClick = onDeleteClick
    }

    private var onCallClick: (contact: Contact) -> Unit = {}
    fun setOnCallClickListener(onCallClick: (contact: Contact) -> Unit) {
        this.onCallClick = onCallClick
    }

    private var onFavoriteClick: (contact: Contact, position: Int) -> Unit = { _, _ ->}
    fun setOnFavoriteClickListener(onFavoriteClick: (contact: Contact, position: Int) -> Unit) {
        this.onFavoriteClick = onFavoriteClick
    }

    fun removeAtPosition(position: Int) {
        contacts.removeAt(position)
        notifyItemRemoved(position)
    }

    private var onItemClick: (contact: Contact, position: Int) -> Unit = { _, _ ->}
    fun setOnItemClickListener(onItemClick: (contact: Contact, position: Int) -> Unit) {
        this.onItemClick = onItemClick
    }

    fun updateContact(position: Int) {
        val currentContact = contacts[position]
        val newContact = currentContact.copy(isFav = 1 - currentContact.isFav)
        contacts[position] = newContact
        notifyItemChanged(position)
    }
}