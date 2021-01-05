package com.example.githubusersubmission3.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusersubmission3.R
import com.example.githubusersubmission3.Utility
import com.example.githubusersubmission3.data.Items
import kotlinx.android.synthetic.main.list_item.view.*

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.UserViewHolder>() {

    private val mData = ArrayList<Items>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(item: ArrayList<Items>?) {
        if (item != null) {
            mData.clear()
            mData.addAll(item)
            notifyDataSetChanged()
        } else {
            mData.clear()
            notifyDataSetChanged()
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(items: Items) {

            with(itemView) {
                usernameUser.text = items.username
                typeUser.text = items.type
                Utility.setImageGlideFromAdapter(itemView.context,items.avatar,avatarUser)
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(items) }

                val anim = AnimationUtils.loadAnimation(context, R.anim.recyclerview_anim)
                itemView.animation = anim
            }
        }
    }
}

interface OnItemClickCallback {
    fun onItemClicked(items: Items)
}