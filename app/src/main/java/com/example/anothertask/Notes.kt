package com.example.anothertask

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteName: String,
    val noteSurname: String,
    val noteAge: Int,
)
