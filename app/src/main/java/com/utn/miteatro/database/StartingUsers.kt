package com.utn.miteatro.database

import android.content.Context
import android.service.autofill.UserData
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.utn.miteatro.R
import com.utn.miteatro.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader

class StartingUsers(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("StartingUsers", "Pre-populating database...")
            fillWithStartingUsersFromJson(context)
        }
    }

    /**
     * Pre-populate database with users from a Json file
     */
    private fun fillWithStartingUsersFromJson(context: Context) {
        val dao = UserDatabase.getInstance(context)?.userDao()

        try {
            val users = loadJSONArray(context, R.raw.users)
            for (i in 0 until users.length()) {
                val item = users.getJSONObject(i)
                val user = User(
                    id = 0,
                    name = item.getString("name"),
                    lastname = item.getString("lastname"),
                    email = item.getString("email"),
                    password = item.getString("password"),
                    image = item.getString("image"),
                )

                dao?.insertUser(user)
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



 /**
    /**
     * Pre-populate database with hard-coded users
     */
    private fun fillWithStartingUsers(context: Context) {
        val users = listOf(
            User(0, "gisela@email.com", "1234"),
            User(1, "juan@email.com", "1234"),
            User(2, "agustina@email.com", "1234"),
            User(3, "andres@email.com", "1234")
        )
        val dao = UserDatabase.getInstance(context)?.userDao()

        users.forEach {
            dao?.insertUser(it)
        }
    }

  */


}