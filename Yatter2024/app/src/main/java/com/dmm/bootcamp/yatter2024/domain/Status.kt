package com.dmm.bootcamp.yatter2024.domain

import com.dmm.bootcamp.yatter2024.common.ddd.Entity


class Status (
    id: StatusId,
    val account: Account,
    val content: String,
): Entity<StatusId>(id)