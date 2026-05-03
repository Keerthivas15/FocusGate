package com.focusgate.di;

import com.focusgate.data.db.CreditDao;
import com.focusgate.data.db.FocusGateDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideCreditDaoFactory implements Factory<CreditDao> {
  private final Provider<FocusGateDatabase> dbProvider;

  public DatabaseModule_ProvideCreditDaoFactory(Provider<FocusGateDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CreditDao get() {
    return provideCreditDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCreditDaoFactory create(
      Provider<FocusGateDatabase> dbProvider) {
    return new DatabaseModule_ProvideCreditDaoFactory(dbProvider);
  }

  public static CreditDao provideCreditDao(FocusGateDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCreditDao(db));
  }
}
