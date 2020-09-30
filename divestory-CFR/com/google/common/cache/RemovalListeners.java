/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.cache;

import com.google.common.base.Preconditions;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import java.util.concurrent.Executor;

public final class RemovalListeners {
    private RemovalListeners() {
    }

    public static <K, V> RemovalListener<K, V> asynchronous(final RemovalListener<K, V> removalListener, final Executor executor) {
        Preconditions.checkNotNull(removalListener);
        Preconditions.checkNotNull(executor);
        return new RemovalListener<K, V>(){

            @Override
            public void onRemoval(final RemovalNotification<K, V> removalNotification) {
                executor.execute(new Runnable(){

                    @Override
                    public void run() {
                        removalListener.onRemoval(removalNotification);
                    }
                });
            }

        };
    }

}

