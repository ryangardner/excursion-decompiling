/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

class ConsumingQueueIterator<T>
extends AbstractIterator<T> {
    private final Queue<T> queue;

    ConsumingQueueIterator(Queue<T> queue) {
        this.queue = Preconditions.checkNotNull(queue);
    }

    ConsumingQueueIterator(T ... arrT) {
        ArrayDeque<T> arrayDeque = new ArrayDeque<T>(arrT.length);
        this.queue = arrayDeque;
        Collections.addAll(arrayDeque, arrT);
    }

    @Override
    public T computeNext() {
        Object t;
        if (this.queue.isEmpty()) {
            t = this.endOfData();
            return t;
        }
        t = this.queue.remove();
        return t;
    }
}

