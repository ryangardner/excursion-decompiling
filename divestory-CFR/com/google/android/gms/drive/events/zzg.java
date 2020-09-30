/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.events;

import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.List;

public final class zzg
implements Parcelable.Creator<CompletionEvent> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Object object;
        Object object2;
        IBinder iBinder;
        IBinder iBinder2;
        Object object3;
        Object object4;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        IBinder iBinder3 = object4 = (object2 = (iBinder2 = (iBinder = (object = (object3 = null)))));
        int n2 = 0;
        block10 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new CompletionEvent((DriveId)object3, (String)object, (ParcelFileDescriptor)iBinder, (ParcelFileDescriptor)iBinder2, (MetadataBundle)object2, (List<String>)object4, n2, iBinder3);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(n3)) {
                default: {
                    SafeParcelReader.skipUnknownField(parcel, n3);
                    continue block10;
                }
                case 9: {
                    iBinder3 = SafeParcelReader.readIBinder(parcel, n3);
                    continue block10;
                }
                case 8: {
                    n2 = SafeParcelReader.readInt(parcel, n3);
                    continue block10;
                }
                case 7: {
                    object4 = SafeParcelReader.createStringList(parcel, n3);
                    continue block10;
                }
                case 6: {
                    object2 = SafeParcelReader.createParcelable(parcel, n3, MetadataBundle.CREATOR);
                    continue block10;
                }
                case 5: {
                    iBinder2 = (ParcelFileDescriptor)SafeParcelReader.createParcelable(parcel, n3, ParcelFileDescriptor.CREATOR);
                    continue block10;
                }
                case 4: {
                    iBinder = (ParcelFileDescriptor)SafeParcelReader.createParcelable(parcel, n3, ParcelFileDescriptor.CREATOR);
                    continue block10;
                }
                case 3: {
                    object = SafeParcelReader.createString(parcel, n3);
                    continue block10;
                }
                case 2: 
            }
            object3 = SafeParcelReader.createParcelable(parcel, n3, DriveId.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new CompletionEvent[n];
    }
}

