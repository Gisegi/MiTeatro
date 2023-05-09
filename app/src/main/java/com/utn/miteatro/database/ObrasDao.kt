package com.utn.miteatro.database

import androidx.room.*
import com.utn.miteatro.entities.Obra

@Dao
interface ObrasDao {

    @Query("SELECT * FROM obras ORDER BY name")
    fun fetchAllObras(): MutableList<Obra?>?

    @Query("SELECT * FROM obras WHERE id = :id")
    fun fetchObraById(id: Int): Obra?

    @Query("SELECT * FROM obras WHERE name = :id")
    fun fetchObraByName(id: String): Obra?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertObra(obras: Obra)

    @Update
    fun updateObra(obras: Obra)

    @Delete
    fun delete(obras: Obra)
}