package com.enfotrix.scooringbook

import android.content.Context
import android.content.SharedPreferences


class SharedPrefManager(context: Context) {
    private var constants =Constant()

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPref.edit()




}