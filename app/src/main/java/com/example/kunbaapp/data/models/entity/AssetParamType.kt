package com.example.kunbaapp.data.models.entity

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson

class AssetParamType : NavType<NodeDbo>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): NodeDbo? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): NodeDbo {
        return Gson().fromJson(value, NodeDbo::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: NodeDbo) {
        bundle.putParcelable(key, value)
    }
}