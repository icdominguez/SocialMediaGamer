package com.icdominguez.socialmediagamerkotlin.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.icdominguez.socialmediagamerkotlin.model.Like
import com.icdominguez.socialmediagamerkotlin.model.Post
import com.icdominguez.socialmediagamerkotlin.provider.AuthProvider
import com.icdominguez.socialmediagamerkotlin.provider.LikeProvider
import com.icdominguez.socialmediagamerkotlin.provider.PostProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel : ViewModel() {

    private var auth = AuthProvider()
    private var likeProvider = LikeProvider()

    fun getAllPosts(): FirestoreRecyclerOptions<Post> {
        var query: Query = PostProvider().getAll()
        return FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()
    }

    fun likePost(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var like = Like()
                like.postId = postId
                like.timestamp = Date().time


                likeProvider.create(like)
            } catch (e: Exception) {

            }
        }
    }

    fun logOut() {
        auth.logOut()
    }
}