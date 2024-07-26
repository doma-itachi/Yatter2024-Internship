package com.dmm.bootcamp.yatter2024.domain

import com.dmm.bootcamp.yatter2024.common.ddd.Identifier

class Username(value: String): Identifier<String>(value){
    fun validate(): Boolean = value.isNotBlank()
}