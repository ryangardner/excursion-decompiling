/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.recyclerview.widget;

import androidx.core.util.Pools;
import androidx.recyclerview.widget.OpReorderer;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AdapterHelper
implements OpReorderer.Callback {
    private static final boolean DEBUG = false;
    static final int POSITION_TYPE_INVISIBLE = 0;
    static final int POSITION_TYPE_NEW_OR_LAID_OUT = 1;
    private static final String TAG = "AHT";
    final Callback mCallback;
    final boolean mDisableRecycler;
    private int mExistingUpdateTypes = 0;
    Runnable mOnItemProcessedCallback;
    final OpReorderer mOpReorderer;
    final ArrayList<UpdateOp> mPendingUpdates = new ArrayList();
    final ArrayList<UpdateOp> mPostponedList = new ArrayList();
    private Pools.Pool<UpdateOp> mUpdateOpPool = new Pools.SimplePool<UpdateOp>(30);

    AdapterHelper(Callback callback) {
        this(callback, false);
    }

    AdapterHelper(Callback callback, boolean bl) {
        this.mCallback = callback;
        this.mDisableRecycler = bl;
        this.mOpReorderer = new OpReorderer(this);
    }

    private void applyAdd(UpdateOp updateOp) {
        this.postponeAndUpdateViewHolders(updateOp);
    }

    private void applyMove(UpdateOp updateOp) {
        this.postponeAndUpdateViewHolders(updateOp);
    }

    private void applyRemove(UpdateOp updateOp) {
        int n = updateOp.positionStart;
        int n2 = updateOp.positionStart + updateOp.itemCount;
        int n3 = -1;
        int n4 = 0;
        for (int i = updateOp.positionStart; i < n2; ++i) {
            int n5;
            if (this.mCallback.findViewHolder(i) == null && !this.canFindInPreLayout(i)) {
                if (n3 == 1) {
                    this.postponeAndUpdateViewHolders(this.obtainUpdateOp(2, n, n4, null));
                    n3 = 1;
                } else {
                    n3 = 0;
                }
                int n6 = 0;
                n5 = n3;
                n3 = n6;
            } else {
                if (n3 == 0) {
                    this.dispatchAndUpdateViewHolders(this.obtainUpdateOp(2, n, n4, null));
                    n5 = 1;
                } else {
                    n5 = 0;
                }
                n3 = 1;
            }
            if (n5 != 0) {
                i -= n4;
                n2 -= n4;
                n5 = 1;
            } else {
                n5 = n4 + 1;
            }
            n4 = n5;
        }
        UpdateOp updateOp2 = updateOp;
        if (n4 != updateOp.itemCount) {
            this.recycleUpdateOp(updateOp);
            updateOp2 = this.obtainUpdateOp(2, n, n4, null);
        }
        if (n3 == 0) {
            this.dispatchAndUpdateViewHolders(updateOp2);
            return;
        }
        this.postponeAndUpdateViewHolders(updateOp2);
    }

    private void applyUpdate(UpdateOp updateOp) {
        int n = updateOp.positionStart;
        int n2 = updateOp.positionStart;
        int n3 = updateOp.itemCount;
        int n4 = -1;
        int n5 = 0;
        for (int i = updateOp.positionStart; i < n2 + n3; ++i) {
            int n6;
            int n7;
            if (this.mCallback.findViewHolder(i) == null && !this.canFindInPreLayout(i)) {
                n7 = n;
                n6 = n5;
                if (n4 == 1) {
                    this.postponeAndUpdateViewHolders(this.obtainUpdateOp(4, n, n5, updateOp.payload));
                    n7 = i;
                    n6 = 0;
                }
                n5 = 0;
                n = n7;
                n7 = n6;
            } else {
                n6 = n;
                n7 = n5;
                if (n4 == 0) {
                    this.dispatchAndUpdateViewHolders(this.obtainUpdateOp(4, n, n5, updateOp.payload));
                    n6 = i;
                    n7 = 0;
                }
                n5 = 1;
                n = n6;
            }
            n6 = n7 + 1;
            n4 = n5;
            n5 = n6;
        }
        Object object = updateOp;
        if (n5 != updateOp.itemCount) {
            object = updateOp.payload;
            this.recycleUpdateOp(updateOp);
            object = this.obtainUpdateOp(4, n, n5, object);
        }
        if (n4 == 0) {
            this.dispatchAndUpdateViewHolders((UpdateOp)object);
            return;
        }
        this.postponeAndUpdateViewHolders((UpdateOp)object);
    }

    private boolean canFindInPreLayout(int n) {
        int n2 = this.mPostponedList.size();
        int n3 = 0;
        while (n3 < n2) {
            UpdateOp updateOp = this.mPostponedList.get(n3);
            if (updateOp.cmd == 8) {
                if (this.findPositionOffset(updateOp.itemCount, n3 + 1) == n) {
                    return true;
                }
            } else if (updateOp.cmd == 1) {
                int n4 = updateOp.positionStart;
                int n5 = updateOp.itemCount;
                for (int i = updateOp.positionStart; i < n4 + n5; ++i) {
                    if (this.findPositionOffset(i, n3 + 1) != n) continue;
                    return true;
                }
            }
            ++n3;
        }
        return false;
    }

    private void dispatchAndUpdateViewHolders(UpdateOp updateOp) {
        int n;
        if (updateOp.cmd == 1) throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        if (updateOp.cmd == 8) throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        int n2 = this.updatePositionWithPostponed(updateOp.positionStart, updateOp.cmd);
        int n3 = updateOp.positionStart;
        int n4 = updateOp.cmd;
        if (n4 != 2) {
            if (n4 != 4) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("op should be remove or update.");
                stringBuilder.append(updateOp);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            n = 1;
        } else {
            n = 0;
        }
        int n5 = 1;
        n4 = 1;
        do {
            Object object;
            if (n5 >= updateOp.itemCount) {
                object = updateOp.payload;
                this.recycleUpdateOp(updateOp);
                if (n4 <= 0) return;
                updateOp = this.obtainUpdateOp(updateOp.cmd, n2, n4, object);
                this.dispatchFirstPassAndUpdateViewHolders(updateOp, n3);
                this.recycleUpdateOp(updateOp);
                return;
            }
            int n6 = this.updatePositionWithPostponed(updateOp.positionStart + n * n5, updateOp.cmd);
            int n7 = updateOp.cmd;
            n7 = !(n7 != 2 ? n7 == 4 && n6 == n2 + 1 : n6 == n2) ? 0 : 1;
            if (n7 != 0) {
                ++n4;
            } else {
                object = this.obtainUpdateOp(updateOp.cmd, n2, n4, updateOp.payload);
                this.dispatchFirstPassAndUpdateViewHolders((UpdateOp)object, n3);
                this.recycleUpdateOp((UpdateOp)object);
                n2 = n3;
                if (updateOp.cmd == 4) {
                    n2 = n3 + n4;
                }
                n7 = n6;
                n4 = 1;
                n3 = n2;
                n2 = n7;
            }
            ++n5;
        } while (true);
    }

    private void postponeAndUpdateViewHolders(UpdateOp updateOp) {
        this.mPostponedList.add(updateOp);
        int n = updateOp.cmd;
        if (n == 1) {
            this.mCallback.offsetPositionsForAdd(updateOp.positionStart, updateOp.itemCount);
            return;
        }
        if (n == 2) {
            this.mCallback.offsetPositionsForRemovingLaidOutOrNewView(updateOp.positionStart, updateOp.itemCount);
            return;
        }
        if (n == 4) {
            this.mCallback.markViewHoldersUpdated(updateOp.positionStart, updateOp.itemCount, updateOp.payload);
            return;
        }
        if (n == 8) {
            this.mCallback.offsetPositionsForMove(updateOp.positionStart, updateOp.itemCount);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown update op type for ");
        stringBuilder.append(updateOp);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private int updatePositionWithPostponed(int n, int n2) {
        UpdateOp updateOp;
        int n3 = n;
        for (int i = this.mPostponedList.size() - 1; i >= 0; --i) {
            updateOp = this.mPostponedList.get(i);
            if (updateOp.cmd == 8) {
                int n4;
                if (updateOp.positionStart < updateOp.itemCount) {
                    n4 = updateOp.positionStart;
                    n = updateOp.itemCount;
                } else {
                    n4 = updateOp.itemCount;
                    n = updateOp.positionStart;
                }
                if (n3 >= n4 && n3 <= n) {
                    if (n4 == updateOp.positionStart) {
                        if (n2 == 1) {
                            ++updateOp.itemCount;
                        } else if (n2 == 2) {
                            --updateOp.itemCount;
                        }
                        n = n3 + 1;
                    } else {
                        if (n2 == 1) {
                            ++updateOp.positionStart;
                        } else if (n2 == 2) {
                            --updateOp.positionStart;
                        }
                        n = n3 - 1;
                    }
                } else {
                    n = n3;
                    if (n3 < updateOp.positionStart) {
                        if (n2 == 1) {
                            ++updateOp.positionStart;
                            ++updateOp.itemCount;
                            n = n3;
                        } else {
                            n = n3;
                            if (n2 == 2) {
                                --updateOp.positionStart;
                                --updateOp.itemCount;
                                n = n3;
                            }
                        }
                    }
                }
            } else if (updateOp.positionStart <= n3) {
                if (updateOp.cmd == 1) {
                    n = n3 - updateOp.itemCount;
                } else {
                    n = n3;
                    if (updateOp.cmd == 2) {
                        n = n3 + updateOp.itemCount;
                    }
                }
            } else if (n2 == 1) {
                ++updateOp.positionStart;
                n = n3;
            } else {
                n = n3;
                if (n2 == 2) {
                    --updateOp.positionStart;
                    n = n3;
                }
            }
            n3 = n;
        }
        n = this.mPostponedList.size() - 1;
        while (n >= 0) {
            updateOp = this.mPostponedList.get(n);
            if (updateOp.cmd == 8) {
                if (updateOp.itemCount == updateOp.positionStart || updateOp.itemCount < 0) {
                    this.mPostponedList.remove(n);
                    this.recycleUpdateOp(updateOp);
                }
            } else if (updateOp.itemCount <= 0) {
                this.mPostponedList.remove(n);
                this.recycleUpdateOp(updateOp);
            }
            --n;
        }
        return n3;
    }

    AdapterHelper addUpdateOp(UpdateOp ... arrupdateOp) {
        Collections.addAll(this.mPendingUpdates, arrupdateOp);
        return this;
    }

    public int applyPendingUpdatesToPosition(int n) {
        int n2 = this.mPendingUpdates.size();
        int n3 = 0;
        int n4 = n;
        while (n3 < n2) {
            UpdateOp updateOp = this.mPendingUpdates.get(n3);
            n = updateOp.cmd;
            if (n != 1) {
                if (n != 2) {
                    if (n != 8) {
                        n = n4;
                    } else if (updateOp.positionStart == n4) {
                        n = updateOp.itemCount;
                    } else {
                        int n5 = n4;
                        if (updateOp.positionStart < n4) {
                            n5 = n4 - 1;
                        }
                        n = n5;
                        if (updateOp.itemCount <= n5) {
                            n = n5 + 1;
                        }
                    }
                } else {
                    n = n4;
                    if (updateOp.positionStart <= n4) {
                        if (updateOp.positionStart + updateOp.itemCount > n4) {
                            return -1;
                        }
                        n = n4 - updateOp.itemCount;
                    }
                }
            } else {
                n = n4;
                if (updateOp.positionStart <= n4) {
                    n = n4 + updateOp.itemCount;
                }
            }
            ++n3;
            n4 = n;
        }
        return n4;
    }

    void consumePostponedUpdates() {
        int n = this.mPostponedList.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.recycleUpdateOpsAndClearList(this.mPostponedList);
                this.mExistingUpdateTypes = 0;
                return;
            }
            this.mCallback.onDispatchSecondPass(this.mPostponedList.get(n2));
            ++n2;
        } while (true);
    }

    void consumeUpdatesInOnePass() {
        this.consumePostponedUpdates();
        int n = this.mPendingUpdates.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.recycleUpdateOpsAndClearList(this.mPendingUpdates);
                this.mExistingUpdateTypes = 0;
                return;
            }
            Object object = this.mPendingUpdates.get(n2);
            int n3 = ((UpdateOp)object).cmd;
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 4) {
                        if (n3 == 8) {
                            this.mCallback.onDispatchSecondPass((UpdateOp)object);
                            this.mCallback.offsetPositionsForMove(((UpdateOp)object).positionStart, ((UpdateOp)object).itemCount);
                        }
                    } else {
                        this.mCallback.onDispatchSecondPass((UpdateOp)object);
                        this.mCallback.markViewHoldersUpdated(((UpdateOp)object).positionStart, ((UpdateOp)object).itemCount, ((UpdateOp)object).payload);
                    }
                } else {
                    this.mCallback.onDispatchSecondPass((UpdateOp)object);
                    this.mCallback.offsetPositionsForRemovingInvisible(((UpdateOp)object).positionStart, ((UpdateOp)object).itemCount);
                }
            } else {
                this.mCallback.onDispatchSecondPass((UpdateOp)object);
                this.mCallback.offsetPositionsForAdd(((UpdateOp)object).positionStart, ((UpdateOp)object).itemCount);
            }
            object = this.mOnItemProcessedCallback;
            if (object != null) {
                object.run();
            }
            ++n2;
        } while (true);
    }

    void dispatchFirstPassAndUpdateViewHolders(UpdateOp updateOp, int n) {
        this.mCallback.onDispatchFirstPass(updateOp);
        int n2 = updateOp.cmd;
        if (n2 != 2) {
            if (n2 != 4) throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
            this.mCallback.markViewHoldersUpdated(n, updateOp.itemCount, updateOp.payload);
            return;
        }
        this.mCallback.offsetPositionsForRemovingInvisible(n, updateOp.itemCount);
    }

    int findPositionOffset(int n) {
        return this.findPositionOffset(n, 0);
    }

    int findPositionOffset(int n, int n2) {
        int n3 = this.mPostponedList.size();
        int n4 = n2;
        n2 = n;
        while (n4 < n3) {
            UpdateOp updateOp = this.mPostponedList.get(n4);
            if (updateOp.cmd == 8) {
                if (updateOp.positionStart == n2) {
                    n = updateOp.itemCount;
                } else {
                    int n5 = n2;
                    if (updateOp.positionStart < n2) {
                        n5 = n2 - 1;
                    }
                    n = n5;
                    if (updateOp.itemCount <= n5) {
                        n = n5 + 1;
                    }
                }
            } else {
                n = n2;
                if (updateOp.positionStart <= n2) {
                    if (updateOp.cmd == 2) {
                        if (n2 < updateOp.positionStart + updateOp.itemCount) {
                            return -1;
                        }
                        n = n2 - updateOp.itemCount;
                    } else {
                        n = n2;
                        if (updateOp.cmd == 1) {
                            n = n2 + updateOp.itemCount;
                        }
                    }
                }
            }
            ++n4;
            n2 = n;
        }
        return n2;
    }

    boolean hasAnyUpdateTypes(int n) {
        if ((n & this.mExistingUpdateTypes) == 0) return false;
        return true;
    }

    boolean hasPendingUpdates() {
        if (this.mPendingUpdates.size() <= 0) return false;
        return true;
    }

    boolean hasUpdates() {
        if (this.mPostponedList.isEmpty()) return false;
        if (this.mPendingUpdates.isEmpty()) return false;
        return true;
    }

    @Override
    public UpdateOp obtainUpdateOp(int n, int n2, int n3, Object object) {
        UpdateOp updateOp = this.mUpdateOpPool.acquire();
        if (updateOp == null) {
            return new UpdateOp(n, n2, n3, object);
        }
        updateOp.cmd = n;
        updateOp.positionStart = n2;
        updateOp.itemCount = n3;
        updateOp.payload = object;
        return updateOp;
    }

    boolean onItemRangeChanged(int n, int n2, Object object) {
        boolean bl = false;
        if (n2 < 1) {
            return false;
        }
        this.mPendingUpdates.add(this.obtainUpdateOp(4, n, n2, object));
        this.mExistingUpdateTypes |= 4;
        if (this.mPendingUpdates.size() != 1) return bl;
        return true;
    }

    boolean onItemRangeInserted(int n, int n2) {
        boolean bl = false;
        if (n2 < 1) {
            return false;
        }
        this.mPendingUpdates.add(this.obtainUpdateOp(1, n, n2, null));
        this.mExistingUpdateTypes |= 1;
        if (this.mPendingUpdates.size() != 1) return bl;
        return true;
    }

    boolean onItemRangeMoved(int n, int n2, int n3) {
        boolean bl = false;
        if (n == n2) {
            return false;
        }
        if (n3 != 1) throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
        this.mPendingUpdates.add(this.obtainUpdateOp(8, n, n2, null));
        this.mExistingUpdateTypes |= 8;
        if (this.mPendingUpdates.size() != 1) return bl;
        return true;
    }

    boolean onItemRangeRemoved(int n, int n2) {
        boolean bl = false;
        if (n2 < 1) {
            return false;
        }
        this.mPendingUpdates.add(this.obtainUpdateOp(2, n, n2, null));
        this.mExistingUpdateTypes |= 2;
        if (this.mPendingUpdates.size() != 1) return bl;
        return true;
    }

    void preProcess() {
        this.mOpReorderer.reorderOps(this.mPendingUpdates);
        int n = this.mPendingUpdates.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.mPendingUpdates.clear();
                return;
            }
            Object object = this.mPendingUpdates.get(n2);
            int n3 = ((UpdateOp)object).cmd;
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 4) {
                        if (n3 == 8) {
                            this.applyMove((UpdateOp)object);
                        }
                    } else {
                        this.applyUpdate((UpdateOp)object);
                    }
                } else {
                    this.applyRemove((UpdateOp)object);
                }
            } else {
                this.applyAdd((UpdateOp)object);
            }
            object = this.mOnItemProcessedCallback;
            if (object != null) {
                object.run();
            }
            ++n2;
        } while (true);
    }

    @Override
    public void recycleUpdateOp(UpdateOp updateOp) {
        if (this.mDisableRecycler) return;
        updateOp.payload = null;
        this.mUpdateOpPool.release(updateOp);
    }

    void recycleUpdateOpsAndClearList(List<UpdateOp> list) {
        int n = list.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                list.clear();
                return;
            }
            this.recycleUpdateOp(list.get(n2));
            ++n2;
        } while (true);
    }

    void reset() {
        this.recycleUpdateOpsAndClearList(this.mPendingUpdates);
        this.recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    static interface Callback {
        public RecyclerView.ViewHolder findViewHolder(int var1);

        public void markViewHoldersUpdated(int var1, int var2, Object var3);

        public void offsetPositionsForAdd(int var1, int var2);

        public void offsetPositionsForMove(int var1, int var2);

        public void offsetPositionsForRemovingInvisible(int var1, int var2);

        public void offsetPositionsForRemovingLaidOutOrNewView(int var1, int var2);

        public void onDispatchFirstPass(UpdateOp var1);

        public void onDispatchSecondPass(UpdateOp var1);
    }

    static class UpdateOp {
        static final int ADD = 1;
        static final int MOVE = 8;
        static final int POOL_SIZE = 30;
        static final int REMOVE = 2;
        static final int UPDATE = 4;
        int cmd;
        int itemCount;
        Object payload;
        int positionStart;

        UpdateOp(int n, int n2, int n3, Object object) {
            this.cmd = n;
            this.positionStart = n2;
            this.itemCount = n3;
            this.payload = object;
        }

        String cmdToString() {
            int n = this.cmd;
            if (n == 1) return "add";
            if (n == 2) return "rm";
            if (n == 4) return "up";
            if (n == 8) return "mv";
            return "??";
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) return false;
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (UpdateOp)object;
            int n = this.cmd;
            if (n != ((UpdateOp)object).cmd) {
                return false;
            }
            if (n == 8 && Math.abs(this.itemCount - this.positionStart) == 1 && this.itemCount == ((UpdateOp)object).positionStart && this.positionStart == ((UpdateOp)object).itemCount) {
                return true;
            }
            if (this.itemCount != ((UpdateOp)object).itemCount) {
                return false;
            }
            if (this.positionStart != ((UpdateOp)object).positionStart) {
                return false;
            }
            Object object2 = this.payload;
            if (object2 != null) {
                if (object2.equals(((UpdateOp)object).payload)) return true;
                return false;
            }
            if (((UpdateOp)object).payload == null) return true;
            return false;
        }

        public int hashCode() {
            return (this.cmd * 31 + this.positionStart) * 31 + this.itemCount;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append("[");
            stringBuilder.append(this.cmdToString());
            stringBuilder.append(",s:");
            stringBuilder.append(this.positionStart);
            stringBuilder.append("c:");
            stringBuilder.append(this.itemCount);
            stringBuilder.append(",p:");
            stringBuilder.append(this.payload);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

}

