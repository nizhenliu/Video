package com.example.home.model.entity

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


data class SimpleVideoEntity(
    val avatar_url: String?,
    val channelid: String?,
    val commentnum: Int,
    val ctime: String?,
    val description: String?,
    val group_id: String?,
    val id: Int,
    val image_url: String?,
    val item_id: String?,
    val name: String?,
    val playnum: Int,
    val preview_url: String?,
    val publish_time: String?,
    val title: String?,
    val userid: String?,
    val verifycode: String?,
    val videomainimag: String?,
    val videopath: String?,
    var isfocuse:Int
):Serializable,Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(avatar_url)
        parcel.writeString(channelid)
        parcel.writeInt(commentnum)
        parcel.writeString(ctime)
        parcel.writeString(description)
        parcel.writeString(group_id)
        parcel.writeInt(id)
        parcel.writeString(image_url)
        parcel.writeString(item_id)
        parcel.writeString(name)
        parcel.writeInt(playnum)
        parcel.writeString(preview_url)
        parcel.writeString(publish_time)
        parcel.writeString(title)
        parcel.writeString(userid)
        parcel.writeString(verifycode)
        parcel.writeString(videomainimag)
        parcel.writeString(videopath)
        parcel.writeInt(isfocuse)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SimpleVideoEntity> {
        override fun createFromParcel(parcel: Parcel): SimpleVideoEntity {
            return SimpleVideoEntity(parcel)
        }

        override fun newArray(size: Int): Array<SimpleVideoEntity?> {
            return arrayOfNulls(size)
        }
    }
}