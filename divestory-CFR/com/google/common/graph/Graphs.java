/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.graph.BaseGraph;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.ForwardingGraph;
import com.google.common.graph.ForwardingNetwork;
import com.google.common.graph.ForwardingValueGraph;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.IncidentEdgeSet;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.Network;
import com.google.common.graph.NetworkBuilder;
import com.google.common.graph.Traverser;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Graphs {
    private Graphs() {
    }

    private static boolean canTraverseWithoutReusingEdge(Graph<?> graph, Object object, @NullableDecl Object object2) {
        if (graph.isDirected()) return true;
        if (Objects.equal(object2, object)) return false;
        return true;
    }

    static int checkNonNegative(int n) {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl, "Not true that %s is non-negative.", n);
        return n;
    }

    static long checkNonNegative(long l) {
        boolean bl = l >= 0L;
        Preconditions.checkArgument(bl, "Not true that %s is non-negative.", l);
        return l;
    }

    static int checkPositive(int n) {
        boolean bl = n > 0;
        Preconditions.checkArgument(bl, "Not true that %s is positive.", n);
        return n;
    }

    static long checkPositive(long l) {
        boolean bl = l > 0L;
        Preconditions.checkArgument(bl, "Not true that %s is positive.", l);
        return l;
    }

    public static <N> MutableGraph<N> copyOf(Graph<N> object) {
        MutableGraph mutableGraph = GraphBuilder.from(object).expectedNodeCount(object.nodes().size()).build();
        Object object2 = object.nodes().iterator();
        while (object2.hasNext()) {
            mutableGraph.addNode(object2.next());
        }
        object = object.edges().iterator();
        while (object.hasNext()) {
            object2 = (EndpointPair)object.next();
            mutableGraph.putEdge(((EndpointPair)object2).nodeU(), ((EndpointPair)object2).nodeV());
        }
        return mutableGraph;
    }

    public static <N, E> MutableNetwork<N, E> copyOf(Network<N, E> network) {
        MutableNetwork mutableNetwork = NetworkBuilder.from(network).expectedNodeCount(network.nodes().size()).expectedEdgeCount(network.edges().size()).build();
        Iterator<Object> iterator2 = network.nodes().iterator();
        while (iterator2.hasNext()) {
            mutableNetwork.addNode(iterator2.next());
        }
        iterator2 = network.edges().iterator();
        while (iterator2.hasNext()) {
            Object object = iterator2.next();
            EndpointPair<N> endpointPair = network.incidentNodes(object);
            mutableNetwork.addEdge(endpointPair.nodeU(), endpointPair.nodeV(), object);
        }
        return mutableNetwork;
    }

    public static <N, V> MutableValueGraph<N, V> copyOf(ValueGraph<N, V> valueGraph) {
        MutableValueGraph mutableValueGraph = ValueGraphBuilder.from(valueGraph).expectedNodeCount(valueGraph.nodes().size()).build();
        Object object = valueGraph.nodes().iterator();
        while (object.hasNext()) {
            mutableValueGraph.addNode(object.next());
        }
        Iterator<EndpointPair<N>> iterator2 = valueGraph.edges().iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            mutableValueGraph.putEdgeValue(((EndpointPair)object).nodeU(), ((EndpointPair)object).nodeV(), valueGraph.edgeValueOrDefault(((EndpointPair)object).nodeU(), ((EndpointPair)object).nodeV(), null));
        }
        return mutableValueGraph;
    }

    public static <N> boolean hasCycle(Graph<N> graph) {
        int n = graph.edges().size();
        if (n == 0) {
            return false;
        }
        if (!graph.isDirected() && n >= graph.nodes().size()) {
            return true;
        }
        HashMap<Object, NodeVisitState> hashMap = Maps.newHashMapWithExpectedSize(graph.nodes().size());
        Iterator<N> iterator2 = graph.nodes().iterator();
        do {
            if (!iterator2.hasNext()) return false;
        } while (!Graphs.subgraphHasCycle(graph, hashMap, iterator2.next(), null));
        return true;
    }

    public static boolean hasCycle(Network<?, ?> network) {
        if (network.isDirected()) return Graphs.hasCycle(network.asGraph());
        if (!network.allowsParallelEdges()) return Graphs.hasCycle(network.asGraph());
        if (network.edges().size() <= network.asGraph().edges().size()) return Graphs.hasCycle(network.asGraph());
        return true;
    }

    public static <N> MutableGraph<N> inducedSubgraph(Graph<N> graph, Iterable<? extends N> object) {
        MutableGraph mutableGraph = object instanceof Collection ? GraphBuilder.from(graph).expectedNodeCount(((Collection)object).size()).build() : GraphBuilder.from(graph).build();
        object = object.iterator();
        while (object.hasNext()) {
            mutableGraph.addNode(object.next());
        }
        Iterator iterator2 = mutableGraph.nodes().iterator();
        block1 : while (iterator2.hasNext()) {
            Object n = iterator2.next();
            Iterator<N> iterator3 = graph.successors(n).iterator();
            do {
                if (!iterator3.hasNext()) continue block1;
                object = iterator3.next();
                if (!mutableGraph.nodes().contains(object)) continue;
                mutableGraph.putEdge(n, object);
            } while (true);
            break;
        }
        return mutableGraph;
    }

    public static <N, E> MutableNetwork<N, E> inducedSubgraph(Network<N, E> network, Iterable<? extends N> iterator2) {
        MutableNetwork mutableNetwork = iterator2 instanceof Collection ? NetworkBuilder.from(network).expectedNodeCount(((Collection)((Object)iterator2)).size()).build() : NetworkBuilder.from(network).build();
        iterator2 = iterator2.iterator();
        while (iterator2.hasNext()) {
            mutableNetwork.addNode(iterator2.next());
        }
        Iterator iterator3 = mutableNetwork.nodes().iterator();
        block1 : while (iterator3.hasNext()) {
            Object n = iterator3.next();
            iterator2 = network.outEdges(n).iterator();
            do {
                if (!iterator2.hasNext()) continue block1;
                E e = iterator2.next();
                N n2 = network.incidentNodes(e).adjacentNode(n);
                if (!mutableNetwork.nodes().contains(n2)) continue;
                mutableNetwork.addEdge(n, n2, e);
            } while (true);
            break;
        }
        return mutableNetwork;
    }

    public static <N, V> MutableValueGraph<N, V> inducedSubgraph(ValueGraph<N, V> valueGraph, Iterable<? extends N> iterator2) {
        MutableValueGraph mutableValueGraph = iterator2 instanceof Collection ? ValueGraphBuilder.from(valueGraph).expectedNodeCount(((Collection)((Object)iterator2)).size()).build() : ValueGraphBuilder.from(valueGraph).build();
        iterator2 = iterator2.iterator();
        while (iterator2.hasNext()) {
            mutableValueGraph.addNode(iterator2.next());
        }
        Iterator iterator3 = mutableValueGraph.nodes().iterator();
        block1 : while (iterator3.hasNext()) {
            Object n = iterator3.next();
            iterator2 = valueGraph.successors(n).iterator();
            do {
                if (!iterator2.hasNext()) continue block1;
                N n2 = iterator2.next();
                if (!mutableValueGraph.nodes().contains(n2)) continue;
                mutableValueGraph.putEdgeValue(n, n2, valueGraph.edgeValueOrDefault(n, n2, null));
            } while (true);
            break;
        }
        return mutableValueGraph;
    }

    public static <N> Set<N> reachableNodes(Graph<N> graph, N n) {
        Preconditions.checkArgument(graph.nodes().contains(n), "Node %s is not an element of this graph.", n);
        return ImmutableSet.copyOf(Traverser.forGraph(graph).breadthFirst(n));
    }

    private static <N> boolean subgraphHasCycle(Graph<N> graph, Map<Object, NodeVisitState> map, N n, @NullableDecl N n2) {
        Object e;
        Object object = map.get(n);
        if (object == NodeVisitState.COMPLETE) {
            return false;
        }
        if (object == NodeVisitState.PENDING) {
            return true;
        }
        map.put(n, NodeVisitState.PENDING);
        object = graph.successors(n).iterator();
        do {
            if (object.hasNext()) continue;
            map.put(n, NodeVisitState.COMPLETE);
            return false;
        } while (!Graphs.canTraverseWithoutReusingEdge(graph, e = object.next(), n2) || !Graphs.subgraphHasCycle(graph, map, e, n));
        return true;
    }

    /*
     * Unable to fully structure code
     */
    public static <N> Graph<N> transitiveClosure(Graph<N> var0) {
        var1_1 = GraphBuilder.from(var0).allowsSelfLoops(true).build();
        if (var0.isDirected()) {
            var2_2 = var0.nodes().iterator();
            block0 : do {
                if (var2_2.hasNext() == false) return var1_1;
                var3_6 = var2_2.next();
                var4_8 = Graphs.reachableNodes(var0, var3_6).iterator();
                do {
                    if (!var4_8.hasNext()) continue block0;
                    var1_1.putEdge(var3_6, var4_8.next());
                } while (true);
                break;
            } while (true);
        }
        var4_9 = new HashSet<N>();
        var3_7 = var0.nodes().iterator();
        block2 : do lbl-1000: // 3 sources:
        {
            if (var3_7.hasNext() == false) return var1_1;
            var2_4 = var3_7.next();
            if (var4_9.contains(var2_4)) ** GOTO lbl-1000
            var5_10 = Graphs.reachableNodes(var0, var2_4);
            var4_9.addAll(var5_10);
            var6_11 = var5_10.iterator();
            var7_12 = 1;
            do {
                if (!var6_11.hasNext()) continue block2;
                var8_13 = var6_11.next();
                var2_5 = Iterables.limit(var5_10, var7_12).iterator();
                while (var2_5.hasNext()) {
                    var1_1.putEdge(var8_13, var2_5.next());
                }
                ++var7_12;
            } while (true);
            break;
        } while (true);
    }

    static <N> EndpointPair<N> transpose(EndpointPair<N> endpointPair) {
        EndpointPair<N> endpointPair2 = endpointPair;
        if (!endpointPair.isOrdered()) return endpointPair2;
        return EndpointPair.ordered(endpointPair.target(), endpointPair.source());
    }

    public static <N> Graph<N> transpose(Graph<N> graph) {
        if (!graph.isDirected()) {
            return graph;
        }
        if (!(graph instanceof TransposedGraph)) return new TransposedGraph<N>(graph);
        return ((TransposedGraph)graph).graph;
    }

    public static <N, E> Network<N, E> transpose(Network<N, E> network) {
        if (!network.isDirected()) {
            return network;
        }
        if (!(network instanceof TransposedNetwork)) return new TransposedNetwork<N, E>(network);
        return ((TransposedNetwork)network).network;
    }

    public static <N, V> ValueGraph<N, V> transpose(ValueGraph<N, V> valueGraph) {
        if (!valueGraph.isDirected()) {
            return valueGraph;
        }
        if (!(valueGraph instanceof TransposedValueGraph)) return new TransposedValueGraph<N, V>(valueGraph);
        return ((TransposedValueGraph)valueGraph).graph;
    }

    private static final class NodeVisitState
    extends Enum<NodeVisitState> {
        private static final /* synthetic */ NodeVisitState[] $VALUES;
        public static final /* enum */ NodeVisitState COMPLETE;
        public static final /* enum */ NodeVisitState PENDING;

        static {
            NodeVisitState nodeVisitState;
            PENDING = new NodeVisitState();
            COMPLETE = nodeVisitState = new NodeVisitState();
            $VALUES = new NodeVisitState[]{PENDING, nodeVisitState};
        }

        public static NodeVisitState valueOf(String string2) {
            return Enum.valueOf(NodeVisitState.class, string2);
        }

        public static NodeVisitState[] values() {
            return (NodeVisitState[])$VALUES.clone();
        }
    }

    private static class TransposedGraph<N>
    extends ForwardingGraph<N> {
        private final Graph<N> graph;

        TransposedGraph(Graph<N> graph) {
            this.graph = graph;
        }

        @Override
        protected Graph<N> delegate() {
            return this.graph;
        }

        @Override
        public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
            return this.delegate().hasEdgeConnecting(Graphs.transpose(endpointPair));
        }

        @Override
        public boolean hasEdgeConnecting(N n, N n2) {
            return this.delegate().hasEdgeConnecting(n2, n);
        }

        @Override
        public int inDegree(N n) {
            return this.delegate().outDegree(n);
        }

        @Override
        public Set<EndpointPair<N>> incidentEdges(N n) {
            return new IncidentEdgeSet<N>(this, n){

                @Override
                public Iterator<EndpointPair<N>> iterator() {
                    return Iterators.transform(TransposedGraph.this.delegate().incidentEdges(this.node).iterator(), new Function<EndpointPair<N>, EndpointPair<N>>(){

                        @Override
                        public EndpointPair<N> apply(EndpointPair<N> endpointPair) {
                            return EndpointPair.of(TransposedGraph.this.delegate(), endpointPair.nodeV(), endpointPair.nodeU());
                        }
                    });
                }

            };
        }

        @Override
        public int outDegree(N n) {
            return this.delegate().inDegree(n);
        }

        @Override
        public Set<N> predecessors(N n) {
            return this.delegate().successors(n);
        }

        @Override
        public Set<N> successors(N n) {
            return this.delegate().predecessors(n);
        }

    }

    private static class TransposedNetwork<N, E>
    extends ForwardingNetwork<N, E> {
        private final Network<N, E> network;

        TransposedNetwork(Network<N, E> network) {
            this.network = network;
        }

        @Override
        protected Network<N, E> delegate() {
            return this.network;
        }

        @Override
        public E edgeConnectingOrNull(EndpointPair<N> endpointPair) {
            return this.delegate().edgeConnectingOrNull(Graphs.transpose(endpointPair));
        }

        @Override
        public E edgeConnectingOrNull(N n, N n2) {
            return this.delegate().edgeConnectingOrNull(n2, n);
        }

        @Override
        public Set<E> edgesConnecting(EndpointPair<N> endpointPair) {
            return this.delegate().edgesConnecting(Graphs.transpose(endpointPair));
        }

        @Override
        public Set<E> edgesConnecting(N n, N n2) {
            return this.delegate().edgesConnecting(n2, n);
        }

        @Override
        public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
            return this.delegate().hasEdgeConnecting(Graphs.transpose(endpointPair));
        }

        @Override
        public boolean hasEdgeConnecting(N n, N n2) {
            return this.delegate().hasEdgeConnecting(n2, n);
        }

        @Override
        public int inDegree(N n) {
            return this.delegate().outDegree(n);
        }

        @Override
        public Set<E> inEdges(N n) {
            return this.delegate().outEdges(n);
        }

        @Override
        public EndpointPair<N> incidentNodes(E object) {
            object = this.delegate().incidentNodes(object);
            return EndpointPair.of(this.network, ((EndpointPair)object).nodeV(), ((EndpointPair)object).nodeU());
        }

        @Override
        public int outDegree(N n) {
            return this.delegate().inDegree(n);
        }

        @Override
        public Set<E> outEdges(N n) {
            return this.delegate().inEdges(n);
        }

        @Override
        public Set<N> predecessors(N n) {
            return this.delegate().successors(n);
        }

        @Override
        public Set<N> successors(N n) {
            return this.delegate().predecessors(n);
        }
    }

    private static class TransposedValueGraph<N, V>
    extends ForwardingValueGraph<N, V> {
        private final ValueGraph<N, V> graph;

        TransposedValueGraph(ValueGraph<N, V> valueGraph) {
            this.graph = valueGraph;
        }

        @Override
        protected ValueGraph<N, V> delegate() {
            return this.graph;
        }

        @NullableDecl
        @Override
        public V edgeValueOrDefault(EndpointPair<N> endpointPair, @NullableDecl V v) {
            return this.delegate().edgeValueOrDefault(Graphs.transpose(endpointPair), v);
        }

        @NullableDecl
        @Override
        public V edgeValueOrDefault(N n, N n2, @NullableDecl V v) {
            return this.delegate().edgeValueOrDefault(n2, n, v);
        }

        @Override
        public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
            return this.delegate().hasEdgeConnecting(Graphs.transpose(endpointPair));
        }

        @Override
        public boolean hasEdgeConnecting(N n, N n2) {
            return this.delegate().hasEdgeConnecting(n2, n);
        }

        @Override
        public int inDegree(N n) {
            return this.delegate().outDegree(n);
        }

        @Override
        public int outDegree(N n) {
            return this.delegate().inDegree(n);
        }

        @Override
        public Set<N> predecessors(N n) {
            return this.delegate().successors(n);
        }

        @Override
        public Set<N> successors(N n) {
            return this.delegate().predecessors(n);
        }
    }

}

