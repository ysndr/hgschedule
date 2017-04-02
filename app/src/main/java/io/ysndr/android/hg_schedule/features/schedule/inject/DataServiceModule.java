package io.ysndr.android.hg_schedule.features.schedule.inject;

import android.app.Application;
import android.support.v7.preference.PreferenceManager;

import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.pacoworks.rxobservablediskcache.RxObservableDiskCache;
import com.pacoworks.rxobservablediskcache.policy.TimeAndVersionPolicy;
import com.pacoworks.rxpaper.RxPaperBook;

import java.io.Serializable;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.ysndr.android.hg_schedule.BuildConfig;
import retrofit2.Retrofit;
import rx.schedulers.Schedulers;

/**
 * Created by yannik on 8/22/16.
 */
@Module
public class DataServiceModule {
    @Provides
    @Singleton
    RemoteDataService provideRemoteDataService(Retrofit retrofit) {
        return retrofit.create(RemoteDataService.class);
    }

    @Provides
    @Singleton
    RxSharedPreferences providePreferences(Application app) {
        return RxSharedPreferences.create(PreferenceManager.getDefaultSharedPreferences(app));
    }


    @Provides
    @Singleton
    RxObservableDiskCache provideDiskCache() {
        return RxObservableDiskCache.create(RxPaperBook.with(".cache", Schedulers.io()),
                TimeAndVersionPolicy.<Serializable>create(BuildConfig.VERSION_CODE),
                TimeAndVersionPolicy.validate(1000 * 60 * 5, BuildConfig.VERSION_CODE)
        );
    }

//    @Provides
//    @Singleton
//    CombinedDataService provideCombinedDataService(RemoteDataService remote) {
//        return new CombinedDataService(remote);
//    }

}
