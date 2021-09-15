package com.example.anothertask

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {

    @Update
    suspend fun updateNote(notes: Notes)

    @Delete
    suspend fun deleteNote(notes: Notes)



    @Query("SELECT * FROM notes ORDER BY noteName ASC")
     fun sortByName():LiveData<List<Notes>>

    @Query("SELECT * FROM notes ORDER BY noteSurname ASC")
    fun sortBySurname():LiveData<List<Notes>>

    @Query("SELECT * FROM notes ORDER BY noteAge DESC")
    fun sortByAge():LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun  addData(notes: Notes)

    @Query("SELECT * FROM notes ORDER BY id ASC")
    fun readAllNotes():LiveData<List<Notes>>
}