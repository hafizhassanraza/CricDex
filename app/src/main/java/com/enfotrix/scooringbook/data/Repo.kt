package com.example.cricketapp.data

import android.content.Context
import android.provider.SyncStateContract
import com.enfotrix.scooringbook.SharedPrefManager

class Repo constructor(val context: Context) {
    private var constants = SyncStateContract.Constants()
    private var sharedPrefManager = SharedPrefManager(context)

}