/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.server.response;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse;
import com.google.android.gms.common.server.response.zal;
import com.google.android.gms.common.server.response.zap;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.MapUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SafeParcelResponse
extends FastSafeParcelableJsonResponse {
    public static final Parcelable.Creator<SafeParcelResponse> CREATOR = new zap();
    private final int zaa;
    private final Parcel zab;
    private final int zac;
    private final zal zad;
    private final String zae;
    private int zaf;
    private int zag;

    SafeParcelResponse(int n, Parcel parcel, zal zal2) {
        this.zaa = n;
        this.zab = Preconditions.checkNotNull(parcel);
        this.zac = 2;
        this.zad = zal2;
        this.zae = zal2 == null ? null : zal2.zac();
        this.zaf = 2;
    }

    private SafeParcelResponse(SafeParcelable safeParcelable, zal zal2, String string2) {
        Parcel parcel;
        this.zaa = 1;
        this.zab = parcel = Parcel.obtain();
        safeParcelable.writeToParcel(parcel, 0);
        this.zac = 1;
        this.zad = Preconditions.checkNotNull(zal2);
        this.zae = Preconditions.checkNotNull(string2);
        this.zaf = 2;
    }

    public SafeParcelResponse(zal zal2, String string2) {
        this.zaa = 1;
        this.zab = Parcel.obtain();
        this.zac = 0;
        this.zad = Preconditions.checkNotNull(zal2);
        this.zae = Preconditions.checkNotNull(string2);
        this.zaf = 0;
    }

    public static <T extends FastJsonResponse> SafeParcelResponse from(T t) {
        String string2 = Preconditions.checkNotNull(t.getClass().getCanonicalName());
        zal zal2 = new zal(t.getClass());
        SafeParcelResponse.zaa(zal2, t);
        zal2.zab();
        zal2.zaa();
        return new SafeParcelResponse((SafeParcelable)t, zal2, string2);
    }

    private final Parcel zaa() {
        int n = this.zaf;
        if (n != 0) {
            if (n != 1) {
                return this.zab;
            }
        } else {
            this.zag = SafeParcelWriter.beginObjectHeader(this.zab);
        }
        SafeParcelWriter.finishObjectHeader(this.zab, this.zag);
        this.zaf = 2;
        return this.zab;
    }

    private final void zaa(FastJsonResponse.Field<?, ?> parcel) {
        int n = parcel.zaf != -1 ? 1 : 0;
        if (n == 0) throw new IllegalStateException("Field does not have a valid safe parcelable field id.");
        parcel = this.zab;
        if (parcel == null) throw new IllegalStateException("Internal Parcel object is null.");
        n = this.zaf;
        if (n != 0) {
            if (n == 1) return;
            if (n == 2) throw new IllegalStateException("Attempted to parse JSON with a SafeParcelResponse object that is already filled with data.");
            throw new IllegalStateException("Unknown parse state in SafeParcelResponse.");
        }
        this.zag = SafeParcelWriter.beginObjectHeader(parcel);
        this.zaf = 1;
    }

    private static void zaa(zal object, FastJsonResponse object2) {
        Class<Object> class_ = object2.getClass();
        if (((zal)object).zaa(class_)) return;
        Map<String, FastJsonResponse.Field<?, ?>> map = ((FastJsonResponse)object2).getFieldMappings();
        ((zal)object).zaa(class_, map);
        Iterator<String> iterator2 = map.keySet().iterator();
        while (iterator2.hasNext()) {
            object2 = map.get(iterator2.next());
            class_ = ((FastJsonResponse.Field)object2).zag;
            if (class_ == null) continue;
            try {
                SafeParcelResponse.zaa((zal)object, (FastJsonResponse)class_.newInstance());
            }
            catch (IllegalAccessException illegalAccessException) {
                object = String.valueOf(Preconditions.checkNotNull(((FastJsonResponse.Field)object2).zag).getCanonicalName());
                if (((String)object).length() != 0) {
                    object = "Could not access object of type ".concat((String)object);
                    throw new IllegalStateException((String)object, illegalAccessException);
                }
                object = new String("Could not access object of type ");
                throw new IllegalStateException((String)object, illegalAccessException);
            }
            catch (InstantiationException instantiationException) {
                object = String.valueOf(Preconditions.checkNotNull(((FastJsonResponse.Field)object2).zag).getCanonicalName());
                if (((String)object).length() != 0) {
                    object = "Could not instantiate an object of type ".concat((String)object);
                    throw new IllegalStateException((String)object, instantiationException);
                }
                object = new String("Could not instantiate an object of type ");
                throw new IllegalStateException((String)object, instantiationException);
            }
        }
    }

    private static void zaa(StringBuilder stringBuilder, int n, Object object) {
        switch (n) {
            default: {
                stringBuilder = new StringBuilder(26);
                stringBuilder.append("Unknown type = ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 11: {
                throw new IllegalArgumentException("Method does not accept concrete type.");
            }
            case 10: {
                MapUtils.writeStringMapToJson(stringBuilder, (HashMap)Preconditions.checkNotNull(object));
                return;
            }
            case 9: {
                stringBuilder.append("\"");
                stringBuilder.append(Base64Utils.encodeUrlSafe((byte[])object));
                stringBuilder.append("\"");
                return;
            }
            case 8: {
                stringBuilder.append("\"");
                stringBuilder.append(Base64Utils.encode((byte[])object));
                stringBuilder.append("\"");
                return;
            }
            case 7: {
                stringBuilder.append("\"");
                stringBuilder.append(JsonUtils.escapeString(Preconditions.checkNotNull(object).toString()));
                stringBuilder.append("\"");
                return;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
        }
        stringBuilder.append(object);
    }

    private static void zaa(StringBuilder stringBuilder, FastJsonResponse.Field<?, ?> field, Object object) {
        if (!field.zab) {
            SafeParcelResponse.zaa(stringBuilder, field.zaa, object);
            return;
        }
        object = (ArrayList)object;
        stringBuilder.append("[");
        int n = ((ArrayList)object).size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                stringBuilder.append("]");
                return;
            }
            if (n2 != 0) {
                stringBuilder.append(",");
            }
            SafeParcelResponse.zaa(stringBuilder, field.zaa, ((ArrayList)object).get(n2));
            ++n2;
        } while (true);
    }

    /*
     * Exception decompiling
     */
    private final void zaa(StringBuilder var1_1, Map<String, FastJsonResponse.Field<?, ?>> var2_2, Parcel var3_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(FastJsonResponse.Field<?, ?> field, String object, ArrayList<T> arrayList) {
        this.zaa(field);
        object = new ArrayList();
        Preconditions.checkNotNull(arrayList).size();
        int n = arrayList.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                SafeParcelWriter.writeParcelList(this.zab, field.getSafeParcelableFieldId(), (List<Parcel>)object, true);
                return;
            }
            T t = arrayList.get(n2);
            ++n2;
            ((ArrayList)object).add(((SafeParcelResponse)((FastJsonResponse)t)).zaa());
        } while (true);
    }

    @Override
    public <T extends FastJsonResponse> void addConcreteTypeInternal(FastJsonResponse.Field<?, ?> field, String string2, T t) {
        this.zaa(field);
        string2 = ((SafeParcelResponse)t).zaa();
        SafeParcelWriter.writeParcel(this.zab, field.getSafeParcelableFieldId(), (Parcel)string2, true);
    }

    @Override
    public Map<String, FastJsonResponse.Field<?, ?>> getFieldMappings() {
        zal zal2 = this.zad;
        if (zal2 != null) return zal2.zaa(Preconditions.checkNotNull(this.zae));
        return null;
    }

    @Override
    public Object getValueObject(String string2) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    @Override
    public boolean isPrimitiveFieldSet(String string2) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    @Override
    protected void setBooleanInternal(FastJsonResponse.Field<?, ?> field, String string2, boolean bl) {
        this.zaa(field);
        SafeParcelWriter.writeBoolean(this.zab, field.getSafeParcelableFieldId(), bl);
    }

    @Override
    protected void setDecodedBytesInternal(FastJsonResponse.Field<?, ?> field, String string2, byte[] arrby) {
        this.zaa(field);
        SafeParcelWriter.writeByteArray(this.zab, field.getSafeParcelableFieldId(), arrby, true);
    }

    @Override
    protected void setIntegerInternal(FastJsonResponse.Field<?, ?> field, String string2, int n) {
        this.zaa(field);
        SafeParcelWriter.writeInt(this.zab, field.getSafeParcelableFieldId(), n);
    }

    @Override
    protected void setLongInternal(FastJsonResponse.Field<?, ?> field, String string2, long l) {
        this.zaa(field);
        SafeParcelWriter.writeLong(this.zab, field.getSafeParcelableFieldId(), l);
    }

    @Override
    protected void setStringInternal(FastJsonResponse.Field<?, ?> field, String string2, String string3) {
        this.zaa(field);
        SafeParcelWriter.writeString(this.zab, field.getSafeParcelableFieldId(), string3, true);
    }

    @Override
    protected void setStringMapInternal(FastJsonResponse.Field<?, ?> field, String string2, Map<String, String> map) {
        this.zaa(field);
        string2 = new Bundle();
        Iterator<String> iterator2 = Preconditions.checkNotNull(map).keySet().iterator();
        do {
            if (!iterator2.hasNext()) {
                SafeParcelWriter.writeBundle(this.zab, field.getSafeParcelableFieldId(), (Bundle)string2, true);
                return;
            }
            String string3 = iterator2.next();
            string2.putString(string3, map.get(string3));
        } while (true);
    }

    @Override
    protected void setStringsInternal(FastJsonResponse.Field<?, ?> field, String arrstring, ArrayList<String> arrayList) {
        this.zaa(field);
        int n = Preconditions.checkNotNull(arrayList).size();
        arrstring = new String[n];
        int n2 = 0;
        do {
            if (n2 >= n) {
                SafeParcelWriter.writeStringArray(this.zab, field.getSafeParcelableFieldId(), arrstring, true);
                return;
            }
            arrstring[n2] = arrayList.get(n2);
            ++n2;
        } while (true);
    }

    @Override
    public String toString() {
        Preconditions.checkNotNull(this.zad, "Cannot convert to JSON on client side.");
        Parcel parcel = this.zaa();
        parcel.setDataPosition(0);
        StringBuilder stringBuilder = new StringBuilder(100);
        this.zaa(stringBuilder, Preconditions.checkNotNull(this.zad.zaa(Preconditions.checkNotNull(this.zae))), parcel);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel object, int n) {
        zal zal2;
        int n2 = SafeParcelWriter.beginObjectHeader((Parcel)object);
        SafeParcelWriter.writeInt((Parcel)object, 1, this.zaa);
        SafeParcelWriter.writeParcel((Parcel)object, 2, this.zaa(), false);
        int n3 = this.zac;
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    n = this.zac;
                    object = new StringBuilder(34);
                    ((StringBuilder)object).append("Invalid creation type: ");
                    ((StringBuilder)object).append(n);
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
                zal2 = this.zad;
            } else {
                zal2 = this.zad;
            }
        } else {
            zal2 = null;
        }
        SafeParcelWriter.writeParcelable((Parcel)object, 3, zal2, n, false);
        SafeParcelWriter.finishObjectHeader((Parcel)object, n2);
    }

    @Override
    protected final void zaa(FastJsonResponse.Field<?, ?> field, String string2, double d) {
        this.zaa(field);
        SafeParcelWriter.writeDouble(this.zab, field.getSafeParcelableFieldId(), d);
    }

    @Override
    protected final void zaa(FastJsonResponse.Field<?, ?> field, String string2, float f) {
        this.zaa(field);
        SafeParcelWriter.writeFloat(this.zab, field.getSafeParcelableFieldId(), f);
    }

    @Override
    protected final void zaa(FastJsonResponse.Field<?, ?> field, String string2, BigDecimal bigDecimal) {
        this.zaa(field);
        SafeParcelWriter.writeBigDecimal(this.zab, field.getSafeParcelableFieldId(), bigDecimal, true);
    }

    @Override
    protected final void zaa(FastJsonResponse.Field<?, ?> field, String string2, BigInteger bigInteger) {
        this.zaa(field);
        SafeParcelWriter.writeBigInteger(this.zab, field.getSafeParcelableFieldId(), bigInteger, true);
    }

    @Override
    protected final void zaa(FastJsonResponse.Field<?, ?> field, String arrn, ArrayList<Integer> arrayList) {
        this.zaa(field);
        int n = Preconditions.checkNotNull(arrayList).size();
        arrn = new int[n];
        int n2 = 0;
        do {
            if (n2 >= n) {
                SafeParcelWriter.writeIntArray(this.zab, field.getSafeParcelableFieldId(), arrn, true);
                return;
            }
            arrn[n2] = arrayList.get(n2);
            ++n2;
        } while (true);
    }

    @Override
    protected final void zab(FastJsonResponse.Field<?, ?> field, String arrbigInteger, ArrayList<BigInteger> arrayList) {
        this.zaa(field);
        int n = Preconditions.checkNotNull(arrayList).size();
        arrbigInteger = new BigInteger[n];
        int n2 = 0;
        do {
            if (n2 >= n) {
                SafeParcelWriter.writeBigIntegerArray(this.zab, field.getSafeParcelableFieldId(), arrbigInteger, true);
                return;
            }
            arrbigInteger[n2] = arrayList.get(n2);
            ++n2;
        } while (true);
    }

    @Override
    protected final void zac(FastJsonResponse.Field<?, ?> field, String arrl, ArrayList<Long> arrayList) {
        this.zaa(field);
        int n = Preconditions.checkNotNull(arrayList).size();
        arrl = new long[n];
        int n2 = 0;
        do {
            if (n2 >= n) {
                SafeParcelWriter.writeLongArray(this.zab, field.getSafeParcelableFieldId(), arrl, true);
                return;
            }
            arrl[n2] = arrayList.get(n2);
            ++n2;
        } while (true);
    }

    @Override
    protected final void zad(FastJsonResponse.Field<?, ?> field, String arrf, ArrayList<Float> arrayList) {
        this.zaa(field);
        int n = Preconditions.checkNotNull(arrayList).size();
        arrf = new float[n];
        int n2 = 0;
        do {
            if (n2 >= n) {
                SafeParcelWriter.writeFloatArray(this.zab, field.getSafeParcelableFieldId(), arrf, true);
                return;
            }
            arrf[n2] = arrayList.get(n2).floatValue();
            ++n2;
        } while (true);
    }

    @Override
    protected final void zae(FastJsonResponse.Field<?, ?> field, String arrd, ArrayList<Double> arrayList) {
        this.zaa(field);
        int n = Preconditions.checkNotNull(arrayList).size();
        arrd = new double[n];
        int n2 = 0;
        do {
            if (n2 >= n) {
                SafeParcelWriter.writeDoubleArray(this.zab, field.getSafeParcelableFieldId(), arrd, true);
                return;
            }
            arrd[n2] = arrayList.get(n2);
            ++n2;
        } while (true);
    }

    @Override
    protected final void zaf(FastJsonResponse.Field<?, ?> field, String arrbigDecimal, ArrayList<BigDecimal> arrayList) {
        this.zaa(field);
        int n = Preconditions.checkNotNull(arrayList).size();
        arrbigDecimal = new BigDecimal[n];
        int n2 = 0;
        do {
            if (n2 >= n) {
                SafeParcelWriter.writeBigDecimalArray(this.zab, field.getSafeParcelableFieldId(), arrbigDecimal, true);
                return;
            }
            arrbigDecimal[n2] = arrayList.get(n2);
            ++n2;
        } while (true);
    }

    @Override
    protected final void zag(FastJsonResponse.Field<?, ?> field, String arrbl, ArrayList<Boolean> arrayList) {
        this.zaa(field);
        int n = Preconditions.checkNotNull(arrayList).size();
        arrbl = new boolean[n];
        int n2 = 0;
        do {
            if (n2 >= n) {
                SafeParcelWriter.writeBooleanArray(this.zab, field.getSafeParcelableFieldId(), arrbl, true);
                return;
            }
            arrbl[n2] = arrayList.get(n2);
            ++n2;
        } while (true);
    }
}

