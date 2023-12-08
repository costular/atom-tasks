package com.costular.atomtasks.review.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory

@Composable
fun ReviewHandler(
    shouldRequestReview: Boolean,
    onFinish: () -> Unit,
) {
    val context = LocalContext.current
    val manager = remember { ReviewManagerFactory.create(context) }

    var reviewInfo: ReviewInfo? by remember { mutableStateOf(null) }
    manager.requestReviewFlow().addOnSuccessListener {
        reviewInfo = it
    }

    LaunchedEffect(reviewInfo, shouldRequestReview) {
        val latestReviewInfo = reviewInfo

        if (latestReviewInfo != null && shouldRequestReview) {
            manager
                .launchReviewFlow(context as Activity, latestReviewInfo)
                .addOnCompleteListener { onFinish() }
        }
    }
}
