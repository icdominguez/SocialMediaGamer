package com.icdominguez.socialmediagamerkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.icdominguez.socialmediagamerkotlin.adapters.PostAdapter.ViewHolder
import com.icdominguez.socialmediagamerkotlin.common.RelativeTime
import com.icdominguez.socialmediagamerkotlin.databinding.CardviewPostBinding
import com.icdominguez.socialmediagamerkotlin.model.Post
import com.squareup.picasso.Picasso
import java.util.*

class PostAdapter(options: FirestoreRecyclerOptions<Post>, ctx: Context) : FirestoreRecyclerAdapter<Post, ViewHolder>(options) {

    var context: Context = ctx

    inner class ViewHolder(val binding : CardviewPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardviewPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, post: Post) {
        var document = snapshots.getSnapshot(position)

        var postId = document.id

        with(holder) {
            binding.textViewTitlePostCard.text = post.title!!.uppercase(Locale.getDefault())
            binding.textViewDescriptionPostCard.text = post.descripcion

            if(document.contains("timestamp")) {
                var timestamp: Long? = document.getLong("timestamp")
                binding.textViewPostTimestamp.text = RelativeTime().getTimeAgo(timestamp!!, context)
            }

            if(post.image1 != null) {
                Picasso.get().load(post.image1).into(binding.imageViewPostCard)
            }

            itemView.setOnClickListener {

            }

            binding.imageViewLike.setOnClickListener{

            }
        }
    }

}