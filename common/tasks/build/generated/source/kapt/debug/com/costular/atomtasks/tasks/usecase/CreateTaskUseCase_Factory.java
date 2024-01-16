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
public final class CreateTaskUseCase_Factory implements Factory<CreateTaskUseCase> {
  private final Provider<TasksRepository> tasksRepositoryProvider;

  private final Provider<TaskReminderManager> taskReminderManagerProvider;

  private final Provider<PopulateRecurringTasksUseCase> populateRecurringTasksUseCaseProvider;

  public CreateTaskUseCase_Factory(Provider<TasksRepository> tasksRepositoryProvider,
      Provider<TaskReminderManager> taskReminderManagerProvider,
      Provider<PopulateRecurringTasksUseCase> populateRecurringTasksUseCaseProvider) {
    this.tasksRepositoryProvider = tasksRepositoryProvider;
    this.taskReminderManagerProvider = taskReminderManagerProvider;
    this.populateRecurringTasksUseCaseProvider = populateRecurringTasksUseCaseProvider;
  }

  @Override
  public CreateTaskUseCase get() {
    return newInstance(tasksRepositoryProvider.get(), taskReminderManagerProvider.get(), populateRecurringTasksUseCaseProvider.get());
  }

  public static CreateTaskUseCase_Factory create(Provider<TasksRepository> tasksRepositoryProvider,
      Provider<TaskReminderManager> taskReminderManagerProvider,
      Provider<PopulateRecurringTasksUseCase> populateRecurringTasksUseCaseProvider) {
    return new CreateTaskUseCase_Factory(tasksRepositoryProvider, taskReminderManagerProvider, populateRecurringTasksUseCaseProvider);
  }

  public static CreateTaskUseCase newInstance(TasksRepository tasksRepository,
      TaskReminderManager taskReminderManager,
      PopulateRecurringTasksUseCase populateRecurringTasksUseCase) {
    return new CreateTaskUseCase(tasksRepository, taskReminderManager, populateRecurringTasksUseCase);
  }
}
