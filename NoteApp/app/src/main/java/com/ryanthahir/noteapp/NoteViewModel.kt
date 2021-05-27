package com.ryanthahir.noteapp
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ryanthahir.noteapp.Note
import com.ryanthahir.noteapp.NoteRepo

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private var noteRepository = NoteRepo(application)
    private var notes: LiveData<List<Note>>? = noteRepository.getNotes()

    fun insertNote(note: Note) {
        noteRepository.insert(note)
    }

    fun getNotes(): LiveData<List<Note>>? {
        return notes
    }

    fun deleteNote(note: Note) {
        noteRepository.delete(note)
    }

    fun updateNote(note: Note) {
        noteRepository.update(note)
    }

}