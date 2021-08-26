package com.icdominguez.socialmediagamerkotlin.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.icdominguez.socialmediagamerkotlin.model.Like
import com.icdominguez.socialmediagamerkotlin.model.Post
import com.icdominguez.socialmediagamerkotlin.model.PostPrint
import com.icdominguez.socialmediagamerkotlin.provider.AuthProvider
import com.icdominguez.socialmediagamerkotlin.provider.LikeProvider
import com.icdominguez.socialmediagamerkotlin.provider.PostProvider
import com.icdominguez.socialmediagamerkotlin.provider.UserProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Dispatcher
import java.util.*

class HomeViewModel : ViewModel() {

    private var auth = AuthProvider()
    private var likeProvider = LikeProvider()
    private var userProvider = UserProvider()

    var listPosts = MutableLiveData<PostPrint>()

    fun getAllPosts(): FirestoreRecyclerOptions<Post> {
        var query: Query = PostProvider().getAll()
        return FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()
    }

    fun getPosts() {

        PostProvider().getAll().get().addOnSuccessListener { posts ->
            createPostPrint(posts)
        }

    }

    private fun createPostPrint(posts: QuerySnapshot) {
        runBlocking {
            for(document in posts) {
                var postPrint = PostPrint(
                    0,
                    "",
                    document.getString("title")!!,
                    document.getString("description")!!,
                    document.getString("image1")!!,
                    false,
                    document.getLong("timestamp")!!)

                launch(Dispatchers.IO) {
                    getNumberLikesByPost(document.id, postPrint)
                }

                launch(Dispatchers.IO) {
                    getUserInfo(document.getString("userId")!!, postPrint)
                }

                launch(Dispatchers.IO) {
                    checkIfLikeExist(document.id, document.getString("userId")!!, postPrint)
                }

                listPosts.postValue(postPrint)
            }
        }
    }

    fun likePost(postId: String) {
        try {
            var like = Like()
            like.postId = postId
            like.timestamp = Date().time

            likeProvider.create(like)
        } catch (e: Exception) {

        }
    }

    fun getNumberLikesByPost(postId: String, postPrint: PostPrint) {

        try {
            likeProvider.getLikesByPost(postId).addSnapshotListener { value, error ->
                if (value != null) {
                    postPrint.numberLikes = value.size()
                }
            }
        } catch (e: Exception) {

        }
    }

    fun getUserInfo(userId: String, postPrint: PostPrint) {

        try {
            userProvider.getUser(userId).addOnSuccessListener { result ->
                if (result.exists()) {
                    postPrint.usermame = result.getString("username")!!
                }
            }
        } catch (e: Exception) {

        }

    }

    fun checkIfLikeExist(postId: String, userId: String, postPrint: PostPrint) {

        try {
            likeProvider.getLikeByPostAndUser(postId, userId).get().addOnSuccessListener { result ->
                if(result.size() > 0) {
                    postPrint.liked = true
                }
            }
        } catch (e: Exception) {

        }

    }

    fun logOut() {
        auth.logOut()
    }
}