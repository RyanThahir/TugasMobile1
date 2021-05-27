package com.ryanthahir.noteapp

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryanthahir.noteapp.R
import com.ryanthahir.noteapp.Note
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            showAlertDialogAdd()
        }
        noteRV.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter(this) { note, i ->
            showAlertMenu(note)
        }
        noteRV.adapter = noteAdapter

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.getNotes()?.observe(this, Observer {
            noteAdapter.setNotes(it)
        })
    }

    private fun showAlertDialogAdd() {
        val alert = AlertDialog.Builder(this)
        val editText = EditText(applicationContext)
        editText.hint = "Enter your text"

        alert.setTitle("New Note")
        alert.setView(editText)

        alert.setPositiveButton("Save") { dialog, _ ->
            noteViewModel.insertNote(
                Note(note = editText.text.toString())
            )
            dialog.dismiss()
        }

        alert.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        alert.show()
    }

    private fun showAlertMenu(note: Note) {
        val items = arrayOf("Edit", "Delete")

        val builder =
            AlertDialog.Builder(this)
        builder.setItems(items) { dialog, which ->
            // the user clicked on colors[which]
            when (which) {
                0 -> {
                    showAlertDialogEdit(note)
                }
                1 -> {
                    noteViewModel.deleteNote(note)
                }
            }
        }
        builder.show()
    }

    private fun showAlertDialogEdit(note: Note) {
        val alert = AlertDialog.Builder(this)

        val editText = EditText(applicationContext)
        editText.setText(note.note)

        alert.setTitle("Edit Note")
        alert.setView(editText)

        alert.setPositiveButton("Update") { dialog, _ ->
            note.note = editText.text.toString()
            noteViewModel.updateNote(note)
            dialog.dismiss()
        }

        alert.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        alert.show()
    }
}