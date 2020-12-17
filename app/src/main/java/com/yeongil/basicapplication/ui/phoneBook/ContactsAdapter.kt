package com.yeongil.basicapplication.ui.phoneBook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yeongil.basicapplication.data.Contact
import com.yeongil.basicapplication.databinding.ItemContactBinding

class ContactsAdapter : ListAdapter<Contact, ContactsAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    class ViewHolder(private val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {
        private val profileImg = binding.profileImage
        private val name = binding.name
        private val phoneNumber = binding.phoneNumber

        fun bind(item: Contact, position: Int) {
            name.text = item.name
            phoneNumber.text = item.phoneNumber
//            binding.root.setOnClickListener
//            binding.setVariable(BR.)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }
}