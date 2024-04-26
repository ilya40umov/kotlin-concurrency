package me.ilya40umov.kc.coroutines.friends

data class User(
    val userId: Long,
    val name: String,
    val friendIds: List<Long> = emptyList()
)