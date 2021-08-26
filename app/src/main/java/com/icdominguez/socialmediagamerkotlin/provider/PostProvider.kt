package com.icdominguez.socialmediagamerkotlin.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.icdominguez.socialmediagamerkotlin.model.Post

class PostProvider {
    var collection: CollectionReference = FirebaseFirestore.getInstance().collection("posts")

    fun getAll() : Query {
        return collection.orderBy("timestamp", Query.Direction.DESCENDING)
    }

    fun add(post: Post) : Task<Void> {
        var document = collection.document()
        var postId = document.id
        post.postId = postId
        return collection.document().set(post)
    }
}