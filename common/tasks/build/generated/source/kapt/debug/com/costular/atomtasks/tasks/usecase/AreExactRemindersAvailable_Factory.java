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
public final class AreExactRemindersAvailable_Factory implements Factory<AreExactRemindersAvailable> {
  private final Provider<TaskReminderManager> tasksReminderManagerProvider;

  public AreExactRemindersAvailable_Factory(
      Provider<TaskReminderManager> tasksReminderManagerProvider) {
    this.tasksReminderManagerProvider = tasksReminderManagerProvider;
  }

  @Override
  public AreExactRemindersAvailable get() {
    return newInstance(tasksReminderManagerProvider.get());
  }

  public static AreExactRemindersAvailable_Factory create(
      Provider<TaskReminderManager> tasksReminderManagerProvider) {
    return new AreExactRemindersAvailable_Factory(tasksReminderManagerProvider);
  }

  public static AreExactRemindersAvailable newInstance(TaskReminderManager tasksReminderManager) {
    return new AreExactRemindersAvailable(tasksReminderManager);
  }
}
