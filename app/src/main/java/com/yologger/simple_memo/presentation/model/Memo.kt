package com.yologger.simple_memo.presentation.model

data class Memo
constructor(
    var id: Int? = 0,
    var title: String,
    var content: String,
    var position: Int = 0
)