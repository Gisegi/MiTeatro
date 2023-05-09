package com.utn.miteatro.database

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.utn.miteatro.R
import com.utn.miteatro.entities.Obra
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader

class StartingObras(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("StartingObras", "Pre-populating database...")
            fillWithStartingObrasFromJson(context)
        }
    }

    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
        super.onDestructiveMigration(db)
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("StartingObras", "Re-populating database...")
            fillWithStartingObrasFromJson(context)
        }
    }

    private fun fillWithStartingObrasFromJson(context: Context) {
        val dao = ObrasDatabase.getInstance(context)?.obrasDao()

        try {
            val obras = loadJSONArray(context, R.raw.obras)
            for (i in 0 until obras.length()) {
                val item = obras.getJSONObject(i)
                val obras = Obra(
                    id = 0,
                    name = item.getString("name"),
                    theater = item.getString("theater"),
                    address = item.getString("address"),
                    category = item.getString("category"),
                    image = item.getString("image"),
                    web = item.getString("web"),
                    about = item.getString("about"),
                )

                dao?.insertObra(obras)
            }
        } catch (e: JSONException) {
            Log.e("fillWithStartingNotes", e.toString())
        }
    }

    /**
     * Utility to load a JSON array from the raw folder
     */
    private fun loadJSONArray(context: Context, file: Int): JSONArray {
        val inputStream = context.resources.openRawResource(file)

        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }

}