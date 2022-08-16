package com.costular.atomtasks.screenshottesting.utils

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams

internal object PaparazziFactory {
    fun create(): Paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.NEXUS_5.copy(softButtons = false, screenHeight = 1),
        renderingMode = SessionParams.RenderingMode.V_SCROLL,
    )
}
