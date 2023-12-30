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
public final class UpdateTaskIsDoneUseCase_Factory implements Factory<UpdateTaskIsDoneUseCase> {
  private final Provider<TasksRepository> tasksRepositoryProvider;

  public UpdateTaskIsDoneUseCase_Factory(Provider<TasksRepository> tasksRepositoryProvider) {
    this.tasksRepositoryProvider = tasksRepositoryProvider;
  }

  @Override
  public UpdateTaskIsDoneUseCase get() {
    return newInstance(tasksRepositoryProvider.get());
  }

  public static UpdateTaskIsDoneUseCase_Factory create(
      Provider<TasksRepository> tasksRepositoryProvider) {
    return new UpdateTaskIsDoneUseCase_Factory(tasksRepositoryProvider);
  }

  public static UpdateTaskIsDoneUseCase newInstance(TasksRepository tasksRepository) {
    return new UpdateTaskIsDoneUseCase(tasksRepository);
  }
}
