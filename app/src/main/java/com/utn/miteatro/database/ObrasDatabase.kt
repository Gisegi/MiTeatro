package com.utn.miteatro.database

import com.utn.miteatro.entities.Obra
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.utn.miteatro.entities.Favoritos

@Database(entities = [Obra::class, Favoritos::class], version = 2, exportSchema = false)
abstract class ObrasDatabase : RoomDatabase() {

    abstract fun obrasDao(): ObrasDao

    companion object {
        private var INSTANCE: ObrasDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ObrasDatabase? {
            if (INSTANCE == null) {
                INSTANCE = buildDatabase(context)
            }
            return INSTANCE
        }

        private fun buildDatabase(context: Context): ObrasDatabase? {
            if (INSTANCE == null) {
                synchronized(ObrasDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ObrasDatabase::class.java,
                        "myDB_obras"
                    )
                        .addCallback(StartingObras(context))
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries() // No es recomendable que se ejecute en el mainthread
                        .build()
                }
            }
            return INSTANCE
        }
    }
}