package com.enfotrix.scooringbook.models


data class PlayerModel(
    val pID: String,
    val matches: String,
    val firstName: String,
    val lastName: String,
    val country: String,
    val city: String,
    val dob: String,
    val playingRole: String,
    val jerseyNumber: String,
    val height: String,
    val weight: String,
    val nickName: String,
    val maritalStatus: String,
    val contactNumber: String,
    val currentClub: String,
    val awards: String,
    val preferredFormat: String,
    val languages: String,
    val teamRepresenting: String,
    val tTen: String,
    val tTwenty: String,
    val odi: String,
    val others: String,
    val clubDs: String,
    val tournaments: String,
    val wicketkeeperRole: String,
    val wicketkeeperDetails: Wicketkeeper,
    val captainDetails: CaptainModel
)

