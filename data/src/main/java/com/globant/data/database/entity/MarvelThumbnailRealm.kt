package com.globant.data.database.entity

import io.realm.RealmObject

open class ThumbnailRealm(
        var path: String = DEFAULT_STRING,
        var extension: String = DEFAULT_STRING
): RealmObject() {
    companion object {
        private const val DEFAULT_STRING = ""
    }
}