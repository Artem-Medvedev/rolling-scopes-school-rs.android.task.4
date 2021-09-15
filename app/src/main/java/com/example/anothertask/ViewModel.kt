package com.example.anothertask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application): AndroidViewModel(application) {

    private val repository: Repository
    val   readAllNotes: LiveData<List<Notes>>
    init{
        val dao = Database.getDatabase(application).dao()
        repository = Repository(dao)
        readAllNotes = repository.readAllNotes
    }

    val sortByTitle: LiveData<List<Notes>> = repository.sortByTitle
    val sortByDesc: LiveData<List<Notes>> = repository.sortByDesc
    val sortByAge: LiveData<List<Notes>> = repository.sortByAge


    fun addData(notes: Notes){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addData(notes)
        }
    }

    fun updateData(notes: Notes){
        viewModelScope.launch(Dispatchers.IO) { repository.updateNote(notes) }
    }

    fun deleteData(notes: Notes){
        viewModelScope.launch(Dispatchers.IO) { repository.deleteData(notes) }
    }


}