/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.nntp;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.net.nntp.ThreadContainer;
import org.apache.commons.net.nntp.Threadable;

public class Threader {
    private void buildContainer(Threadable object, HashMap<String, ThreadContainer> object2) {
        Object object3;
        Object object4;
        Object object5;
        block15 : {
            block16 : {
                object5 = object.messageThreadId();
                String[] arrstring = ((HashMap)object2).get(object5);
                int n = 0;
                object4 = object5;
                object3 = arrstring;
                if (arrstring != null) {
                    if (arrstring.threadable != null) {
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("<Bogus-id:");
                        ((StringBuilder)object3).append(0);
                        ((StringBuilder)object3).append(">");
                        object4 = ((StringBuilder)object3).toString();
                        object3 = null;
                    } else {
                        arrstring.threadable = object;
                        object3 = arrstring;
                        object4 = object5;
                    }
                }
                object5 = object3;
                if (object3 == null) {
                    object5 = new ThreadContainer();
                    ((ThreadContainer)object5).threadable = object;
                    ((HashMap)object2).put((String)object4, (ThreadContainer)object5);
                }
                arrstring = object.messageThreadReferences();
                int n2 = arrstring.length;
                object = null;
                while (n < n2) {
                    String string2 = arrstring[n];
                    object3 = object4 = (ThreadContainer)((HashMap)object2).get(string2);
                    if (object4 == null) {
                        object3 = new ThreadContainer();
                        ((HashMap)object2).put(string2, object3);
                    }
                    if (object != null && ((ThreadContainer)object3).parent == null && object != object3 && !((ThreadContainer)object3).findChild((ThreadContainer)object)) {
                        ((ThreadContainer)object3).parent = object;
                        ((ThreadContainer)object3).next = ((ThreadContainer)object).child;
                        ((ThreadContainer)object).child = object3;
                    }
                    ++n;
                    object = object3;
                }
                object2 = object;
                if (object == null) break block15;
                if (object == object5) break block16;
                object2 = object;
                if (!((ThreadContainer)object5).findChild((ThreadContainer)object)) break block15;
            }
            object2 = null;
        }
        if (((ThreadContainer)object5).parent != null) {
            object = object5.parent.child;
            object3 = null;
            while (object != null && object != object5) {
                object4 = ((ThreadContainer)object).next;
                object3 = object;
                object = object4;
            }
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Didnt find ");
                ((StringBuilder)object).append(object5);
                ((StringBuilder)object).append(" in parent");
                ((StringBuilder)object).append(((ThreadContainer)object5).parent);
                throw new RuntimeException(((StringBuilder)object).toString());
            }
            if (object3 == null) {
                object5.parent.child = ((ThreadContainer)object5).next;
            } else {
                ((ThreadContainer)object3).next = ((ThreadContainer)object5).next;
            }
            ((ThreadContainer)object5).next = null;
            ((ThreadContainer)object5).parent = null;
        }
        if (object2 == null) return;
        ((ThreadContainer)object5).parent = object2;
        ((ThreadContainer)object5).next = ((ThreadContainer)object2).child;
        ((ThreadContainer)object2).child = object5;
    }

    private ThreadContainer findRootSet(HashMap<String, ThreadContainer> serializable) {
        ThreadContainer threadContainer = new ThreadContainer();
        Iterator<String> iterator2 = ((HashMap)serializable).keySet().iterator();
        while (iterator2.hasNext()) {
            ThreadContainer threadContainer2 = ((HashMap)serializable).get(iterator2.next());
            if (threadContainer2.parent != null) continue;
            if (threadContainer2.next != null) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("c.next is ");
                ((StringBuilder)serializable).append(threadContainer2.next.toString());
                throw new RuntimeException(((StringBuilder)serializable).toString());
            }
            threadContainer2.next = threadContainer.child;
            threadContainer.child = threadContainer2;
        }
        return threadContainer;
    }

    private void gatherSubjects(ThreadContainer threadContainer) {
        Object object;
        Object object2;
        Object object3 = threadContainer.child;
        int n = 0;
        int n2 = 0;
        while (object3 != null) {
            ++n2;
            object3 = ((ThreadContainer)object3).next;
        }
        HashMap<Object, ThreadContainer> hashMap = new HashMap<Object, ThreadContainer>((int)((double)n2 * 1.2), 0.9f);
        object3 = threadContainer.child;
        while (object3 != null) {
            block22 : {
                block24 : {
                    block23 : {
                        object = object2 = ((ThreadContainer)object3).threadable;
                        if (object2 == null) {
                            object = object3.child.threadable;
                        }
                        object2 = object.simplifiedSubject();
                        n2 = n;
                        if (object2 == null) break block22;
                        if (((String)object2).length() != 0) break block23;
                        n2 = n;
                        break block22;
                    }
                    object = (ThreadContainer)hashMap.get(object2);
                    if (object == null || ((ThreadContainer)object3).threadable == null && ((ThreadContainer)object).threadable != null) break block24;
                    n2 = n;
                    if (((ThreadContainer)object).threadable == null) break block22;
                    n2 = n;
                    if (!((ThreadContainer)object).threadable.subjectIsReply()) break block22;
                    n2 = n;
                    if (((ThreadContainer)object3).threadable == null) break block22;
                    n2 = n;
                    if (((ThreadContainer)object3).threadable.subjectIsReply()) break block22;
                }
                hashMap.put(object2, (ThreadContainer)object3);
                n2 = n + 1;
            }
            object3 = ((ThreadContainer)object3).next;
            n = n2;
        }
        if (n == 0) {
            return;
        }
        object3 = threadContainer.child;
        object = ((ThreadContainer)object3).next;
        object2 = null;
        do {
            Object object4;
            if (object3 == null) {
                hashMap.clear();
                return;
            }
            Object object5 = object4 = ((ThreadContainer)object3).threadable;
            if (object4 == null) {
                object5 = object3.child.threadable;
            }
            if ((object5 = object5.simplifiedSubject()) != null && ((String)object5).length() != 0 && (object4 = (ThreadContainer)hashMap.get(object5)) != object3) {
                if (object2 == null) {
                    threadContainer.child = ((ThreadContainer)object3).next;
                } else {
                    ((ThreadContainer)object2).next = ((ThreadContainer)object3).next;
                }
                ((ThreadContainer)object3).next = null;
                if (((ThreadContainer)object4).threadable == null && ((ThreadContainer)object3).threadable == null) {
                    object5 = ((ThreadContainer)object4).child;
                    while (object5 != null && ((ThreadContainer)object5).next != null) {
                        object5 = ((ThreadContainer)object5).next;
                    }
                    if (object5 != null) {
                        ((ThreadContainer)object5).next = ((ThreadContainer)object3).child;
                    }
                    object5 = ((ThreadContainer)object3).child;
                    while (object5 != null) {
                        ((ThreadContainer)object5).parent = object4;
                        object5 = ((ThreadContainer)object5).next;
                    }
                    ((ThreadContainer)object3).child = null;
                } else if (((ThreadContainer)object4).threadable != null && (((ThreadContainer)object3).threadable == null || !((ThreadContainer)object3).threadable.subjectIsReply() || ((ThreadContainer)object4).threadable.subjectIsReply())) {
                    ThreadContainer threadContainer2 = new ThreadContainer();
                    threadContainer2.threadable = ((ThreadContainer)object4).threadable;
                    threadContainer2.child = ((ThreadContainer)object4).child;
                    object5 = threadContainer2.child;
                    while (object5 != null) {
                        ((ThreadContainer)object5).parent = threadContainer2;
                        object5 = ((ThreadContainer)object5).next;
                    }
                    ((ThreadContainer)object4).threadable = null;
                    ((ThreadContainer)object4).child = null;
                    ((ThreadContainer)object3).parent = object4;
                    threadContainer2.parent = object4;
                    ((ThreadContainer)object4).child = object3;
                    ((ThreadContainer)object3).next = threadContainer2;
                } else {
                    ((ThreadContainer)object3).parent = object4;
                    ((ThreadContainer)object3).next = ((ThreadContainer)object4).child;
                    ((ThreadContainer)object4).child = object3;
                }
            } else {
                object2 = object3;
            }
            object3 = object == null ? null : ((ThreadContainer)object).next;
            object5 = object3;
            object3 = object;
            object = object5;
        } while (true);
    }

    private void pruneEmptyContainers(ThreadContainer threadContainer) {
        ThreadContainer threadContainer2 = threadContainer.child;
        ThreadContainer threadContainer3 = threadContainer2.next;
        ThreadContainer threadContainer4 = null;
        while (threadContainer2 != null) {
            ThreadContainer threadContainer5;
            if (threadContainer2.threadable == null && threadContainer2.child == null) {
                if (threadContainer4 == null) {
                    threadContainer.child = threadContainer2.next;
                } else {
                    threadContainer4.next = threadContainer2.next;
                }
            } else if (threadContainer2.threadable == null && threadContainer2.child != null && (threadContainer2.parent != null || threadContainer2.child.next == null)) {
                threadContainer3 = threadContainer2.child;
                if (threadContainer4 == null) {
                    threadContainer.child = threadContainer3;
                } else {
                    threadContainer4.next = threadContainer3;
                }
                threadContainer5 = threadContainer3;
                while (threadContainer5.next != null) {
                    threadContainer5.parent = threadContainer2.parent;
                    threadContainer5 = threadContainer5.next;
                }
                threadContainer5.parent = threadContainer2.parent;
                threadContainer5.next = threadContainer2.next;
            } else {
                if (threadContainer2.child != null) {
                    this.pruneEmptyContainers(threadContainer2);
                }
                threadContainer4 = threadContainer2;
            }
            if (threadContainer3 == null) {
                threadContainer5 = null;
                threadContainer2 = threadContainer3;
                threadContainer3 = threadContainer5;
                continue;
            }
            threadContainer5 = threadContainer3.next;
            threadContainer2 = threadContainer3;
            threadContainer3 = threadContainer5;
        }
    }

    public Threadable thread(Iterable<? extends Threadable> object) {
        Object var2_2 = null;
        if (object == null) {
            return null;
        }
        HashMap<String, ThreadContainer> hashMap = new HashMap<String, ThreadContainer>();
        Object object2 = object.iterator();
        while (object2.hasNext()) {
            object = object2.next();
            if (object.isDummy()) continue;
            this.buildContainer((Threadable)object, hashMap);
        }
        if (hashMap.isEmpty()) {
            return null;
        }
        object2 = this.findRootSet(hashMap);
        hashMap.clear();
        this.pruneEmptyContainers((ThreadContainer)object2);
        ((ThreadContainer)object2).reverseChildren();
        this.gatherSubjects((ThreadContainer)object2);
        if (((ThreadContainer)object2).next != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("root node has a next:");
            ((StringBuilder)object).append(object2);
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        object = ((ThreadContainer)object2).child;
        while (object != null) {
            if (((ThreadContainer)object).threadable == null) {
                ((ThreadContainer)object).threadable = object.child.threadable.makeDummy();
            }
            object = ((ThreadContainer)object).next;
        }
        object = ((ThreadContainer)object2).child == null ? var2_2 : object2.child.threadable;
        ((ThreadContainer)object2).flush();
        return object;
    }

    public Threadable thread(List<? extends Threadable> list) {
        return this.thread((Iterable<? extends Threadable>)list);
    }

    @Deprecated
    public Threadable thread(Threadable[] arrthreadable) {
        if (arrthreadable != null) return this.thread((List<? extends Threadable>)Arrays.asList(arrthreadable));
        return null;
    }
}

