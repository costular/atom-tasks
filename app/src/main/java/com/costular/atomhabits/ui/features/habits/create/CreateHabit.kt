package com.costular.atomhabits.ui.features.habits.create

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import com.costular.atomhabits.R
import com.costular.atomhabits.ui.util.rememberFlowWithLifecycle
import com.costular.atomhabits.ui.components.AutoSizedCircularProgressIndicator
import com.google.accompanist.pager.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

@ExperimentalPagerApi
@Composable
fun CreateHabit(
    goBack: () -> Unit
) {
    val viewModel: CreateHabitViewModel = hiltViewModel()
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(CreateHabitState())
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        if (pagerState.isFirstPage()) {
            goBack()
        } else {
            viewModel.goToPage(pagerState.currentPage - 1)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is CreateHabitEvents.SavedSuccessfully -> goBack()
                is CreateHabitEvents.GoToPage -> {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = event.page)
                    }
                }
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column {
            IconButton(onClick = goBack, modifier = Modifier.padding(8.dp)) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                ) // TODO: 23/6/21 add content description
            }

            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f), count = 3) {
                when (pagerState.currentPage) {
                    PAGE_NAME -> CreateHabitName(state, onChangeName = { viewModel.setName(it) })
                    PAGE_REPETITION -> CreateHabitRepetition(
                        state,
                        onRepetitionSelected = { viewModel.setRepetition(it) }
                    )
                    PAGE_REMINDER -> CreateHabitReminder(
                        state,
                        onChangeReminderEnabled = {
                            viewModel.setReminderEnabled(it)
                        },
                        onChangeReminderTime = {
                            viewModel.setReminderTime(it)
                        }
                    )
                    else -> throw IllegalStateException("Page not supported")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(LocalContentColor.current.copy(alpha = ContentAlpha.disabled))
            )
            BottomActionBar(
                isSaving = state.isSaving,
                pagerState = pagerState,
                onNext = {
                    viewModel.goToPage(pagerState.currentPage + 1)
                },
                onSave = {
                    viewModel.save()
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
private fun BottomActionBar(
    isSaving: Boolean,
    pagerState: PagerState,
    onNext: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(70.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HorizontalPagerIndicator(
            pagerState,
            activeColor = MaterialTheme.colors.primary,
            indicatorWidth = 10.dp,
            spacing = 16.dp,
            modifier = Modifier.padding(16.dp)
        )

        val isLastPage = pagerState.isLastPage()
        Button(
            onClick = if (isLastPage) onSave else onNext,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .requiredWidth(150.dp)
                .requiredHeight(50.dp)
        ) {
            if (isSaving) {
                AutoSizedCircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                val text =
                    if (isLastPage) stringResource(R.string.create_habit_save) else stringResource(
                        R.string.create_habit_next
                    )
                Text(text = text)
            }
        }
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
private fun BottomActionBarPreview() {
    BottomActionBar(
        isSaving = true,
        rememberPagerState(),
        onNext = {},
        onSave = {}
    )
}

@ExperimentalPagerApi
private fun PagerState.isLastPage(): Boolean = (currentPage + 1) == pageCount

@ExperimentalPagerApi
private fun PagerState.isFirstPage(): Boolean = currentPage == 0

private const val PAGE_NAME = 0
private const val PAGE_REPETITION = 1
private const val PAGE_REMINDER = 2