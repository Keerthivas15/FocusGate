package com.focusgate.ui.screen;

import com.focusgate.data.prefs.AppPreferences;
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
    "KotlinInternalInJava",
    "cast"
})
public final class OnboardingViewModel_Factory implements Factory<OnboardingViewModel> {
  private final Provider<AppPreferences> prefsProvider;

  public OnboardingViewModel_Factory(Provider<AppPreferences> prefsProvider) {
    this.prefsProvider = prefsProvider;
  }

  @Override
  public OnboardingViewModel get() {
    return newInstance(prefsProvider.get());
  }

  public static OnboardingViewModel_Factory create(Provider<AppPreferences> prefsProvider) {
    return new OnboardingViewModel_Factory(prefsProvider);
  }

  public static OnboardingViewModel newInstance(AppPreferences prefs) {
    return new OnboardingViewModel(prefs);
  }
}
