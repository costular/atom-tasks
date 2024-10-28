package com.costular.atomtasks.feature.onboarding

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.costular.atomtasks.core.ui.mvi.EventObserver
import com.costular.designsystem.components.OutlinedButton
import com.costular.designsystem.components.PrimaryButton
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import com.costular.atomtasks.core.ui.R.string as S

@SuppressLint("InlinedApi")
@Destination<OnboardingGraph>(
    start = true,
)
@Composable
internal fun OnboardingScreen(
    navigator: OnboardingNavigator,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermission(isGranted)
        }
    )

    EventObserver(viewModel.uiEvents) { event ->
        when (event) {
            is OnboardingUiEvent.NavigateToAgenda -> {
                navigator.navigateToAgenda()
            }

            is OnboardingUiEvent.RequestNotificationPermission -> {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    OnboardingScreenContent(
        uiState = uiState,
        onPageChanged = viewModel::onPageChanged,
        onSkip = viewModel::onSkip,
        onFinish = { viewModel.onFinish(Build.VERSION.SDK_INT >= TIRAMISU) },
        onNext = viewModel::onNext,
    )
}

@Composable
internal fun OnboardingScreenContent(
    uiState: OnboardingUiState,
    onPageChanged: (Int) -> Unit,
    onSkip: () -> Unit,
    onFinish: () -> Unit,
    onNext: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { uiState.totalPages })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collectLatest {
                onPageChanged(it)
            }
    }

    Surface {
        Box {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
            ) { page ->
                OnboardingStep(
                    onboardingStep = uiState.steps[page],
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = AppTheme.dimens.contentMargin),
                )
            }

            IconButton(
                onClick = onSkip,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(AppTheme.dimens.spacingSmall)
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(AppTheme.dimens.contentMargin),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                PageIndicator(
                    currentPage = uiState.currentPage,
                    totalPages = uiState.totalPages,
                    modifier = Modifier.width(160.dp)
                )

                Spacer(Modifier.height(AppTheme.dimens.spacingHuge))

                OnboardingActions(
                    onSkip = onSkip,
                    onNext = {
                        onNext()
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(uiState.currentPage + 1)
                        }
                    },
                    onFinish = onFinish,
                    isLastPage = uiState.isLastPage,
                )
            }
        }
    }
}

@Composable
private fun OnboardingStep(
    onboardingStep: OnboardingStep?,
    modifier: Modifier = Modifier,
) {
    if (onboardingStep == null) return

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(onboardingStep.imageRes),
            contentDescription = null,
        )

        Spacer(Modifier.height(48.dp))

        Text(
            stringResource(onboardingStep.titleRes),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displaySmall,
        )

        Spacer(Modifier.height(AppTheme.dimens.spacingLarge))

        Text(
            stringResource(onboardingStep.descriptionRes),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun PageIndicator(
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(totalPages) { page ->
            val lineWeight = animateFloatAsState(
                targetValue = if (currentPage == page) {
                    2f
                } else {
                    1f
                },
                label = "Pager line",
                animationSpec = tween(300, easing = EaseInOut)
            )

            val color = animateColorAsState(
                targetValue = if (currentPage == page) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surfaceContainerHighest
                },
                animationSpec = tween(durationMillis = 300, easing = EaseInOut),
                label = "Indicator color",
            )

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color.value)
                    .weight(lineWeight.value)
                    .height(8.dp)
            )
        }
    }
}

@Composable
private fun OnboardingActions(
    onSkip: () -> Unit,
    onNext: () -> Unit,
    onFinish: () -> Unit,
    isLastPage: Boolean,
) {
    AnimatedContent(targetState = isLastPage, label = "Actions") { isShown ->
        if (isShown) {
            PrimaryButton(
                onClick = onFinish,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(S.onboarding_finish))
            }
        } else {
            Row {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = onSkip
                ) {
                    Text(stringResource(S.onboarding_skip))
                }

                Spacer(Modifier.width(AppTheme.dimens.spacingMedium))

                PrimaryButton(
                    modifier = Modifier.weight(1f),
                    onClick = onNext,
                ) {
                    Text(stringResource(S.onboarding_next))
                }
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    AtomTheme {
        var currentPage by remember { mutableIntStateOf(0) }

        OnboardingScreenContent(
            uiState = OnboardingUiState(
                currentPage = currentPage,
            ),
            onPageChanged = { currentPage = it },
            onSkip = {},
            onFinish = {},
            onNext = {},
        )
    }
}