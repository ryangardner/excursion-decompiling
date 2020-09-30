/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.util.InternCache;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public final class ByteQuadsCanonicalizer {
    private static final int DEFAULT_T_SIZE = 64;
    static final int MAX_ENTRIES_FOR_REUSE = 6000;
    private static final int MAX_T_SIZE = 65536;
    private static final int MIN_HASH_SIZE = 16;
    private static final int MULT = 33;
    private static final int MULT2 = 65599;
    private static final int MULT3 = 31;
    private int _count;
    private final boolean _failOnDoS;
    private int[] _hashArea;
    private boolean _hashShared;
    private int _hashSize;
    private boolean _intern;
    private int _longNameOffset;
    private String[] _names;
    private final ByteQuadsCanonicalizer _parent;
    private int _secondaryStart;
    private final int _seed;
    private int _spilloverEnd;
    private final AtomicReference<TableInfo> _tableInfo;
    private int _tertiaryShift;
    private int _tertiaryStart;

    private ByteQuadsCanonicalizer(int n, boolean bl, int n2, boolean bl2) {
        this._parent = null;
        this._seed = n2;
        this._intern = bl;
        this._failOnDoS = bl2;
        int n3 = 16;
        if (n < 16) {
            n2 = 16;
        } else {
            n2 = n;
            if ((n - 1 & n) != 0) {
                for (n2 = n3; n2 < n; n2 += n2) {
                }
            }
        }
        this._tableInfo = new AtomicReference<TableInfo>(TableInfo.createInitial(n2));
    }

    private ByteQuadsCanonicalizer(ByteQuadsCanonicalizer byteQuadsCanonicalizer, boolean bl, int n, boolean bl2, TableInfo tableInfo) {
        this._parent = byteQuadsCanonicalizer;
        this._seed = n;
        this._intern = bl;
        this._failOnDoS = bl2;
        this._tableInfo = null;
        this._count = tableInfo.count;
        this._hashSize = n = tableInfo.size;
        this._secondaryStart = n <<= 2;
        this._tertiaryStart = n + (n >> 1);
        this._tertiaryShift = tableInfo.tertiaryShift;
        this._hashArea = tableInfo.mainHash;
        this._names = tableInfo.names;
        this._spilloverEnd = tableInfo.spilloverEnd;
        this._longNameOffset = tableInfo.longNameOffset;
        this._hashShared = true;
    }

    private int _appendLongName(int[] arrn, int n) {
        int n2 = this._longNameOffset;
        int n3 = n2 + n;
        int[] arrn2 = this._hashArea;
        if (n3 > arrn2.length) {
            int n4 = arrn2.length;
            int n5 = Math.min(4096, this._hashSize);
            int n6 = this._hashArea.length;
            n3 = Math.max(n3 - n4, n5);
            this._hashArea = Arrays.copyOf(this._hashArea, n6 + n3);
        }
        System.arraycopy(arrn, 0, this._hashArea, n2, n);
        this._longNameOffset += n;
        return n2;
    }

    private final int _calcOffset(int n) {
        return (n & this._hashSize - 1) << 2;
    }

    static int _calcTertiaryShift(int n) {
        if ((n >>= 2) < 64) {
            return 4;
        }
        if (n <= 256) {
            return 5;
        }
        if (n > 1024) return 7;
        return 6;
    }

    private boolean _checkNeedForRehash() {
        int n;
        if (this._count <= this._hashSize >> 1) return false;
        int n2 = this._spilloverEnd;
        int n3 = this._spilloverStart();
        if (n2 - n3 >> 2 > (n = this._count) + 1 >> 7) return true;
        if (!((double)n > (double)this._hashSize * 0.8)) return false;
        return true;
    }

    private int _findOffsetForAdd(int n) {
        int[] arrn = this._hashArea;
        int n2 = this._calcOffset(n);
        if (arrn[n2 + 3] == 0) {
            return n2;
        }
        if (this._checkNeedForRehash()) {
            return this._resizeAndFindOffsetForAdd(n);
        }
        int n3 = this._secondaryStart + (n2 >> 3 << 2);
        if (arrn[n3 + 3] == 0) {
            return n3;
        }
        n3 = this._tertiaryStart;
        int n4 = this._tertiaryShift;
        n3 += n2 >> n4 + 2 << n4;
        n2 = n3;
        do {
            if (n2 >= (1 << n4) + n3) {
                n2 = this._spilloverEnd;
                this._spilloverEnd = n3 = n2 + 4;
                if (n3 < this._hashSize << 3) return n2;
                if (!this._failOnDoS) return this._resizeAndFindOffsetForAdd(n);
                this._reportTooManyCollisions();
                return this._resizeAndFindOffsetForAdd(n);
            }
            if (arrn[n2 + 3] == 0) {
                return n2;
            }
            n2 += 4;
        } while (true);
    }

    private String _findSecondary(int n, int n2) {
        int n3 = this._tertiaryStart;
        int n4 = this._tertiaryShift;
        n3 += n >> n4 + 2 << n4;
        int[] arrn = this._hashArea;
        for (n = n3; n < (1 << n4) + n3; n += 4) {
            int n5 = arrn[n + 3];
            if (n2 == arrn[n] && 1 == n5) {
                return this._names[n >> 2];
            }
            if (n5 != 0) continue;
            return null;
        }
        n = this._spilloverStart();
        while (n < this._spilloverEnd) {
            if (n2 == arrn[n] && 1 == arrn[n + 3]) {
                return this._names[n >> 2];
            }
            n += 4;
        }
        return null;
    }

    private String _findSecondary(int n, int n2, int n3) {
        int n4 = this._tertiaryStart;
        int n5 = this._tertiaryShift;
        n4 += n >> n5 + 2 << n5;
        int[] arrn = this._hashArea;
        for (n = n4; n < (1 << n5) + n4; n += 4) {
            int n6 = arrn[n + 3];
            if (n2 == arrn[n] && n3 == arrn[n + 1] && 2 == n6) {
                return this._names[n >> 2];
            }
            if (n6 != 0) continue;
            return null;
        }
        n = this._spilloverStart();
        while (n < this._spilloverEnd) {
            if (n2 == arrn[n] && n3 == arrn[n + 1] && 2 == arrn[n + 3]) {
                return this._names[n >> 2];
            }
            n += 4;
        }
        return null;
    }

    private String _findSecondary(int n, int n2, int n3, int n4) {
        int n5 = this._tertiaryStart;
        int n6 = this._tertiaryShift;
        n5 += n >> n6 + 2 << n6;
        int[] arrn = this._hashArea;
        for (n = n5; n < (1 << n6) + n5; n += 4) {
            int n7 = arrn[n + 3];
            if (n2 == arrn[n] && n3 == arrn[n + 1] && n4 == arrn[n + 2] && 3 == n7) {
                return this._names[n >> 2];
            }
            if (n7 != 0) continue;
            return null;
        }
        n = this._spilloverStart();
        while (n < this._spilloverEnd) {
            if (n2 == arrn[n] && n3 == arrn[n + 1] && n4 == arrn[n + 2] && 3 == arrn[n + 3]) {
                return this._names[n >> 2];
            }
            n += 4;
        }
        return null;
    }

    private String _findSecondary(int n, int n2, int[] arrn, int n3) {
        int n4 = this._tertiaryStart;
        int n5 = this._tertiaryShift;
        n4 += n >> n5 + 2 << n5;
        int[] arrn2 = this._hashArea;
        for (n = n4; n < (1 << n5) + n4; n += 4) {
            int n6 = arrn2[n + 3];
            if (n2 == arrn2[n] && n3 == n6 && this._verifyLongName(arrn, n3, arrn2[n + 1])) {
                return this._names[n >> 2];
            }
            if (n6 != 0) continue;
            return null;
        }
        n = this._spilloverStart();
        while (n < this._spilloverEnd) {
            if (n2 == arrn2[n] && n3 == arrn2[n + 3] && this._verifyLongName(arrn, n3, arrn2[n + 1])) {
                return this._names[n >> 2];
            }
            n += 4;
        }
        return null;
    }

    private int _resizeAndFindOffsetForAdd(int n) {
        this.rehash();
        n = this._calcOffset(n);
        int[] arrn = this._hashArea;
        if (arrn[n + 3] == 0) {
            return n;
        }
        int n2 = this._secondaryStart + (n >> 3 << 2);
        if (arrn[n2 + 3] == 0) {
            return n2;
        }
        n2 = this._tertiaryStart;
        int n3 = this._tertiaryShift;
        n2 += n >> n3 + 2 << n3;
        n = n2;
        do {
            if (n >= (1 << n3) + n2) {
                n = this._spilloverEnd;
                this._spilloverEnd = n + 4;
                return n;
            }
            if (arrn[n + 3] == 0) {
                return n;
            }
            n += 4;
        } while (true);
    }

    private final int _spilloverStart() {
        int n = this._hashSize;
        return (n << 3) - n;
    }

    /*
     * Unable to fully structure code
     */
    private boolean _verifyLongName(int[] var1_1, int var2_2, int var3_3) {
        block14 : {
            var4_4 = this._hashArea;
            switch (var2_2) {
                default: {
                    return this._verifyLongName2(var1_1, var2_2, var3_3);
                }
                case 8: {
                    if (var1_1[0] != var4_4[var3_3]) {
                        return false;
                    }
                    ++var3_3;
                    var2_2 = 1;
                    ** GOTO lbl13
                }
                case 7: {
                    var2_2 = 0;
lbl13: // 2 sources:
                    var5_5 = var2_2 + 1;
                    if (var1_1[var2_2] != var4_4[var3_3]) {
                        return false;
                    }
                    ++var3_3;
                    var2_2 = var5_5;
                    ** GOTO lbl21
                }
                case 6: {
                    var2_2 = 0;
lbl21: // 2 sources:
                    var5_5 = var2_2 + 1;
                    if (var1_1[var2_2] != var4_4[var3_3]) {
                        return false;
                    }
                    ++var3_3;
                    var2_2 = var5_5;
                    ** GOTO lbl29
                }
                case 5: {
                    var2_2 = 0;
lbl29: // 2 sources:
                    var5_5 = var2_2 + 1;
                    if (var1_1[var2_2] != var4_4[var3_3]) {
                        return false;
                    }
                    ++var3_3;
                    var2_2 = var5_5;
                    break block14;
                }
                case 4: 
            }
            var2_2 = 0;
        }
        var5_5 = var2_2 + 1;
        var6_6 = var1_1[var2_2];
        var2_2 = var3_3 + 1;
        if (var6_6 != var4_4[var3_3]) {
            return false;
        }
        var3_3 = var5_5 + 1;
        var6_6 = var1_1[var5_5];
        var5_5 = var2_2 + 1;
        if (var6_6 != var4_4[var2_2]) {
            return false;
        }
        if (var1_1[var3_3] != var4_4[var5_5]) {
            return false;
        }
        if (var1_1[var3_3 + 1] == var4_4[var5_5 + 1]) return true;
        return false;
    }

    private boolean _verifyLongName2(int[] arrn, int n, int n2) {
        int n3 = 0;
        do {
            int n4 = n3 + 1;
            if (arrn[n3] != this._hashArea[n2]) {
                return false;
            }
            if (n4 >= n) {
                return true;
            }
            n3 = n4;
            ++n2;
        } while (true);
    }

    private void _verifySharing() {
        if (!this._hashShared) return;
        Object[] arrobject = this._hashArea;
        this._hashArea = Arrays.copyOf(arrobject, arrobject.length);
        arrobject = this._names;
        this._names = (String[])Arrays.copyOf(arrobject, arrobject.length);
        this._hashShared = false;
    }

    public static ByteQuadsCanonicalizer createRoot() {
        long l = System.currentTimeMillis();
        return ByteQuadsCanonicalizer.createRoot((int)l + (int)(l >>> 32) | 1);
    }

    protected static ByteQuadsCanonicalizer createRoot(int n) {
        return new ByteQuadsCanonicalizer(64, true, n, true);
    }

    private void mergeChild(TableInfo tableInfo) {
        int n = tableInfo.count;
        TableInfo tableInfo2 = this._tableInfo.get();
        if (n == tableInfo2.count) {
            return;
        }
        if (n > 6000) {
            tableInfo = TableInfo.createInitial(64);
        }
        this._tableInfo.compareAndSet(tableInfo2, tableInfo);
    }

    private void nukeSymbols(boolean bl) {
        this._count = 0;
        this._spilloverEnd = this._spilloverStart();
        this._longNameOffset = this._hashSize << 3;
        if (!bl) return;
        Arrays.fill(this._hashArea, 0);
        Arrays.fill(this._names, null);
    }

    private void rehash() {
        int[] arrn;
        this._hashShared = false;
        int[] arrn2 = this._hashArea;
        String[] arrstring = this._names;
        int n = this._hashSize;
        int n2 = this._count;
        int n3 = n + n;
        int n4 = this._spilloverEnd;
        if (n3 > 65536) {
            this.nukeSymbols(true);
            return;
        }
        this._hashArea = new int[arrn2.length + (n << 3)];
        this._hashSize = n3;
        this._secondaryStart = n = n3 << 2;
        this._tertiaryStart = n + (n >> 1);
        this._tertiaryShift = ByteQuadsCanonicalizer._calcTertiaryShift(n3);
        this._names = new String[arrstring.length << 1];
        this.nukeSymbols(false);
        int[] arrn3 = new int[16];
        n = 0;
        for (n3 = 0; n3 < n4; n3 += 4) {
            int n5 = arrn2[n3 + 3];
            if (n5 == 0) {
                arrn = arrn3;
            } else {
                ++n;
                String string2 = arrstring[n3 >> 2];
                if (n5 != 1) {
                    if (n5 != 2) {
                        if (n5 != 3) {
                            arrn = arrn3;
                            if (n5 > arrn3.length) {
                                arrn = new int[n5];
                            }
                            System.arraycopy(arrn2, arrn2[n3 + 1], arrn, 0, n5);
                            this.addName(string2, arrn, n5);
                        } else {
                            arrn3[0] = arrn2[n3];
                            arrn3[1] = arrn2[n3 + 1];
                            arrn3[2] = arrn2[n3 + 2];
                            this.addName(string2, arrn3, 3);
                            arrn = arrn3;
                        }
                    } else {
                        arrn3[0] = arrn2[n3];
                        arrn3[1] = arrn2[n3 + 1];
                        this.addName(string2, arrn3, 2);
                        arrn = arrn3;
                    }
                } else {
                    arrn3[0] = arrn2[n3];
                    this.addName(string2, arrn3, 1);
                    arrn = arrn3;
                }
            }
            arrn3 = arrn;
        }
        if (n == n2) {
            return;
        }
        arrn = new StringBuilder();
        arrn.append("Failed rehash(): old count=");
        arrn.append(n2);
        arrn.append(", copyCount=");
        arrn.append(n);
        throw new IllegalStateException(arrn.toString());
    }

    protected void _reportTooManyCollisions() {
        if (this._hashSize <= 1024) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Spill-over slots in symbol table with ");
        stringBuilder.append(this._count);
        stringBuilder.append(" entries, hash area of ");
        stringBuilder.append(this._hashSize);
        stringBuilder.append(" slots is now full (all ");
        stringBuilder.append(this._hashSize >> 3);
        stringBuilder.append(" slots -- suspect a DoS attack based on hash collisions. You can disable the check via `JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW`");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public String addName(String arrn, int n) {
        this._verifySharing();
        int[] arrn2 = arrn;
        if (this._intern) {
            arrn2 = InternCache.instance.intern((String)arrn);
        }
        int n2 = this._findOffsetForAdd(this.calcHash(n));
        arrn = this._hashArea;
        arrn[n2] = n;
        arrn[n2 + 3] = 1;
        this._names[n2 >> 2] = arrn2;
        ++this._count;
        return arrn2;
    }

    public String addName(String arrn, int n, int n2) {
        this._verifySharing();
        int[] arrn2 = arrn;
        if (this._intern) {
            arrn2 = InternCache.instance.intern((String)arrn);
        }
        int n3 = n2 == 0 ? this.calcHash(n) : this.calcHash(n, n2);
        n3 = this._findOffsetForAdd(n3);
        arrn = this._hashArea;
        arrn[n3] = n;
        arrn[n3 + 1] = n2;
        arrn[n3 + 3] = 2;
        this._names[n3 >> 2] = arrn2;
        ++this._count;
        return arrn2;
    }

    public String addName(String arrn, int n, int n2, int n3) {
        this._verifySharing();
        int[] arrn2 = arrn;
        if (this._intern) {
            arrn2 = InternCache.instance.intern((String)arrn);
        }
        int n4 = this._findOffsetForAdd(this.calcHash(n, n2, n3));
        arrn = this._hashArea;
        arrn[n4] = n;
        arrn[n4 + 1] = n2;
        arrn[n4 + 2] = n3;
        arrn[n4 + 3] = 3;
        this._names[n4 >> 2] = arrn2;
        ++this._count;
        return arrn2;
    }

    public String addName(String arrn, int[] arrn2, int n) {
        this._verifySharing();
        int[] arrn3 = arrn;
        if (this._intern) {
            arrn3 = InternCache.instance.intern((String)arrn);
        }
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    int n2 = this.calcHash(arrn2, n);
                    int n3 = this._findOffsetForAdd(n2);
                    this._hashArea[n3] = n2;
                    n2 = this._appendLongName(arrn2, n);
                    arrn = this._hashArea;
                    arrn[n3 + 1] = n2;
                    arrn[n3 + 3] = n;
                    n = n3;
                } else {
                    n = this._findOffsetForAdd(this.calcHash(arrn2[0], arrn2[1], arrn2[2]));
                    arrn = this._hashArea;
                    arrn[n] = arrn2[0];
                    arrn[n + 1] = arrn2[1];
                    arrn[n + 2] = arrn2[2];
                    arrn[n + 3] = 3;
                }
            } else {
                n = this._findOffsetForAdd(this.calcHash(arrn2[0], arrn2[1]));
                arrn = this._hashArea;
                arrn[n] = arrn2[0];
                arrn[n + 1] = arrn2[1];
                arrn[n + 3] = 2;
            }
        } else {
            n = this._findOffsetForAdd(this.calcHash(arrn2[0]));
            arrn = this._hashArea;
            arrn[n] = arrn2[0];
            arrn[n + 3] = 1;
        }
        this._names[n >> 2] = arrn3;
        ++this._count;
        return arrn3;
    }

    public int bucketCount() {
        return this._hashSize;
    }

    public int calcHash(int n) {
        n ^= this._seed;
        n += n >>> 16;
        n ^= n << 3;
        return n + (n >>> 12);
    }

    public int calcHash(int n, int n2) {
        n += n >>> 15;
        n = (n ^ n >>> 9) + n2 * 33 ^ this._seed;
        n += n >>> 16;
        n ^= n >>> 4;
        return n + (n << 3);
    }

    public int calcHash(int n, int n2, int n3) {
        n ^= this._seed;
        n = ((n + (n >>> 9)) * 31 + n2) * 33;
        n = n + (n >>> 15) ^ n3;
        n += n >>> 4;
        n += n >>> 15;
        return n ^ n << 9;
    }

    public int calcHash(int[] arrn, int n) {
        if (n < 4) throw new IllegalArgumentException();
        int n2 = arrn[0] ^ this._seed;
        n2 = n2 + (n2 >>> 9) + arrn[1];
        n2 = (n2 + (n2 >>> 15)) * 33 ^ arrn[2];
        int n3 = n2 + (n2 >>> 4);
        n2 = 3;
        do {
            if (n2 >= n) {
                n = n3 * 65599;
                n += n >>> 19;
                return n << 5 ^ n;
            }
            int n4 = arrn[n2];
            n3 += n4 ^ n4 >> 21;
            ++n2;
        } while (true);
    }

    public String findName(int n) {
        int n2;
        int[] arrn = this._hashArea;
        int n3 = this._calcOffset(this.calcHash(n));
        int n4 = arrn[n3 + 3];
        if (n4 == 1) {
            if (arrn[n3] == n) {
                return this._names[n3 >> 2];
            }
        } else if (n4 == 0) {
            return null;
        }
        if ((n2 = arrn[(n4 = this._secondaryStart + (n3 >> 3 << 2)) + 3]) == 1) {
            if (arrn[n4] != n) return this._findSecondary(n3, n);
            return this._names[n4 >> 2];
        }
        if (n2 != 0) return this._findSecondary(n3, n);
        return null;
    }

    public String findName(int n, int n2) {
        int n3;
        int[] arrn = this._hashArea;
        int n4 = this._calcOffset(this.calcHash(n, n2));
        int n5 = arrn[n4 + 3];
        if (n5 == 2) {
            if (n == arrn[n4] && n2 == arrn[n4 + 1]) {
                return this._names[n4 >> 2];
            }
        } else if (n5 == 0) {
            return null;
        }
        if ((n5 = arrn[(n3 = this._secondaryStart + (n4 >> 3 << 2)) + 3]) == 2) {
            if (n != arrn[n3]) return this._findSecondary(n4, n, n2);
            if (n2 != arrn[n3 + 1]) return this._findSecondary(n4, n, n2);
            return this._names[n3 >> 2];
        }
        if (n5 != 0) return this._findSecondary(n4, n, n2);
        return null;
    }

    public String findName(int n, int n2, int n3) {
        int n4;
        int[] arrn = this._hashArea;
        int n5 = this._calcOffset(this.calcHash(n, n2, n3));
        int n6 = arrn[n5 + 3];
        if (n6 == 3) {
            if (n == arrn[n5] && arrn[n5 + 1] == n2 && arrn[n5 + 2] == n3) {
                return this._names[n5 >> 2];
            }
        } else if (n6 == 0) {
            return null;
        }
        if ((n6 = arrn[(n4 = this._secondaryStart + (n5 >> 3 << 2)) + 3]) == 3) {
            if (n != arrn[n4]) return this._findSecondary(n5, n, n2, n3);
            if (arrn[n4 + 1] != n2) return this._findSecondary(n5, n, n2, n3);
            if (arrn[n4 + 2] != n3) return this._findSecondary(n5, n, n2, n3);
            return this._names[n4 >> 2];
        }
        if (n6 != 0) return this._findSecondary(n5, n, n2, n3);
        return null;
    }

    public String findName(int[] arrn, int n) {
        if (n < 4) {
            if (n == 1) return this.findName(arrn[0]);
            if (n == 2) return this.findName(arrn[0], arrn[1]);
            if (n == 3) return this.findName(arrn[0], arrn[1], arrn[2]);
            return "";
        }
        int n2 = this.calcHash(arrn, n);
        int n3 = this._calcOffset(n2);
        int[] arrn2 = this._hashArea;
        int n4 = arrn2[n3 + 3];
        if (n2 == arrn2[n3] && n4 == n && this._verifyLongName(arrn, n, arrn2[n3 + 1])) {
            return this._names[n3 >> 2];
        }
        if (n4 == 0) {
            return null;
        }
        n4 = this._secondaryStart + (n3 >> 3 << 2);
        int n5 = arrn2[n4 + 3];
        if (n2 != arrn2[n4]) return this._findSecondary(n3, n2, arrn, n);
        if (n5 != n) return this._findSecondary(n3, n2, arrn, n);
        if (!this._verifyLongName(arrn, n, arrn2[n4 + 1])) return this._findSecondary(n3, n2, arrn, n);
        return this._names[n4 >> 2];
    }

    public int hashSeed() {
        return this._seed;
    }

    public ByteQuadsCanonicalizer makeChild(int n) {
        return new ByteQuadsCanonicalizer(this, JsonFactory.Feature.INTERN_FIELD_NAMES.enabledIn(n), this._seed, JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(n), this._tableInfo.get());
    }

    public boolean maybeDirty() {
        return this._hashShared ^ true;
    }

    public int primaryCount() {
        int n = this._secondaryStart;
        int n2 = 0;
        int n3 = 3;
        while (n3 < n) {
            int n4 = n2;
            if (this._hashArea[n3] != 0) {
                n4 = n2 + 1;
            }
            n3 += 4;
            n2 = n4;
        }
        return n2;
    }

    public void release() {
        if (this._parent == null) return;
        if (!this.maybeDirty()) return;
        this._parent.mergeChild(new TableInfo(this));
        this._hashShared = true;
    }

    public int secondaryCount() {
        int n = this._secondaryStart + 3;
        int n2 = this._tertiaryStart;
        int n3 = 0;
        while (n < n2) {
            int n4 = n3;
            if (this._hashArea[n] != 0) {
                n4 = n3 + 1;
            }
            n += 4;
            n3 = n4;
        }
        return n3;
    }

    public int size() {
        AtomicReference<TableInfo> atomicReference = this._tableInfo;
        if (atomicReference == null) return this._count;
        return atomicReference.get().count;
    }

    public int spilloverCount() {
        return this._spilloverEnd - this._spilloverStart() >> 2;
    }

    public int tertiaryCount() {
        int n = this._tertiaryStart + 3;
        int n2 = this._hashSize;
        int n3 = 0;
        int n4 = n;
        while (n4 < n2 + n) {
            int n5 = n3;
            if (this._hashArea[n4] != 0) {
                n5 = n3 + 1;
            }
            n4 += 4;
            n3 = n5;
        }
        return n3;
    }

    public String toString() {
        int n = this.primaryCount();
        int n2 = this.secondaryCount();
        int n3 = this.tertiaryCount();
        int n4 = this.spilloverCount();
        int n5 = this.totalCount();
        return String.format("[%s: size=%d, hashSize=%d, %d/%d/%d/%d pri/sec/ter/spill (=%s), total:%d]", this.getClass().getName(), this._count, this._hashSize, n, n2, n3, n4, n + n2 + n3 + n4, n5);
    }

    public int totalCount() {
        int n = this._hashSize;
        int n2 = 3;
        int n3 = 0;
        while (n2 < n << 3) {
            int n4 = n3;
            if (this._hashArea[n2] != 0) {
                n4 = n3 + 1;
            }
            n2 += 4;
            n3 = n4;
        }
        return n3;
    }

    private static final class TableInfo {
        public final int count;
        public final int longNameOffset;
        public final int[] mainHash;
        public final String[] names;
        public final int size;
        public final int spilloverEnd;
        public final int tertiaryShift;

        public TableInfo(int n, int n2, int n3, int[] arrn, String[] arrstring, int n4, int n5) {
            this.size = n;
            this.count = n2;
            this.tertiaryShift = n3;
            this.mainHash = arrn;
            this.names = arrstring;
            this.spilloverEnd = n4;
            this.longNameOffset = n5;
        }

        public TableInfo(ByteQuadsCanonicalizer byteQuadsCanonicalizer) {
            this.size = byteQuadsCanonicalizer._hashSize;
            this.count = byteQuadsCanonicalizer._count;
            this.tertiaryShift = byteQuadsCanonicalizer._tertiaryShift;
            this.mainHash = byteQuadsCanonicalizer._hashArea;
            this.names = byteQuadsCanonicalizer._names;
            this.spilloverEnd = byteQuadsCanonicalizer._spilloverEnd;
            this.longNameOffset = byteQuadsCanonicalizer._longNameOffset;
        }

        public static TableInfo createInitial(int n) {
            int n2 = n << 3;
            return new TableInfo(n, 0, ByteQuadsCanonicalizer._calcTertiaryShift(n), new int[n2], new String[n << 1], n2 - n, n2);
        }
    }

}

