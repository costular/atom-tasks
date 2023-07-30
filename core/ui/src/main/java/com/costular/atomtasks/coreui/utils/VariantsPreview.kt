package com.costular.atomtasks.core.ui.utils

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f,
)
@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
)
annotation class VariantsPreview
