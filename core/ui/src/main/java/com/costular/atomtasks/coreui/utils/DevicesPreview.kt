package com.costular.atomtasks.core.ui.utils

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "phone", device = Devices.PHONE)
@Preview(name = "tablet", device = Devices.TABLET)
@Preview(name = "foldable", device = Devices.FOLDABLE)
annotation class DevicesPreview
