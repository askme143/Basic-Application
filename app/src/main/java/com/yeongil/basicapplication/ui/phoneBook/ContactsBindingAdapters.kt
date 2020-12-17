package com.yeongil.basicapplication.ui.phoneBook

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeongil.basicapplication.data.Contact

object ContactsBindingAdapters {
    @BindingAdapter("contact_list")
    @JvmStatic
    fun bindRecyclerView(recyclerView: RecyclerView, data: List<Contact>?) {
        if (recyclerView.adapter == null) {
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.adapter = ContactsAdapter()
        }
        (recyclerView.adapter as ContactsAdapter).submitList(data)
    }
}