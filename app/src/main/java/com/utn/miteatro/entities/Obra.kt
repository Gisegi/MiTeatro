package com.utn.miteatro.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "obras")
class Obra (id:Int = 0,
            name: String ? = null,
            theater : String ? = null,
            address : String ? = null,
            category : String ? = null,
            image : String ? = null,
            web : String ? = null,
            about : String ? = null): Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int

    @ColumnInfo(name = "name")
    var name: String ?

    @ColumnInfo(name = "theater")
    var theater: String ?

    @ColumnInfo(name = "address")
    var address: String ?

    @ColumnInfo(name = "category")
    var category: String ?

    @ColumnInfo(name = "image")
    var image: String ?

    @ColumnInfo(name = "web")
    var web: String ?

    @ColumnInfo(name = "about")
    var about: String ?


    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    init {
        this.id = id
        this.name = name
        this.theater = theater
        this.address = address
        this.category = category
        this.image = image
        this.web = web
        this.about = about
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(theater)
        parcel.writeString(address)
        parcel.writeString(category)
        parcel.writeString(image)
        parcel.writeString(web)
        parcel.writeString(about)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Obra> {
        override fun createFromParcel(parcel: Parcel): Obra {
            return Obra(parcel)
        }

        override fun newArray(size: Int): Array<Obra?> {
            return arrayOfNulls(size)
        }
    }
}