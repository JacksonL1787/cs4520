package com.cs4520.assignment4.data

import android.content.Context
import androidx.room.Room

object DatabaseClient {
    private var db: AppDatabase? = null

    fun get(context: Context): AppDatabase {
        if (db == null) {
            db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "database.db"
            ).fallbackToDestructiveMigration().build()
        }
        return db!!
    }
}