package com.census.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import org.jetbrains.annotations.NotNull;

/**
 * This class is LifeCycle aware Network Monitoring helper.
 */
public class NetworkMonitoringHelper extends ConnectivityManager.NetworkCallback implements LifecycleObserver {
    private final NetworkRequest networkRequest;
    private Lifecycle mLifecycle;
    private Context context;
    private NetworkConnectionListener networkConnectionListener;
    private ConnectivityManager connectivityManager;

    private NetworkMonitoringHelper(Context context, NetworkConnectionListener connectionListener) {
        this.context = context;
        this.networkConnectionListener = connectionListener;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();
    }

    public static NetworkMonitoringHelper newInstance(final Context context, @NotNull NetworkConnectionListener connectionListener) {
        return new NetworkMonitoringHelper(context, connectionListener);
    }

    /**
     * Sets the lifecycle owner for this view. This means you don't need
     * to call {@link #resume()}, {@link #pause()} at all.
     *
     * @param owner the owner activity or fragment
     */
    public void setLifecycleOwner(@NonNull LifecycleOwner owner) {
        if (mLifecycle != null) mLifecycle.removeObserver(this);
        mLifecycle = owner.getLifecycle();
        mLifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume() {
        if (context != null)
            connectivityManager.registerNetworkCallback(networkRequest, this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void pause() {
        if (context != null)
            connectivityManager.unregisterNetworkCallback(this);
    }

    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        if (networkConnectionListener != null)
            networkConnectionListener.onNetworkConnected();
    }

    @Override
    public void onUnavailable() {
        super.onUnavailable();
    }

    public interface NetworkConnectionListener {
        void onNetworkConnected();
    }

}
