/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zziu;
import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzjk;
import com.google.android.gms.internal.drive.zzjr;
import com.google.android.gms.internal.drive.zzlq;
import java.io.IOException;

public abstract class zzit<MessageType extends zzit<MessageType, BuilderType>, BuilderType extends zziu<MessageType, BuilderType>>
implements zzlq {
    private static boolean zznf = false;
    protected int zzne = 0;

    public final byte[] toByteArray() {
        try {
            byte[] arrby = new byte[this.zzcx()];
            zzjr zzjr2 = zzjr.zzb(arrby);
            this.zzb(zzjr2);
            zzjr2.zzcb();
            return arrby;
        }
        catch (IOException iOException) {
            String string2 = this.getClass().getName();
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 62 + "byte array".length());
            stringBuilder.append("Serializing ");
            stringBuilder.append(string2);
            stringBuilder.append(" to a ");
            stringBuilder.append("byte array");
            stringBuilder.append(" threw an IOException (should never happen).");
            throw new RuntimeException(stringBuilder.toString(), iOException);
        }
    }

    @Override
    public final zzjc zzbl() {
        try {
            zzjk zzjk2 = zzjc.zzu(this.zzcx());
            this.zzb(zzjk2.zzby());
            return zzjk2.zzbx();
        }
        catch (IOException iOException) {
            String string2 = this.getClass().getName();
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 62 + "ByteString".length());
            stringBuilder.append("Serializing ");
            stringBuilder.append(string2);
            stringBuilder.append(" to a ");
            stringBuilder.append("ByteString");
            stringBuilder.append(" threw an IOException (should never happen).");
            throw new RuntimeException(stringBuilder.toString(), iOException);
        }
    }

    int zzbm() {
        throw new UnsupportedOperationException();
    }

    void zzo(int n) {
        throw new UnsupportedOperationException();
    }
}

