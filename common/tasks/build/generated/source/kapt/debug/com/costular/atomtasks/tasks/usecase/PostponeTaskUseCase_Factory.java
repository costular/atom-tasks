package com.costular.atomtasks.tasks.usecase;

import com.costular.atomtasks.tasks.helper.TaskReminderManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class PostponeTaskUseCase_Factory implements Factory<PostponeTaskUseCase> {
  private final Provider<GetTaskByIdUseCase> getTaskByIdUseCaseProvider;

  private final Provider<UpdateTaskReminderInteractor> updateTaskReminderInteractorProvider;

  private final Provider<TaskReminderManager> taskReminderManagerProvider;

  private final Provider<EditTaskUseCase> editTaskUseCaseProvider;

  public PostponeTaskUseCase_Factory(Provider<GetTaskByIdUseCase> getTaskByIdUseCaseProvider,
      Provider<UpdateTaskReminderInteractor> updateTaskReminderInteractorProvider,
      Provider<TaskReminderManager> taskReminderManagerProvider,
      Provider<EditTaskUseCase> editTaskUseCaseProvider) {
    this.getTaskByIdUseCaseProvider = getTaskByIdUseCaseProvider;
    this.updateTaskReminderInteractorProvider = updateTaskReminderInteractorProvider;
    this.taskReminderManagerProvider = taskReminderManagerProvider;
    this.editTaskUseCaseProvider = editTaskUseCaseProvider;
  }

  @Override
  public PostponeTaskUseCase get() {
    return newInstance(getTaskByIdUseCaseProvider.get(), updateTaskReminderInteractorProvider.get(), taskReminderManagerProvider.get(), editTaskUseCaseProvider.get());
  }

  public static PostponeTaskUseCase_Factory create(
      Provider<GetTaskByIdUseCase> getTaskByIdUseCaseProvider,
      Provider<UpdateTaskReminderInteractor> updateTaskReminderInteractorProvider,
      Provider<TaskReminderManager> taskReminderManagerProvider,
      Provider<EditTaskUseCase> editTaskUseCaseProvider) {
    return new PostponeTaskUseCase_Factory(getTaskByIdUseCaseProvider, updateTaskReminderInteractorProvider, taskReminderManagerProvider, editTaskUseCaseProvider);
  }

  public static PostponeTaskUseCase newInstance(GetTaskByIdUseCase getTaskByIdUseCase,
      UpdateTaskReminderInteractor updateTaskReminderInteractor,
      TaskReminderManager taskReminderManager, EditTaskUseCase editTaskUseCase) {
    return new PostponeTaskUseCase(getTaskByIdUseCase, updateTaskReminderInteractor, taskReminderManager, editTaskUseCase);
  }
}
