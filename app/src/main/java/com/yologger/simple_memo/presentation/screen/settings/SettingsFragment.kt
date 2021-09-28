package com.yologger.simple_memo.presentation.screen.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import com.yologger.simple_memo.R
import com.yologger.simple_memo.application.ThemeManager
import com.yologger.simple_memo.presentation.base.BaseFragment
import com.yologger.simple_memo.presentation.screen.AppActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SettingsFragment : BaseFragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var switchTheme: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)
        switchTheme = rootView.findViewById(R.id.fragment_settings_sw_theme)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSwitch()
    }

    private fun setupSwitch() {
        val currentTheme = ThemeManager.getCurrentTheme(requireContext())
        switchTheme.isChecked = when(currentTheme) {
            ThemeManager.ThemeMode.LIGHT -> { false }
            ThemeManager.ThemeMode.DARK -> { true }
            ThemeManager.ThemeMode.DEFAULT -> { false }
        }

        switchTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                ThemeManager.applyTheme(requireContext(), ThemeManager.ThemeMode.DARK)
            } else {
                ThemeManager.applyTheme(requireContext(), ThemeManager.ThemeMode.LIGHT)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SettingsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}