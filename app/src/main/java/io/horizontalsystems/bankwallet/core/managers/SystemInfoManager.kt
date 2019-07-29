package io.horizontalsystems.bankwallet.core.managers

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import io.horizontalsystems.bankwallet.BuildConfig
import io.horizontalsystems.bankwallet.core.App
import io.horizontalsystems.bankwallet.core.ISystemInfoManager
import io.horizontalsystems.bankwallet.entities.BiometryType

class SystemInfoManager : ISystemInfoManager {
    override val appVersion: String = BuildConfig.VERSION_NAME

    override val biometryType: BiometryType
        get() {
            return when {
                phoneHasFingerprintSensor() -> BiometryType.FINGER
                else -> BiometryType.NONE
            }
        }

    override val isSystemLockOff: Boolean
        get() {
            val keyguardManager = App.instance.getSystemService(Activity.KEYGUARD_SERVICE) as KeyguardManager
            return !keyguardManager.isDeviceSecure
        }

    override fun phoneHasFingerprintSensor(): Boolean {
        val fingerprintManager = FingerprintManagerCompat.from(App.instance)
        return fingerprintManager.isHardwareDetected
    }

    override fun touchSensorCanBeUsed(): Boolean {
        val fingerprintManager = FingerprintManagerCompat.from(App.instance)
        return when {
            fingerprintManager.isHardwareDetected -> {
                val keyguardManager = App.instance.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
                keyguardManager.isKeyguardSecure && fingerprintManager.hasEnrolledFingerprints()
            }
            else -> false
        }
    }
}
