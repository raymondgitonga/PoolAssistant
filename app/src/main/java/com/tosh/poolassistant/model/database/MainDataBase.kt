package com.tosh.poolassistant.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var db: MainDatabase? = null
        fun getInstance( context: Context): MainDatabase? {
            if (db == null) {
                synchronized(MainDatabase::class.java) {
                    db = Room.databaseBuilder(context.applicationContext, MainDatabase::class.java, "users_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return db
        }

        fun destroyInstance() {
            db = null
        }
    }
}