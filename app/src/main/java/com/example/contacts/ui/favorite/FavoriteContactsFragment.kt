package com.example.contacts.ui.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.contacts.R
import com.example.contacts.data.Contact
import com.example.contacts.data.ContactDao
import com.example.contacts.data.ContactDatabase
import com.example.contacts.databinding.FragmentContactsFavoriteBinding
import com.example.contacts.ui.ContactAdapter
import com.example.contacts.ui.all.AddContactDialog
import com.example.contacts.ui.all.EditContactFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class FavoriteContactsFragment: Fragment(R.layout.fragment_contacts_favorite) {
    private lateinit var binding: FragmentContactsFavoriteBinding
    private lateinit var dao: ContactDao
    private val adapter = ContactAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactsFavoriteBinding.bind(view)

        dao = ContactDatabase.getInstance(requireContext()).getContactDao()

        binding.apply {
            recyclerView.adapter = adapter

            adapter.contacts = dao.getFavorite().toMutableList()

            adapter.setOnDeleteClickListener {  contact, position ->
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Delete Contact")
                    .setMessage("${contact.name}-di oshirip tastayiqpa?")
                    .setPositiveButton("Yes") { dialog, _ ->
                        dao.deleteContact(contact)
                        adapter.removeAtPosition(position)
                        Snackbar.make(recyclerView, "Contact deleted successfully!", Snackbar.LENGTH_SHORT)
                            .show()
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

            adapter.setOnFavoriteClickListener { contact, position ->
                val newContact = contact.copy(isFav =  1 - contact.isFav)
                dao.updateContact(newContact)
                adapter.removeAtPosition(position)
            }

            adapter.setOnCallClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${it.phone}")
                startActivity(intent)
            }

            adapter.setOnItemClickListener { contact, position ->
                val bundle = Bundle()

                bundle.putInt("id", contact.id)
                bundle.putString("name", contact.name)
                bundle.putString("phone", contact.phone)
                bundle.putString("image", contact.image)
                bundle.putInt("isFavorite", contact.isFav)

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, EditContactFragment::class.java, bundle)
                    .addToBackStack(EditContactFragment::class.java.simpleName)
                    .commit()
            }
        }
    }
}