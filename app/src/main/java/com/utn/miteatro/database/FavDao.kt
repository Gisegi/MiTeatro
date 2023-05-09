package com.utn.miteatro.database

import androidx.room.*
import com.utn.miteatro.entities.Favoritos
import com.utn.miteatro.entities.Obra

@Dao
interface FavDao {

    @Query("SELECT * FROM favoritos ORDER BY id_user")
    fun fetchAllObras(): MutableList<Favoritos?>?

    @Query("SELECT * FROM favoritos WHERE id_user = :id")
    fun fetchFavByUser(id: Int): Favoritos?

    @Query("SELECT * FROM obras WHERE name = :id")
    fun fetchObraByName(id: String): Obra?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertObra(obras: Obra)

    @Update
    fun updateObra(obras: Obra)

    @Delete
    fun delete(obras: Obra)

}