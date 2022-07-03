package com.costular.commonui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.costular.common_ui.R

val Lato = FontFamily(
    Font(R.font.lato_regular),
    Font(R.font.lato_light, FontWeight.Light),
    Font(R.font.lato_bold, FontWeight.Bold),
    Font(R.font.lato_black, FontWeight.Black),
)

val ZillaSlab = FontFamily(
    Font(R.font.zilla_slab_regular),
    Font(R.font.zilla_slab_light, FontWeight.Light),
    Font(R.font.zilla_slab_medium, FontWeight.Medium),
)

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = ZillaSlab,
        fontWeight = FontWeight.Normal,
        fontSize = 96.sp,
    ),
    h2 = TextStyle(
        fontFamily = ZillaSlab,
        fontWeight = FontWeight.Normal,
        fontSize = 60.sp,
    ),
    h3 = TextStyle(
        fontFamily = ZillaSlab,
        fontWeight = FontWeight.Medium,
        fontSize = 48.sp,
    ),
    h4 = TextStyle(
        fontFamily = ZillaSlab,
        fontWeight = FontWeight.SemiBold,
        fontSize = 34.sp,
    ),
    h5 = TextStyle(
        fontFamily = ZillaSlab,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    ),
    h6 = TextStyle(
        fontFamily = ZillaSlab,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
    ),
    subtitle1 = TextStyle(
        fontFamily = ZillaSlab,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    ),
    subtitle2 = TextStyle(
        fontFamily = ZillaSlab,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
    ),
    body1 = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    body2 = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    button = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    caption = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    overline = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
    ),
)
