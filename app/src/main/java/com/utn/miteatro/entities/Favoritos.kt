package com.utn.miteatro.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritos")
class Favoritos (id:Int = 0,
                 id_user: Int,
                 id_obra : Int): Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int

    @ColumnInfo(name = "id_user")
    var id_user: Int

    @ColumnInfo(name = "theater")
    var id_obra: Int

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    init {
        this.id = id
        this.id_user = id_user
        this.id_obra = id_obra
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(id_user)
        parcel.writeInt(id_obra)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Favoritos> {
        override fun createFromParcel(parcel: Parcel): Favoritos {
            return Favoritos(parcel)
        }

        override fun newArray(size: Int): Array<Favoritos?> {
            return arrayOfNulls(size)
        }
    }

}

