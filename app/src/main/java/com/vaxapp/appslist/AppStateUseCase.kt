package com.vaxapp.appslist

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


internal class AppStateUseCase(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun getApps(packageManager: PackageManager): List<InstalledApp> =
        withContext(dispatcher) {
            delay(2000)
            val packages: MutableList<ApplicationInfo> =
                packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            packages.toList()
                .map {
                    mapInstalledApp(packageManager, it)
                }
        }

    private fun mapInstalledApp(
        packageManager: PackageManager,
        it: ApplicationInfo
    ): InstalledApp {
        val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(it.packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            packageManager.getPackageInfo(it.packageName, 0)
        }
        return InstalledApp(
            packageManager.getApplicationLabel(it).toString(),
            it.packageName?: "Missing package name",
            packageInfo.versionName?: "Missing version"
        )
    }
}
