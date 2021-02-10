package com.yologger.simple_memo.presentation.base

import androidx.fragment.app.Fragment
import com.yologger.simple_memo.presentation.screen.AppActivity
import com.yologger.simple_memo.presentation.screen.AppRouter

open class BaseFragment: Fragment() {
    val router: AppRouter get() = (activity as AppActivity).router
}