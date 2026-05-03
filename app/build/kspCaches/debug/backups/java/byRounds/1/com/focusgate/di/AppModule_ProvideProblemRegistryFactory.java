package com.focusgate.di;

import com.focusgate.math.ProblemRegistry;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class AppModule_ProvideProblemRegistryFactory implements Factory<ProblemRegistry> {
  @Override
  public ProblemRegistry get() {
    return provideProblemRegistry();
  }

  public static AppModule_ProvideProblemRegistryFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ProblemRegistry provideProblemRegistry() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideProblemRegistry());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideProblemRegistryFactory INSTANCE = new AppModule_ProvideProblemRegistryFactory();
  }
}
