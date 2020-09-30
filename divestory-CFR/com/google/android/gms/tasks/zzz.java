/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class zzz
implements Continuation<Void, Task<List<Task<?>>>> {
    private final /* synthetic */ Collection zza;

    zzz(Collection collection) {
        this.zza = collection;
    }

    @Override
    public final /* synthetic */ Object then(Task object) throws Exception {
        object = new ArrayList();
        object.addAll(this.zza);
        return Tasks.forResult(object);
    }
}

