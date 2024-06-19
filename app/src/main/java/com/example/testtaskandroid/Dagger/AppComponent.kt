package com.example.testtaskandroid.Dagger

import com.example.testtaskandroid.MainActivity
import com.example.testtaskandroid.AllBilets
import com.example.testtaskandroid.SearchActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(allBilets: AllBilets)
    fun inject(searchActivity: SearchActivity)
}