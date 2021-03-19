package com.delaroystudios.weatherapp.di;


import com.delaroystudios.weatherapp.ui.settings.SettingsFragment;
import com.delaroystudios.weatherapp.ui.today.TodayFragment;
import com.delaroystudios.weatherapp.ui.weekly.WeeklyFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract TodayFragment contributeTodayFragment();

    @ContributesAndroidInjector
    abstract WeeklyFragment contributeWeeklyFragment();

    @ContributesAndroidInjector
    abstract SettingsFragment contributeSettingsFragment();
}
