package com.costular.atomtasks.tasks.usecase;

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
public final class GetTasksWithReminderInteractor_Factory implements Factory<GetTasksWithReminderInteractor> {
  private final Provider<TasksRepository> tasksRepositoryProvider;

  public GetTasksWithReminderInteractor_Factory(Provider<TasksRepository> tasksRepositoryProvider) {
    this.tasksRepositoryProvider = tasksRepositoryProvider;
  }

  @Override
  public GetTasksWithReminderInteractor get() {
    return newInstance(tasksRepositoryProvider.get());
  }

  public static GetTasksWithReminderInteractor_Factory create(
      Provider<TasksRepository> tasksRepositoryProvider) {
    return new GetTasksWithReminderInteractor_Factory(tasksRepositoryProvider);
  }

  public static GetTasksWithReminderInteractor newInstance(TasksRepository tasksRepository) {
    return new GetTasksWithReminderInteractor(tasksRepository);
  }
}
