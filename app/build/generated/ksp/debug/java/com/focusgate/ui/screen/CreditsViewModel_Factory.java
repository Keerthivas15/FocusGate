package com.focusgate.ui.screen;

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
public final class CreditsViewModel_Factory implements Factory<CreditsViewModel> {
  private final Provider<CreditRepository> repoProvider;

  public CreditsViewModel_Factory(Provider<CreditRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public CreditsViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static CreditsViewModel_Factory create(Provider<CreditRepository> repoProvider) {
    return new CreditsViewModel_Factory(repoProvider);
  }

  public static CreditsViewModel newInstance(CreditRepository repo) {
    return new CreditsViewModel(repo);
  }
}
