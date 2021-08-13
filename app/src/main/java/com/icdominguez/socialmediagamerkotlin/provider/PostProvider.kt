package com.icdominguez.socialmediagamerkotlin.provider

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PostProvider {
    var collection: CollectionReference = FirebaseFirestore.getInstance().collection("posts")

    fun getAll() : Query {
        return collection.orderBy("timestamp", Query.Direction.DESCENDING)
    }
}