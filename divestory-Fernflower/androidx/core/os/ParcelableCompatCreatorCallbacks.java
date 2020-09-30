package androidx.core.os;

import android.os.Parcel;

@Deprecated
public interface ParcelableCompatCreatorCallbacks<T> {
   T createFromParcel(Parcel var1, ClassLoader var2);

   T[] newArray(int var1);
}
