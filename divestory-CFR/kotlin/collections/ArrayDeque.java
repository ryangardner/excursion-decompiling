/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractMutableList;
import kotlin.collections.ArrayDequeKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005B\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0006B\u0015\b\u0016\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b\u00a2\u0006\u0002\u0010\tJ\u0015\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u0016J\u001d\u0010\u0013\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u0019J\u001e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0016\u0010\u001a\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0013\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u001cJ\b\u0010\u001e\u001a\u00020\u0017H\u0016J\u0016\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010\u0016J\u001e\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0002J\u0010\u0010\"\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004H\u0002J\u0010\u0010$\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0010\u0010%\u001a\u00020\u00172\u0006\u0010&\u001a\u00020\u0004H\u0002J\u001d\u0010'\u001a\u00020\u00142\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00140)H\u0082\bJ\u000b\u0010*\u001a\u00028\u0000\u00a2\u0006\u0002\u0010+J\r\u0010,\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0002\u0010+J\u0016\u0010-\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0004H\u0096\u0002\u00a2\u0006\u0002\u0010.J\u0010\u0010/\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0015\u00100\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u00101J\u0016\u00102\u001a\u00028\u00002\u0006\u0010!\u001a\u00020\u0004H\u0083\b\u00a2\u0006\u0002\u0010.J\u0011\u0010!\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0083\bJM\u00103\u001a\u00020\u00172>\u00104\u001a:\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b6\u0012\b\b7\u0012\u0004\b\b(\u000e\u0012\u001b\u0012\u0019\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b\u00a2\u0006\f\b6\u0012\b\b7\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\u001705H\u0000\u00a2\u0006\u0002\b8J\b\u00109\u001a\u00020\u0014H\u0016J\u000b\u0010:\u001a\u00028\u0000\u00a2\u0006\u0002\u0010+J\u0015\u0010;\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u00101J\r\u0010<\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0002\u0010+J\u0010\u0010=\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u001d\u0010#\u001a\u00020\u00042\u0006\u0010>\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\b?J\u0010\u0010@\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0015\u0010A\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010B\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0015\u0010C\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0004H\u0016\u00a2\u0006\u0002\u0010.J\u000b\u0010D\u001a\u00028\u0000\u00a2\u0006\u0002\u0010+J\r\u0010E\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0002\u0010+J\u000b\u0010F\u001a\u00028\u0000\u00a2\u0006\u0002\u0010+J\r\u0010G\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0002\u0010+J\u0016\u0010H\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u001e\u0010I\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010JR\u0018\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004@RX\u0096\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006K"}, d2={"Lkotlin/collections/ArrayDeque;", "E", "Lkotlin/collections/AbstractMutableList;", "initialCapacity", "", "(I)V", "()V", "elements", "", "(Ljava/util/Collection;)V", "elementData", "", "", "[Ljava/lang/Object;", "head", "<set-?>", "size", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "", "index", "(ILjava/lang/Object;)V", "addAll", "addFirst", "(Ljava/lang/Object;)V", "addLast", "clear", "contains", "copyCollectionElements", "internalIndex", "copyElements", "newCapacity", "decremented", "ensureCapacity", "minCapacity", "filterInPlace", "predicate", "Lkotlin/Function1;", "first", "()Ljava/lang/Object;", "firstOrNull", "get", "(I)Ljava/lang/Object;", "incremented", "indexOf", "(Ljava/lang/Object;)I", "internalGet", "internalStructure", "structure", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "internalStructure$kotlin_stdlib", "isEmpty", "last", "lastIndexOf", "lastOrNull", "negativeMod", "oldCapacity", "newCapacity$kotlin_stdlib", "positiveMod", "remove", "removeAll", "removeAt", "removeFirst", "removeFirstOrNull", "removeLast", "removeLastOrNull", "retainAll", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class ArrayDeque<E>
extends AbstractMutableList<E> {
    private Object[] elementData;
    private int head;
    private int size;

    public ArrayDeque() {
        this.elementData = ArrayDequeKt.access$getEmptyElementData$p();
    }

    public ArrayDeque(int n) {
        Object[] arrobject;
        if (n == 0) {
            arrobject = ArrayDequeKt.access$getEmptyElementData$p();
        } else {
            if (n <= 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Illegal Capacity: ");
                stringBuilder.append(n);
                throw (Throwable)new IllegalArgumentException(stringBuilder.toString());
            }
            arrobject = new Object[n];
        }
        this.elementData = arrobject;
    }

    public ArrayDeque(Collection<? extends E> arrobject) {
        Intrinsics.checkParameterIsNotNull(arrobject, "elements");
        boolean bl = false;
        arrobject = arrobject.toArray(new Object[0]);
        if (arrobject == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        this.elementData = arrobject;
        this.size = arrobject.length;
        if (arrobject.length == 0) {
            bl = true;
        }
        if (!bl) return;
        this.elementData = ArrayDequeKt.access$getEmptyElementData$p();
    }

    public static final /* synthetic */ int access$getSize$p(ArrayDeque arrayDeque) {
        return arrayDeque.size();
    }

    public static final /* synthetic */ void access$setElementData$p(ArrayDeque arrayDeque, Object[] arrobject) {
        arrayDeque.elementData = arrobject;
    }

    public static final /* synthetic */ void access$setHead$p(ArrayDeque arrayDeque, int n) {
        arrayDeque.head = n;
    }

    public static final /* synthetic */ void access$setSize$p(ArrayDeque arrayDeque, int n) {
        arrayDeque.size = n;
    }

    private final void copyCollectionElements(int n, Collection<? extends E> collection) {
        Iterator<E> iterator2 = collection.iterator();
        int n2 = this.elementData.length;
        while (n < n2 && iterator2.hasNext()) {
            this.elementData[n] = iterator2.next();
            ++n;
        }
        n2 = this.head;
        for (n = 0; n < n2 && iterator2.hasNext(); ++n) {
            this.elementData[n] = iterator2.next();
        }
        this.size = this.size() + collection.size();
    }

    private final void copyElements(int n) {
        Object[] arrobject = new Object[n];
        Object[] arrobject2 = this.elementData;
        ArraysKt.copyInto(arrobject2, arrobject, 0, this.head, arrobject2.length);
        arrobject2 = this.elementData;
        n = arrobject2.length;
        int n2 = this.head;
        ArraysKt.copyInto(arrobject2, arrobject, n - n2, 0, n2);
        this.head = 0;
        this.elementData = arrobject;
    }

    private final int decremented(int n) {
        if (n == 0) {
            return ArraysKt.getLastIndex(this.elementData);
        }
        --n;
        return n;
    }

    private final void ensureCapacity(int n) {
        if (n < 0) throw (Throwable)new IllegalStateException("Deque is too big.");
        Object[] arrobject = this.elementData;
        if (n <= arrobject.length) {
            return;
        }
        if (arrobject == ArrayDequeKt.access$getEmptyElementData$p()) {
            this.elementData = new Object[kotlin.ranges.RangesKt.coerceAtLeast(n, 10)];
            return;
        }
        this.copyElements(this.newCapacity$kotlin_stdlib(this.elementData.length, n));
    }

    private final boolean filterInPlace(Function1<? super E, Boolean> function1) {
        boolean bl = this.isEmpty();
        int n = 0;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = bl2;
        if (bl) return bl4;
        int n2 = this.elementData.length == 0 ? 1 : 0;
        if (n2 != 0) {
            return bl2;
        }
        n2 = this.size();
        int n3 = this.positiveMod(this.head + n2);
        int n4 = this.head;
        if (this.head < n3) {
            n2 = n4;
            for (n = ArrayDeque.access$getHead$p((ArrayDeque)this); n < n3; ++n) {
                Object object = this.elementData[n];
                if (function1.invoke(object).booleanValue()) {
                    ArrayDeque.access$getElementData$p((ArrayDeque)this)[n2] = object;
                    ++n2;
                    continue;
                }
                bl3 = true;
            }
            ArraysKt.fill(this.elementData, null, n2, n3);
        } else {
            Object object;
            int n5 = this.elementData.length;
            bl3 = false;
            for (n2 = ArrayDeque.access$getHead$p((ArrayDeque)this); n2 < n5; ++n2) {
                object = this.elementData[n2];
                ArrayDeque.access$getElementData$p((ArrayDeque)this)[n2] = null;
                if (function1.invoke(object).booleanValue()) {
                    ArrayDeque.access$getElementData$p((ArrayDeque)this)[n4] = object;
                    ++n4;
                    continue;
                }
                bl3 = true;
            }
            n2 = this.positiveMod(n4);
            for (n4 = n; n4 < n3; ++n4) {
                object = this.elementData[n4];
                ArrayDeque.access$getElementData$p((ArrayDeque)this)[n4] = null;
                if (function1.invoke(object).booleanValue()) {
                    ArrayDeque.access$getElementData$p((ArrayDeque)this)[n2] = object;
                    n2 = this.incremented(n2);
                    continue;
                }
                bl3 = true;
            }
        }
        bl4 = bl3;
        if (!bl3) return bl4;
        ArrayDeque.access$setSize$p(this, this.negativeMod(n2 - this.head));
        return bl3;
    }

    private final int incremented(int n) {
        if (n == ArraysKt.getLastIndex(this.elementData)) {
            return 0;
        }
        ++n;
        return n;
    }

    private final E internalGet(int n) {
        return (E)this.elementData[n];
    }

    private final int internalIndex(int n) {
        return this.positiveMod(this.head + n);
    }

    private final int negativeMod(int n) {
        int n2 = n;
        if (n >= 0) return n2;
        return n + this.elementData.length;
    }

    private final int positiveMod(int n) {
        Object[] arrobject = this.elementData;
        int n2 = n;
        if (n < arrobject.length) return n2;
        return n - arrobject.length;
    }

    @Override
    public void add(int n, E e) {
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(n, this.size());
        if (n == this.size()) {
            this.addLast(e);
            return;
        }
        if (n == 0) {
            this.addFirst(e);
            return;
        }
        this.ensureCapacity(this.size() + 1);
        int n2 = this.positiveMod(this.head + n);
        if (n < this.size() + 1 >> 1) {
            n = this.decremented(n2);
            int n3 = this.decremented(this.head);
            n2 = this.head;
            if (n >= n2) {
                Object[] arrobject = this.elementData;
                arrobject[n3] = arrobject[n2];
                ArraysKt.copyInto(arrobject, arrobject, n2, n2 + 1, n + 1);
            } else {
                Object[] arrobject = this.elementData;
                ArraysKt.copyInto(arrobject, arrobject, n2 - 1, n2, arrobject.length);
                arrobject = this.elementData;
                arrobject[arrobject.length - 1] = arrobject[0];
                ArraysKt.copyInto(arrobject, arrobject, 0, 1, n + 1);
            }
            this.elementData[n] = e;
            this.head = n3;
        } else {
            n = this.size();
            n = this.positiveMod(this.head + n);
            if (n2 < n) {
                Object[] arrobject = this.elementData;
                ArraysKt.copyInto(arrobject, arrobject, n2 + 1, n2, n);
            } else {
                Object[] arrobject = this.elementData;
                ArraysKt.copyInto(arrobject, arrobject, 1, 0, n);
                arrobject = this.elementData;
                arrobject[0] = arrobject[arrobject.length - 1];
                ArraysKt.copyInto(arrobject, arrobject, n2 + 1, n2, arrobject.length - 1);
            }
            this.elementData[n2] = e;
        }
        this.size = this.size() + 1;
    }

    @Override
    public boolean add(E e) {
        this.addLast(e);
        return true;
    }

    @Override
    public boolean addAll(int n, Collection<? extends E> collection) {
        Intrinsics.checkParameterIsNotNull(collection, "elements");
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(n, this.size());
        if (collection.isEmpty()) {
            return false;
        }
        if (n == this.size()) {
            return this.addAll(collection);
        }
        this.ensureCapacity(this.size() + collection.size());
        int n2 = this.size();
        int n3 = this.positiveMod(this.head + n2);
        n2 = this.positiveMod(this.head + n);
        int n4 = collection.size();
        if (n < this.size() + 1 >> 1) {
            n3 = this.head;
            n = n3 - n4;
            if (n2 >= n3) {
                if (n >= 0) {
                    Object[] arrobject = this.elementData;
                    ArraysKt.copyInto(arrobject, arrobject, n, n3, n2);
                } else {
                    Object[] arrobject = this.elementData;
                    int n5 = arrobject.length - (n += arrobject.length);
                    if (n5 >= n2 - n3) {
                        ArraysKt.copyInto(arrobject, arrobject, n, n3, n2);
                    } else {
                        ArraysKt.copyInto(arrobject, arrobject, n, n3, n3 + n5);
                        arrobject = this.elementData;
                        ArraysKt.copyInto(arrobject, arrobject, 0, this.head + n5, n2);
                    }
                }
            } else {
                Object[] arrobject = this.elementData;
                ArraysKt.copyInto(arrobject, arrobject, n, n3, arrobject.length);
                if (n4 >= n2) {
                    arrobject = this.elementData;
                    ArraysKt.copyInto(arrobject, arrobject, arrobject.length - n4, 0, n2);
                } else {
                    arrobject = this.elementData;
                    ArraysKt.copyInto(arrobject, arrobject, arrobject.length - n4, 0, n4);
                    arrobject = this.elementData;
                    ArraysKt.copyInto(arrobject, arrobject, 0, n4, n2);
                }
            }
            this.head = n;
            this.copyCollectionElements(this.negativeMod(n2 - n4), collection);
            return true;
        }
        n = n2 + n4;
        if (n2 < n3) {
            Object[] arrobject = this.elementData;
            if ((n4 += n3) <= arrobject.length) {
                ArraysKt.copyInto(arrobject, arrobject, n, n2, n3);
            } else if (n >= arrobject.length) {
                ArraysKt.copyInto(arrobject, arrobject, n - arrobject.length, n2, n3);
            } else {
                n4 = n3 - (n4 - arrobject.length);
                ArraysKt.copyInto(arrobject, arrobject, 0, n4, n3);
                arrobject = this.elementData;
                ArraysKt.copyInto(arrobject, arrobject, n, n2, n4);
            }
        } else {
            Object[] arrobject = this.elementData;
            ArraysKt.copyInto(arrobject, arrobject, n4, 0, n3);
            arrobject = this.elementData;
            if (n >= arrobject.length) {
                ArraysKt.copyInto(arrobject, arrobject, n - arrobject.length, n2, arrobject.length);
            } else {
                ArraysKt.copyInto(arrobject, arrobject, 0, arrobject.length - n4, arrobject.length);
                arrobject = this.elementData;
                ArraysKt.copyInto(arrobject, arrobject, n, n2, arrobject.length - n4);
            }
        }
        this.copyCollectionElements(n2, collection);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        Intrinsics.checkParameterIsNotNull(collection, "elements");
        if (collection.isEmpty()) {
            return false;
        }
        this.ensureCapacity(this.size() + collection.size());
        int n = this.size();
        this.copyCollectionElements(this.positiveMod(this.head + n), collection);
        return true;
    }

    public final void addFirst(E e) {
        int n;
        this.ensureCapacity(this.size() + 1);
        this.head = n = this.decremented(this.head);
        this.elementData[n] = e;
        this.size = this.size() + 1;
    }

    public final void addLast(E e) {
        this.ensureCapacity(this.size() + 1);
        Object[] arrobject = this.elementData;
        int n = this.size();
        arrobject[ArrayDeque.access$positiveMod((ArrayDeque)this, (int)(ArrayDeque.access$getHead$p((ArrayDeque)this) + n))] = e;
        this.size = this.size() + 1;
    }

    @Override
    public void clear() {
        int n = this.size();
        int n2 = this.positiveMod(this.head + n);
        n = this.head;
        if (n < n2) {
            ArraysKt.fill(this.elementData, null, n, n2);
        } else if (this.isEmpty() ^ true) {
            Object[] arrobject = this.elementData;
            ArraysKt.fill(arrobject, null, this.head, arrobject.length);
            ArraysKt.fill(this.elementData, null, 0, n2);
        }
        this.head = 0;
        this.size = 0;
    }

    @Override
    public boolean contains(Object object) {
        if (this.indexOf(object) == -1) return false;
        return true;
    }

    public final E first() {
        if (this.isEmpty()) throw (Throwable)new NoSuchElementException("ArrayDeque is empty.");
        int n = this.head;
        return (E)this.elementData[n];
    }

    public final E firstOrNull() {
        Object object;
        if (this.isEmpty()) {
            object = null;
            return (E)object;
        }
        int n = this.head;
        object = this.elementData[n];
        return (E)object;
    }

    @Override
    public E get(int n) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(n, this.size());
        n = this.positiveMod(this.head + n);
        return (E)this.elementData[n];
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public int indexOf(Object object) {
        int n;
        int n2 = this.size();
        int n3 = this.positiveMod(this.head + n2);
        n2 = this.head;
        if (n2 >= n3) {
            if (n2 < n3) return -1;
            n = this.elementData.length;
        } else {
            while (n2 < n3) {
                if (Intrinsics.areEqual(object, this.elementData[n2])) {
                    n3 = this.head;
                    return n2 - n3;
                }
                ++n2;
            }
            return -1;
        }
        while (n2 < n) {
            if (Intrinsics.areEqual(object, this.elementData[n2])) {
                n3 = this.head;
                return n2 - n3;
            }
            ++n2;
        }
        n2 = 0;
        while (n2 < n3) {
            if (Intrinsics.areEqual(object, this.elementData[n2])) {
                n3 = this.head;
                return (n2 += this.elementData.length) - n3;
            }
            ++n2;
        }
        return -1;
    }

    public final void internalStructure$kotlin_stdlib(Function2<? super Integer, ? super Object[], Unit> function2) {
        Intrinsics.checkParameterIsNotNull(function2, "structure");
        int n = this.size();
        int n2 = this.positiveMod(this.head + n);
        if (this.isEmpty()) {
            function2.invoke((Integer)this.head, (Object[])new Object[0]);
            return;
        }
        Object[] arrobject = new Object[this.size()];
        n = this.head;
        if (n < n2) {
            ArraysKt.copyInto$default(this.elementData, arrobject, 0, n, n2, 2, null);
            function2.invoke((Integer)this.head, (Object[])arrobject);
            return;
        }
        ArraysKt.copyInto$default(this.elementData, arrobject, 0, n, 0, 10, null);
        Object[] arrobject2 = this.elementData;
        ArraysKt.copyInto(arrobject2, arrobject, arrobject2.length - this.head, 0, n2);
        function2.invoke((Integer)(this.head - this.elementData.length), (Object[])arrobject);
    }

    @Override
    public boolean isEmpty() {
        if (this.size() != 0) return false;
        return true;
    }

    public final E last() {
        if (this.isEmpty()) throw (Throwable)new NoSuchElementException("ArrayDeque is empty.");
        int n = CollectionsKt.getLastIndex(this);
        n = this.positiveMod(this.head + n);
        return (E)this.elementData[n];
    }

    @Override
    public int lastIndexOf(Object object) {
        int n = this.size();
        int n2 = this.head;
        n = this.positiveMod(this.head + n);
        if (n2 >= n) {
            if (n2 <= n) return -1;
            --n;
        } else {
            if (--n < n2) return -1;
            do {
                if (Intrinsics.areEqual(object, this.elementData[n])) {
                    n2 = this.head;
                    return n - n2;
                }
                if (n == n2) return -1;
                --n;
            } while (true);
        }
        while (n >= 0) {
            if (Intrinsics.areEqual(object, this.elementData[n])) {
                n2 = this.head;
                return (n += this.elementData.length) - n2;
            }
            --n;
        }
        n = ArraysKt.getLastIndex(this.elementData);
        if (n < (n2 = this.head)) return -1;
        do {
            if (Intrinsics.areEqual(object, this.elementData[n])) {
                n2 = this.head;
                return n - n2;
            }
            if (n == n2) return -1;
            --n;
        } while (true);
    }

    public final E lastOrNull() {
        Object object;
        if (this.isEmpty()) {
            object = null;
            return (E)object;
        }
        int n = CollectionsKt.getLastIndex(this);
        n = this.positiveMod(this.head + n);
        object = this.elementData[n];
        return (E)object;
    }

    public final int newCapacity$kotlin_stdlib(int n, int n2) {
        int n3;
        n = n3 = n + (n >> 1);
        if (n3 - n2 < 0) {
            n = n2;
        }
        n3 = n;
        if (n - 2147483639 <= 0) return n3;
        if (n2 <= 2147483639) return 2147483639;
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean remove(Object object) {
        int n = this.indexOf(object);
        if (n == -1) {
            return false;
        }
        this.remove(n);
        return true;
    }

    @Override
    public boolean removeAll(Collection<? extends Object> collection) {
        Intrinsics.checkParameterIsNotNull(collection, "elements");
        boolean bl = this.isEmpty();
        int n = 0;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = bl2;
        if (bl) return bl4;
        int n2 = this.elementData.length == 0 ? 1 : 0;
        if (n2 != 0) {
            return bl2;
        }
        n2 = this.size();
        int n3 = this.positiveMod(this.head + n2);
        int n4 = this.head;
        if (this.head < n3) {
            n2 = n4;
            for (n = ArrayDeque.access$getHead$p((ArrayDeque)this); n < n3; ++n) {
                Object object = this.elementData[n];
                if (collection.contains(object) ^ true) {
                    ArrayDeque.access$getElementData$p((ArrayDeque)this)[n2] = object;
                    ++n2;
                    continue;
                }
                bl3 = true;
            }
            ArraysKt.fill(this.elementData, null, n2, n3);
        } else {
            Object object;
            int n5 = this.elementData.length;
            bl3 = false;
            for (n2 = ArrayDeque.access$getHead$p((ArrayDeque)this); n2 < n5; ++n2) {
                object = this.elementData[n2];
                ArrayDeque.access$getElementData$p((ArrayDeque)this)[n2] = null;
                if (collection.contains(object) ^ true) {
                    ArrayDeque.access$getElementData$p((ArrayDeque)this)[n4] = object;
                    ++n4;
                    continue;
                }
                bl3 = true;
            }
            n2 = this.positiveMod(n4);
            for (n4 = n; n4 < n3; ++n4) {
                object = this.elementData[n4];
                ArrayDeque.access$getElementData$p((ArrayDeque)this)[n4] = null;
                if (collection.contains(object) ^ true) {
                    ArrayDeque.access$getElementData$p((ArrayDeque)this)[n2] = object;
                    n2 = this.incremented(n2);
                    continue;
                }
                bl3 = true;
            }
        }
        bl4 = bl3;
        if (!bl3) return bl4;
        ArrayDeque.access$setSize$p(this, this.negativeMod(n2 - this.head));
        return bl3;
    }

    @Override
    public E removeAt(int n) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(n, this.size());
        if (n == CollectionsKt.getLastIndex(this)) {
            return this.removeLast();
        }
        if (n == 0) {
            return this.removeFirst();
        }
        int n2 = this.positiveMod(this.head + n);
        Object object = this.elementData[n2];
        if (n < this.size() >> 1) {
            Object[] arrobject;
            n = this.head;
            if (n2 >= n) {
                arrobject = this.elementData;
                ArraysKt.copyInto(arrobject, arrobject, n + 1, n, n2);
            } else {
                arrobject = this.elementData;
                ArraysKt.copyInto(arrobject, arrobject, 1, 0, n2);
                arrobject = this.elementData;
                arrobject[0] = arrobject[arrobject.length - 1];
                n = this.head;
                ArraysKt.copyInto(arrobject, arrobject, n + 1, n, arrobject.length - 1);
            }
            arrobject = this.elementData;
            n = this.head;
            arrobject[n] = null;
            this.head = this.incremented(n);
        } else {
            n = CollectionsKt.getLastIndex(this);
            n = this.positiveMod(this.head + n);
            if (n2 <= n) {
                Object[] arrobject = this.elementData;
                ArraysKt.copyInto(arrobject, arrobject, n2, n2 + 1, n + 1);
            } else {
                Object[] arrobject = this.elementData;
                ArraysKt.copyInto(arrobject, arrobject, n2, n2 + 1, arrobject.length);
                arrobject = this.elementData;
                arrobject[arrobject.length - 1] = arrobject[0];
                ArraysKt.copyInto(arrobject, arrobject, 0, 1, n + 1);
            }
            this.elementData[n] = null;
        }
        this.size = this.size() - 1;
        return (E)object;
    }

    public final E removeFirst() {
        if (this.isEmpty()) throw (Throwable)new NoSuchElementException("ArrayDeque is empty.");
        int n = this.head;
        Object object = this.elementData[n];
        Object[] arrobject = this.elementData;
        n = this.head;
        arrobject[n] = null;
        this.head = this.incremented(n);
        this.size = this.size() - 1;
        return (E)object;
    }

    public final E removeFirstOrNull() {
        E e;
        if (this.isEmpty()) {
            e = null;
            return e;
        }
        e = this.removeFirst();
        return e;
    }

    public final E removeLast() {
        if (this.isEmpty()) throw (Throwable)new NoSuchElementException("ArrayDeque is empty.");
        int n = CollectionsKt.getLastIndex(this);
        n = this.positiveMod(this.head + n);
        Object object = this.elementData[n];
        this.elementData[n] = null;
        this.size = this.size() - 1;
        return (E)object;
    }

    public final E removeLastOrNull() {
        E e;
        if (this.isEmpty()) {
            e = null;
            return e;
        }
        e = this.removeLast();
        return e;
    }

    @Override
    public boolean retainAll(Collection<? extends Object> collection) {
        Intrinsics.checkParameterIsNotNull(collection, "elements");
        boolean bl = this.isEmpty();
        int n = 0;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = bl2;
        if (bl) return bl4;
        int n2 = this.elementData.length == 0 ? 1 : 0;
        if (n2 != 0) {
            return bl2;
        }
        n2 = this.size();
        int n3 = this.positiveMod(this.head + n2);
        n2 = this.head;
        if (this.head < n3) {
            for (int i = ArrayDeque.access$getHead$p((ArrayDeque)this); i < n3; ++i) {
                Object object = this.elementData[i];
                if (collection.contains(object)) {
                    ArrayDeque.access$getElementData$p((ArrayDeque)this)[n2] = object;
                    ++n2;
                    continue;
                }
                bl3 = true;
            }
            ArraysKt.fill(this.elementData, null, n2, n3);
        } else {
            Object object;
            int n4;
            int n5 = this.elementData.length;
            bl3 = false;
            for (n4 = ArrayDeque.access$getHead$p((ArrayDeque)this); n4 < n5; ++n4) {
                object = this.elementData[n4];
                ArrayDeque.access$getElementData$p((ArrayDeque)this)[n4] = null;
                if (collection.contains(object)) {
                    ArrayDeque.access$getElementData$p((ArrayDeque)this)[n2] = object;
                    ++n2;
                    continue;
                }
                bl3 = true;
            }
            n2 = this.positiveMod(n2);
            for (n4 = n; n4 < n3; ++n4) {
                object = this.elementData[n4];
                ArrayDeque.access$getElementData$p((ArrayDeque)this)[n4] = null;
                if (collection.contains(object)) {
                    ArrayDeque.access$getElementData$p((ArrayDeque)this)[n2] = object;
                    n2 = this.incremented(n2);
                    continue;
                }
                bl3 = true;
            }
        }
        bl4 = bl3;
        if (!bl3) return bl4;
        ArrayDeque.access$setSize$p(this, this.negativeMod(n2 - this.head));
        return bl3;
    }

    @Override
    public E set(int n, E e) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(n, this.size());
        n = this.positiveMod(this.head + n);
        Object object = this.elementData[n];
        this.elementData[n] = e;
        return (E)object;
    }
}

