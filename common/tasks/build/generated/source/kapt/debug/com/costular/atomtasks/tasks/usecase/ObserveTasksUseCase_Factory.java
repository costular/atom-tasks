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
public final class ObserveTasksUseCase_Factory implements Factory<ObserveTasksUseCase> {
  private final Provider<TasksRepository> tasksRepositoryProvider;

  public ObserveTasksUseCase_Factory(Provider<TasksRepository> tasksRepositoryProvider) {
    this.tasksRepositoryProvider = tasksRepositoryProvider;
  }

  @Override
  public ObserveTasksUseCase get() {
    return newInstance(tasksRepositoryProvider.get());
  }

  public static ObserveTasksUseCase_Factory create(
      Provider<TasksRepository> tasksRepositoryProvider) {
    return new ObserveTasksUseCase_Factory(tasksRepositoryProvider);
  }

  public static ObserveTasksUseCase newInstance(TasksRepository tasksRepository) {
    return new ObserveTasksUseCase(tasksRepository);
  }
}
