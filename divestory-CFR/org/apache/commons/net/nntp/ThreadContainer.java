/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.nntp;

import org.apache.commons.net.nntp.Threadable;

class ThreadContainer {
    ThreadContainer child;
    ThreadContainer next;
    ThreadContainer parent;
    Threadable threadable;

    ThreadContainer() {
    }

    boolean findChild(ThreadContainer threadContainer) {
        ThreadContainer threadContainer2 = this.child;
        if (threadContainer2 == null) {
            return false;
        }
        if (threadContainer2 != threadContainer) return threadContainer2.findChild(threadContainer);
        return true;
    }

    void flush() {
        Object object;
        if (this.parent != null && this.threadable == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("no threadable in ");
            stringBuilder.append(this.toString());
            throw new RuntimeException(stringBuilder.toString());
        }
        this.parent = null;
        Threadable threadable = this.threadable;
        if (threadable != null) {
            object = this.child;
            object = object == null ? null : ((ThreadContainer)object).threadable;
            threadable.setChild((Threadable)object);
        }
        if ((object = this.child) != null) {
            ((ThreadContainer)object).flush();
            this.child = null;
        }
        if ((threadable = this.threadable) != null) {
            object = this.next;
            object = object == null ? null : ((ThreadContainer)object).threadable;
            threadable.setNext((Threadable)object);
        }
        if ((object = this.next) != null) {
            ((ThreadContainer)object).flush();
            this.next = null;
        }
        this.threadable = null;
    }

    void reverseChildren() {
        ThreadContainer threadContainer = this.child;
        if (threadContainer == null) return;
        ThreadContainer threadContainer2 = threadContainer.next;
        ThreadContainer threadContainer3 = null;
        while (threadContainer != null) {
            threadContainer.next = threadContainer3;
            threadContainer3 = threadContainer2 == null ? null : threadContainer2.next;
            ThreadContainer threadContainer4 = threadContainer;
            threadContainer = threadContainer2;
            threadContainer2 = threadContainer3;
            threadContainer3 = threadContainer4;
        }
        this.child = threadContainer3;
        while (threadContainer3 != null) {
            threadContainer3.reverseChildren();
            threadContainer3 = threadContainer3.next;
        }
    }
}

