package com.focusgate.di;

import com.focusgate.data.db.BlockedAppDao;
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
public final class DatabaseModule_ProvideBlockedAppDaoFactory implements Factory<BlockedAppDao> {
  private final Provider<FocusGateDatabase> dbProvider;

  public DatabaseModule_ProvideBlockedAppDaoFactory(Provider<FocusGateDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public BlockedAppDao get() {
    return provideBlockedAppDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideBlockedAppDaoFactory create(
      Provider<FocusGateDatabase> dbProvider) {
    return new DatabaseModule_ProvideBlockedAppDaoFactory(dbProvider);
  }

  public static BlockedAppDao provideBlockedAppDao(FocusGateDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBlockedAppDao(db));
  }
}
