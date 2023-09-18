package com.costular.atomtasks.screenshottesting.utils

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams

internal object PaparazziFactory {
    fun create(): Paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_6.copy(locale = "en"),
        showSystemUi = false,
        renderingMode = SessionParams.RenderingMode.SHRINK,
    )
}
