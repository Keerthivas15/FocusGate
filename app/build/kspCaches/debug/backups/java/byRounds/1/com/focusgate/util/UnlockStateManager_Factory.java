package com.focusgate.util;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
    "KotlinInternalInJava",
    "cast"
})
public final class UnlockStateManager_Factory implements Factory<UnlockStateManager> {
  private final Provider<TimeProvider> timeProvider;

  public UnlockStateManager_Factory(Provider<TimeProvider> timeProvider) {
    this.timeProvider = timeProvider;
  }

  @Override
  public UnlockStateManager get() {
    return newInstance(timeProvider.get());
  }

  public static UnlockStateManager_Factory create(Provider<TimeProvider> timeProvider) {
    return new UnlockStateManager_Factory(timeProvider);
  }

  public static UnlockStateManager newInstance(TimeProvider timeProvider) {
    return new UnlockStateManager(timeProvider);
  }
}
