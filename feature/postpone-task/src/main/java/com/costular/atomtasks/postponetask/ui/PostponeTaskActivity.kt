package com.costular.atomtasks.postponetask.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.costular.designsystem.theme.AtomTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostponeTaskActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val taskId = intent.getLongExtra(ParamTaskId, -1L)

            AtomTheme {
                PostponeTaskScreen(
                    taskId = taskId,
                    onClose = {
                        finish()
                    },
                )
            }
        }
    }

    companion object {
        fun buildIntent(context: Context, taskId: Long): Intent {
            return Intent(context, PostponeTaskActivity::class.java).apply {
                putExtra(ParamTaskId, taskId)
            }
        }

        private const val ParamTaskId = "postpone_param_task_id"
    }
}
