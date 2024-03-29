/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.recyclerview.widget;

import androidx.recyclerview.widget.AdapterHelper;
import java.util.List;

class OpReorderer {
    final Callback mCallback;

    OpReorderer(Callback callback) {
        this.mCallback = callback;
    }

    private int getLastMoveOutOfOrder(List<AdapterHelper.UpdateOp> list) {
        int n = list.size() - 1;
        boolean bl = false;
        while (n >= 0) {
            boolean bl2;
            if (list.get((int)n).cmd == 8) {
                bl2 = bl;
                if (bl) {
                    return n;
                }
            } else {
                bl2 = true;
            }
            --n;
            bl = bl2;
        }
        return -1;
    }

    private void swapMoveAdd(List<AdapterHelper.UpdateOp> list, int n, AdapterHelper.UpdateOp updateOp, int n2, AdapterHelper.UpdateOp updateOp2) {
        int n3 = updateOp.itemCount < updateOp2.positionStart ? -1 : 0;
        int n4 = n3;
        if (updateOp.positionStart < updateOp2.positionStart) {
            n4 = n3 + 1;
        }
        if (updateOp2.positionStart <= updateOp.positionStart) {
            updateOp.positionStart += updateOp2.itemCount;
        }
        if (updateOp2.positionStart <= updateOp.itemCount) {
            updateOp.itemCount += updateOp2.itemCount;
        }
        updateOp2.positionStart += n4;
        list.set(n, updateOp2);
        list.set(n2, updateOp);
    }

    private void swapMoveOp(List<AdapterHelper.UpdateOp> list, int n, int n2) {
        AdapterHelper.UpdateOp updateOp = list.get(n);
        AdapterHelper.UpdateOp updateOp2 = list.get(n2);
        int n3 = updateOp2.cmd;
        if (n3 == 1) {
            this.swapMoveAdd(list, n, updateOp, n2, updateOp2);
            return;
        }
        if (n3 == 2) {
            this.swapMoveRemove(list, n, updateOp, n2, updateOp2);
            return;
        }
        if (n3 != 4) {
            return;
        }
        this.swapMoveUpdate(list, n, updateOp, n2, updateOp2);
    }

    void reorderOps(List<AdapterHelper.UpdateOp> list) {
        int n;
        while ((n = this.getLastMoveOutOfOrder(list)) != -1) {
            this.swapMoveOp(list, n, n + 1);
        }
    }

