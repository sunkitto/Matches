package com.sunkitto.matches.domain.model

data class MemoryCard(
    val id: Int,
    var isFlipped: Boolean = false,
    var isMatched: Boolean = false,
)