package com.example.kunbaapp.ui.poc

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by Nirbhay Pherwani on 8/14/2023.
 * Linktree - https://linktree.com/nirbhaypherwani
 */

class NotificationsViewModel: ViewModel() {
    init {
        quickNotifications()
        //summarizedNotifications()
        //specificNotifications()
    }

    private fun quickNotifications(){
        viewModelScope.launch {
            val likesActivity: Flow<Like> = flowOf(*likesList.toTypedArray()).onEach {
                delay(50)
            }
            val commentsActivity: Flow<Comment> = flowOf(*commentsList.toTypedArray()).onEach {
                delay(200)
            }
            val postsActivity: Flow<Post> = flowOf(*postsList.toTypedArray()).onEach {
                delay(100)
            }

            merge(likesActivity, commentsActivity, postsActivity).collect {
                when(it) {
                    is Comment -> {
                        Log.i("Quick Notification", "${it.userName} commented on your post: ${it.commentText}")
                    }
                    is Like -> {
                        Log.i("Quick Notification", "${it.userName} reacted with ${it.reactionName} to your post: ${it.postTitle}")
                    }
                    is Post -> {
                        Log.i("Quick Notification", "${it.userName} added a new post: ${it.postTitle}")
                    }
                }
            }
        }
    }

    private fun summarizedNotifications() {
        viewModelScope.launch {
            val likesActivity: Flow<Like> = flowOf(*likesList.toTypedArray()).onEach {
                delay(50)
            }
            val commentsActivity: Flow<Comment> = flowOf(*commentsList.toTypedArray()).onEach {
                delay(200)
            }
            val postsActivity: Flow<Post> = flowOf(*postsList.toTypedArray()).onEach {
                delay(100)
            }

            likesActivity.combine(commentsActivity) { like, comment ->
                "${like.userName} reacted with ${like.reactionName} on ${like.postTitle}\n" +
                        "and ${comment.userName} commented ${comment.commentText} on ${comment.postTitle}"

            }.combine(postsActivity) { activity, post ->
                Log.i("Summary", "$activity .. Also ${post.userName} added a new post '${post.postTitle}'")
            }.collect()
        }
    }

    private fun specificNotifications() {
        viewModelScope.launch {
            val likesActivity: Flow<Like> = flowOf(*likesList.toTypedArray())
            val commentsActivity: Flow<Comment> = flowOf(*commentsList.toTypedArray())

            likesActivity.zip(commentsActivity) { like, comment ->
                if(like.userName == comment.userName && like.postTitle == comment.postTitle) {
                    Log.i("Notification","${like.userName} reacted and commented on your post ${like.postTitle}")
                }
            }.collect()
        }
    }
}