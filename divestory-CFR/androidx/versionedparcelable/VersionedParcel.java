/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.BadParcelableException
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.NetworkOnMainThreadException
 *  android.os.Parcelable
 *  android.util.Size
 *  android.util.SizeF
 *  android.util.SparseBooleanArray
 */
package androidx.versionedparcelable;

import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.NetworkOnMainThreadException;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseBooleanArray;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import androidx.versionedparcelable.VersionedParcelable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class VersionedParcel {
    private static final int EX_BAD_PARCELABLE = -2;
    private static final int EX_ILLEGAL_ARGUMENT = -3;
    private static final int EX_ILLEGAL_STATE = -5;
    private static final int EX_NETWORK_MAIN_THREAD = -6;
    private static final int EX_NULL_POINTER = -4;
    private static final int EX_PARCELABLE = -9;
    private static final int EX_SECURITY = -1;
    private static final int EX_UNSUPPORTED_OPERATION = -7;
    private static final String TAG = "VersionedParcel";
    private static final int TYPE_BINDER = 5;
    private static final int TYPE_FLOAT = 8;
    private static final int TYPE_INTEGER = 7;
    private static final int TYPE_PARCELABLE = 2;
    private static final int TYPE_SERIALIZABLE = 3;
    private static final int TYPE_STRING = 4;
    private static final int TYPE_VERSIONED_PARCELABLE = 1;
    protected final ArrayMap<String, Class> mParcelizerCache;
    protected final ArrayMap<String, Method> mReadCache;
    protected final ArrayMap<String, Method> mWriteCache;

    public VersionedParcel(ArrayMap<String, Method> arrayMap, ArrayMap<String, Method> arrayMap2, ArrayMap<String, Class> arrayMap3) {
        this.mReadCache = arrayMap;
        this.mWriteCache = arrayMap2;
        this.mParcelizerCache = arrayMap3;
    }

    private Exception createException(int n, String string2) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown exception code: ");
                stringBuilder.append(n);
                stringBuilder.append(" msg ");
                stringBuilder.append(string2);
                return new RuntimeException(stringBuilder.toString());
            }
            case -1: {
                return new SecurityException(string2);
            }
            case -2: {
                return new BadParcelableException(string2);
            }
            case -3: {
                return new IllegalArgumentException(string2);
            }
            case -4: {
                return new NullPointerException(string2);
            }
            case -5: {
                return new IllegalStateException(string2);
            }
            case -6: {
                return new NetworkOnMainThreadException();
            }
            case -7: {
                return new UnsupportedOperationException(string2);
            }
            case -9: 
        }
        return (Exception)this.readParcelable();
    }

    private Class findParcelClass(Class<? extends VersionedParcelable> class_) throws ClassNotFoundException {
        Class<?> class_2;
        Class<?> class_3 = class_2 = (Class<?>)this.mParcelizerCache.get(class_.getName());
        if (class_2 != null) return class_3;
        class_3 = Class.forName(String.format("%s.%sParcelizer", class_.getPackage().getName(), class_.getSimpleName()), false, class_.getClassLoader());
        this.mParcelizerCache.put(class_.getName(), class_3);
        return class_3;
    }

    private Method getReadMethod(String string2) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        Method method;
        Method method2 = method = (Method)this.mReadCache.get(string2);
        if (method != null) return method2;
        System.currentTimeMillis();
        method2 = Class.forName(string2, true, VersionedParcel.class.getClassLoader()).getDeclaredMethod("read", VersionedParcel.class);
        this.mReadCache.put(string2, method2);
        return method2;
    }

    protected static Throwable getRootCause(Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

    private <T> int getType(T t) {
        if (t instanceof String) {
            return 4;
        }
        if (t instanceof Parcelable) {
            return 2;
        }
        if (t instanceof VersionedParcelable) {
            return 1;
        }
        if (t instanceof Serializable) {
            return 3;
        }
        if (t instanceof IBinder) {
            return 5;
        }
        if (t instanceof Integer) {
            return 7;
        }
        if (t instanceof Float) {
            return 8;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(t.getClass().getName());
        stringBuilder.append(" cannot be VersionedParcelled");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private Method getWriteMethod(Class class_) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        Method method = (Method)this.mWriteCache.get(class_.getName());
        GenericDeclaration genericDeclaration = method;
        if (method != null) return genericDeclaration;
        genericDeclaration = this.findParcelClass(class_);
        System.currentTimeMillis();
        genericDeclaration = ((Class)genericDeclaration).getDeclaredMethod("write", class_, VersionedParcel.class);
        this.mWriteCache.put(class_.getName(), (Method)genericDeclaration);
        return genericDeclaration;
    }

    private <T, S extends Collection<T>> S readCollection(S s) {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        if (n == 0) return s;
        int n2 = this.readInt();
        if (n < 0) {
            return null;
        }
        int n3 = n;
        if (n2 == 1) {
            while (n3 > 0) {
                s.add(this.readVersionedParcelable());
                --n3;
            }
            return s;
        }
        n3 = n;
        if (n2 == 2) {
            while (n3 > 0) {
                s.add(this.readParcelable());
                --n3;
            }
            return s;
        }
        n3 = n;
        if (n2 == 3) {
            while (n3 > 0) {
                s.add((Serializable)this.readSerializable());
                --n3;
            }
            return s;
        }
        n3 = n;
        if (n2 == 4) {
            while (n3 > 0) {
                s.add((String)this.readString());
                --n3;
            }
            return s;
        }
        if (n2 != 5) {
            return s;
        }
        while (n > 0) {
            s.add((IBinder)this.readStrongBinder());
            --n;
        }
        return s;
    }

    private Exception readException(int n, String string2) {
        return this.createException(n, string2);
    }

    private int readExceptionCode() {
        return this.readInt();
    }

    private <T> void writeCollection(Collection<T> iterator2) {
        if (iterator2 == null) {
            this.writeInt(-1);
            return;
        }
        int n = iterator2.size();
        this.writeInt(n);
        if (n <= 0) return;
        n = this.getType(iterator2.iterator().next());
        this.writeInt(n);
        switch (n) {
            default: {
                return;
            }
            case 8: {
                iterator2 = iterator2.iterator();
                while (iterator2.hasNext()) {
                    this.writeFloat(((Float)iterator2.next()).floatValue());
                }
                return;
            }
            case 7: {
                iterator2 = iterator2.iterator();
                while (iterator2.hasNext()) {
                    this.writeInt((Integer)iterator2.next());
                }
                return;
            }
            case 5: {
                iterator2 = iterator2.iterator();
                while (iterator2.hasNext()) {
                    this.writeStrongBinder((IBinder)iterator2.next());
                }
                return;
            }
            case 4: {
                iterator2 = iterator2.iterator();
                while (iterator2.hasNext()) {
                    this.writeString((String)iterator2.next());
                }
                return;
            }
            case 3: {
                iterator2 = iterator2.iterator();
                while (iterator2.hasNext()) {
                    this.writeSerializable((Serializable)iterator2.next());
                }
                return;
            }
            case 2: {
                iterator2 = iterator2.iterator();
                while (iterator2.hasNext()) {
                    this.writeParcelable((Parcelable)iterator2.next());
                }
                return;
            }
            case 1: 
        }
        iterator2 = iterator2.iterator();
        while (iterator2.hasNext()) {
            this.writeVersionedParcelable((VersionedParcelable)iterator2.next());
        }
    }

    private <T> void writeCollection(Collection<T> collection, int n) {
        this.setOutputField(n);
        this.writeCollection(collection);
    }

    private void writeSerializable(Serializable serializable) {
        if (serializable == null) {
            this.writeString(null);
            return;
        }
        String string2 = serializable.getClass().getName();
        this.writeString(string2);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.close();
            this.writeByteArray(byteArrayOutputStream.toByteArray());
            return;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("VersionedParcelable encountered IOException writing serializable object (name = ");
            stringBuilder.append(string2);
            stringBuilder.append(")");
            throw new RuntimeException(stringBuilder.toString(), iOException);
        }
    }

    private void writeVersionedParcelableCreator(VersionedParcelable versionedParcelable) {
        try {
            Class class_ = this.findParcelClass(versionedParcelable.getClass());
            this.writeString(class_.getName());
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(versionedParcelable.getClass().getSimpleName());
            stringBuilder.append(" does not have a Parcelizer");
            throw new RuntimeException(stringBuilder.toString(), classNotFoundException);
        }
    }

    protected abstract void closeField();

    protected abstract VersionedParcel createSubParcel();

    public boolean isStream() {
        return false;
    }

    protected <T> T[] readArray(T[] arrT) {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        ArrayList<Object> arrayList = new ArrayList<Object>(n);
        if (n == 0) return arrayList.toArray(arrT);
        int n2 = this.readInt();
        if (n < 0) {
            return null;
        }
        int n3 = n;
        if (n2 == 1) {
            while (n3 > 0) {
                arrayList.add(this.readVersionedParcelable());
                --n3;
            }
            return arrayList.toArray(arrT);
        }
        n3 = n;
        if (n2 == 2) {
            while (n3 > 0) {
                arrayList.add(this.readParcelable());
                --n3;
            }
            return arrayList.toArray(arrT);
        }
        n3 = n;
        if (n2 == 3) {
            while (n3 > 0) {
                arrayList.add(this.readSerializable());
                --n3;
            }
            return arrayList.toArray(arrT);
        }
        n3 = n;
        if (n2 == 4) {
            while (n3 > 0) {
                arrayList.add(this.readString());
                --n3;
            }
            return arrayList.toArray(arrT);
        }
        if (n2 != 5) {
            return arrayList.toArray(arrT);
        }
        while (n > 0) {
            arrayList.add((Object)this.readStrongBinder());
            --n;
        }
        return arrayList.toArray(arrT);
    }

    public <T> T[] readArray(T[] arrT, int n) {
        if (this.readField(n)) return this.readArray(arrT);
        return arrT;
    }

    protected abstract boolean readBoolean();

    public boolean readBoolean(boolean bl, int n) {
        if (this.readField(n)) return this.readBoolean();
        return bl;
    }

    protected boolean[] readBooleanArray() {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        boolean[] arrbl = new boolean[n];
        int n2 = 0;
        while (n2 < n) {
            boolean bl = this.readInt() != 0;
            arrbl[n2] = bl;
            ++n2;
        }
        return arrbl;
    }

    public boolean[] readBooleanArray(boolean[] arrbl, int n) {
        if (this.readField(n)) return this.readBooleanArray();
        return arrbl;
    }

    protected abstract Bundle readBundle();

    public Bundle readBundle(Bundle bundle, int n) {
        if (this.readField(n)) return this.readBundle();
        return bundle;
    }

    public byte readByte(byte by, int n) {
        if (this.readField(n)) return (byte)(this.readInt() & 255);
        return by;
    }

    protected abstract byte[] readByteArray();

    public byte[] readByteArray(byte[] arrby, int n) {
        if (this.readField(n)) return this.readByteArray();
        return arrby;
    }

    public char[] readCharArray(char[] arrc, int n) {
        if (!this.readField(n)) {
            return arrc;
        }
        int n2 = this.readInt();
        if (n2 < 0) {
            return null;
        }
        arrc = new char[n2];
        n = 0;
        while (n < n2) {
            arrc[n] = (char)this.readInt();
            ++n;
        }
        return arrc;
    }

    protected abstract CharSequence readCharSequence();

    public CharSequence readCharSequence(CharSequence charSequence, int n) {
        if (this.readField(n)) return this.readCharSequence();
        return charSequence;
    }

    protected abstract double readDouble();

    public double readDouble(double d, int n) {
        if (this.readField(n)) return this.readDouble();
        return d;
    }

    protected double[] readDoubleArray() {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        double[] arrd = new double[n];
        int n2 = 0;
        while (n2 < n) {
            arrd[n2] = this.readDouble();
            ++n2;
        }
        return arrd;
    }

    public double[] readDoubleArray(double[] arrd, int n) {
        if (this.readField(n)) return this.readDoubleArray();
        return arrd;
    }

    public Exception readException(Exception exception, int n) {
        if (!this.readField(n)) {
            return exception;
        }
        n = this.readExceptionCode();
        if (n == 0) return exception;
        return this.readException(n, this.readString());
    }

    protected abstract boolean readField(int var1);

    protected abstract float readFloat();

    public float readFloat(float f, int n) {
        if (this.readField(n)) return this.readFloat();
        return f;
    }

    protected float[] readFloatArray() {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        float[] arrf = new float[n];
        int n2 = 0;
        while (n2 < n) {
            arrf[n2] = this.readFloat();
            ++n2;
        }
        return arrf;
    }

    public float[] readFloatArray(float[] arrf, int n) {
        if (this.readField(n)) return this.readFloatArray();
        return arrf;
    }

    protected <T extends VersionedParcelable> T readFromParcel(String object, VersionedParcel versionedParcel) {
        try {
            object = (VersionedParcelable)this.getReadMethod((String)object).invoke(null, versionedParcel);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", classNotFoundException);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", noSuchMethodException);
        }
        catch (InvocationTargetException invocationTargetException) {
            if (!(invocationTargetException.getCause() instanceof RuntimeException)) throw new RuntimeException("VersionedParcel encountered InvocationTargetException", invocationTargetException);
            throw (RuntimeException)invocationTargetException.getCause();
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", illegalAccessException);
        }
        return (T)object;
    }

    protected abstract int readInt();

    public int readInt(int n, int n2) {
        if (this.readField(n2)) return this.readInt();
        return n;
    }

    protected int[] readIntArray() {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        int[] arrn = new int[n];
        int n2 = 0;
        while (n2 < n) {
            arrn[n2] = this.readInt();
            ++n2;
        }
        return arrn;
    }

    public int[] readIntArray(int[] arrn, int n) {
        if (this.readField(n)) return this.readIntArray();
        return arrn;
    }

    public <T> List<T> readList(List<T> list, int n) {
        if (this.readField(n)) return this.readCollection(new ArrayList<E>());
        return list;
    }

    protected abstract long readLong();

    public long readLong(long l, int n) {
        if (this.readField(n)) return this.readLong();
        return l;
    }

    protected long[] readLongArray() {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        long[] arrl = new long[n];
        int n2 = 0;
        while (n2 < n) {
            arrl[n2] = this.readLong();
            ++n2;
        }
        return arrl;
    }

    public long[] readLongArray(long[] arrl, int n) {
        if (this.readField(n)) return this.readLongArray();
        return arrl;
    }

    public <K, V> Map<K, V> readMap(Map<K, V> object, int n) {
        if (!this.readField(n)) {
            return object;
        }
        int n2 = this.readInt();
        if (n2 < 0) {
            return null;
        }
        ArrayMap<E, E> arrayMap = new ArrayMap<E, E>();
        if (n2 == 0) {
            return arrayMap;
        }
        ArrayList<E> arrayList = new ArrayList<E>();
        object = new ArrayList<E>();
        this.readCollection(arrayList);
        this.readCollection(object);
        n = 0;
        while (n < n2) {
            arrayMap.put(arrayList.get(n), object.get(n));
            ++n;
        }
        return arrayMap;
    }

    protected abstract <T extends Parcelable> T readParcelable();

    public <T extends Parcelable> T readParcelable(T t, int n) {
        if (this.readField(n)) return this.readParcelable();
        return t;
    }

    protected Serializable readSerializable() {
        String string2 = this.readString();
        if (string2 == null) {
            return null;
        }
        Object object = new ByteArrayInputStream(this.readByteArray());
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream((InputStream)object){

                @Override
                protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                    Class<?> class_ = Class.forName(objectStreamClass.getName(), false, this.getClass().getClassLoader());
                    if (class_ == null) return super.resolveClass(objectStreamClass);
                    return class_;
                }
            };
            return (Serializable)objectInputStream.readObject();
        }
        catch (ClassNotFoundException classNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("VersionedParcelable encountered ClassNotFoundException reading a Serializable object (name = ");
            stringBuilder.append(string2);
            stringBuilder.append(")");
            throw new RuntimeException(stringBuilder.toString(), classNotFoundException);
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("VersionedParcelable encountered IOException reading a Serializable object (name = ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(")");
            throw new RuntimeException(((StringBuilder)object).toString(), iOException);
        }
    }

    public <T> Set<T> readSet(Set<T> set, int n) {
        if (this.readField(n)) return this.readCollection(new ArraySet<E>());
        return set;
    }

    public Size readSize(Size size, int n) {
        if (!this.readField(n)) {
            return size;
        }
        if (!this.readBoolean()) return null;
        return new Size(this.readInt(), this.readInt());
    }

    public SizeF readSizeF(SizeF sizeF, int n) {
        if (!this.readField(n)) {
            return sizeF;
        }
        if (!this.readBoolean()) return null;
        return new SizeF(this.readFloat(), this.readFloat());
    }

    public SparseBooleanArray readSparseBooleanArray(SparseBooleanArray sparseBooleanArray, int n) {
        if (!this.readField(n)) {
            return sparseBooleanArray;
        }
        int n2 = this.readInt();
        if (n2 < 0) {
            return null;
        }
        sparseBooleanArray = new SparseBooleanArray(n2);
        n = 0;
        while (n < n2) {
            sparseBooleanArray.put(this.readInt(), this.readBoolean());
            ++n;
        }
        return sparseBooleanArray;
    }

    protected abstract String readString();

    public String readString(String string2, int n) {
        if (this.readField(n)) return this.readString();
        return string2;
    }

    protected abstract IBinder readStrongBinder();

    public IBinder readStrongBinder(IBinder iBinder, int n) {
        if (this.readField(n)) return this.readStrongBinder();
        return iBinder;
    }

    protected <T extends VersionedParcelable> T readVersionedParcelable() {
        String string2 = this.readString();
        if (string2 != null) return this.readFromParcel(string2, this.createSubParcel());
        return null;
    }

    public <T extends VersionedParcelable> T readVersionedParcelable(T t, int n) {
        if (this.readField(n)) return this.readVersionedParcelable();
        return t;
    }

    protected abstract void setOutputField(int var1);

    public void setSerializationFlags(boolean bl, boolean bl2) {
    }

    protected <T> void writeArray(T[] arrT) {
        if (arrT == null) {
            this.writeInt(-1);
            return;
        }
        int n = arrT.length;
        this.writeInt(n);
        if (n <= 0) return;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = this.getType(arrT[0]);
        this.writeInt(n7);
        if (n7 == 1) {
            while (n5 < n) {
                this.writeVersionedParcelable((VersionedParcelable)arrT[n5]);
                ++n5;
            }
            return;
        }
        n5 = n4;
        if (n7 == 2) {
            while (n5 < n) {
                this.writeParcelable((Parcelable)arrT[n5]);
                ++n5;
            }
            return;
        }
        n5 = n3;
        if (n7 == 3) {
            while (n5 < n) {
                this.writeSerializable((Serializable)arrT[n5]);
                ++n5;
            }
            return;
        }
        n5 = n2;
        if (n7 == 4) {
            while (n5 < n) {
                this.writeString((String)arrT[n5]);
                ++n5;
            }
            return;
        }
        n5 = n6;
        if (n7 != 5) {
            return;
        }
        while (n5 < n) {
            this.writeStrongBinder((IBinder)arrT[n5]);
            ++n5;
        }
    }

    public <T> void writeArray(T[] arrT, int n) {
        this.setOutputField(n);
        this.writeArray(arrT);
    }

    protected abstract void writeBoolean(boolean var1);

    public void writeBoolean(boolean bl, int n) {
        this.setOutputField(n);
        this.writeBoolean(bl);
    }

    protected void writeBooleanArray(boolean[] arrbl) {
        if (arrbl == null) {
            this.writeInt(-1);
            return;
        }
        int n = arrbl.length;
        this.writeInt(n);
        int n2 = 0;
        while (n2 < n) {
            this.writeInt(arrbl[n2]);
            ++n2;
        }
    }

    public void writeBooleanArray(boolean[] arrbl, int n) {
        this.setOutputField(n);
        this.writeBooleanArray(arrbl);
    }

    protected abstract void writeBundle(Bundle var1);

    public void writeBundle(Bundle bundle, int n) {
        this.setOutputField(n);
        this.writeBundle(bundle);
    }

    public void writeByte(byte by, int n) {
        this.setOutputField(n);
        this.writeInt(by);
    }

    protected abstract void writeByteArray(byte[] var1);

    public void writeByteArray(byte[] arrby, int n) {
        this.setOutputField(n);
        this.writeByteArray(arrby);
    }

    protected abstract void writeByteArray(byte[] var1, int var2, int var3);

    public void writeByteArray(byte[] arrby, int n, int n2, int n3) {
        this.setOutputField(n3);
        this.writeByteArray(arrby, n, n2);
    }

    public void writeCharArray(char[] arrc, int n) {
        this.setOutputField(n);
        if (arrc == null) {
            this.writeInt(-1);
            return;
        }
        int n2 = arrc.length;
        this.writeInt(n2);
        n = 0;
        while (n < n2) {
            this.writeInt(arrc[n]);
            ++n;
        }
    }

    protected abstract void writeCharSequence(CharSequence var1);

    public void writeCharSequence(CharSequence charSequence, int n) {
        this.setOutputField(n);
        this.writeCharSequence(charSequence);
    }

    protected abstract void writeDouble(double var1);

    public void writeDouble(double d, int n) {
        this.setOutputField(n);
        this.writeDouble(d);
    }

    protected void writeDoubleArray(double[] arrd) {
        if (arrd == null) {
            this.writeInt(-1);
            return;
        }
        int n = arrd.length;
        this.writeInt(n);
        int n2 = 0;
        while (n2 < n) {
            this.writeDouble(arrd[n2]);
            ++n2;
        }
    }

    public void writeDoubleArray(double[] arrd, int n) {
        this.setOutputField(n);
        this.writeDoubleArray(arrd);
    }

    public void writeException(Exception exception, int n) {
        this.setOutputField(n);
        if (exception == null) {
            this.writeNoException();
            return;
        }
        n = 0;
        if (exception instanceof Parcelable && exception.getClass().getClassLoader() == Parcelable.class.getClassLoader()) {
            n = -9;
        } else if (exception instanceof SecurityException) {
            n = -1;
        } else if (exception instanceof BadParcelableException) {
            n = -2;
        } else if (exception instanceof IllegalArgumentException) {
            n = -3;
        } else if (exception instanceof NullPointerException) {
            n = -4;
        } else if (exception instanceof IllegalStateException) {
            n = -5;
        } else if (exception instanceof NetworkOnMainThreadException) {
            n = -6;
        } else if (exception instanceof UnsupportedOperationException) {
            n = -7;
        }
        this.writeInt(n);
        if (n == 0) {
            if (!(exception instanceof RuntimeException)) throw new RuntimeException(exception);
            throw (RuntimeException)exception;
        }
        this.writeString(exception.getMessage());
        if (n != -9) {
            return;
        }
        this.writeParcelable((Parcelable)exception);
    }

    protected abstract void writeFloat(float var1);

    public void writeFloat(float f, int n) {
        this.setOutputField(n);
        this.writeFloat(f);
    }

    protected void writeFloatArray(float[] arrf) {
        if (arrf == null) {
            this.writeInt(-1);
            return;
        }
        int n = arrf.length;
        this.writeInt(n);
        int n2 = 0;
        while (n2 < n) {
            this.writeFloat(arrf[n2]);
            ++n2;
        }
    }

    public void writeFloatArray(float[] arrf, int n) {
        this.setOutputField(n);
        this.writeFloatArray(arrf);
    }

    protected abstract void writeInt(int var1);

    public void writeInt(int n, int n2) {
        this.setOutputField(n2);
        this.writeInt(n);
    }

    protected void writeIntArray(int[] arrn) {
        if (arrn == null) {
            this.writeInt(-1);
            return;
        }
        int n = arrn.length;
        this.writeInt(n);
        int n2 = 0;
        while (n2 < n) {
            this.writeInt(arrn[n2]);
            ++n2;
        }
    }

    public void writeIntArray(int[] arrn, int n) {
        this.setOutputField(n);
        this.writeIntArray(arrn);
    }

    public <T> void writeList(List<T> list, int n) {
        this.writeCollection(list, n);
    }

    protected abstract void writeLong(long var1);

    public void writeLong(long l, int n) {
        this.setOutputField(n);
        this.writeLong(l);
    }

    protected void writeLongArray(long[] arrl) {
        if (arrl == null) {
            this.writeInt(-1);
            return;
        }
        int n = arrl.length;
        this.writeInt(n);
        int n2 = 0;
        while (n2 < n) {
            this.writeLong(arrl[n2]);
            ++n2;
        }
    }

    public void writeLongArray(long[] arrl, int n) {
        this.setOutputField(n);
        this.writeLongArray(arrl);
    }

    public <K, V> void writeMap(Map<K, V> object, int n) {
        this.setOutputField(n);
        if (object == null) {
            this.writeInt(-1);
            return;
        }
        n = object.size();
        this.writeInt(n);
        if (n == 0) {
            return;
        }
        ArrayList<K> arrayList = new ArrayList<K>();
        ArrayList<V> arrayList2 = new ArrayList<V>();
        Iterator<Map.Entry<K, V>> iterator2 = object.entrySet().iterator();
        do {
            if (!iterator2.hasNext()) {
                this.writeCollection(arrayList);
                this.writeCollection(arrayList2);
                return;
            }
            object = iterator2.next();
            arrayList.add(object.getKey());
            arrayList2.add(object.getValue());
        } while (true);
    }

    protected void writeNoException() {
        this.writeInt(0);
    }

    protected abstract void writeParcelable(Parcelable var1);

    public void writeParcelable(Parcelable parcelable, int n) {
        this.setOutputField(n);
        this.writeParcelable(parcelable);
    }

    public void writeSerializable(Serializable serializable, int n) {
        this.setOutputField(n);
        this.writeSerializable(serializable);
    }

    public <T> void writeSet(Set<T> set, int n) {
        this.writeCollection(set, n);
    }

    public void writeSize(Size size, int n) {
        this.setOutputField(n);
        boolean bl = size != null;
        this.writeBoolean(bl);
        if (size == null) return;
        this.writeInt(size.getWidth());
        this.writeInt(size.getHeight());
    }

    public void writeSizeF(SizeF sizeF, int n) {
        this.setOutputField(n);
        boolean bl = sizeF != null;
        this.writeBoolean(bl);
        if (sizeF == null) return;
        this.writeFloat(sizeF.getWidth());
        this.writeFloat(sizeF.getHeight());
    }

    public void writeSparseBooleanArray(SparseBooleanArray sparseBooleanArray, int n) {
        this.setOutputField(n);
        if (sparseBooleanArray == null) {
            this.writeInt(-1);
            return;
        }
        int n2 = sparseBooleanArray.size();
        this.writeInt(n2);
        n = 0;
        while (n < n2) {
            this.writeInt(sparseBooleanArray.keyAt(n));
            this.writeBoolean(sparseBooleanArray.valueAt(n));
            ++n;
        }
    }

    protected abstract void writeString(String var1);

    public void writeString(String string2, int n) {
        this.setOutputField(n);
        this.writeString(string2);
    }

    protected abstract void writeStrongBinder(IBinder var1);

    public void writeStrongBinder(IBinder iBinder, int n) {
        this.setOutputField(n);
        this.writeStrongBinder(iBinder);
    }

    protected abstract void writeStrongInterface(IInterface var1);

    public void writeStrongInterface(IInterface iInterface, int n) {
        this.setOutputField(n);
        this.writeStrongInterface(iInterface);
    }

    protected <T extends VersionedParcelable> void writeToParcel(T t, VersionedParcel versionedParcel) {
        try {
            this.getWriteMethod(t.getClass()).invoke(null, t, versionedParcel);
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", classNotFoundException);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", noSuchMethodException);
        }
        catch (InvocationTargetException invocationTargetException) {
            if (!(invocationTargetException.getCause() instanceof RuntimeException)) throw new RuntimeException("VersionedParcel encountered InvocationTargetException", invocationTargetException);
            throw (RuntimeException)invocationTargetException.getCause();
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", illegalAccessException);
        }
    }

    protected void writeVersionedParcelable(VersionedParcelable versionedParcelable) {
        if (versionedParcelable == null) {
            this.writeString(null);
            return;
        }
        this.writeVersionedParcelableCreator(versionedParcelable);
        VersionedParcel versionedParcel = this.createSubParcel();
        this.writeToParcel(versionedParcelable, versionedParcel);
        versionedParcel.closeField();
    }

    public void writeVersionedParcelable(VersionedParcelable versionedParcelable, int n) {
        this.setOutputField(n);
        this.writeVersionedParcelable(versionedParcelable);
    }

    public static class ParcelException
    extends RuntimeException {
        public ParcelException(Throwable throwable) {
            super(throwable);
        }
    }

}

