/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphConnections;
import com.google.common.graph.Graphs;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class DirectedGraphConnections<N, V>
implements GraphConnections<N, V> {
    private static final Object PRED = new Object();
    private final Map<N, Object> adjacentNodeValues;
    @NullableDecl
    private final List<NodeConnection<N>> orderedNodeConnections;
    private int predecessorCount;
    private int successorCount;

    private DirectedGraphConnections(Map<N, Object> map, @NullableDecl List<NodeConnection<N>> list, int n, int n2) {
        this.adjacentNodeValues = Preconditions.checkNotNull(map);
        this.orderedNodeConnections = list;
        this.predecessorCount = Graphs.checkNonNegative(n);
        this.successorCount = Graphs.checkNonNegative(n2);
        boolean bl = n <= map.size() && n2 <= map.size();
        Preconditions.checkState(bl);
    }

    private static boolean isPredecessor(@NullableDecl Object object) {
        if (object == PRED) return true;
        if (object instanceof PredAndSucc) return true;
        return false;
    }

    private static boolean isSuccessor(@NullableDecl Object object) {
        if (object == PRED) return false;
        if (object == null) return false;
        return true;
    }

    static <N, V> DirectedGraphConnections<N, V> of(ElementOrder<N> object) {
        int n = 8.$SwitchMap$com$google$common$graph$ElementOrder$Type[((ElementOrder)object).type().ordinal()];
        if (n != 1) {
            if (n != 2) throw new AssertionError((Object)((ElementOrder)object).type());
            object = new ArrayList();
            return new DirectedGraphConnections(new HashMap(4, 1.0f), (List<NodeConnection<N>>)object, 0, 0);
        }
        object = null;
        return new DirectedGraphConnections(new HashMap(4, 1.0f), (List<NodeConnection<N>>)object, 0, 0);
    }

    static <N, V> DirectedGraphConnections<N, V> ofImmutable(N n, Iterable<EndpointPair<N>> object, Function<N, V> function) {
        HashMap hashMap = new HashMap();
        ImmutableList.Builder builder = ImmutableList.builder();
        object = object.iterator();
        int n2 = 0;
        int n3 = 0;
        while (object.hasNext()) {
            Object object2 = (EndpointPair)object.next();
            if (((EndpointPair)object2).nodeU().equals(n) && ((EndpointPair)object2).nodeV().equals(n)) {
                hashMap.put(n, new PredAndSucc(function.apply(n)));
                builder.add(new NodeConnection.Pred<N>(n));
                builder.add(new NodeConnection.Succ<N>(n));
                ++n2;
            } else {
                Object object3;
                if (((EndpointPair)object2).nodeV().equals(n)) {
                    object3 = ((EndpointPair)object2).nodeU();
                    object2 = hashMap.put(object3, PRED);
                    if (object2 != null) {
                        hashMap.put(object3, new PredAndSucc(object2));
                    }
                    builder.add(new NodeConnection.Pred(object3));
                    ++n2;
                    continue;
                }
                Preconditions.checkArgument(((EndpointPair)object2).nodeU().equals(n));
                Object n4 = ((EndpointPair)object2).nodeV();
                object2 = function.apply(n4);
                object3 = hashMap.put(n4, object2);
                if (object3 != null) {
                    boolean bl = object3 == PRED;
                    Preconditions.checkArgument(bl);
                    hashMap.put(n4, new PredAndSucc(object2));
                }
                builder.add(new NodeConnection.Succ(n4));
            }
            ++n3;
        }
        return new DirectedGraphConnections(hashMap, (List<NodeConnection<N>>)builder.build(), n2, n3);
    }

    @Override
    public void addPredecessor(N n, V object) {
        int n2;
        block4 : {
            block2 : {
                block3 : {
                    object = this.adjacentNodeValues.put(n, PRED);
                    n2 = 0;
                    if (object == null) break block2;
                    if (!(object instanceof PredAndSucc)) break block3;
                    this.adjacentNodeValues.put(n, object);
                    break block4;
                }
                if (object == PRED) break block4;
                this.adjacentNodeValues.put(n, new PredAndSucc(object));
            }
            n2 = 1;
        }
        if (n2 == 0) return;
        this.predecessorCount = n2 = this.predecessorCount + 1;
        Graphs.checkPositive(n2);
        object = this.orderedNodeConnections;
        if (object == null) return;
        object.add(new NodeConnection.Pred<N>(n));
    }

    @Override
    public V addSuccessor(N n, V object) {
        Object object2;
        int n2;
        block4 : {
            block2 : {
                Object object3;
                block3 : {
                    object3 = this.adjacentNodeValues.put(n, object);
                    if (object3 == null) break block2;
                    if (!(object3 instanceof PredAndSucc)) break block3;
                    this.adjacentNodeValues.put(n, new PredAndSucc(object));
                    object2 = ((PredAndSucc)object3).successorValue;
                    break block4;
                }
                object2 = object3;
                if (object3 != PRED) break block4;
                this.adjacentNodeValues.put(n, new PredAndSucc(object));
            }
            object2 = null;
        }
        if (object2 != null) return (V)object2;
        this.successorCount = n2 = this.successorCount + 1;
        Graphs.checkPositive(n2);
        object = this.orderedNodeConnections;
        if (object == null) return (V)object2;
        object.add(new NodeConnection.Succ<N>(n));
        return (V)object2;
    }

    @Override
    public Set<N> adjacentNodes() {
        if (this.orderedNodeConnections != null) return new AbstractSet<N>(){

            @Override
            public boolean contains(@NullableDecl Object object) {
                return DirectedGraphConnections.this.adjacentNodeValues.containsKey(object);
            }

            @Override
            public UnmodifiableIterator<N> iterator() {
                return new AbstractIterator<N>(DirectedGraphConnections.this.orderedNodeConnections.iterator(), new HashSet()){
                    final /* synthetic */ Iterator val$nodeConnections;
                    final /* synthetic */ Set val$seenNodes;
                    {
                        this.val$nodeConnections = iterator2;
                        this.val$seenNodes = set;
                    }

                    @Override
                    protected N computeNext() {
                        NodeConnection nodeConnection;
                        do {
                            if (!this.val$nodeConnections.hasNext()) return (N)this.endOfData();
                            nodeConnection = (NodeConnection)this.val$nodeConnections.next();
                        } while (!this.val$seenNodes.add(nodeConnection.node));
                        return nodeConnection.node;
                    }
                };
            }

            @Override
            public int size() {
                return DirectedGraphConnections.this.adjacentNodeValues.size();
            }

        };
        return Collections.unmodifiableSet(this.adjacentNodeValues.keySet());
    }

    @Override
    public Iterator<EndpointPair<N>> incidentEdgeIterator(final N object) {
        List<NodeConnection<N>> list = this.orderedNodeConnections;
        if (list == null) {
            object = Iterators.concat(Iterators.transform(this.predecessors().iterator(), new Function<N, EndpointPair<N>>(){

                @Override
                public EndpointPair<N> apply(N n) {
                    return EndpointPair.ordered(n, object);
                }
            }), Iterators.transform(this.successors().iterator(), new Function<N, EndpointPair<N>>(){

                @Override
                public EndpointPair<N> apply(N n) {
                    return EndpointPair.ordered(object, n);
                }
            }));
            return new AbstractIterator<EndpointPair<N>>((Iterator)object, new AtomicBoolean(false)){
                final /* synthetic */ AtomicBoolean val$alreadySeenSelfLoop;
                final /* synthetic */ Iterator val$resultWithDoubleSelfLoop;
                {
                    this.val$resultWithDoubleSelfLoop = iterator2;
                    this.val$alreadySeenSelfLoop = atomicBoolean;
                }

                @Override
                protected EndpointPair<N> computeNext() {
                    EndpointPair endpointPair;
                    do {
                        if (!this.val$resultWithDoubleSelfLoop.hasNext()) return (EndpointPair)this.endOfData();
                        endpointPair = (EndpointPair)this.val$resultWithDoubleSelfLoop.next();
                        if (!endpointPair.nodeU().equals(endpointPair.nodeV())) return endpointPair;
                    } while (this.val$alreadySeenSelfLoop.getAndSet(true));
                    return endpointPair;
                }
            };
        }
        object = Iterators.transform(list.iterator(), new Function<NodeConnection<N>, EndpointPair<N>>(){

            @Override
            public EndpointPair<N> apply(NodeConnection<N> nodeConnection) {
                if (!(nodeConnection instanceof NodeConnection.Succ)) return EndpointPair.ordered(nodeConnection.node, object);
                return EndpointPair.ordered(object, nodeConnection.node);
            }
        });
        return new /* invalid duplicate definition of identical inner class */;
    }

    @Override
    public Set<N> predecessors() {
        return new AbstractSet<N>(){

            @Override
            public boolean contains(@NullableDecl Object object) {
                return DirectedGraphConnections.isPredecessor(DirectedGraphConnections.this.adjacentNodeValues.get(object));
            }

            @Override
            public UnmodifiableIterator<N> iterator() {
                if (DirectedGraphConnections.this.orderedNodeConnections != null) return new AbstractIterator<N>(DirectedGraphConnections.this.orderedNodeConnections.iterator()){
                    final /* synthetic */ Iterator val$nodeConnections;
                    {
                        this.val$nodeConnections = iterator2;
                    }

                    @Override
                    protected N computeNext() {
                        NodeConnection nodeConnection;
                        do {
                            if (!this.val$nodeConnections.hasNext()) return (N)this.endOfData();
                        } while (!((nodeConnection = (NodeConnection)this.val$nodeConnections.next()) instanceof NodeConnection.Pred));
                        return nodeConnection.node;
                    }
                };
                return new AbstractIterator<N>(DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator()){
                    final /* synthetic */ Iterator val$entries;
                    {
                        this.val$entries = iterator2;
                    }

                    @Override
                    protected N computeNext() {
                        Map.Entry entry;
                        do {
                            if (!this.val$entries.hasNext()) return (N)this.endOfData();
                        } while (!DirectedGraphConnections.isPredecessor((entry = (Map.Entry)this.val$entries.next()).getValue()));
                        return (N)entry.getKey();
                    }
                };
            }

            @Override
            public int size() {
                return DirectedGraphConnections.this.predecessorCount;
            }

        };
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void removePredecessor(N var1_1) {
        block2 : {
            var2_2 = this.adjacentNodeValues.get(var1_1);
            if (var2_2 != DirectedGraphConnections.PRED) break block2;
            this.adjacentNodeValues.remove(var1_1);
            ** GOTO lbl10
        }
        if (var2_2 instanceof PredAndSucc) {
            this.adjacentNodeValues.put(var1_1, PredAndSucc.access$600((PredAndSucc)var2_2));
lbl10: // 2 sources:
            var3_3 = 1;
        } else {
            var3_3 = 0;
        }
        if (var3_3 == 0) return;
        this.predecessorCount = var3_3 = this.predecessorCount - 1;
        Graphs.checkNonNegative(var3_3);
        var2_2 = this.orderedNodeConnections;
        if (var2_2 == null) return;
        var2_2.remove(new NodeConnection.Pred<N>(var1_1));
    }

    @Override
    public V removeSuccessor(Object object) {
        int n;
        List<NodeConnection<N>> list;
        Object object2 = this.adjacentNodeValues.get(object);
        if (object2 != null && object2 != (list = PRED)) {
            if (object2 instanceof PredAndSucc) {
                this.adjacentNodeValues.put(object, list);
                object2 = ((PredAndSucc)object2).successorValue;
            } else {
                this.adjacentNodeValues.remove(object);
            }
        } else {
            object2 = null;
        }
        if (object2 == null) return (V)object2;
        this.successorCount = n = this.successorCount - 1;
        Graphs.checkNonNegative(n);
        list = this.orderedNodeConnections;
        if (list == null) return (V)object2;
        list.remove(new NodeConnection.Succ<Object>(object));
        return (V)object2;
    }

    @Override
    public Set<N> successors() {
        return new AbstractSet<N>(){

            @Override
            public boolean contains(@NullableDecl Object object) {
                return DirectedGraphConnections.isSuccessor(DirectedGraphConnections.this.adjacentNodeValues.get(object));
            }

            @Override
            public UnmodifiableIterator<N> iterator() {
                if (DirectedGraphConnections.this.orderedNodeConnections != null) return new AbstractIterator<N>(DirectedGraphConnections.this.orderedNodeConnections.iterator()){
                    final /* synthetic */ Iterator val$nodeConnections;
                    {
                        this.val$nodeConnections = iterator2;
                    }

                    @Override
                    protected N computeNext() {
                        NodeConnection nodeConnection;
                        do {
                            if (!this.val$nodeConnections.hasNext()) return (N)this.endOfData();
                        } while (!((nodeConnection = (NodeConnection)this.val$nodeConnections.next()) instanceof NodeConnection.Succ));
                        return nodeConnection.node;
                    }
                };
                return new AbstractIterator<N>(DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator()){
                    final /* synthetic */ Iterator val$entries;
                    {
                        this.val$entries = iterator2;
                    }

                    @Override
                    protected N computeNext() {
                        Map.Entry entry;
                        do {
                            if (!this.val$entries.hasNext()) return (N)this.endOfData();
                        } while (!DirectedGraphConnections.isSuccessor((entry = (Map.Entry)this.val$entries.next()).getValue()));
                        return (N)entry.getKey();
                    }
                };
            }

            @Override
            public int size() {
                return DirectedGraphConnections.this.successorCount;
            }

        };
    }

    @Override
    public V value(N object) {
        Object object2 = this.adjacentNodeValues.get(object);
        if (object2 == PRED) {
            return null;
        }
        object = object2;
        if (!(object2 instanceof PredAndSucc)) return (V)object;
        object = ((PredAndSucc)object2).successorValue;
        return (V)object;
    }

    private static abstract class NodeConnection<N> {
        final N node;

        NodeConnection(N n) {
            this.node = Preconditions.checkNotNull(n);
        }

        static final class Pred<N>
        extends NodeConnection<N> {
            Pred(N n) {
                super(n);
            }

            public boolean equals(Object object) {
                if (!(object instanceof Pred)) return false;
                return this.node.equals(((Pred)object).node);
            }

            public int hashCode() {
                return Pred.class.hashCode() + this.node.hashCode();
            }
        }

        static final class Succ<N>
        extends NodeConnection<N> {
            Succ(N n) {
                super(n);
            }

            public boolean equals(Object object) {
                if (!(object instanceof Succ)) return false;
                return this.node.equals(((Succ)object).node);
            }

            public int hashCode() {
                return Succ.class.hashCode() + this.node.hashCode();
            }
        }

    }

    private static final class PredAndSucc {
        private final Object successorValue;

        PredAndSucc(Object object) {
            this.successorValue = object;
        }
    }

}

