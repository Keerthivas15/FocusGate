package com.focusgate.ui.screen;

import com.focusgate.data.prefs.AppPreferences;
import com.focusgate.data.repository.BlockedAppRepository;
import com.focusgate.data.repository.CreditRepository;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<BlockedAppRepository> blockedRepoProvider;

  private final Provider<CreditRepository> creditRepoProvider;

  private final Provider<AppPreferences> prefsProvider;

  public DashboardViewModel_Factory(Provider<BlockedAppRepository> blockedRepoProvider,
      Provider<CreditRepository> creditRepoProvider, Provider<AppPreferences> prefsProvider) {
    this.blockedRepoProvider = blockedRepoProvider;
    this.creditRepoProvider = creditRepoProvider;
    this.prefsProvider = prefsProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(blockedRepoProvider.get(), creditRepoProvider.get(), prefsProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<BlockedAppRepository> blockedRepoProvider,
      Provider<CreditRepository> creditRepoProvider, Provider<AppPreferences> prefsProvider) {
    return new DashboardViewModel_Factory(blockedRepoProvider, creditRepoProvider, prefsProvider);
  }

  public static DashboardViewModel newInstance(BlockedAppRepository blockedRepo,
      CreditRepository creditRepo, AppPreferences prefs) {
    return new DashboardViewModel(blockedRepo, creditRepo, prefs);
  }
}
