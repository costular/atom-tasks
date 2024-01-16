package com.costular.atomtasks.tasks.usecase;

import com.costular.atomtasks.tasks.helper.TaskReminderManager;
import com.costular.atomtasks.tasks.repository.TasksRepository;
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
public final class RemoveTaskUseCase_Factory implements Factory<RemoveTaskUseCase> {
  private final Provider<TasksRepository> tasksRepositoryProvider;

  private final Provider<TaskReminderManager> taskReminderManagerProvider;

  public RemoveTaskUseCase_Factory(Provider<TasksRepository> tasksRepositoryProvider,
      Provider<TaskReminderManager> taskReminderManagerProvider) {
    this.tasksRepositoryProvider = tasksRepositoryProvider;
    this.taskReminderManagerProvider = taskReminderManagerProvider;
  }

  @Override
  public RemoveTaskUseCase get() {
    return newInstance(tasksRepositoryProvider.get(), taskReminderManagerProvider.get());
  }

  public static RemoveTaskUseCase_Factory create(Provider<TasksRepository> tasksRepositoryProvider,
      Provider<TaskReminderManager> taskReminderManagerProvider) {
    return new RemoveTaskUseCase_Factory(tasksRepositoryProvider, taskReminderManagerProvider);
  }

  public static RemoveTaskUseCase newInstance(TasksRepository tasksRepository,
      TaskReminderManager taskReminderManager) {
    return new RemoveTaskUseCase(tasksRepository, taskReminderManager);
  }
}
