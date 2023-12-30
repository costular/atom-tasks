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
public final class GetTaskByIdUseCase_Factory implements Factory<GetTaskByIdUseCase> {
  private final Provider<TasksRepository> tasksRepositoryProvider;

  public GetTaskByIdUseCase_Factory(Provider<TasksRepository> tasksRepositoryProvider) {
    this.tasksRepositoryProvider = tasksRepositoryProvider;
  }

  @Override
  public GetTaskByIdUseCase get() {
    return newInstance(tasksRepositoryProvider.get());
  }

  public static GetTaskByIdUseCase_Factory create(
      Provider<TasksRepository> tasksRepositoryProvider) {
    return new GetTaskByIdUseCase_Factory(tasksRepositoryProvider);
  }

  public static GetTaskByIdUseCase newInstance(TasksRepository tasksRepository) {
    return new GetTaskByIdUseCase(tasksRepository);
  }
}
