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
public final class UpdateTaskReminderInteractor_Factory implements Factory<UpdateTaskReminderInteractor> {
  private final Provider<TasksRepository> tasksRepositoryProvider;

  public UpdateTaskReminderInteractor_Factory(Provider<TasksRepository> tasksRepositoryProvider) {
    this.tasksRepositoryProvider = tasksRepositoryProvider;
  }

  @Override
  public UpdateTaskReminderInteractor get() {
    return newInstance(tasksRepositoryProvider.get());
  }

  public static UpdateTaskReminderInteractor_Factory create(
      Provider<TasksRepository> tasksRepositoryProvider) {
    return new UpdateTaskReminderInteractor_Factory(tasksRepositoryProvider);
  }

  public static UpdateTaskReminderInteractor newInstance(TasksRepository tasksRepository) {
    return new UpdateTaskReminderInteractor(tasksRepository);
  }
}
