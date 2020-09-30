/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public abstract class FastSafeParcelableJsonResponse
extends FastJsonResponse
implements SafeParcelable {
    public final int describeContents() {
        return 0;
    }

    /*
     * Unable to fully structure code
     */
    public boolean equals(Object var1_1) {
        if (var1_1 == null) {
            return false;
        }
        if (this == var1_1) {
            return true;
        }
        if (!this.getClass().isInstance(var1_1)) {
            return false;
        }
        var2_2 = (FastJsonResponse)var1_1;
        var3_3 = this.getFieldMappings().values().iterator();
        do lbl-1000: // 3 sources:
        {
            if (var3_3.hasNext() == false) return true;
            var1_1 = var3_3.next();
            if (!this.isFieldSet(var1_1)) continue;
            if (var2_2.isFieldSet(var1_1) == false) return false;
            if (Objects.equal(this.getFieldValue(var1_1), var2_2.getFieldValue(var1_1))) ** GOTO lbl-1000
            return false;
        } while (!var2_2.isFieldSet(var1_1));
        return false;
    }

    @Override
    public Object getValueObject(String string2) {
        return null;
    }

    public int hashCode() {
        Iterator<FastJsonResponse.Field<?, ?>> iterator2 = this.getFieldMappings().values().iterator();
        int n = 0;
        while (iterator2.hasNext()) {
            FastJsonResponse.Field<?, ?> field = iterator2.next();
            if (!this.isFieldSet(field)) continue;
            n = n * 31 + Preconditions.checkNotNull(this.getFieldValue(field)).hashCode();
        }
        return n;
    }

    @Override
    public boolean isPrimitiveFieldSet(String string2) {
        return false;
    }

    public byte[] toByteArray() {
        Parcel parcel = Parcel.obtain();
        this.writeToParcel(parcel, 0);
        byte[] arrby = parcel.marshall();
        parcel.recycle();
        return arrby;
    }
}

