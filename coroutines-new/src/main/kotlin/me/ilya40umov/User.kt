package me.ilya40umov

data class User(
    val userId: Long,
    val name: String,
    val friendIds: List<Long> = emptyList()
)