package com.justaccounts.mindbrowseandroidtest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.justaccounts.mindbrowseandroidtest.R
import com.justaccounts.mindbrowseandroidtest.interfaces.ContactListener
import com.justaccounts.mindbrowseandroidtest.model.PhoneContact
import kotlinx.android.synthetic.main.contact_item_row.view.*

class FavoriteContactListAdapter(val context: Context): RecyclerView.Adapter<FavoriteContactListAdapter.MyViewHolder>() {

    private var contactList = emptyList<PhoneContact>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fav_contact_item_row,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = contactList[position]
        holder.itemView.nameTv.text = currentItem.name
        holder.itemView.phoneTv.text = currentItem.mobileNo
        Glide.with(context)
            .load(currentItem.profile)
            .placeholder(R.drawable.no_profile_avaliable)
            .centerCrop()
            .into(holder.itemView.profilePhoto)
    }

    fun setData(user: List<PhoneContact>){
        this.contactList = user
        notifyDataSetChanged()
    }
}