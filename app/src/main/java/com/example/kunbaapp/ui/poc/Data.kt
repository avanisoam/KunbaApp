package com.example.kunbaapp.ui.poc

/**
 * Created by Nirbhay Pherwani on 8/14/2023.
 * Linktree - https://linktree.com/nirbhaypherwani
 */

data class Like(val userName: String, val postTitle: String, val reactionName: String)

data class Comment(val userName: String, val postTitle: String, val commentText: String)

data class Post(val userName: String, val postTitle: String)

val likesList = listOf(
    Like("Alice", "Exploring the Cosmos", "👍"),
    Like("Rahul", "Starry Night Photography", "❤"),
    Like("Grace", "Astronomy Basics", "👏"),
    Like("Charlie", "Starry Night Photography", "👍"),
    Like("Charlie", "Astronomy Basics", "❤️"),
    Like("Bob", "Exploring the Cosmos", "👏"),
)

val commentsList = listOf(
    Comment("Alice", "Exploring the Cosmos", "This is amazing!"),
    Comment("Charlie", "Starry Night Photography", "Beautiful shot!"),
    Comment("Alice", "Astronomy Basics", "Great explanation!"),
    Comment("Rahul", "Starry Night Photography", "Wow, I love this!"),
    Comment("Rahul", "Exploring the Cosmos", "Mind-blowing!"),
    Comment("Grace", "Astronomy 2 Basics", "I finally understand this concept!")
)

val postsList = listOf(
    Post("Alice", "Exploring the Cosmos"),
    Post("Bob", "Starry Night Photography"),
    Post("Charlie", "Astronomy 2 Basics"),
    Post("Eve", "Starry Night Photography"),
    Post("Alice", "Astronomy Basics")
)