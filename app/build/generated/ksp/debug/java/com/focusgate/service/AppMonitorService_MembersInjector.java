package com.focusgate.service;

import com.focusgate.data.prefs.AppPreferences;
import com.focusgate.data.repository.BlockedAppRepository;
import com.focusgate.data.repository.CreditRepository;
import com.focusgate.util.TimeProvider;
import com.focusgate.util.UnlockStateManager;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AppMonitorService_MembersInjector implements MembersInjector<AppMonitorService> {
  private final Provider<BlockedAppRepository> blockedRepoProvider;

  private final Provider<UnlockStateManager> unlockManagerProvider;

  private final Provider<TimeProvider> timeProvider;

  private final Provider<CreditRepository> creditRepoProvider;

  private final Provider<AppPreferences> prefsProvider;

  public AppMonitorService_MembersInjector(Provider<BlockedAppRepository> blockedRepoProvider,
      Provider<UnlockStateManager> unlockManagerProvider, Provider<TimeProvider> timeProvider,
      Provider<CreditRepository> creditRepoProvider, Provider<AppPreferences> prefsProvider) {
    this.blockedRepoProvider = blockedRepoProvider;
    this.unlockManagerProvider = unlockManagerProvider;
    this.timeProvider = timeProvider;
    this.creditRepoProvider = creditRepoProvider;
    this.prefsProvider = prefsProvider;
  }

  public static MembersInjector<AppMonitorService> create(
      Provider<BlockedAppRepository> blockedRepoProvider,
      Provider<UnlockStateManager> unlockManagerProvider, Provider<TimeProvider> timeProvider,
      Provider<CreditRepository> creditRepoProvider, Provider<AppPreferences> prefsProvider) {
    return new AppMonitorService_MembersInjector(blockedRepoProvider, unlockManagerProvider, timeProvider, creditRepoProvider, prefsProvider);
  }

  @Override
  public void injectMembers(AppMonitorService instance) {
    injectBlockedRepo(instance, blockedRepoProvider.get());
    injectUnlockManager(instance, unlockManagerProvider.get());
    injectTimeProvider(instance, timeProvider.get());
    injectCreditRepo(instance, creditRepoProvider.get());
    injectPrefs(instance, prefsProvider.get());
  }

  @InjectedFieldSignature("com.focusgate.service.AppMonitorService.blockedRepo")
  public static void injectBlockedRepo(AppMonitorService instance,
      BlockedAppRepository blockedRepo) {
    instance.blockedRepo = blockedRepo;
  }

  @InjectedFieldSignature("com.focusgate.service.AppMonitorService.unlockManager")
  public static void injectUnlockManager(AppMonitorService instance,
      UnlockStateManager unlockManager) {
    instance.unlockManager = unlockManager;
  }

  @InjectedFieldSignature("com.focusgate.service.AppMonitorService.timeProvider")
  public static void injectTimeProvider(AppMonitorService instance, TimeProvider timeProvider) {
    instance.timeProvider = timeProvider;
  }

  @InjectedFieldSignature("com.focusgate.service.AppMonitorService.creditRepo")
  public static void injectCreditRepo(AppMonitorService instance, CreditRepository creditRepo) {
    instance.creditRepo = creditRepo;
  }

  @InjectedFieldSignature("com.focusgate.service.AppMonitorService.prefs")
  public static void injectPrefs(AppMonitorService instance, AppPreferences prefs) {
    instance.prefs = prefs;
  }
}
