/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.common.server.response;

import android.util.Log;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.zab;
import com.google.android.gms.common.server.response.zac;
import com.google.android.gms.common.server.response.zad;
import com.google.android.gms.common.server.response.zae;
import com.google.android.gms.common.server.response.zaf;
import com.google.android.gms.common.server.response.zag;
import com.google.android.gms.common.server.response.zah;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class FastParser<T extends FastJsonResponse> {
    private static final char[] zaf = new char[]{'u', 'l', 'l'};
    private static final char[] zag = new char[]{'r', 'u', 'e'};
    private static final char[] zah = new char[]{'r', 'u', 'e', '\"'};
    private static final char[] zai = new char[]{'a', 'l', 's', 'e'};
    private static final char[] zaj = new char[]{'a', 'l', 's', 'e', '\"'};
    private static final char[] zak = new char[]{'\n'};
    private static final zaa<Integer> zam = new com.google.android.gms.common.server.response.zaa();
    private static final zaa<Long> zan = new zac();
    private static final zaa<Float> zao = new zab();
    private static final zaa<Double> zap = new zae();
    private static final zaa<Boolean> zaq = new zad();
    private static final zaa<String> zar = new zag();
    private static final zaa<BigInteger> zas = new zaf();
    private static final zaa<BigDecimal> zat = new zah();
    private final char[] zaa = new char[1];
    private final char[] zab = new char[32];
    private final char[] zac = new char[1024];
    private final StringBuilder zad = new StringBuilder(32);
    private final StringBuilder zae = new StringBuilder(1024);
    private final Stack<Integer> zal = new Stack();

    static /* synthetic */ int zaa(FastParser fastParser, BufferedReader bufferedReader) throws ParseException, IOException {
        return fastParser.zad(bufferedReader);
    }

    private final int zaa(BufferedReader bufferedReader, char[] arrc) throws ParseException, IOException {
        int n;
        block7 : {
            int n2 = this.zaj(bufferedReader);
            if (n2 == 0) throw new ParseException("Unexpected EOF");
            if (n2 == 44) throw new ParseException("Missing value");
            if (n2 == 110) {
                this.zab(bufferedReader, zaf);
                return 0;
            }
            bufferedReader.mark(1024);
            if (n2 == 34) {
                n2 = 0;
                boolean bl = false;
                do {
                    n = n2;
                    if (n2 < arrc.length) {
                        n = n2;
                        if (bufferedReader.read(arrc, n2, 1) != -1) {
                            char c = arrc[n2];
                            if (Character.isISOControl(c)) throw new ParseException("Unexpected control character while reading string");
                            if (c == '\"' && !bl) {
                                bufferedReader.reset();
                                bufferedReader.skip(n2 + 1);
                                return n2;
                            }
                            bl = c == '\\' ? (bl ^= true) : false;
                            ++n2;
                            continue;
                        }
                    }
                    break block7;
                    break;
                } while (true);
            }
            arrc[0] = (char)n2;
            n2 = 1;
            do {
                n = n2;
                if (n2 >= arrc.length) break block7;
                n = n2;
                if (bufferedReader.read(arrc, n2, 1) == -1) break block7;
                if (arrc[n2] == '}' || arrc[n2] == ',' || Character.isWhitespace(arrc[n2]) || arrc[n2] == ']') break;
                ++n2;
            } while (true);
            bufferedReader.reset();
            bufferedReader.skip(n2 - '\u0001');
            arrc[n2] = (char)(false ? 1 : 0);
            return n2;
        }
        if (n != arrc.length) throw new ParseException("Unexpected EOF");
        throw new ParseException("Absurdly long value");
    }

    private final String zaa(BufferedReader object) throws ParseException, IOException {
        this.zal.push(2);
        char c = this.zaj((BufferedReader)object);
        if (c == '\"') {
            this.zal.push(3);
            String string2 = FastParser.zab((BufferedReader)object, this.zab, this.zad, null);
            this.zaa(3);
            if (this.zaj((BufferedReader)object) != ':') throw new ParseException("Expected key/value separator");
            return string2;
        }
        if (c == ']') {
            this.zaa(2);
            this.zaa(1);
            this.zaa(5);
            return null;
        }
        if (c == '}') {
            this.zaa(2);
            return null;
        }
        object = new StringBuilder(19);
        ((StringBuilder)object).append("Unexpected token: ");
        ((StringBuilder)object).append(c);
        throw new ParseException(((StringBuilder)object).toString());
    }

    private final String zaa(BufferedReader bufferedReader, char[] arrc, StringBuilder stringBuilder, char[] arrc2) throws ParseException, IOException {
        char c = this.zaj(bufferedReader);
        if (c == '\"') return FastParser.zab(bufferedReader, arrc, stringBuilder, arrc2);
        if (c != 'n') throw new ParseException("Expected string");
        this.zab(bufferedReader, zaf);
        return null;
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    private final <T extends FastJsonResponse> ArrayList<T> zaa(BufferedReader object, FastJsonResponse.Field<?, ?> field) throws ParseException, IOException {
        ArrayList<FastJsonResponse> arrayList = new ArrayList<FastJsonResponse>();
        char c = this.zaj((BufferedReader)object);
        if (c == ']') {
            this.zaa(5);
            return arrayList;
        }
        if (c == 'n') {
            this.zab((BufferedReader)object, zaf);
            this.zaa(5);
            return null;
        }
        if (c != '{') {
            object = new StringBuilder(19);
            ((StringBuilder)object).append("Unexpected token: ");
            ((StringBuilder)object).append(c);
            throw new ParseException(((StringBuilder)object).toString());
        }
        this.zal.push(1);
        do {
            block8 : {
                FastJsonResponse fastJsonResponse = field.zac();
                if (!this.zaa((BufferedReader)object, fastJsonResponse)) return arrayList;
                arrayList.add(fastJsonResponse);
                c = this.zaj((BufferedReader)object);
                if (c == ',') break block8;
                if (c == ']') {
                    this.zaa(5);
                    return arrayList;
                }
                object = new StringBuilder(19);
                ((StringBuilder)object).append("Unexpected token: ");
                ((StringBuilder)object).append(c);
                throw new ParseException(((StringBuilder)object).toString());
            }
            if (this.zaj((BufferedReader)object) != '{') throw new ParseException("Expected start of next object in array");
            this.zal.push(1);
        } while (true);
        catch (IllegalAccessException illegalAccessException) {
            throw new ParseException("Error instantiating inner object", illegalAccessException);
        }
        catch (InstantiationException instantiationException) {
            throw new ParseException("Error instantiating inner object", instantiationException);
        }
    }

    private final <O> ArrayList<O> zaa(BufferedReader bufferedReader, zaa<O> zaa2) throws ParseException, IOException {
        char c = this.zaj(bufferedReader);
        if (c == 'n') {
            this.zab(bufferedReader, zaf);
            return null;
        }
        if (c != '[') throw new ParseException("Expected start of array");
        this.zal.push(5);
        ArrayList<O> arrayList = new ArrayList<O>();
        do {
            bufferedReader.mark(1024);
            c = this.zaj(bufferedReader);
            if (c == '\u0000') throw new ParseException("Unexpected EOF");
            if (c == ',') continue;
            if (c == ']') {
                this.zaa(5);
                return arrayList;
            }
            bufferedReader.reset();
            arrayList.add(zaa2.zaa(this, bufferedReader));
        } while (true);
    }

    private final void zaa(int n) throws ParseException {
        if (this.zal.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder(46);
            stringBuilder.append("Expected state ");
            stringBuilder.append(n);
            stringBuilder.append(" but had empty stack");
            throw new ParseException(stringBuilder.toString());
        }
        int n2 = this.zal.pop();
        if (n2 == n) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(46);
        stringBuilder.append("Expected state ");
        stringBuilder.append(n);
        stringBuilder.append(" but had ");
        stringBuilder.append(n2);
        throw new ParseException(stringBuilder.toString());
    }

    static /* synthetic */ boolean zaa(FastParser fastParser, BufferedReader bufferedReader, boolean bl) throws ParseException, IOException {
        return fastParser.zaa(bufferedReader, false);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private final boolean zaa(BufferedReader var1_1, FastJsonResponse var2_4) throws ParseException, IOException {
        var3_5 = var2_4.getFieldMappings();
        var4_6 = this.zaa((BufferedReader)var1_1);
        var5_7 = 1;
        if (var4_6 == null) {
            this.zaa(1);
            return false;
        }
        do {
            if (var4_6 == null) {
                this.zaa(1);
                return true;
            }
            var6_8 = var3_5.get(var4_6);
            if (var6_8 == null) {
                var4_6 = this.zab((BufferedReader)var1_1);
                continue;
            }
            this.zal.push(4);
            switch (var6_8.zaa) {
                default: {
                    var7_9 = var6_8.zaa;
                    var1_1 = new StringBuilder(30);
                    var1_1.append("Invalid field type ");
                    var1_1.append(var7_9);
                    throw new ParseException(var1_1.toString());
                }
                case 11: {
                    if (var6_8.zab) {
                        var7_9 = this.zaj((BufferedReader)var1_1);
                        if (var7_9 == 110) {
                            this.zab((BufferedReader)var1_1, FastParser.zaf);
                            var2_4.addConcreteTypeArrayInternal(var6_8, var6_8.zae, null);
                            ** break;
                        }
                        this.zal.push(5);
                        if (var7_9 != 91) throw new ParseException("Expected array start");
                        var2_4.addConcreteTypeArrayInternal(var6_8, var6_8.zae, this.zaa((BufferedReader)var1_1, var6_8));
                        ** break;
                    }
                    var7_9 = this.zaj((BufferedReader)var1_1);
                    if (var7_9 == 110) {
                        this.zab((BufferedReader)var1_1, FastParser.zaf);
                        var2_4.addConcreteTypeInternal(var6_8, var6_8.zae, null);
                        ** break;
                    }
                    this.zal.push(var5_7);
                    if (var7_9 != 123) throw new ParseException("Expected start of object");
                    try {
                        var4_6 = var6_8.zac();
                        this.zaa((BufferedReader)var1_1, (FastJsonResponse)var4_6);
                        var2_4.addConcreteTypeInternal(var6_8, var6_8.zae, var4_6);
                        ** break;
                    }
                    catch (IllegalAccessException var1_2) {
                        throw new ParseException("Error instantiating inner object", var1_2);
                    }
                    catch (InstantiationException var1_3) {
                        throw new ParseException("Error instantiating inner object", var1_3);
                    }
                }
                case 10: {
                    var7_9 = this.zaj((BufferedReader)var1_1);
                    if (var7_9 != 110) ** GOTO lbl62
                    this.zab((BufferedReader)var1_1, FastParser.zaf);
                    var4_6 = null;
                    ** GOTO lbl98
lbl62: // 1 sources:
                    if (var7_9 != 123) throw new ParseException("Expected start of a map object");
                    this.zal.push(var5_7);
                    var4_6 = new HashMap<K, V>();
                    do lbl-1000: // 3 sources:
                    {
                        if ((var7_9 = (int)this.zaj((BufferedReader)var1_1)) == 0) throw new ParseException("Unexpected EOF");
                        if (var7_9 == 34) ** GOTO lbl72
                        if (var7_9 != 125) ** GOTO lbl-1000
                        this.zaa(1);
                        ** GOTO lbl98
lbl72: // 1 sources:
                        var8_10 = FastParser.zab((BufferedReader)var1_1, this.zab, this.zad, null);
                        if (this.zaj((BufferedReader)var1_1) != ':') {
                            var1_1 = String.valueOf(var8_10);
                            if (var1_1.length() != 0) {
                                var1_1 = "No map value found for key ".concat((String)var1_1);
                                throw new ParseException((String)var1_1);
                            }
                            var1_1 = new String("No map value found for key ");
                            throw new ParseException((String)var1_1);
                        }
                        if (this.zaj((BufferedReader)var1_1) != '\"') {
                            var1_1 = String.valueOf(var8_10);
                            if (var1_1.length() != 0) {
                                var1_1 = "Expected String value for key ".concat((String)var1_1);
                                throw new ParseException((String)var1_1);
                            }
                            var1_1 = new String("Expected String value for key ");
                            throw new ParseException((String)var1_1);
                        }
                        var4_6.put(var8_10, FastParser.zab((BufferedReader)var1_1, this.zab, this.zad, null));
                    } while ((var9_11 = this.zaj((BufferedReader)var1_1)) == ',');
                    if (var9_11 != '}') {
                        var1_1 = new StringBuilder(48);
                        var1_1.append("Unexpected character while parsing string map: ");
                        var1_1.append(var9_11);
                        throw new ParseException(var1_1.toString());
                    }
                    this.zaa(1);
lbl98: // 3 sources:
                    var2_4.zaa(var6_8, (Map<String, String>)var4_6);
                    ** break;
                }
                case 9: {
                    var2_4.zaa(var6_8, Base64Utils.decodeUrlSafe(this.zaa((BufferedReader)var1_1, this.zac, this.zae, FastParser.zak)));
                    ** break;
                }
                case 8: {
                    var2_4.zaa(var6_8, Base64Utils.decode(this.zaa((BufferedReader)var1_1, this.zac, this.zae, FastParser.zak)));
                    ** break;
                }
                case 7: {
                    if (var6_8.zab) {
                        var2_4.zah(var6_8, this.zaa((BufferedReader)var1_1, FastParser.zar));
                        ** break;
                    }
                    var2_4.zaa(var6_8, this.zac((BufferedReader)var1_1));
                    ** break;
                }
                case 6: {
                    if (var6_8.zab) {
                        var2_4.zag(var6_8, this.zaa((BufferedReader)var1_1, FastParser.zaq));
                        ** break;
                    }
                    var2_4.zaa(var6_8, this.zaa((BufferedReader)var1_1, false));
                    ** break;
                }
                case 5: {
                    if (var6_8.zab) {
                        var2_4.zaf(var6_8, this.zaa((BufferedReader)var1_1, FastParser.zat));
                        ** break;
                    }
                    var2_4.zaa(var6_8, this.zai((BufferedReader)var1_1));
                    ** break;
                }
                case 4: {
                    if (var6_8.zab) {
                        var2_4.zae(var6_8, this.zaa((BufferedReader)var1_1, FastParser.zap));
                        ** break;
                    }
                    var2_4.zaa(var6_8, this.zah((BufferedReader)var1_1));
                    ** break;
                }
                case 3: {
                    if (var6_8.zab) {
                        var2_4.zad(var6_8, this.zaa((BufferedReader)var1_1, FastParser.zao));
                        ** break;
                    }
                    var2_4.zaa(var6_8, this.zag((BufferedReader)var1_1));
                    ** break;
                }
                case 2: {
                    if (var6_8.zab) {
                        var2_4.zac(var6_8, this.zaa((BufferedReader)var1_1, FastParser.zan));
                        ** break;
                    }
                    var2_4.zaa(var6_8, this.zae((BufferedReader)var1_1));
                    ** break;
                }
                case 1: {
                    if (var6_8.zab) {
                        var2_4.zab(var6_8, this.zaa((BufferedReader)var1_1, FastParser.zas));
                        ** break;
                    }
                    var2_4.zaa(var6_8, this.zaf((BufferedReader)var1_1));
                    ** break;
                }
                case 0: 
            }
            if (var6_8.zab) {
                var2_4.zaa(var6_8, this.zaa((BufferedReader)var1_1, FastParser.zam));
                ** break;
            }
            var2_4.zaa(var6_8, this.zad((BufferedReader)var1_1));
lbl153: // 23 sources:
            this.zaa(4);
            this.zaa(2);
            var9_11 = this.zaj((BufferedReader)var1_1);
            if (var9_11 != ',') {
                if (var9_11 != '}') {
                    var1_1 = new StringBuilder(55);
                    var1_1.append("Expected end of object or field separator, but found: ");
                    var1_1.append(var9_11);
                    throw new ParseException(var1_1.toString());
                }
                var4_6 = null;
                continue;
            }
            var4_6 = this.zaa((BufferedReader)var1_1);
        } while (true);
    }

    private final boolean zaa(BufferedReader object, boolean bl) throws ParseException, IOException {
        char[] arrc;
        do {
            char c;
            if ((c = this.zaj((BufferedReader)object)) != '\"') {
                if (c != 'f') {
                    if (c == 'n') {
                        this.zab((BufferedReader)object, zaf);
                        return false;
                    }
                    if (c != 't') {
                        object = new StringBuilder(19);
                        ((StringBuilder)object).append("Unexpected token: ");
                        ((StringBuilder)object).append(c);
                        throw new ParseException(((StringBuilder)object).toString());
                    }
                    if (bl) {
                        arrc = zah;
                        break;
                    }
                    arrc = zag;
                    break;
                }
                char[] arrc2 = bl ? zaj : zai;
                this.zab((BufferedReader)object, arrc2);
                return false;
            }
            if (bl) throw new ParseException("No boolean value found in string");
            bl = true;
        } while (true);
        this.zab((BufferedReader)object, arrc);
        return true;
    }

    static /* synthetic */ long zab(FastParser fastParser, BufferedReader bufferedReader) throws ParseException, IOException {
        return fastParser.zae(bufferedReader);
    }

    /*
     * Unable to fully structure code
     */
    private final String zab(BufferedReader var1_1) throws ParseException, IOException {
        block25 : {
            var1_1.mark(1024);
            var2_2 = this.zaj((BufferedReader)var1_1);
            if (var2_2 == 34) break block25;
            if (var2_2 == 44) throw new ParseException("Missing value");
            var3_3 = 1;
            if (var2_2 != 91) {
                if (var2_2 != 123) {
                    var1_1.reset();
                    this.zaa((BufferedReader)var1_1, this.zac);
                } else {
                    this.zal.push(1);
                    var1_1.mark(32);
                    var4_5 = this.zaj((BufferedReader)var1_1);
                    if (var4_5 == '}') {
                        this.zaa(1);
                    } else {
                        if (var4_5 != '\"') {
                            var1_1 = new StringBuilder(18);
                            var1_1.append("Unexpected token ");
                            var1_1.append(var4_5);
                            throw new ParseException(var1_1.toString());
                        }
                        var1_1.reset();
                        this.zaa((BufferedReader)var1_1);
                        while (this.zab((BufferedReader)var1_1) != null) {
                        }
                        this.zaa(1);
                    }
                }
            } else {
                this.zal.push(5);
                var1_1.mark(32);
                if (this.zaj((BufferedReader)var1_1) == ']') {
                    this.zaa(5);
                } else {
                    var1_1.reset();
                    var2_2 = 0;
                    var5_6 = 0;
                    while (var3_3 > 0) {
                        var4_5 = this.zaj((BufferedReader)var1_1);
                        if (var4_5 == '\u0000') throw new ParseException("Unexpected EOF while parsing array");
                        if (Character.isISOControl(var4_5) != false) throw new ParseException("Unexpected control character while reading array");
                        var6_7 = var5_6;
                        if (var4_5 == '\"') {
                            var6_7 = var5_6;
                            if (var2_2 == 0) {
                                var6_7 = var5_6 ^ 1;
                            }
                        }
                        var5_6 = var3_3;
                        if (var4_5 == '[') {
                            var5_6 = var3_3;
                            if (var6_7 == 0) {
                                var5_6 = var3_3 + 1;
                            }
                        }
                        var3_3 = var5_6;
                        if (var4_5 == ']') {
                            var3_3 = var5_6;
                            if (var6_7 == 0) {
                                var3_3 = var5_6 - 1;
                            }
                        }
                        if (var4_5 == '\\' && var6_7 != 0) {
                            var2_2 ^= 1;
                            var5_6 = var6_7;
                            continue;
                        }
                        var2_2 = 0;
                        var5_6 = var6_7;
                    }
                    this.zaa(5);
                }
            }
            ** GOTO lbl76
        }
        if (var1_1.read(this.zaa) == -1) throw new ParseException("Unexpected EOF while parsing string");
        var3_4 = this.zaa[0];
        var2_2 = 0;
        do {
            block26 : {
                if (var3_4 != '\"' || var2_2 != 0) break block26;
lbl76: // 6 sources:
                var4_5 = this.zaj((BufferedReader)var1_1);
                if (var4_5 == ',') {
                    this.zaa(2);
                    return this.zaa((BufferedReader)var1_1);
                }
                if (var4_5 == '}') {
                    this.zaa(2);
                    return null;
                }
                var1_1 = new StringBuilder(18);
                var1_1.append("Unexpected token ");
                var1_1.append(var4_5);
                throw new ParseException(var1_1.toString());
            }
            var2_2 = var3_4 == '\\' ? (var2_2 ^= '\u0001') : 0;
            if (var1_1.read(this.zaa) == -1) throw new ParseException("Unexpected EOF while parsing string");
            var4_5 = this.zaa[0];
            if (Character.isISOControl(var4_5) != false) throw new ParseException("Unexpected control character while reading string");
            var3_4 = var4_5;
        } while (true);
    }

    private static String zab(BufferedReader bufferedReader, char[] arrc, StringBuilder stringBuilder, char[] arrc2) throws ParseException, IOException {
        stringBuilder.setLength(0);
        bufferedReader.mark(arrc.length);
        int n = 0;
        int n2 = 0;
        block0 : do {
            int n3;
            if ((n3 = bufferedReader.read(arrc)) == -1) throw new ParseException("Unexpected EOF while parsing string");
            int n4 = 0;
            do {
                char c;
                int n5;
                block8 : {
                    block6 : {
                        block9 : {
                            block10 : {
                                block7 : {
                                    if (n4 >= n3) break block7;
                                    c = arrc[n4];
                                    if (!Character.isISOControl(c)) break block8;
                                    if (arrc2 == null) break block9;
                                    break block10;
                                }
                                stringBuilder.append(arrc, 0, n3);
                                bufferedReader.mark(arrc.length);
                                continue block0;
                            }
                            for (n5 = 0; n5 < arrc2.length; ++n5) {
                                if (arrc2[n5] != c) continue;
                                n5 = 1;
                                break block6;
                            }
                        }
                        n5 = 0;
                    }
                    if (n5 == 0) throw new ParseException("Unexpected control character while reading string");
                }
                if (c == '\"' && n == 0) {
                    stringBuilder.append(arrc, 0, n4);
                    bufferedReader.reset();
                    bufferedReader.skip(n4 + 1);
                    if (n2 == 0) return stringBuilder.toString();
                    return JsonUtils.unescapeString(stringBuilder.toString());
                }
                if (c == '\\') {
                    n2 = n ^ true;
                    n5 = 1;
                } else {
                    n = 0;
                    n5 = n2;
                    n2 = n;
                }
                ++n4;
                n = n2;
                n2 = n5;
            } while (true);
            break;
        } while (true);
    }

    private final void zab(BufferedReader bufferedReader, char[] arrc) throws ParseException, IOException {
        int n = 0;
        while (n < arrc.length) {
            int n2 = bufferedReader.read(this.zab, 0, arrc.length - n);
            if (n2 == -1) throw new ParseException("Unexpected EOF");
            for (int i = 0; i < n2; ++i) {
                if (arrc[i + n] != this.zab[i]) throw new ParseException("Unexpected character");
            }
            n += n2;
        }
    }

    static /* synthetic */ float zac(FastParser fastParser, BufferedReader bufferedReader) throws ParseException, IOException {
        return fastParser.zag(bufferedReader);
    }

    private final String zac(BufferedReader bufferedReader) throws ParseException, IOException {
        return this.zaa(bufferedReader, this.zab, this.zad, null);
    }

    static /* synthetic */ double zad(FastParser fastParser, BufferedReader bufferedReader) throws ParseException, IOException {
        return fastParser.zah(bufferedReader);
    }

    private final int zad(BufferedReader arrc) throws ParseException, IOException {
        int n;
        boolean bl;
        int n2;
        int n3 = this.zaa((BufferedReader)arrc, this.zac);
        int n4 = 0;
        if (n3 == 0) {
            return 0;
        }
        arrc = this.zac;
        if (n3 <= 0) throw new ParseException("No number to parse");
        if (arrc[0] == '-') {
            n = Integer.MIN_VALUE;
            n2 = 1;
            bl = true;
        } else {
            n = -2147483647;
            n2 = 0;
            bl = false;
        }
        int n5 = n2;
        if (n2 < n3) {
            n5 = Character.digit(arrc[n2], 10);
            if (n5 < 0) throw new ParseException("Unexpected non-digit character");
            n4 = -n5;
            n5 = n2 + 1;
        }
        do {
            if (n5 >= n3) {
                if (!bl) return -n4;
                if (n5 <= 1) throw new ParseException("No digits to parse");
                return n4;
            }
            n2 = Character.digit(arrc[n5], 10);
            if (n2 < 0) throw new ParseException("Unexpected non-digit character");
            if (n4 < -214748364) throw new ParseException("Number too large");
            if ((n4 *= 10) < n + n2) throw new ParseException("Number too large");
            n4 -= n2;
            ++n5;
        } while (true);
    }

    private final long zae(BufferedReader arrc) throws ParseException, IOException {
        long l;
        boolean bl;
        int n = this.zaa((BufferedReader)arrc, this.zac);
        long l2 = 0L;
        if (n == 0) {
            return 0L;
        }
        arrc = this.zac;
        if (n <= 0) throw new ParseException("No number to parse");
        int n2 = 0;
        if (arrc[0] == '-') {
            l = Long.MIN_VALUE;
            n2 = 1;
            bl = true;
        } else {
            l = -9223372036854775807L;
            bl = false;
        }
        int n3 = n2;
        if (n2 < n) {
            n3 = Character.digit(arrc[n2], 10);
            if (n3 < 0) throw new ParseException("Unexpected non-digit character");
            l2 = -n3;
            n3 = n2 + 1;
        }
        do {
            if (n3 >= n) {
                if (!bl) return -l2;
                if (n3 <= 1) throw new ParseException("No digits to parse");
                return l2;
            }
            n2 = Character.digit(arrc[n3], 10);
            if (n2 < 0) throw new ParseException("Unexpected non-digit character");
            if (l2 < -922337203685477580L) throw new ParseException("Number too large");
            long l3 = l2 * 10L;
            l2 = n2;
            if (l3 < l + l2) throw new ParseException("Number too large");
            l2 = l3 - l2;
            ++n3;
        } while (true);
    }

    static /* synthetic */ String zae(FastParser fastParser, BufferedReader bufferedReader) throws ParseException, IOException {
        return fastParser.zac(bufferedReader);
    }

    static /* synthetic */ BigInteger zaf(FastParser fastParser, BufferedReader bufferedReader) throws ParseException, IOException {
        return fastParser.zaf(bufferedReader);
    }

    private final BigInteger zaf(BufferedReader bufferedReader) throws ParseException, IOException {
        int n = this.zaa(bufferedReader, this.zac);
        if (n != 0) return new BigInteger(new String(this.zac, 0, n));
        return null;
    }

    private final float zag(BufferedReader bufferedReader) throws ParseException, IOException {
        int n = this.zaa(bufferedReader, this.zac);
        if (n != 0) return Float.parseFloat(new String(this.zac, 0, n));
        return 0.0f;
    }

    static /* synthetic */ BigDecimal zag(FastParser fastParser, BufferedReader bufferedReader) throws ParseException, IOException {
        return fastParser.zai(bufferedReader);
    }

    private final double zah(BufferedReader bufferedReader) throws ParseException, IOException {
        int n = this.zaa(bufferedReader, this.zac);
        if (n != 0) return Double.parseDouble(new String(this.zac, 0, n));
        return 0.0;
    }

    private final BigDecimal zai(BufferedReader bufferedReader) throws ParseException, IOException {
        int n = this.zaa(bufferedReader, this.zac);
        if (n != 0) return new BigDecimal(new String(this.zac, 0, n));
        return null;
    }

    private final char zaj(BufferedReader bufferedReader) throws ParseException, IOException {
        if (bufferedReader.read(this.zaa) == -1) {
            return '\u0000';
        }
        do {
            if (!Character.isWhitespace(this.zaa[0])) return this.zaa[0];
        } while (bufferedReader.read(this.zaa) != -1);
        return '\u0000';
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public void parse(InputStream var1_1, T var2_4) throws ParseException {
        block13 : {
            block12 : {
                var1_1 = new BufferedReader(new InputStreamReader((InputStream)var1_1), 1024);
                this.zal.push(0);
                var3_7 = this.zaj((BufferedReader)var1_1);
                if (var3_7 == '\u0000') ** GOTO lbl41
                if (var3_7 != '[') {
                    if (var3_7 != '{') {
                        var2_4 = new StringBuilder(19);
                        var2_4.append("Unexpected token: ");
                        var2_4.append(var3_7);
                        var4_8 = new ParseException(var2_4.toString());
                        throw var4_8;
                    }
                    this.zal.push(1);
                    this.zaa((BufferedReader)var1_1, (FastJsonResponse)var2_4);
                } else {
                    this.zal.push(5);
                    var4_9 = var2_4.getFieldMappings();
                    if (var4_9.size() != 1) break block12;
                    var4_9 = (FastJsonResponse.Field)var4_9.entrySet().iterator().next().getValue();
                    var5_10 = this.zaa((BufferedReader)var1_1, (FastJsonResponse.Field<?, ?>)var4_9);
                    var2_4.addConcreteTypeArrayInternal((FastJsonResponse.Field<?, ?>)var4_9, var4_9.zae, var5_10);
                }
                this.zaa(0);
                try {
                    var1_1.close();
                    return;
                }
                catch (IOException var1_2) {
                    Log.w((String)"FastParser", (String)"Failed to close reader while parsing.");
                    return;
                }
            }
            try {
                var2_4 = new ParseException("Object array response class must have a single Field");
                throw var2_4;
lbl41: // 1 sources:
                var2_4 = new ParseException("No data to parse");
                throw var2_4;
            }
            catch (Throwable var2_5) {
                break block13;
            }
            catch (IOException var2_6) {
                var4_9 = new ParseException(var2_6);
                throw var4_9;
            }
        }
        try {
            var1_1.close();
            throw var2_5;
        }
        catch (IOException var1_3) {
            Log.w((String)"FastParser", (String)"Failed to close reader while parsing.");
        }
        throw var2_5;
    }

    public static class ParseException
    extends Exception {
        public ParseException(String string2) {
            super(string2);
        }

        public ParseException(String string2, Throwable throwable) {
            super(string2, throwable);
        }

        public ParseException(Throwable throwable) {
            super(throwable);
        }
    }

    private static interface zaa<O> {
        public O zaa(FastParser var1, BufferedReader var2) throws ParseException, IOException;
    }

}

