package com.focusgate.ui.screen;

import android.content.Context;
import com.focusgate.data.repository.BlockedAppRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AppSelectViewModel_Factory implements Factory<AppSelectViewModel> {
  private final Provider<Context> ctxProvider;

  private final Provider<BlockedAppRepository> repoProvider;

  public AppSelectViewModel_Factory(Provider<Context> ctxProvider,
      Provider<BlockedAppRepository> repoProvider) {
    this.ctxProvider = ctxProvider;
    this.repoProvider = repoProvider;
  }

  @Override
  public AppSelectViewModel get() {
    return newInstance(ctxProvider.get(), repoProvider.get());
  }

  public static AppSelectViewModel_Factory create(Provider<Context> ctxProvider,
      Provider<BlockedAppRepository> repoProvider) {
    return new AppSelectViewModel_Factory(ctxProvider, repoProvider);
  }

  public static AppSelectViewModel newInstance(Context ctx, BlockedAppRepository repo) {
    return new AppSelectViewModel(ctx, repo);
  }
}
