package com.justaccounts.mindbrowseandroidtest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.justaccounts.mindbrowseandroidtest.R
import com.justaccounts.mindbrowseandroidtest.interfaces.DeleteContactListener
import com.justaccounts.mindbrowseandroidtest.model.DeletedPhoneContact
import kotlinx.android.synthetic.main.contact_item_row.view.nameTv
import kotlinx.android.synthetic.main.contact_item_row.view.phoneTv
import kotlinx.android.synthetic.main.contact_item_row.view.profilePhoto
import kotlinx.android.synthetic.main.fav_contact_item_row.view.*

class DeletedContactListAdapter(val context: Context,val deleteContactListener: DeleteContactListener): RecyclerView.Adapter<DeletedContactListAdapter.MyViewHolder>() {

    private var contactList = emptyList<DeletedPhoneContact>()

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
        holder.itemView.contact_row.setOnClickListener {
            deleteContactListener.onDeleteContactClick(currentItem,position)
        }
//        holder.itemView.delete.setOnClickListener {
//            contactListener.onDeleteClick(currentItem,position)
//        }
    }

    fun setData(user: List<DeletedPhoneContact>){
        this.contactList = user
        notifyDataSetChanged()
    }

    fun refreshRemovedData(user: List<DeletedPhoneContact>,position:Int){
        this.contactList = user
        notifyItemRemoved(position)
    }
}