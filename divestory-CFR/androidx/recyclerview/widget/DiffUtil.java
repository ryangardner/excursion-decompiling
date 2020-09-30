/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.recyclerview.widget;

import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.BatchingListUpdateCallback;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class DiffUtil {
    private static final Comparator<Snake> SNAKE_COMPARATOR = new Comparator<Snake>(){

        @Override
        public int compare(Snake snake, Snake snake2) {
            int n;
            int n2 = n = snake.x - snake2.x;
            if (n != 0) return n2;
            return snake.y - snake2.y;
        }
    };

    private DiffUtil() {
    }

    public static DiffResult calculateDiff(Callback callback) {
        return DiffUtil.calculateDiff(callback, true);
    }

    public static DiffResult calculateDiff(Callback callback, boolean bl) {
        int n = callback.getOldListSize();
        int n2 = callback.getNewListSize();
        ArrayList<Snake> arrayList = new ArrayList<Snake>();
        ArrayList<Range> arrayList2 = new ArrayList<Range>();
        arrayList2.add(new Range(0, n, 0, n2));
        n2 = n + n2 + Math.abs(n - n2);
        n = n2 * 2;
        int[] arrn = new int[n];
        int[] arrn2 = new int[n];
        ArrayList<Range> arrayList3 = new ArrayList<Range>();
        do {
            if (arrayList2.isEmpty()) {
                Collections.sort(arrayList, SNAKE_COMPARATOR);
                return new DiffResult(callback, arrayList, arrn, arrn2, bl);
            }
            Range range = (Range)arrayList2.remove(arrayList2.size() - 1);
            Snake snake = DiffUtil.diffPartial(callback, range.oldListStart, range.oldListEnd, range.newListStart, range.newListEnd, arrn, arrn2, n2);
            if (snake != null) {
                if (snake.size > 0) {
                    arrayList.add(snake);
                }
                snake.x += range.oldListStart;
                snake.y += range.newListStart;
                Range range2 = arrayList3.isEmpty() ? new Range() : (Range)arrayList3.remove(arrayList3.size() - 1);
                range2.oldListStart = range.oldListStart;
                range2.newListStart = range.newListStart;
                if (snake.reverse) {
                    range2.oldListEnd = snake.x;
                    range2.newListEnd = snake.y;
                } else if (snake.removal) {
                    range2.oldListEnd = snake.x - 1;
                    range2.newListEnd = snake.y;
                } else {
                    range2.oldListEnd = snake.x;
                    range2.newListEnd = snake.y - 1;
                }
                arrayList2.add(range2);
                if (snake.reverse) {
                    if (snake.removal) {
                        range.oldListStart = snake.x + snake.size + 1;
                        range.newListStart = snake.y + snake.size;
                    } else {
                        range.oldListStart = snake.x + snake.size;
                        range.newListStart = snake.y + snake.size + 1;
                    }
                } else {
                    range.oldListStart = snake.x + snake.size;
                    range.newListStart = snake.y + snake.size;
                }
                arrayList2.add(range);
                continue;
            }
            arrayList3.add(range);
        } while (true);
    }

    private static Snake diffPartial(Callback object, int n, int n2, int n3, int n4, int[] arrn, int[] arrn2, int n5) {
        int n6 = n4 - n3;
        if ((n2 -= n) < 1) return null;
        if (n6 < 1) {
            return null;
        }
        int n7 = n2 - n6;
        int n8 = (n2 + n6 + 1) / 2;
        int n9 = n5 - n8 - 1;
        n4 = n5 + n8 + 1;
        Arrays.fill(arrn, n9, n4, 0);
        Arrays.fill(arrn2, n9 + n7, n4 + n7, n2);
        n9 = n7 % 2 != 0 ? 1 : 0;
        int n10 = 0;
        while (n10 <= n8) {
            int n11;
            int n12;
            int n13;
            boolean bl;
            for (n11 = n13 = -n10; n11 <= n10; n11 += 2) {
                if (n11 != n13 && (n11 == n10 || arrn[(n4 = n5 + n11) - 1] >= arrn[n4 + 1])) {
                    n4 = arrn[n5 + n11 - 1] + 1;
                    bl = true;
                } else {
                    n4 = arrn[n5 + n11 + 1];
                    bl = false;
                }
                for (n12 = n4 - n11; n4 < n2 && n12 < n6 && ((Callback)object).areItemsTheSame(n + n4, n3 + n12); ++n4, ++n12) {
                }
                n12 = n5 + n11;
                arrn[n12] = n4;
                if (n9 == 0 || n11 < n7 - n10 + 1 || n11 > n7 + n10 - 1 || arrn[n12] < arrn2[n12]) continue;
                object = new Snake();
                ((Snake)object).x = arrn2[n12];
                ((Snake)object).y = ((Snake)object).x - n11;
                ((Snake)object).size = arrn[n12] - arrn2[n12];
                ((Snake)object).removal = bl;
                ((Snake)object).reverse = false;
                return object;
            }
            for (n11 = n13; n11 <= n10; n11 += 2) {
                int n14 = n11 + n7;
                if (n14 != n10 + n7 && (n14 == n13 + n7 || arrn2[(n4 = n5 + n14) - 1] >= arrn2[n4 + 1])) {
                    n4 = arrn2[n5 + n14 + 1] - 1;
                    bl = true;
                } else {
                    n4 = arrn2[n5 + n14 - 1];
                    bl = false;
                }
                for (n12 = n4 - n14; n4 > 0 && n12 > 0 && ((Callback)object).areItemsTheSame(n + n4 - 1, n3 + n12 - 1); --n4, --n12) {
                }
                n12 = n5 + n14;
                arrn2[n12] = n4;
                if (n9 != 0 || n14 < n13 || n14 > n10 || arrn[n12] < arrn2[n12]) continue;
                object = new Snake();
                ((Snake)object).x = arrn2[n12];
                ((Snake)object).y = ((Snake)object).x - n14;
                ((Snake)object).size = arrn[n12] - arrn2[n12];
                ((Snake)object).removal = bl;
                ((Snake)object).reverse = true;
                return object;
            }
            ++n10;
        }
        throw new IllegalStateException("DiffUtil hit an unexpected case while trying to calculate the optimal path. Please make sure your data is not changing during the diff calculation.");
    }

    public static abstract class Callback {
        public abstract boolean areContentsTheSame(int var1, int var2);

        public abstract boolean areItemsTheSame(int var1, int var2);

        public Object getChangePayload(int n, int n2) {
            return null;
        }

        public abstract int getNewListSize();

        public abstract int getOldListSize();
    }

    public static class DiffResult {
        private static final int FLAG_CHANGED = 2;
        private static final int FLAG_IGNORE = 16;
        private static final int FLAG_MASK = 31;
        private static final int FLAG_MOVED_CHANGED = 4;
        private static final int FLAG_MOVED_NOT_CHANGED = 8;
        private static final int FLAG_NOT_CHANGED = 1;
        private static final int FLAG_OFFSET = 5;
        public static final int NO_POSITION = -1;
        private final Callback mCallback;
        private final boolean mDetectMoves;
        private final int[] mNewItemStatuses;
        private final int mNewListSize;
        private final int[] mOldItemStatuses;
        private final int mOldListSize;
        private final List<Snake> mSnakes;

        DiffResult(Callback callback, List<Snake> list, int[] arrn, int[] arrn2, boolean bl) {
            this.mSnakes = list;
            this.mOldItemStatuses = arrn;
            this.mNewItemStatuses = arrn2;
            Arrays.fill(arrn, 0);
            Arrays.fill(this.mNewItemStatuses, 0);
            this.mCallback = callback;
            this.mOldListSize = callback.getOldListSize();
            this.mNewListSize = callback.getNewListSize();
            this.mDetectMoves = bl;
            this.addRootSnake();
            this.findMatchingItems();
        }

        private void addRootSnake() {
            Snake snake = this.mSnakes.isEmpty() ? null : this.mSnakes.get(0);
            if (snake != null && snake.x == 0) {
                if (snake.y == 0) return;
            }
            snake = new Snake();
            snake.x = 0;
            snake.y = 0;
            snake.removal = false;
            snake.size = 0;
            snake.reverse = false;
            this.mSnakes.add(0, snake);
        }

        private void dispatchAdditions(List<PostponedUpdate> object, ListUpdateCallback listUpdateCallback, int n, int n2, int n3) {
            if (!this.mDetectMoves) {
                listUpdateCallback.onInserted(n, n2);
                return;
            }
            --n2;
            while (n2 >= 0) {
                block6 : {
                    Object object2;
                    block4 : {
                        int n4;
                        int n5;
                        block5 : {
                            object2 = this.mNewItemStatuses;
                            n4 = n3 + n2;
                            n5 = object2[n4] & 31;
                            if (n5 == 0) break block4;
                            if (n5 == 4 || n5 == 8) break block5;
                            if (n5 != 16) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("unknown flag for pos ");
                                ((StringBuilder)object).append(n4);
                                ((StringBuilder)object).append(" ");
                                ((StringBuilder)object).append(Long.toBinaryString(n5));
                                throw new IllegalStateException(((StringBuilder)object).toString());
                            }
                            object.add((PostponedUpdate)new PostponedUpdate(n4, n, false));
                            break block6;
                        }
                        int n6 = this.mNewItemStatuses[n4] >> 5;
                        listUpdateCallback.onMoved(DiffResult.removePostponedUpdate(object, (int)n6, (boolean)true).currentPos, n);
                        if (n5 != 4) break block6;
                        listUpdateCallback.onChanged(n, 1, this.mCallback.getChangePayload(n6, n4));
                        break block6;
                    }
                    listUpdateCallback.onInserted(n, 1);
                    Iterator<PostponedUpdate> iterator2 = object.iterator();
                    while (iterator2.hasNext()) {
                        object2 = iterator2.next();
                        ++object2.currentPos;
                    }
                }
                --n2;
            }
        }

        private void dispatchRemovals(List<PostponedUpdate> object, ListUpdateCallback listUpdateCallback, int n, int n2, int n3) {
            if (!this.mDetectMoves) {
                listUpdateCallback.onRemoved(n, n2);
                return;
            }
            --n2;
            while (n2 >= 0) {
                block6 : {
                    Object object2;
                    block4 : {
                        int n4;
                        int n5;
                        block5 : {
                            object2 = this.mOldItemStatuses;
                            n4 = n3 + n2;
                            n5 = object2[n4] & 31;
                            if (n5 == 0) break block4;
                            if (n5 == 4 || n5 == 8) break block5;
                            if (n5 != 16) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("unknown flag for pos ");
                                ((StringBuilder)object).append(n4);
                                ((StringBuilder)object).append(" ");
                                ((StringBuilder)object).append(Long.toBinaryString(n5));
                                throw new IllegalStateException(((StringBuilder)object).toString());
                            }
                            object.add((PostponedUpdate)new PostponedUpdate(n4, n + n2, true));
                            break block6;
                        }
                        int n6 = this.mOldItemStatuses[n4] >> 5;
                        object2 = DiffResult.removePostponedUpdate(object, n6, false);
                        listUpdateCallback.onMoved(n + n2, ((PostponedUpdate)object2).currentPos - 1);
                        if (n5 != 4) break block6;
                        listUpdateCallback.onChanged(((PostponedUpdate)object2).currentPos - 1, 1, this.mCallback.getChangePayload(n4, n6));
                        break block6;
                    }
                    listUpdateCallback.onRemoved(n + n2, 1);
                    object2 = object.iterator();
                    while (object2.hasNext()) {
                        PostponedUpdate postponedUpdate = (PostponedUpdate)object2.next();
                        --postponedUpdate.currentPos;
                    }
                }
                --n2;
            }
        }

        private void findAddition(int n, int n2, int n3) {
            if (this.mOldItemStatuses[n - 1] != 0) {
                return;
            }
            this.findMatchingItem(n, n2, n3, false);
        }

        private boolean findMatchingItem(int n, int n2, int n3, boolean bl) {
            int[] arrn;
            int n4;
            block11 : {
                int n5;
                int n6;
                if (bl) {
                    n6 = n2 - 1;
                    n2 = n;
                    n5 = n6;
                } else {
                    n4 = n5 = n - 1;
                    n6 = n2;
                    n2 = n4;
                }
                while (n3 >= 0) {
                    arrn = this.mSnakes.get(n3);
                    int n7 = arrn.x;
                    int n8 = arrn.size;
                    int n9 = arrn.y;
                    int n10 = arrn.size;
                    n4 = 8;
                    if (bl) {
                        --n2;
                        while (n2 >= n7 + n8) {
                            if (this.mCallback.areItemsTheSame(n2, n5)) {
                                if (!this.mCallback.areContentsTheSame(n2, n5)) {
                                    n4 = 4;
                                }
                            } else {
                                --n2;
                                continue;
                            }
                            this.mNewItemStatuses[n5] = n2 << 5 | 16;
                            this.mOldItemStatuses[n2] = n5 << 5 | n4;
                            return true;
                        }
                    } else {
                        for (n2 = n6 - 1; n2 >= n9 + n10; --n2) {
                            if (!this.mCallback.areItemsTheSame(n5, n2)) continue;
                            if (!this.mCallback.areContentsTheSame(n5, n2)) {
                                n4 = 4;
                            }
                            break block11;
                        }
                    }
                    n2 = arrn.x;
                    n6 = arrn.y;
                    --n3;
                }
                return false;
            }
            arrn = this.mOldItemStatuses;
            arrn[--n] = n2 << 5 | 16;
            this.mNewItemStatuses[n2] = n << 5 | n4;
            return true;
        }

        private void findMatchingItems() {
            int n = this.mOldListSize;
            int n2 = this.mNewListSize;
            int n3 = this.mSnakes.size() - 1;
            while (n3 >= 0) {
                int n4;
                Snake snake = this.mSnakes.get(n3);
                int n5 = snake.x;
                int n6 = snake.size;
                int n7 = snake.y;
                int n8 = snake.size;
                if (this.mDetectMoves) {
                    do {
                        if (n <= n5 + n6) break;
                        this.findAddition(n, n2, n3);
                        --n;
                    } while (true);
                    for (n4 = n2; n4 > n7 + n8; --n4) {
                        this.findRemoval(n, n4, n3);
                    }
                }
                for (n2 = 0; n2 < snake.size; ++n2) {
                    n8 = snake.x + n2;
                    n4 = snake.y + n2;
                    n = this.mCallback.areContentsTheSame(n8, n4) ? 1 : 2;
                    this.mOldItemStatuses[n8] = n4 << 5 | n;
                    this.mNewItemStatuses[n4] = n8 << 5 | n;
                }
                n = snake.x;
                n2 = snake.y;
                --n3;
            }
        }

        private void findRemoval(int n, int n2, int n3) {
            if (this.mNewItemStatuses[n2 - 1] != 0) {
                return;
            }
            this.findMatchingItem(n, n2, n3, true);
        }

        private static PostponedUpdate removePostponedUpdate(List<PostponedUpdate> list, int n, boolean bl) {
            PostponedUpdate postponedUpdate;
            int n2;
            block3 : {
                n2 = list.size() - 1;
                while (n2 >= 0) {
                    postponedUpdate = list.get(n2);
                    if (postponedUpdate.posInOwnerList != n || postponedUpdate.removal != bl) {
                        --n2;
                        continue;
                    }
                    break block3;
                }
                return null;
            }
            list.remove(n2);
            while (n2 < list.size()) {
                PostponedUpdate postponedUpdate2 = list.get(n2);
                int n3 = postponedUpdate2.currentPos;
                n = bl ? 1 : -1;
                postponedUpdate2.currentPos = n3 + n;
                ++n2;
            }
            return postponedUpdate;
        }

        public int convertNewPositionToOld(int n) {
            if (n >= 0 && n < this.mNewListSize) {
                if (((n = this.mNewItemStatuses[n]) & 31) != 0) return n >> 5;
                return -1;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Index out of bounds - passed position = ");
            stringBuilder.append(n);
            stringBuilder.append(", new list size = ");
            stringBuilder.append(this.mNewListSize);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        public int convertOldPositionToNew(int n) {
            if (n >= 0 && n < this.mOldListSize) {
                if (((n = this.mOldItemStatuses[n]) & 31) != 0) return n >> 5;
                return -1;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Index out of bounds - passed position = ");
            stringBuilder.append(n);
            stringBuilder.append(", old list size = ");
            stringBuilder.append(this.mOldListSize);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        public void dispatchUpdatesTo(ListUpdateCallback listUpdateCallback) {
            listUpdateCallback = listUpdateCallback instanceof BatchingListUpdateCallback ? (BatchingListUpdateCallback)listUpdateCallback : new BatchingListUpdateCallback(listUpdateCallback);
            ArrayList<PostponedUpdate> arrayList = new ArrayList<PostponedUpdate>();
            int n = this.mOldListSize;
            int n2 = this.mNewListSize;
            int n3 = this.mSnakes.size();
            --n3;
            do {
                if (n3 < 0) {
                    ((BatchingListUpdateCallback)listUpdateCallback).dispatchLastEvent();
                    return;
                }
                Snake snake = this.mSnakes.get(n3);
                int n4 = snake.size;
                int n5 = snake.x + n4;
                int n6 = snake.y + n4;
                if (n5 < n) {
                    this.dispatchRemovals(arrayList, listUpdateCallback, n5, n - n5, n5);
                }
                if (n6 < n2) {
                    this.dispatchAdditions(arrayList, listUpdateCallback, n5, n2 - n6, n6);
                }
                for (n = n4 - 1; n >= 0; --n) {
                    if ((this.mOldItemStatuses[snake.x + n] & 31) != 2) continue;
                    ((BatchingListUpdateCallback)listUpdateCallback).onChanged(snake.x + n, 1, this.mCallback.getChangePayload(snake.x + n, snake.y + n));
                }
                n = snake.x;
                n2 = snake.y;
                --n3;
            } while (true);
        }

        public void dispatchUpdatesTo(RecyclerView.Adapter adapter) {
            this.dispatchUpdatesTo(new AdapterListUpdateCallback(adapter));
        }

        List<Snake> getSnakes() {
            return this.mSnakes;
        }
    }

    public static abstract class ItemCallback<T> {
        public abstract boolean areContentsTheSame(T var1, T var2);

        public abstract boolean areItemsTheSame(T var1, T var2);

        public Object getChangePayload(T t, T t2) {
            return null;
        }
    }

    private static class PostponedUpdate {
        int currentPos;
        int posInOwnerList;
        boolean removal;

        public PostponedUpdate(int n, int n2, boolean bl) {
            this.posInOwnerList = n;
            this.currentPos = n2;
            this.removal = bl;
        }
    }

    static class Range {
        int newListEnd;
        int newListStart;
        int oldListEnd;
        int oldListStart;

        public Range() {
        }

        public Range(int n, int n2, int n3, int n4) {
            this.oldListStart = n;
            this.oldListEnd = n2;
            this.newListStart = n3;
            this.newListEnd = n4;
        }
    }

    static class Snake {
        boolean removal;
        boolean reverse;
        int size;
        int x;
        int y;

        Snake() {
        }
    }

}

