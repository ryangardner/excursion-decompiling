/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.util.Log
 */
package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.util.Log;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.server.converter.zaa;
import com.google.android.gms.common.server.response.SafeParcelResponse;
import com.google.android.gms.common.server.response.zaj;
import com.google.android.gms.common.server.response.zal;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.MapUtils;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class FastJsonResponse {
    protected static <O, I> I zaa(Field<I, O> field, Object object) {
        if (field.zak == null) return (I)object;
        return field.zab(object);
    }

    private static <O> void zaa(String string2) {
        if (!Log.isLoggable((String)"FastJsonResponse", (int)6)) return;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 58);
        stringBuilder.append("Output field (");
        stringBuilder.append(string2);
        stringBuilder.append(") has a null value, but expected a primitive");
        Log.e((String)"FastJsonResponse", (String)stringBuilder.toString());
    }

    private static void zaa(StringBuilder stringBuilder, Field object, Object object2) {
        if (((Field)object).zaa == 11) {
            object = ((Field)object).zag;
            Preconditions.checkNotNull(object);
            stringBuilder.append(((FastJsonResponse)((Class)object).cast(object2)).toString());
            return;
        }
        if (((Field)object).zaa == 7) {
            stringBuilder.append("\"");
            stringBuilder.append(JsonUtils.escapeString((String)object2));
            stringBuilder.append("\"");
            return;
        }
        stringBuilder.append(object2);
    }

    private final <I, O> void zab(Field<I, O> object, I object2) {
        String string2 = ((Field)object).zae;
        object2 = ((Field)object).zaa(object2);
        switch (((Field)object).zac) {
            default: {
                int n = ((Field)object).zac;
                object = new StringBuilder(44);
                ((StringBuilder)object).append("Unsupported type for conversion: ");
                ((StringBuilder)object).append(n);
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            case 8: 
            case 9: {
                if (object2 != null) {
                    this.setDecodedBytesInternal((Field<?, ?>)object, string2, (byte[])object2);
                    return;
                }
                FastJsonResponse.zaa(string2);
                return;
            }
            case 7: {
                this.setStringInternal((Field<?, ?>)object, string2, (String)object2);
                return;
            }
            case 6: {
                if (object2 != null) {
                    this.setBooleanInternal((Field<?, ?>)object, string2, (Boolean)object2);
                    return;
                }
                FastJsonResponse.zaa(string2);
                return;
            }
            case 5: {
                this.zaa((Field<?, ?>)object, string2, (BigDecimal)object2);
                return;
            }
            case 4: {
                if (object2 != null) {
                    this.zaa((Field<?, ?>)object, string2, (Double)object2);
                    return;
                }
                FastJsonResponse.zaa(string2);
                return;
            }
            case 2: {
                if (object2 != null) {
                    this.setLongInternal((Field<?, ?>)object, string2, (Long)object2);
                    return;
                }
                FastJsonResponse.zaa(string2);
                return;
            }
            case 1: {
                this.zaa((Field<?, ?>)object, string2, (BigInteger)object2);
                return;
            }
            case 0: 
        }
        if (object2 != null) {
            this.setIntegerInternal((Field<?, ?>)object, string2, (Integer)object2);
            return;
        }
        FastJsonResponse.zaa(string2);
    }

    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(Field<?, ?> field, String string2, ArrayList<T> arrayList) {
        throw new UnsupportedOperationException("Concrete type array not supported");
    }

    public <T extends FastJsonResponse> void addConcreteTypeInternal(Field<?, ?> field, String string2, T t) {
        throw new UnsupportedOperationException("Concrete type not supported");
    }

    public abstract Map<String, Field<?, ?>> getFieldMappings();

    protected Object getFieldValue(Field object) {
        CharSequence charSequence = ((Field)object).zae;
        if (((Field)object).zag == null) return this.getValueObject(((Field)object).zae);
        boolean bl = this.getValueObject(((Field)object).zae) == null;
        Preconditions.checkState(bl, "Concrete field shouldn't be value object: %s", ((Field)object).zae);
        bl = ((Field)object).zad;
        try {
            char c = Character.toUpperCase(((String)charSequence).charAt(0));
            object = ((String)charSequence).substring(1);
            int n = String.valueOf(object).length();
            charSequence = new StringBuilder(n + 4);
            ((StringBuilder)charSequence).append("get");
            ((StringBuilder)charSequence).append(c);
            ((StringBuilder)charSequence).append((String)object);
            object = ((StringBuilder)charSequence).toString();
            return this.getClass().getMethod((String)object, new Class[0]).invoke(this, new Object[0]);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    protected abstract Object getValueObject(String var1);

    protected boolean isFieldSet(Field object) {
        if (((Field)object).zac != 11) return this.isPrimitiveFieldSet(((Field)object).zae);
        if (((Field)object).zad) {
            object = ((Field)object).zae;
            throw new UnsupportedOperationException("Concrete type arrays not supported");
        }
        object = ((Field)object).zae;
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    protected abstract boolean isPrimitiveFieldSet(String var1);

    protected void setBooleanInternal(Field<?, ?> field, String string2, boolean bl) {
        throw new UnsupportedOperationException("Boolean not supported");
    }

    protected void setDecodedBytesInternal(Field<?, ?> field, String string2, byte[] arrby) {
        throw new UnsupportedOperationException("byte[] not supported");
    }

    protected void setIntegerInternal(Field<?, ?> field, String string2, int n) {
        throw new UnsupportedOperationException("Integer not supported");
    }

    protected void setLongInternal(Field<?, ?> field, String string2, long l) {
        throw new UnsupportedOperationException("Long not supported");
    }

    protected void setStringInternal(Field<?, ?> field, String string2, String string3) {
        throw new UnsupportedOperationException("String not supported");
    }

    protected void setStringMapInternal(Field<?, ?> field, String string2, Map<String, String> map) {
        throw new UnsupportedOperationException("String map not supported");
    }

    protected void setStringsInternal(Field<?, ?> field, String string2, ArrayList<String> arrayList) {
        throw new UnsupportedOperationException("String list not supported");
    }

    /*
     * Unable to fully structure code
     */
    public String toString() {
        var1_1 = this.getFieldMappings();
        var2_2 = new StringBuilder(100);
        for (String var4_5 : var1_1.keySet()) {
            block13 : {
                var5_7 = var1_1.get(var4_5);
                if (!this.isFieldSet(var5_7)) continue;
                var6_8 = FastJsonResponse.zaa(var5_7, this.getFieldValue(var5_7));
                if (var2_2.length() == 0) {
                    var2_2.append("{");
                } else {
                    var2_2.append(",");
                }
                var2_2.append("\"");
                var2_2.append(var4_5);
                var2_2.append("\":");
                if (var6_8 == null) {
                    var2_2.append("null");
                    continue;
                }
                switch (var5_7.zac) {
                    default: {
                        if (var5_7.zab) {
                            var6_8 = (ArrayList)var6_8;
                            var2_2.append("[");
                            var8_10 = var6_8.size();
                            ** break;
                        }
                        break block13;
                    }
                    case 10: {
                        MapUtils.writeStringMapToJson(var2_2, (HashMap)var6_8);
                        break;
                    }
                    case 9: {
                        var2_2.append("\"");
                        var2_2.append(Base64Utils.encodeUrlSafe((byte[])var6_8));
                        var2_2.append("\"");
                        break;
                    }
                    case 8: {
                        var2_2.append("\"");
                        var2_2.append(Base64Utils.encode((byte[])var6_8));
                        var2_2.append("\"");
                        break;
                    }
                }
                continue;
lbl52: // 3 sources:
                for (var7_9 = 0; var7_9 < var8_10; ++var7_9) {
                    if (var7_9 > 0) {
                        var2_2.append(",");
                    }
                    if ((var4_6 = var6_8.get(var7_9)) == null) continue;
                    FastJsonResponse.zaa(var2_2, var5_7, var4_6);
                }
                var2_2.append("]");
                continue;
            }
            FastJsonResponse.zaa(var2_2, var5_7, var6_8);
        }
        if (var2_2.length() > 0) {
            var2_2.append("}");
            return var2_2.toString();
        }
        var2_2.append("{}");
        return var2_2.toString();
    }

    public final <O> void zaa(Field<Double, O> field, double d) {
        if (field.zak != null) {
            this.zab(field, d);
            return;
        }
        this.zaa(field, field.zae, d);
    }

    public final <O> void zaa(Field<Float, O> field, float f) {
        if (field.zak != null) {
            this.zab(field, Float.valueOf(f));
            return;
        }
        this.zaa((Field<?, ?>)field, field.zae, f);
    }

    public final <O> void zaa(Field<Integer, O> field, int n) {
        if (field.zak != null) {
            this.zab(field, n);
            return;
        }
        this.setIntegerInternal(field, field.zae, n);
    }

    public final <O> void zaa(Field<Long, O> field, long l) {
        if (field.zak != null) {
            this.zab(field, l);
            return;
        }
        this.setLongInternal(field, field.zae, l);
    }

    public final <O> void zaa(Field<String, O> field, String string2) {
        if (field.zak != null) {
            this.zab(field, string2);
            return;
        }
        this.setStringInternal(field, field.zae, string2);
    }

    protected void zaa(Field<?, ?> field, String string2, double d) {
        throw new UnsupportedOperationException("Double not supported");
    }

    protected void zaa(Field<?, ?> field, String string2, float f) {
        throw new UnsupportedOperationException("Float not supported");
    }

    protected void zaa(Field<?, ?> field, String string2, BigDecimal bigDecimal) {
        throw new UnsupportedOperationException("BigDecimal not supported");
    }

    protected void zaa(Field<?, ?> field, String string2, BigInteger bigInteger) {
        throw new UnsupportedOperationException("BigInteger not supported");
    }

    protected void zaa(Field<?, ?> field, String string2, ArrayList<Integer> arrayList) {
        throw new UnsupportedOperationException("Integer list not supported");
    }

    public final <O> void zaa(Field<BigDecimal, O> field, BigDecimal bigDecimal) {
        if (field.zak != null) {
            this.zab(field, bigDecimal);
            return;
        }
        this.zaa(field, field.zae, bigDecimal);
    }

    public final <O> void zaa(Field<BigInteger, O> field, BigInteger bigInteger) {
        if (field.zak != null) {
            this.zab(field, bigInteger);
            return;
        }
        this.zaa(field, field.zae, bigInteger);
    }

    public final <O> void zaa(Field<ArrayList<Integer>, O> field, ArrayList<Integer> arrayList) {
        if (field.zak != null) {
            this.zab(field, (I)arrayList);
            return;
        }
        this.zaa(field, field.zae, arrayList);
    }

    public final <O> void zaa(Field<Map<String, String>, O> field, Map<String, String> map) {
        if (field.zak != null) {
            this.zab(field, map);
            return;
        }
        this.setStringMapInternal(field, field.zae, map);
    }

    public final <O> void zaa(Field<Boolean, O> field, boolean bl) {
        if (field.zak != null) {
            this.zab(field, bl);
            return;
        }
        this.setBooleanInternal(field, field.zae, bl);
    }

    public final <O> void zaa(Field<byte[], O> field, byte[] arrby) {
        if (field.zak != null) {
            this.zab(field, arrby);
            return;
        }
        this.setDecodedBytesInternal(field, field.zae, arrby);
    }

    protected void zab(Field<?, ?> field, String string2, ArrayList<BigInteger> arrayList) {
        throw new UnsupportedOperationException("BigInteger list not supported");
    }

    public final <O> void zab(Field<ArrayList<BigInteger>, O> field, ArrayList<BigInteger> arrayList) {
        if (field.zak != null) {
            this.zab((Field<I, O>)field, (I)arrayList);
            return;
        }
        this.zab(field, field.zae, arrayList);
    }

    protected void zac(Field<?, ?> field, String string2, ArrayList<Long> arrayList) {
        throw new UnsupportedOperationException("Long list not supported");
    }

    public final <O> void zac(Field<ArrayList<Long>, O> field, ArrayList<Long> arrayList) {
        if (field.zak != null) {
            this.zab(field, (I)arrayList);
            return;
        }
        this.zac(field, field.zae, arrayList);
    }

    protected void zad(Field<?, ?> field, String string2, ArrayList<Float> arrayList) {
        throw new UnsupportedOperationException("Float list not supported");
    }

    public final <O> void zad(Field<ArrayList<Float>, O> field, ArrayList<Float> arrayList) {
        if (field.zak != null) {
            this.zab(field, (I)arrayList);
            return;
        }
        this.zad(field, field.zae, arrayList);
    }

    protected void zae(Field<?, ?> field, String string2, ArrayList<Double> arrayList) {
        throw new UnsupportedOperationException("Double list not supported");
    }

    public final <O> void zae(Field<ArrayList<Double>, O> field, ArrayList<Double> arrayList) {
        if (field.zak != null) {
            this.zab(field, (I)arrayList);
            return;
        }
        this.zae(field, field.zae, arrayList);
    }

    protected void zaf(Field<?, ?> field, String string2, ArrayList<BigDecimal> arrayList) {
        throw new UnsupportedOperationException("BigDecimal list not supported");
    }

    public final <O> void zaf(Field<ArrayList<BigDecimal>, O> field, ArrayList<BigDecimal> arrayList) {
        if (field.zak != null) {
            this.zab(field, (I)arrayList);
            return;
        }
        this.zaf(field, field.zae, arrayList);
    }

    protected void zag(Field<?, ?> field, String string2, ArrayList<Boolean> arrayList) {
        throw new UnsupportedOperationException("Boolean list not supported");
    }

    public final <O> void zag(Field<ArrayList<Boolean>, O> field, ArrayList<Boolean> arrayList) {
        if (field.zak != null) {
            this.zab(field, (I)arrayList);
            return;
        }
        this.zag(field, field.zae, arrayList);
    }

    public final <O> void zah(Field<ArrayList<String>, O> field, ArrayList<String> arrayList) {
        if (field.zak != null) {
            this.zab(field, (I)arrayList);
            return;
        }
        this.setStringsInternal(field, field.zae, arrayList);
    }

    public static class Field<I, O>
    extends AbstractSafeParcelable {
        public static final zaj CREATOR = new zaj();
        protected final int zaa;
        protected final boolean zab;
        protected final int zac;
        protected final boolean zad;
        protected final String zae;
        protected final int zaf;
        protected final Class<? extends FastJsonResponse> zag;
        private final int zah;
        private final String zai;
        private zal zaj;
        private FieldConverter<I, O> zak;

        Field(int n, int n2, boolean bl, int n3, boolean bl2, String string2, int n4, String string3, zaa zaa2) {
            this.zah = n;
            this.zaa = n2;
            this.zab = bl;
            this.zac = n3;
            this.zad = bl2;
            this.zae = string2;
            this.zaf = n4;
            if (string3 == null) {
                this.zag = null;
                this.zai = null;
            } else {
                this.zag = SafeParcelResponse.class;
                this.zai = string3;
            }
            if (zaa2 == null) {
                this.zak = null;
                return;
            }
            this.zak = zaa2.zaa();
        }

        private Field(int n, boolean bl, int n2, boolean bl2, String string2, int n3, Class<? extends FastJsonResponse> class_, FieldConverter<I, O> fieldConverter) {
            this.zah = 1;
            this.zaa = n;
            this.zab = bl;
            this.zac = n2;
            this.zad = bl2;
            this.zae = string2;
            this.zaf = n3;
            this.zag = class_;
            this.zai = class_ == null ? null : class_.getCanonicalName();
            this.zak = fieldConverter;
        }

        public static Field<byte[], byte[]> forBase64(String string2, int n) {
            return new Field<byte[], byte[]>(8, false, 8, false, string2, n, null, null);
        }

        public static Field<Boolean, Boolean> forBoolean(String string2, int n) {
            return new Field<Boolean, Boolean>(6, false, 6, false, string2, n, null, null);
        }

        public static <T extends FastJsonResponse> Field<T, T> forConcreteType(String string2, int n, Class<T> class_) {
            return new Field<I, O>(11, false, 11, false, string2, n, class_, null);
        }

        public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> forConcreteTypeArray(String string2, int n, Class<T> class_) {
            return new Field<ArrayList<T>, ArrayList<T>>(11, true, 11, true, string2, n, class_, null);
        }

        public static Field<Double, Double> forDouble(String string2, int n) {
            return new Field<Double, Double>(4, false, 4, false, string2, n, null, null);
        }

        public static Field<Float, Float> forFloat(String string2, int n) {
            return new Field<Float, Float>(3, false, 3, false, string2, n, null, null);
        }

        public static Field<Integer, Integer> forInteger(String string2, int n) {
            return new Field<Integer, Integer>(0, false, 0, false, string2, n, null, null);
        }

        public static Field<Long, Long> forLong(String string2, int n) {
            return new Field<Long, Long>(2, false, 2, false, string2, n, null, null);
        }

        public static Field<String, String> forString(String string2, int n) {
            return new Field<String, String>(7, false, 7, false, string2, n, null, null);
        }

        public static Field<HashMap<String, String>, HashMap<String, String>> forStringMap(String string2, int n) {
            return new Field<HashMap<String, String>, HashMap<String, String>>(10, false, 10, false, string2, n, null, null);
        }

        public static Field<ArrayList<String>, ArrayList<String>> forStrings(String string2, int n) {
            return new Field<ArrayList<String>, ArrayList<String>>(7, true, 7, true, string2, n, null, null);
        }

        public static Field withConverter(String string2, int n, FieldConverter<?, ?> fieldConverter, boolean bl) {
            return new Field(fieldConverter.zaa(), bl, fieldConverter.zab(), false, string2, n, null, fieldConverter);
        }

        private final String zae() {
            String string2;
            String string3 = string2 = this.zai;
            if (string2 != null) return string3;
            return null;
        }

        private final zaa zaf() {
            FieldConverter<I, O> fieldConverter = this.zak;
            if (fieldConverter != null) return zaa.zaa(fieldConverter);
            return null;
        }

        public int getSafeParcelableFieldId() {
            return this.zaf;
        }

        public String toString() {
            Objects.ToStringHelper toStringHelper = Objects.toStringHelper(this).add("versionCode", this.zah).add("typeIn", this.zaa).add("typeInArray", this.zab).add("typeOut", this.zac).add("typeOutArray", this.zad).add("outputFieldName", this.zae).add("safeParcelFieldId", this.zaf).add("concreteTypeName", this.zae());
            Class<? extends FastJsonResponse> class_ = this.zag;
            if (class_ != null) {
                toStringHelper.add("concreteType.class", class_.getCanonicalName());
            }
            if ((class_ = this.zak) == null) return toStringHelper.toString();
            toStringHelper.add("converterName", class_.getClass().getCanonicalName());
            return toStringHelper.toString();
        }

        public void writeToParcel(Parcel parcel, int n) {
            int n2 = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeInt(parcel, 1, this.zah);
            SafeParcelWriter.writeInt(parcel, 2, this.zaa);
            SafeParcelWriter.writeBoolean(parcel, 3, this.zab);
            SafeParcelWriter.writeInt(parcel, 4, this.zac);
            SafeParcelWriter.writeBoolean(parcel, 5, this.zad);
            SafeParcelWriter.writeString(parcel, 6, this.zae, false);
            SafeParcelWriter.writeInt(parcel, 7, this.getSafeParcelableFieldId());
            SafeParcelWriter.writeString(parcel, 8, this.zae(), false);
            SafeParcelWriter.writeParcelable(parcel, 9, this.zaf(), n, false);
            SafeParcelWriter.finishObjectHeader(parcel, n2);
        }

        public final Field<I, O> zaa() {
            return new Field<I, O>(this.zah, this.zaa, this.zab, this.zac, this.zad, this.zae, this.zaf, this.zai, this.zaf());
        }

        public final O zaa(I i) {
            Preconditions.checkNotNull(this.zak);
            return Preconditions.checkNotNull(this.zak.zab(i));
        }

        public final void zaa(zal zal2) {
            this.zaj = zal2;
        }

        public final I zab(O o) {
            Preconditions.checkNotNull(this.zak);
            return this.zak.zaa(o);
        }

        public final boolean zab() {
            if (this.zak == null) return false;
            return true;
        }

        public final FastJsonResponse zac() throws InstantiationException, IllegalAccessException {
            Preconditions.checkNotNull(this.zag);
            Class<? extends FastJsonResponse> class_ = this.zag;
            if (class_ != SafeParcelResponse.class) return class_.newInstance();
            Preconditions.checkNotNull(this.zai);
            Preconditions.checkNotNull(this.zaj, "The field mapping dictionary must be set if the concrete type is a SafeParcelResponse object.");
            return new SafeParcelResponse(this.zaj, this.zai);
        }

        public final Map<String, Field<?, ?>> zad() {
            Preconditions.checkNotNull(this.zai);
            Preconditions.checkNotNull(this.zaj);
            return Preconditions.checkNotNull(this.zaj.zaa(this.zai));
        }
    }

    public static interface FieldConverter<I, O> {
        public int zaa();

        public I zaa(O var1);

        public int zab();

        public O zab(I var1);
    }

}

