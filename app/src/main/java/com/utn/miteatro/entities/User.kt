package com.utn.miteatro.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users")
class User (id:Int, name: String, lastname: String, email:String, password:String, image: String): Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int

    @ColumnInfo(name = "name")
    var name: String

    @ColumnInfo(name = "lastname")
    var lastname: String

    @ColumnInfo(name = "email")
    var email: String

    @ColumnInfo(name = "password")
    var password: String

    @ColumnInfo(name = "image")
    var image: String

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    init {
        this.id = id
        this.name = name
        this.lastname = lastname
        this.email = email
        this.password = password
        this.image = image
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(lastname)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
