package com.focusgate.ui.screen;

import com.focusgate.data.prefs.AppPreferences;
import com.focusgate.data.repository.CreditRepository;
import com.focusgate.math.ProblemRegistry;
import com.focusgate.util.UnlockStateManager;
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
public final class MathGateViewModel_Factory implements Factory<MathGateViewModel> {
  private final Provider<ProblemRegistry> registryProvider;

  private final Provider<UnlockStateManager> unlockManagerProvider;

  private final Provider<CreditRepository> creditRepoProvider;

  private final Provider<AppPreferences> prefsProvider;

  public MathGateViewModel_Factory(Provider<ProblemRegistry> registryProvider,
      Provider<UnlockStateManager> unlockManagerProvider,
      Provider<CreditRepository> creditRepoProvider, Provider<AppPreferences> prefsProvider) {
    this.registryProvider = registryProvider;
    this.unlockManagerProvider = unlockManagerProvider;
    this.creditRepoProvider = creditRepoProvider;
    this.prefsProvider = prefsProvider;
  }

  @Override
  public MathGateViewModel get() {
    return newInstance(registryProvider.get(), unlockManagerProvider.get(), creditRepoProvider.get(), prefsProvider.get());
  }

  public static MathGateViewModel_Factory create(Provider<ProblemRegistry> registryProvider,
      Provider<UnlockStateManager> unlockManagerProvider,
      Provider<CreditRepository> creditRepoProvider, Provider<AppPreferences> prefsProvider) {
    return new MathGateViewModel_Factory(registryProvider, unlockManagerProvider, creditRepoProvider, prefsProvider);
  }

  public static MathGateViewModel newInstance(ProblemRegistry registry,
      UnlockStateManager unlockManager, CreditRepository creditRepo, AppPreferences prefs) {
    return new MathGateViewModel(registry, unlockManager, creditRepo, prefs);
  }
}
