package com.focusgate;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.focusgate.data.db.BlockedAppDao;
import com.focusgate.data.db.CreditDao;
import com.focusgate.data.db.FocusGateDatabase;
import com.focusgate.data.prefs.AppPreferences;
import com.focusgate.data.repository.BlockedAppRepository;
import com.focusgate.data.repository.CreditRepository;
import com.focusgate.di.AppModule_ProvideProblemRegistryFactory;
import com.focusgate.di.DatabaseModule_ProvideBlockedAppDaoFactory;
import com.focusgate.di.DatabaseModule_ProvideCreditDaoFactory;
import com.focusgate.di.DatabaseModule_ProvideDatabaseFactory;
import com.focusgate.math.ProblemRegistry;
import com.focusgate.service.AppMonitorService;
import com.focusgate.service.AppMonitorService_MembersInjector;
import com.focusgate.service.BootReceiver;
import com.focusgate.service.BootReceiver_MembersInjector;
import com.focusgate.ui.screen.AppSelectViewModel;
import com.focusgate.ui.screen.AppSelectViewModel_HiltModules;
import com.focusgate.ui.screen.CreditsViewModel;
import com.focusgate.ui.screen.CreditsViewModel_HiltModules;
import com.focusgate.ui.screen.DashboardViewModel;
import com.focusgate.ui.screen.DashboardViewModel_HiltModules;
import com.focusgate.ui.screen.MathGateActivity;
import com.focusgate.ui.screen.MathGateViewModel;
import com.focusgate.ui.screen.MathGateViewModel_HiltModules;
import com.focusgate.ui.screen.OnboardingViewModel;
import com.focusgate.ui.screen.OnboardingViewModel_HiltModules;
import com.focusgate.ui.screen.SettingsViewModel;
import com.focusgate.ui.screen.SettingsViewModel_HiltModules;
import com.focusgate.util.TimeProvider;
import com.focusgate.util.UnlockStateManager;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerFocusGateApp_HiltComponents_SingletonC {
  private DaggerFocusGateApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public FocusGateApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements FocusGateApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public FocusGateApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements FocusGateApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public FocusGateApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements FocusGateApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public FocusGateApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements FocusGateApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public FocusGateApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements FocusGateApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public FocusGateApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements FocusGateApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public FocusGateApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements FocusGateApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public FocusGateApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends FocusGateApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends FocusGateApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends FocusGateApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends FocusGateApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public void injectMathGateActivity(MathGateActivity mathGateActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(6).put(LazyClassKeyProvider.com_focusgate_ui_screen_AppSelectViewModel, AppSelectViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_focusgate_ui_screen_CreditsViewModel, CreditsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_focusgate_ui_screen_DashboardViewModel, DashboardViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_focusgate_ui_screen_MathGateViewModel, MathGateViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_focusgate_ui_screen_OnboardingViewModel, OnboardingViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_focusgate_ui_screen_SettingsViewModel, SettingsViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_focusgate_ui_screen_AppSelectViewModel = "com.focusgate.ui.screen.AppSelectViewModel";

      static String com_focusgate_ui_screen_CreditsViewModel = "com.focusgate.ui.screen.CreditsViewModel";

      static String com_focusgate_ui_screen_MathGateViewModel = "com.focusgate.ui.screen.MathGateViewModel";

      static String com_focusgate_ui_screen_SettingsViewModel = "com.focusgate.ui.screen.SettingsViewModel";

      static String com_focusgate_ui_screen_OnboardingViewModel = "com.focusgate.ui.screen.OnboardingViewModel";

      static String com_focusgate_ui_screen_DashboardViewModel = "com.focusgate.ui.screen.DashboardViewModel";

      @KeepFieldType
      AppSelectViewModel com_focusgate_ui_screen_AppSelectViewModel2;

      @KeepFieldType
      CreditsViewModel com_focusgate_ui_screen_CreditsViewModel2;

      @KeepFieldType
      MathGateViewModel com_focusgate_ui_screen_MathGateViewModel2;

      @KeepFieldType
      SettingsViewModel com_focusgate_ui_screen_SettingsViewModel2;

      @KeepFieldType
      OnboardingViewModel com_focusgate_ui_screen_OnboardingViewModel2;

      @KeepFieldType
      DashboardViewModel com_focusgate_ui_screen_DashboardViewModel2;
    }
  }

  private static final class ViewModelCImpl extends FocusGateApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AppSelectViewModel> appSelectViewModelProvider;

    private Provider<CreditsViewModel> creditsViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<MathGateViewModel> mathGateViewModelProvider;

    private Provider<OnboardingViewModel> onboardingViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.appSelectViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.creditsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.mathGateViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.onboardingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(6).put(LazyClassKeyProvider.com_focusgate_ui_screen_AppSelectViewModel, ((Provider) appSelectViewModelProvider)).put(LazyClassKeyProvider.com_focusgate_ui_screen_CreditsViewModel, ((Provider) creditsViewModelProvider)).put(LazyClassKeyProvider.com_focusgate_ui_screen_DashboardViewModel, ((Provider) dashboardViewModelProvider)).put(LazyClassKeyProvider.com_focusgate_ui_screen_MathGateViewModel, ((Provider) mathGateViewModelProvider)).put(LazyClassKeyProvider.com_focusgate_ui_screen_OnboardingViewModel, ((Provider) onboardingViewModelProvider)).put(LazyClassKeyProvider.com_focusgate_ui_screen_SettingsViewModel, ((Provider) settingsViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_focusgate_ui_screen_DashboardViewModel = "com.focusgate.ui.screen.DashboardViewModel";

      static String com_focusgate_ui_screen_OnboardingViewModel = "com.focusgate.ui.screen.OnboardingViewModel";

      static String com_focusgate_ui_screen_CreditsViewModel = "com.focusgate.ui.screen.CreditsViewModel";

      static String com_focusgate_ui_screen_SettingsViewModel = "com.focusgate.ui.screen.SettingsViewModel";

      static String com_focusgate_ui_screen_MathGateViewModel = "com.focusgate.ui.screen.MathGateViewModel";

      static String com_focusgate_ui_screen_AppSelectViewModel = "com.focusgate.ui.screen.AppSelectViewModel";

      @KeepFieldType
      DashboardViewModel com_focusgate_ui_screen_DashboardViewModel2;

      @KeepFieldType
      OnboardingViewModel com_focusgate_ui_screen_OnboardingViewModel2;

      @KeepFieldType
      CreditsViewModel com_focusgate_ui_screen_CreditsViewModel2;

      @KeepFieldType
      SettingsViewModel com_focusgate_ui_screen_SettingsViewModel2;

      @KeepFieldType
      MathGateViewModel com_focusgate_ui_screen_MathGateViewModel2;

      @KeepFieldType
      AppSelectViewModel com_focusgate_ui_screen_AppSelectViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.focusgate.ui.screen.AppSelectViewModel 
          return (T) new AppSelectViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.blockedAppRepositoryProvider.get());

          case 1: // com.focusgate.ui.screen.CreditsViewModel 
          return (T) new CreditsViewModel(singletonCImpl.creditRepositoryProvider.get());

          case 2: // com.focusgate.ui.screen.DashboardViewModel 
          return (T) new DashboardViewModel(singletonCImpl.blockedAppRepositoryProvider.get(), singletonCImpl.creditRepositoryProvider.get(), singletonCImpl.appPreferencesProvider.get());

          case 3: // com.focusgate.ui.screen.MathGateViewModel 
          return (T) new MathGateViewModel(singletonCImpl.provideProblemRegistryProvider.get(), singletonCImpl.unlockStateManagerProvider.get(), singletonCImpl.creditRepositoryProvider.get(), singletonCImpl.appPreferencesProvider.get());

          case 4: // com.focusgate.ui.screen.OnboardingViewModel 
          return (T) new OnboardingViewModel(singletonCImpl.appPreferencesProvider.get());

          case 5: // com.focusgate.ui.screen.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.appPreferencesProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends FocusGateApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends FocusGateApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectAppMonitorService(AppMonitorService appMonitorService) {
      injectAppMonitorService2(appMonitorService);
    }

    private AppMonitorService injectAppMonitorService2(AppMonitorService instance) {
      AppMonitorService_MembersInjector.injectBlockedRepo(instance, singletonCImpl.blockedAppRepositoryProvider.get());
      AppMonitorService_MembersInjector.injectUnlockManager(instance, singletonCImpl.unlockStateManagerProvider.get());
      AppMonitorService_MembersInjector.injectTimeProvider(instance, singletonCImpl.timeProvider.get());
      AppMonitorService_MembersInjector.injectCreditRepo(instance, singletonCImpl.creditRepositoryProvider.get());
      AppMonitorService_MembersInjector.injectPrefs(instance, singletonCImpl.appPreferencesProvider.get());
      return instance;
    }
  }

  private static final class SingletonCImpl extends FocusGateApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<AppPreferences> appPreferencesProvider;

    private Provider<FocusGateDatabase> provideDatabaseProvider;

    private Provider<BlockedAppRepository> blockedAppRepositoryProvider;

    private Provider<CreditRepository> creditRepositoryProvider;

    private Provider<ProblemRegistry> provideProblemRegistryProvider;

    private Provider<TimeProvider> timeProvider;

    private Provider<UnlockStateManager> unlockStateManagerProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private BlockedAppDao blockedAppDao() {
      return DatabaseModule_ProvideBlockedAppDaoFactory.provideBlockedAppDao(provideDatabaseProvider.get());
    }

    private CreditDao creditDao() {
      return DatabaseModule_ProvideCreditDaoFactory.provideCreditDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.appPreferencesProvider = DoubleCheck.provider(new SwitchingProvider<AppPreferences>(singletonCImpl, 0));
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<FocusGateDatabase>(singletonCImpl, 2));
      this.blockedAppRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<BlockedAppRepository>(singletonCImpl, 1));
      this.creditRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<CreditRepository>(singletonCImpl, 3));
      this.provideProblemRegistryProvider = DoubleCheck.provider(new SwitchingProvider<ProblemRegistry>(singletonCImpl, 4));
      this.timeProvider = DoubleCheck.provider(new SwitchingProvider<TimeProvider>(singletonCImpl, 6));
      this.unlockStateManagerProvider = DoubleCheck.provider(new SwitchingProvider<UnlockStateManager>(singletonCImpl, 5));
    }

    @Override
    public void injectFocusGateApp(FocusGateApp focusGateApp) {
    }

    @Override
    public void injectBootReceiver(BootReceiver bootReceiver) {
      injectBootReceiver2(bootReceiver);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private BootReceiver injectBootReceiver2(BootReceiver instance) {
      BootReceiver_MembersInjector.injectPrefs(instance, appPreferencesProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.focusgate.data.prefs.AppPreferences 
          return (T) new AppPreferences(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 1: // com.focusgate.data.repository.BlockedAppRepository 
          return (T) new BlockedAppRepository(singletonCImpl.blockedAppDao());

          case 2: // com.focusgate.data.db.FocusGateDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // com.focusgate.data.repository.CreditRepository 
          return (T) new CreditRepository(singletonCImpl.creditDao(), singletonCImpl.appPreferencesProvider.get());

          case 4: // com.focusgate.math.ProblemRegistry 
          return (T) AppModule_ProvideProblemRegistryFactory.provideProblemRegistry();

          case 5: // com.focusgate.util.UnlockStateManager 
          return (T) new UnlockStateManager(singletonCImpl.timeProvider.get());

          case 6: // com.focusgate.util.TimeProvider 
          return (T) new TimeProvider();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
