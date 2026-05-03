package com.focusgate.data.repository;

import com.focusgate.data.db.BlockedAppDao;
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
public final class BlockedAppRepository_Factory implements Factory<BlockedAppRepository> {
  private final Provider<BlockedAppDao> daoProvider;

  public BlockedAppRepository_Factory(Provider<BlockedAppDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public BlockedAppRepository get() {
    return newInstance(daoProvider.get());
  }

  public static BlockedAppRepository_Factory create(Provider<BlockedAppDao> daoProvider) {
    return new BlockedAppRepository_Factory(daoProvider);
  }

  public static BlockedAppRepository newInstance(BlockedAppDao dao) {
    return new BlockedAppRepository(dao);
  }
}
