package com.example.anothertask

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = AdapterNotes()
        recyclerViewMain.adapter = adapter
        recyclerViewMain.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        viewModel.readAllNotes.observe(this,{notes -> adapter.setData(notes)})

        addNewNote.setOnClickListener{
            showDialogWindow()
        }

        adapter.OnItemClick = {notes -> showActionDialog(notes)  }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.sortNotes) {
            val intent = Intent(this,SettingsActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onResume() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val adapter = AdapterNotes()
        recyclerViewMain.adapter = adapter
        recyclerViewMain.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)


        if(prefs.getBoolean("name",false)){
            viewModel.sortByTitle.observe(this,{notes -> adapter.setData(notes)})
        }

        if(prefs.getBoolean("surname",false)){
            viewModel.sortByDesc.observe(this,{notes -> adapter.setData(notes)})
        }

        if(prefs.getBoolean("age",false)){
            viewModel.sortByAge.observe(this,{notes -> adapter.setData(notes)})
        }

        viewModel.readAllNotes.observe(this,{notes -> adapter.setData(notes)})

        addNewNote.setOnClickListener{
            showDialogWindow()
        }

        adapter.OnItemClick = {notes -> showActionDialog(notes)  }

        super.onResume()
    }

    private fun showActionDialog(notes: Notes) {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Delete"){_,_->
            viewModel.deleteData(notes)
        }
        builder.setNegativeButton("Update"){_,_->
           showUpdateDialog(notes)
        }
        builder.setNeutralButton("Cancel"){_,_->
            Toast.makeText(this,"Cancel", Toast.LENGTH_SHORT).show()
        }

        builder.setTitle("Select Action")
        builder.create().show()
    }

    private fun showUpdateDialog(notes: Notes) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_window)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        val etNoteName: EditText = dialog.findViewById(R.id.noteName)
        val etNoteSurname: EditText = dialog.findViewById(R.id.noteSurname)
        val etNoteAge: EditText = dialog.findViewById(R.id.noteAge)

        etNoteName.setText(notes.noteName)
        etNoteSurname.setText(notes.noteSurname)
        etNoteAge.setText(notes.noteAge)

        dialog.findViewById<Button>(R.id.cancel_bt).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.add_button).setOnClickListener {
            if(inputCheck(etNoteName.text.toString(),etNoteSurname.text.toString(),etNoteAge.text.toString())){

                val notes = Notes(notes.id,etNoteName.text.toString(),etNoteSurname.text.toString(),etNoteAge.text.toString().toInt())
                viewModel.updateData(notes)
                Toast.makeText(this,"Note updated",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }else{
                Toast.makeText(this,"please enter data",Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
        dialog.window?.attributes = layoutParams

    }

    private fun showDialogWindow(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_window)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        val etNoteName: EditText = dialog.findViewById(R.id.noteName)
        val etNoteSurname: EditText = dialog.findViewById(R.id.noteSurname)
        val etNoteAge: EditText = dialog.findViewById(R.id.noteAge)

        dialog.findViewById<Button>(R.id.cancel_bt).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.add_button).setOnClickListener {
            if(inputCheck(etNoteName.text.toString(),etNoteSurname.text.toString(),etNoteAge.text.toString())){

                val notes = Notes(0,etNoteName.text.toString(),etNoteSurname.text.toString(),etNoteAge.text.toString().toInt())
                viewModel.addData(notes)
                Toast.makeText(this,"Data added",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }else{
                Toast.makeText(this,"please enter data",Toast.LENGTH_SHORT).show()
            }
        }


        dialog.show()
        dialog.window?.attributes = layoutParams
    }

    private fun inputCheck(title: String,desc:String, age: String): Boolean{
        return !TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc) && !TextUtils.isEmpty(age)
    }
}