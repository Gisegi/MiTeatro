package com.utn.miteatro.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.utn.miteatro.entities.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao



    companion object {
        private var INSTANCE: UserDatabase? = null

        @Synchronized
        fun getInstance(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                INSTANCE = buildDatabase(context)
            }
            return INSTANCE
        }

        private fun buildDatabase(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "myDB"
                    )
                        .addCallback(StartingUsers(context))
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries() // No es recomendable que se ejecute en el mainthread
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
