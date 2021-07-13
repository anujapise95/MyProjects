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

class ContactListAdapter(val context: Context,val contactListener: ContactListener): RecyclerView.Adapter<ContactListAdapter.MyViewHolder>() {

    private var contactList = emptyList<PhoneContact>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.contact_item_row,
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
        if (currentItem.profile!=null){
            holder.itemView.profilePhoto.visibility = View.VISIBLE
            holder.itemView.noProfileText.visibility = View.INVISIBLE
            Glide.with(context)
                .load(currentItem.profile)
                .placeholder(R.drawable.no_profile_avaliable)
                .centerCrop()
                .into(holder.itemView.profilePhoto)
        }else{
            holder.itemView.profilePhoto.visibility = View.INVISIBLE
            holder.itemView.noProfileText.visibility = View.VISIBLE
            holder.itemView.noProfileText.text = currentItem.name.substring(0,1)
        }
        holder.itemView.fav.isChecked = currentItem.isFav
        holder.itemView.fav.setOnClickListener {
            if(currentItem.isFav){
                currentItem.isFav = false
            }else{
                currentItem.isFav = true
            }
            contactListener.onFavClick(currentItem)
        }
        holder.itemView.delete.setOnClickListener {
            contactListener.onDeleteClick(currentItem,position)
        }
        holder.itemView.contact_row.setOnClickListener {
            contactListener.onRowClick(currentItem)
        }
    }

    fun setData(user: List<PhoneContact>){
        this.contactList = user
        notifyDataSetChanged()
    }

    fun refreshRemovedData(user: List<PhoneContact>,position:Int){
        this.contactList = user
        notifyItemRemoved(position)
    }
}