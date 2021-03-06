package com.example.anothertask

import androidx.lifecycle.LiveData

class Repository(private val dao: Dao)  {
    suspend fun addData(notes: Notes){
        dao.addData(notes)
    }

    val readAllNotes: LiveData<List<Notes>> = dao.readAllNotes()

    suspend fun updateNote(notes: Notes){
        dao.updateNote(notes)
    }

    suspend fun  deleteData(notes: Notes){
        dao.deleteNote(notes)
    }


    val sortByName: LiveData<List<Notes>> = dao.sortByName()

    val sortBySurname: LiveData<List<Notes>> = dao.sortBySurname()

    val sortByAge: LiveData<List<Notes>> = dao.sortByAge()



}