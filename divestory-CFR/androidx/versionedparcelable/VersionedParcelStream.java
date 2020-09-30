/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcelable
 */
package androidx.versionedparcelable;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcelable;
import androidx.collection.ArrayMap;
import androidx.versionedparcelable.VersionedParcel;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

class VersionedParcelStream
extends VersionedParcel {
    private static final int TYPE_BOOLEAN = 5;
    private static final int TYPE_BOOLEAN_ARRAY = 6;
    private static final int TYPE_DOUBLE = 7;
    private static final int TYPE_DOUBLE_ARRAY = 8;
    private static final int TYPE_FLOAT = 13;
    private static final int TYPE_FLOAT_ARRAY = 14;
    private static final int TYPE_INT = 9;
    private static final int TYPE_INT_ARRAY = 10;
    private static final int TYPE_LONG = 11;
    private static final int TYPE_LONG_ARRAY = 12;
    private static final int TYPE_NULL = 0;
    private static final int TYPE_STRING = 3;
    private static final int TYPE_STRING_ARRAY = 4;
    private static final int TYPE_SUB_BUNDLE = 1;
    private static final int TYPE_SUB_PERSISTABLE_BUNDLE = 2;
    private static final Charset UTF_16 = Charset.forName("UTF-16");
    int mCount = 0;
    private DataInputStream mCurrentInput;
    private DataOutputStream mCurrentOutput;
    private FieldBuffer mFieldBuffer;
    private int mFieldId = -1;
    int mFieldSize = -1;
    private boolean mIgnoreParcelables;
    private final DataInputStream mMasterInput;
    private final DataOutputStream mMasterOutput;

    public VersionedParcelStream(InputStream inputStream2, OutputStream outputStream2) {
        this(inputStream2, outputStream2, new ArrayMap<String, Method>(), new ArrayMap<String, Method>(), new ArrayMap<String, Class>());
    }

    private VersionedParcelStream(InputStream object, OutputStream outputStream2, ArrayMap<String, Method> arrayMap, ArrayMap<String, Method> arrayMap2, ArrayMap<String, Class> arrayMap3) {
        super(arrayMap, arrayMap2, arrayMap3);
        arrayMap = null;
        object = object != null ? new DataInputStream(new FilterInputStream((InputStream)object){

            @Override
            public int read() throws IOException {
                if (VersionedParcelStream.this.mFieldSize != -1) {
                    if (VersionedParcelStream.this.mCount >= VersionedParcelStream.this.mFieldSize) throw new IOException();
                }
                int n = super.read();
                VersionedParcelStream versionedParcelStream = VersionedParcelStream.this;
                ++versionedParcelStream.mCount;
                return n;
            }

            @Override
            public int read(byte[] object, int n, int n2) throws IOException {
                if (VersionedParcelStream.this.mFieldSize != -1) {
                    if (VersionedParcelStream.this.mCount >= VersionedParcelStream.this.mFieldSize) throw new IOException();
                }
                if ((n = super.read((byte[])object, n, n2)) <= 0) return n;
                object = VersionedParcelStream.this;
                object.mCount += n;
                return n;
            }

            @Override
            public long skip(long l) throws IOException {
                if (VersionedParcelStream.this.mFieldSize != -1) {
                    if (VersionedParcelStream.this.mCount >= VersionedParcelStream.this.mFieldSize) throw new IOException();
                }
                if ((l = super.skip(l)) <= 0L) return l;
                VersionedParcelStream versionedParcelStream = VersionedParcelStream.this;
                versionedParcelStream.mCount += (int)l;
                return l;
            }
        }) : null;
        this.mMasterInput = object;
        object = arrayMap;
        if (outputStream2 != null) {
            object = new DataOutputStream(outputStream2);
        }
        this.mMasterOutput = object;
        this.mCurrentInput = this.mMasterInput;
        this.mCurrentOutput = object;
    }

    private void readObject(int n, String charSequence, Bundle bundle) {
        switch (n) {
            default: {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unknown type ");
                ((StringBuilder)charSequence).append(n);
                throw new RuntimeException(((StringBuilder)charSequence).toString());
            }
            case 14: {
                bundle.putFloatArray((String)charSequence, this.readFloatArray());
                return;
            }
            case 13: {
                bundle.putFloat((String)charSequence, this.readFloat());
                return;
            }
            case 12: {
                bundle.putLongArray((String)charSequence, this.readLongArray());
                return;
            }
            case 11: {
                bundle.putLong((String)charSequence, this.readLong());
                return;
            }
            case 10: {
                bundle.putIntArray((String)charSequence, this.readIntArray());
                return;
            }
            case 9: {
                bundle.putInt((String)charSequence, this.readInt());
                return;
            }
            case 8: {
                bundle.putDoubleArray((String)charSequence, this.readDoubleArray());
                return;
            }
            case 7: {
                bundle.putDouble((String)charSequence, this.readDouble());
                return;
            }
            case 6: {
                bundle.putBooleanArray((String)charSequence, this.readBooleanArray());
                return;
            }
            case 5: {
                bundle.putBoolean((String)charSequence, this.readBoolean());
                return;
            }
            case 4: {
                bundle.putStringArray((String)charSequence, this.readArray(new String[0]));
                return;
            }
            case 3: {
                bundle.putString((String)charSequence, this.readString());
                return;
            }
            case 2: {
                bundle.putBundle((String)charSequence, this.readBundle());
                return;
            }
            case 1: {
                bundle.putBundle((String)charSequence, this.readBundle());
                return;
            }
            case 0: 
        }
        bundle.putParcelable((String)charSequence, null);
    }

    private void writeObject(Object object) {
        if (object == null) {
            this.writeInt(0);
            return;
        }
        if (object instanceof Bundle) {
            this.writeInt(1);
            this.writeBundle((Bundle)object);
            return;
        }
        if (object instanceof String) {
            this.writeInt(3);
            this.writeString((String)object);
            return;
        }
        if (object instanceof String[]) {
            this.writeInt(4);
            this.writeArray((String[])object);
            return;
        }
        if (object instanceof Boolean) {
            this.writeInt(5);
            this.writeBoolean((Boolean)object);
            return;
        }
        if (object instanceof boolean[]) {
            this.writeInt(6);
            this.writeBooleanArray((boolean[])object);
            return;
        }
        if (object instanceof Double) {
            this.writeInt(7);
            this.writeDouble((Double)object);
            return;
        }
        if (object instanceof double[]) {
            this.writeInt(8);
            this.writeDoubleArray((double[])object);
            return;
        }
        if (object instanceof Integer) {
            this.writeInt(9);
            this.writeInt((Integer)object);
            return;
        }
        if (object instanceof int[]) {
            this.writeInt(10);
            this.writeIntArray((int[])object);
            return;
        }
        if (object instanceof Long) {
            this.writeInt(11);
            this.writeLong((Long)object);
            return;
        }
        if (object instanceof long[]) {
            this.writeInt(12);
            this.writeLongArray((long[])object);
            return;
        }
        if (object instanceof Float) {
            this.writeInt(13);
            this.writeFloat(((Float)object).floatValue());
            return;
        }
        if (object instanceof float[]) {
            this.writeInt(14);
            this.writeFloatArray((float[])object);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported type ");
        stringBuilder.append(object.getClass());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void closeField() {
        FieldBuffer fieldBuffer = this.mFieldBuffer;
        if (fieldBuffer == null) return;
        try {
            if (fieldBuffer.mOutput.size() != 0) {
                this.mFieldBuffer.flushField();
            }
            this.mFieldBuffer = null;
            return;
        }
        catch (IOException iOException) {
            throw new VersionedParcel.ParcelException(iOException);
        }
    }

    @Override
    protected VersionedParcel createSubParcel() {
        return new VersionedParcelStream(this.mCurrentInput, this.mCurrentOutput, this.mReadCache, this.mWriteCache, this.mParcelizerCache);
    }

    @Override
    public boolean isStream() {
        return true;
    }

    @Override
    public boolean readBoolean() {
        try {
            return this.mCurrentInput.readBoolean();
        }
        catch (IOException iOException) {
            throw new VersionedParcel.ParcelException(iOException);
        }
    }

    @Override
    public Bundle readBundle() {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        Bundle bundle = new Bundle();
        int n2 = 0;
        while (n2 < n) {
            String string2 = this.readString();
            this.readObject(this.readInt(), string2, bundle);
            ++n2;
        }
        return bundle;
    }

    @Override
    public byte[] readByteArray() {
        try {
            int n = this.mCurrentInput.readInt();
            if (n <= 0) return null;
            byte[] arrby = new byte[n];
            this.mCurrentInput.readFully(arrby);
            return arrby;
        }
        catch (IOException iOException) {
            throw new VersionedParcel.ParcelException(iOException);
        }
    }

    @Override
    protected CharSequence readCharSequence() {
        return null;
    }

    @Override
    public double readDouble() {
        try {
            return this.mCurrentInput.readDouble();
        }
        catch (IOException iOException) {
            throw new VersionedParcel.ParcelException(iOException);
        }
    }

    @Override
    public boolean readField(int n) {
        try {
            do {
                int n2;
                if (this.mFieldId == n) {
                    return true;
                }
                if (String.valueOf(this.mFieldId).compareTo(String.valueOf(n)) > 0) {
                    return false;
                }
                if (this.mCount < this.mFieldSize) {
                    this.mMasterInput.skip(this.mFieldSize - this.mCount);
                }
                this.mFieldSize = -1;
                int n3 = this.mMasterInput.readInt();
                this.mCount = 0;
                int n4 = n2 = n3 & 65535;
                if (n2 == 65535) {
                    n4 = this.mMasterInput.readInt();
                }
                this.mFieldId = n3 >> 16 & 65535;
                this.mFieldSize = n4;
            } while (true);
        }
        catch (IOException iOException) {
            return false;
        }
    }

    @Override
    public float readFloat() {
        try {
            return this.mCurrentInput.readFloat();
        }
        catch (IOException iOException) {
            throw new VersionedParcel.ParcelException(iOException);
        }
    }

    @Override
    public int readInt() {
        try {
            return this.mCurrentInput.readInt();
        }
        catch (IOException iOException) {
            throw new VersionedParcel.ParcelException(iOException);
        }
    }

    @Override
    public long readLong() {
        try {
            return this.mCurrentInput.readLong();
        }
        catch (IOException iOException) {
            throw new VersionedParcel.ParcelException(iOException);
        }
    }

    @Override
    public <T extends Parcelable> T readParcelable() {
        return null;
    }

    @Override
    public String readString() {
        try {
            int n = this.mCurrentInput.readInt();
            if (n <= 0) return null;
            Object object = new byte[n];
            this.mCurrentInput.readFully((byte[])object);
            return new String((byte[])object, UTF_16);
        }
        catch (IOException iOException) {
            throw new VersionedParcel.ParcelException(iOException);
        }
    }

    @Override
    public IBinder readStrongBinder() {
        return null;
    }

    @Override
    public void setOutputField(int n) {
        FieldBuffer fieldBuffer;
        this.closeField();
        this.mFieldBuffer = fieldBuffer = new FieldBuffer(n, this.mMasterOutput);
        this.mCurrentOutput = fieldBuffer.mDataStream;
    }

    @Override
    public void setSerializationFlags(boolean bl, boolean bl2) {
        if (!bl) throw new RuntimeException("Serialization of this object is not allowed");
        this.mIgnoreParcelables = bl2;
    }

    @Override
    public void writeBoolean(boolean bl) {
        try {
            this.mCurrentOutput.writeBoolean(bl);
            return;
        }
        catch (IOException iOException) {
            throw new VersionedParcel.ParcelException(iOException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public void writeBundle(Bundle var1_1) {
        if (var1_1 == null) ** GOTO lbl12
        try {
            var2_3 = var1_1.keySet();
            this.mCurrentOutput.writeInt(var2_3.size());
            var3_4 = var2_3.iterator();
            while (var3_4.hasNext() != false) {
                var2_3 = (String)var3_4.next();
                this.writeString((String)var2_3);
                this.writeObject(var1_1.get((String)var2_3));
            }
            return;
lbl12: // 1 sources:
            this.mCurrentOutput.writeInt(-1);
            return;
        }
        catch (IOException var1_2) {
            throw new VersionedParcel.ParcelException(var1_2);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public void writeByteArray(byte[] var1_1) {
        if (var1_1 == null) ** GOTO lbl6
        try {
            this.mCurrentOutput.writeInt(var1_1.length);
            this.mCurrentOutput.write(var1_1);
            return;
lbl6: // 1 sources:
            this.mCurrentOutput.writeInt(-1);
            return;
        }
        catch (IOException var1_2) {
            throw new VersionedParcel.ParcelException(var1_2);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public void writeByteArray(byte[] var1_1, int var2_3, int var3_4) {
        if (var1_1 == null) ** GOTO lbl6
        try {
            this.mCurrentOutput.writeInt(var3_4);
            this.mCurrentOutput.write(var1_1, var2_3, var3_4);
            return;
lbl6: // 1 sources:
            this.mCurrentOutput.writeInt(-1);
            return;
        }
        catch (IOException var1_2) {
            throw new VersionedParcel.ParcelException(var1_2);
        }
    }

    @Override
    protected void writeCharSequence(CharSequence charSequence) {
        if (!this.mIgnoreParcelables) throw new RuntimeException("CharSequence cannot be written to an OutputStream");
    }

    @Override
    public void writeDouble(double d) {
        try {
            this.mCurrentOutput.writeDouble(d);
            return;
        }
        catch (IOException iOException) {
            throw new VersionedParcel.ParcelException(iOException);
        }
    }

    @Override
    public void writeFloat(float f) {
        try {
            this.mCurrentOutput.writeFloat(f);
            return;
        }
        catch (IOException iOException) {
            throw new VersionedParcel.ParcelException(iOException);
        }
    }

    @Override
    public void writeInt(int n) {
        try {
            this.mCurrentOutput.writeInt(n);
            return;
        }
        catch (IOException iOException) {
            throw new VersionedParcel.ParcelException(iOException);
        }
    }

    @Override
    public void writeLong(long l) {
        try {
            this.mCurrentOutput.writeLong(l);
            return;
        }
        catch (IOException iOException) {
            throw new VersionedParcel.ParcelException(iOException);
        }
    }

    @Override
    public void writeParcelable(Parcelable parcelable) {
        if (!this.mIgnoreParcelables) throw new RuntimeException("Parcelables cannot be written to an OutputStream");
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public void writeString(String var1_1) {
        if (var1_1 == null) ** GOTO lbl7
        try {
            var1_1 = var1_1.getBytes(VersionedParcelStream.UTF_16);
            this.mCurrentOutput.writeInt(var1_1.length);
            this.mCurrentOutput.write(var1_1);
            return;
lbl7: // 1 sources:
            this.mCurrentOutput.writeInt(-1);
            return;
        }
        catch (IOException var1_2) {
            throw new VersionedParcel.ParcelException(var1_2);
        }
    }

    @Override
    public void writeStrongBinder(IBinder iBinder) {
        if (!this.mIgnoreParcelables) throw new RuntimeException("Binders cannot be written to an OutputStream");
    }

    @Override
    public void writeStrongInterface(IInterface iInterface) {
        if (!this.mIgnoreParcelables) throw new RuntimeException("Binders cannot be written to an OutputStream");
    }

    private static class FieldBuffer {
        final DataOutputStream mDataStream = new DataOutputStream(this.mOutput);
        private final int mFieldId;
        final ByteArrayOutputStream mOutput = new ByteArrayOutputStream();
        private final DataOutputStream mTarget;

        FieldBuffer(int n, DataOutputStream dataOutputStream) {
            this.mFieldId = n;
            this.mTarget = dataOutputStream;
        }

        void flushField() throws IOException {
            this.mDataStream.flush();
            int n = this.mOutput.size();
            int n2 = this.mFieldId;
            int n3 = n >= 65535 ? 65535 : n;
            this.mTarget.writeInt(n2 << 16 | n3);
            if (n >= 65535) {
                this.mTarget.writeInt(n);
            }
            this.mOutput.writeTo(this.mTarget);
        }
    }

}

