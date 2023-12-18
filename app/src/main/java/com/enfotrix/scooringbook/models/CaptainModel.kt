package com.enfotrix.scooringbook.models

data class CaptainModel(
    val pid: String,
    val cId: String,
    val sDate: String,
    val currentCaptain: Boolean,
    val eDate: String,
    val totalInnings: Int,
    val totalWin: Int,
    val totalLoss: Int,
    val Awards: Int,
    val tosswin: Int,
    val tossloss: Int,


    )
