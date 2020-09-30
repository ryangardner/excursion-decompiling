/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

final class zzaa
implements Continuation<Void, List<TResult>> {
    private final /* synthetic */ Collection zza;

    zzaa(Collection collection) {
        this.zza = collection;
    }

    @Override
    public final /* synthetic */ Object then(Task object) throws Exception {
        ArrayList arrayList = new ArrayList();
        object = this.zza.iterator();
        while (object.hasNext()) {
            arrayList.add(((Task)object.next()).getResult());
        }
        return arrayList;
    }
}

