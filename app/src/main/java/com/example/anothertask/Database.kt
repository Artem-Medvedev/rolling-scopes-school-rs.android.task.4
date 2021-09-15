package com.example.anothertask

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class Database:RoomDatabase() {
    abstract  fun dao(): Dao

    companion object{
        @Volatile
        private var INSTANCE: com.example.anothertask.Database? = null

        fun  getDatabase(context: Context): com.example.anothertask.Database {
            val instance = INSTANCE
            if(instance != null) {
                return instance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, com.example.anothertask.Database::class.java, "notes"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}