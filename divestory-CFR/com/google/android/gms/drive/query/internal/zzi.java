/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive.query.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.Iterator;
import java.util.Set;

final class zzi {
    static MetadataField<?> zza(MetadataBundle object) {
        if ((object = ((MetadataBundle)object).zzbg()).size() != 1) throw new IllegalArgumentException("bundle should have exactly 1 populated field");
        return (MetadataField)object.iterator().next();
    }
}

