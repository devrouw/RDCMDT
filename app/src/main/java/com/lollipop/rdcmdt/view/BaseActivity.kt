package com.lollipop.rdcmdt.view

interface BaseActivity {

    /** initialize view model */
    fun initializeViewModel()

    /** observe all live data from view model */
    fun observableLiveData()
}