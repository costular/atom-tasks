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
public final class MoveTaskUseCase_Factory implements Factory<MoveTaskUseCase> {
  private final Provider<TasksRepository> tasksRepositoryProvider;

  public MoveTaskUseCase_Factory(Provider<TasksRepository> tasksRepositoryProvider) {
    this.tasksRepositoryProvider = tasksRepositoryProvider;
  }

  @Override
  public MoveTaskUseCase get() {
    return newInstance(tasksRepositoryProvider.get());
  }

  public static MoveTaskUseCase_Factory create(Provider<TasksRepository> tasksRepositoryProvider) {
    return new MoveTaskUseCase_Factory(tasksRepositoryProvider);
  }

  public static MoveTaskUseCase newInstance(TasksRepository tasksRepository) {
    return new MoveTaskUseCase(tasksRepository);
  }
}
