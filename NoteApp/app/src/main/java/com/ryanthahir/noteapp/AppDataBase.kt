package com.ryanthahir.noteapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ryanthahir.noteapp.Note
import com.ryanthahir.noteapp.NoteDao

@Database(entities = [Note::class], exportSchema = false, version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        private const val DB_NAME = "NOTE_DB"
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase? {
            if (instance == null) {
                synchronized(AppDataBase::class) {
                    instance = Room
                        .databaseBuilder(
                            context,
                            AppDataBase::class.java,
                            DB_NAME
                        )
                        .build()
                }
            }
            return instance
        }

    }

}