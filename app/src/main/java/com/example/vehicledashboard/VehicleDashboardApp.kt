package com.example.vehicledashboard

import android.app.Application
import com.example.vehicledashboard.core.AppContainer

class VehicleDashboardApp : Application() {

    // Dependencies are created once for the lifetime of the app.
    val container: AppContainer by lazy {
        AppContainer()
    }
}