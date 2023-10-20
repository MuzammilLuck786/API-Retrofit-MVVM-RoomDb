package com.api.apirectrofitmvvmroomdb.interfaces

import com.api.apirectrofitmvvmroomdb.models.Hit

interface ItemClick {
    fun itemClick(hit: Hit, position: Int)
}