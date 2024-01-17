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
public final class PopulateRecurringTasksUseCase_Factory implements Factory<PopulateRecurringTasksUseCase> {
  private final Provider<TasksRepository> tasksRepositoryProvider;

  public PopulateRecurringTasksUseCase_Factory(Provider<TasksRepository> tasksRepositoryProvider) {
    this.tasksRepositoryProvider = tasksRepositoryProvider;
  }

  @Override
  public PopulateRecurringTasksUseCase get() {
    return newInstance(tasksRepositoryProvider.get());
  }

  public static PopulateRecurringTasksUseCase_Factory create(
      Provider<TasksRepository> tasksRepositoryProvider) {
    return new PopulateRecurringTasksUseCase_Factory(tasksRepositoryProvider);
  }

  public static PopulateRecurringTasksUseCase newInstance(TasksRepository tasksRepository) {
    return new PopulateRecurringTasksUseCase(tasksRepository);
  }
}