    /*
     * Unable to fully structure code
     */
    void swapMoveRemove(List<AdapterHelper.UpdateOp> var1_1, int var2_2, AdapterHelper.UpdateOp var3_3, int var4_4, AdapterHelper.UpdateOp var5_5) {
        block23 : {
            block21 : {
                block22 : {
                    var6_6 = var3_3.positionStart;
                    var7_7 = var3_3.itemCount;
                    var8_8 = false;
                    if (var6_6 >= var7_7) break block21;
                    if (var5_5.positionStart != var3_3.positionStart || var5_5.itemCount != var3_3.itemCount - var3_3.positionStart) break block22;
                    var6_6 = 0;
                    ** GOTO lbl14
                }
                var6_6 = 0;
                break block23;
            }
            if (var5_5.positionStart == var3_3.itemCount + 1 && var5_5.itemCount == var3_3.positionStart - var3_3.itemCount) {
                var6_6 = 1;
lbl14: // 2 sources:
                var8_8 = true;
            } else {
                var6_6 = 1;
            }
        }
        if (var3_3.itemCount < var5_5.positionStart) {
            --var5_5.positionStart;
        } else if (var3_3.itemCount < var5_5.positionStart + var5_5.itemCount) {
            --var5_5.itemCount;
            var3_3.cmd = 2;
            var3_3.itemCount = 1;
            if (var5_5.itemCount != 0) return;
            var1_1.remove(var4_4);
            this.mCallback.recycleUpdateOp(var5_5);
            return;
        }
        var9_9 = var3_3.positionStart;
        var7_7 = var5_5.positionStart++;
        var10_10 = null;
        if (var9_9 > var7_7 && var3_3.positionStart < var5_5.positionStart + var5_5.itemCount) {
            var9_9 = var5_5.positionStart;
            var7_7 = var5_5.itemCount;
            var11_11 = var3_3.positionStart;
            var10_10 = this.mCallback.obtainUpdateOp(2, var3_3.positionStart + 1, var9_9 + var7_7 - var11_11, null);
            var5_5.itemCount = var3_3.positionStart - var5_5.positionStart;
        }
        if (var8_8) {
            var1_1.set(var2_2, var5_5);
            var1_1.remove(var4_4);
            this.mCallback.recycleUpdateOp(var3_3);
            return;
        }
        if (var6_6 != 0) {
            if (var10_10 != null) {
                if (var3_3.positionStart > var10_10.positionStart) {
                    var3_3.positionStart -= var10_10.itemCount;
                }
                if (var3_3.itemCount > var10_10.positionStart) {
                    var3_3.itemCount -= var10_10.itemCount;
                }
            }
            if (var3_3.positionStart > var5_5.positionStart) {
                var3_3.positionStart -= var5_5.itemCount;
            }
            if (var3_3.itemCount > var5_5.positionStart) {
                var3_3.itemCount -= var5_5.itemCount;
            }
        } else {
            if (var10_10 != null) {
                if (var3_3.positionStart >= var10_10.positionStart) {
                    var3_3.positionStart -= var10_10.itemCount;
                }
                if (var3_3.itemCount >= var10_10.positionStart) {
                    var3_3.itemCount -= var10_10.itemCount;
                }
            }
            if (var3_3.positionStart >= var5_5.positionStart) {
                var3_3.positionStart -= var5_5.itemCount;
            }
            if (var3_3.itemCount >= var5_5.positionStart) {
                var3_3.itemCount -= var5_5.itemCount;
            }
        }
        var1_1.set(var2_2, var5_5);
        if (var3_3.positionStart != var3_3.itemCount) {
            var1_1.set(var4_4, var3_3);
        } else {
            var1_1.remove(var4_4);
        }
        if (var10_10 == null) return;
        var1_1.add(var2_2, var10_10);
    }

    void swapMoveUpdate(List<AdapterHelper.UpdateOp> list, int n, AdapterHelper.UpdateOp updateOp, int n2, AdapterHelper.UpdateOp updateOp2) {
        AdapterHelper.UpdateOp updateOp3;
        int n3 = updateOp.itemCount;
        int n4 = updateOp2.positionStart--;
        AdapterHelper.UpdateOp updateOp4 = null;
        if (n3 >= n4 && updateOp.itemCount < updateOp2.positionStart + updateOp2.itemCount) {
            --updateOp2.itemCount;
            updateOp3 = this.mCallback.obtainUpdateOp(4, updateOp.positionStart, 1, updateOp2.payload);
        } else {
            updateOp3 = null;
        }
        if (updateOp.positionStart <= updateOp2.positionStart) {
            ++updateOp2.positionStart;
        } else if (updateOp.positionStart < updateOp2.positionStart + updateOp2.itemCount) {
            n3 = updateOp2.positionStart + updateOp2.itemCount - updateOp.positionStart;
            updateOp4 = this.mCallback.obtainUpdateOp(4, updateOp.positionStart + 1, n3, updateOp2.payload);
            updateOp2.itemCount -= n3;
        }
        list.set(n2, updateOp);
        if (updateOp2.itemCount > 0) {
            list.set(n, updateOp2);
        } else {
            list.remove(n);
            this.mCallback.recycleUpdateOp(updateOp2);
        }
        if (updateOp3 != null) {
            list.add(n, updateOp3);
        }
        if (updateOp4 == null) return;
        list.add(n, updateOp4);
    }

    static interface Callback {
        public AdapterHelper.UpdateOp obtainUpdateOp(int var1, int var2, int var3, Object var4);

        public void recycleUpdateOp(AdapterHelper.UpdateOp var1);
    }

}

