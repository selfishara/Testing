package com.selfishara.test.model

data class Character(
    val name: String,
    val nickName: String,
    val race: Race?,
    val gender: String,
    val skills: Set<String>
)