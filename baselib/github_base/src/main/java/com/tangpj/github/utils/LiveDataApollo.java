package com.tangpj.github.utils;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloQueryWatcher;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import static com.apollographql.apollo.api.internal.Utils.checkNotNull;

import javax.annotation.Nonnull;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class LiveDataApollo {

    private LiveDataApollo(){
        throw new AssertionError("This class cannot be instantiated");
    }

    /**
     * Converts an {@link ApolloQueryWatcher} to an asynchronous Observable.
     *
     * @param watcher the ApolloQueryWatcher to convert.
     * @param <T>     the value type
     * @return the converted Observable
     * @throws NullPointerException if watcher == null
     */
    public static <T> LiveData<Response<T>> from(@Nonnull final ApolloQueryWatcher<T> watcher) {
        checkNotNull(watcher, "watcher == null");
        LiveData<Response<T>> liveData = new MediatorLiveData<>();
        watcher.enqueueAndWatch(new ApolloCall.Callback<T>() {
            @Override
            public void onResponse(@NotNull Response<T> response) {

            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });
        return liveData;
    }
}
