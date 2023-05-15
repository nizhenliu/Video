package com.example.videohall.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MutilVideoEntity(
    val actorlist: String,
    val albumgroupid: Long,
    val albumid: Long,
    val arealist: String,
    val avatar_url: String,
    val cover_height: Int,
    val cover_largeurllist: String,
    val cover_mediumurllist: String,
    val cover_thumburllist: String,
    val cover_uri: String,
    val cover_url: String,
    val cover_urllist: String,
    val cover_width: Int,
    val ctime: String,
    val description: String,
    val duration: String,
    val favoritecount: Int,
    val id: Int,
    val intro: String,
    val languageinfo: String,
    val name: String,
    val shareurl: String,
    val subtitle: String,
    val taglist: String,
    val title: String,
    val toutiaolvideotext: String,
    val userid: String,
    val verifycode: String,
    val videomainimag: String,
    val videopath: String,
    val videotypeid: Int,
    val year: String
):Parcelable