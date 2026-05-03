package com.focusgate.data.repository;

import com.focusgate.data.db.CreditDao;
import com.focusgate.data.prefs.AppPreferences;
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
public final class CreditRepository_Factory implements Factory<CreditRepository> {
  private final Provider<CreditDao> daoProvider;

  private final Provider<AppPreferences> prefsProvider;

  public CreditRepository_Factory(Provider<CreditDao> daoProvider,
      Provider<AppPreferences> prefsProvider) {
    this.daoProvider = daoProvider;
    this.prefsProvider = prefsProvider;
  }

  @Override
  public CreditRepository get() {
    return newInstance(daoProvider.get(), prefsProvider.get());
  }

  public static CreditRepository_Factory create(Provider<CreditDao> daoProvider,
      Provider<AppPreferences> prefsProvider) {
    return new CreditRepository_Factory(daoProvider, prefsProvider);
  }

  public static CreditRepository newInstance(CreditDao dao, AppPreferences prefs) {
    return new CreditRepository(dao, prefs);
  }
}
