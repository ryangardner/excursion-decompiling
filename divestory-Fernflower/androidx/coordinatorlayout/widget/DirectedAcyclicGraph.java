package androidx.coordinatorlayout.widget;

import androidx.collection.SimpleArrayMap;
import androidx.core.util.Pools;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class DirectedAcyclicGraph<T> {
   private final SimpleArrayMap<T, ArrayList<T>> mGraph = new SimpleArrayMap();
   private final Pools.Pool<ArrayList<T>> mListPool = new Pools.SimplePool(10);
   private final ArrayList<T> mSortResult = new ArrayList();
   private final HashSet<T> mSortTmpMarked = new HashSet();

   private void dfs(T var1, ArrayList<T> var2, HashSet<T> var3) {
      if (!var2.contains(var1)) {
         if (var3.contains(var1)) {
            throw new RuntimeException("This graph contains cyclic dependencies");
         } else {
            var3.add(var1);
            ArrayList var4 = (ArrayList)this.mGraph.get(var1);
            if (var4 != null) {
               int var5 = 0;

               for(int var6 = var4.size(); var5 < var6; ++var5) {
                  this.dfs(var4.get(var5), var2, var3);
               }
            }

            var3.remove(var1);
            var2.add(var1);
         }
      }
   }

   private ArrayList<T> getEmptyList() {
      ArrayList var1 = (ArrayList)this.mListPool.acquire();
      ArrayList var2 = var1;
      if (var1 == null) {
         var2 = new ArrayList();
      }

      return var2;
   }

   private void poolList(ArrayList<T> var1) {
      var1.clear();
      this.mListPool.release(var1);
   }

   public void addEdge(T var1, T var2) {
      if (this.mGraph.containsKey(var1) && this.mGraph.containsKey(var2)) {
         ArrayList var3 = (ArrayList)this.mGraph.get(var1);
         ArrayList var4 = var3;
         if (var3 == null) {
            var4 = this.getEmptyList();
            this.mGraph.put(var1, var4);
         }

         var4.add(var2);
      } else {
         throw new IllegalArgumentException("All nodes must be present in the graph before being added as an edge");
      }
   }

   public void addNode(T var1) {
      if (!this.mGraph.containsKey(var1)) {
         this.mGraph.put(var1, (Object)null);
      }

   }

   public void clear() {
      int var1 = this.mGraph.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         ArrayList var3 = (ArrayList)this.mGraph.valueAt(var2);
         if (var3 != null) {
            this.poolList(var3);
         }
      }

      this.mGraph.clear();
   }

   public boolean contains(T var1) {
      return this.mGraph.containsKey(var1);
   }

   public List getIncomingEdges(T var1) {
      return (List)this.mGraph.get(var1);
   }

   public List<T> getOutgoingEdges(T var1) {
      int var2 = this.mGraph.size();
      ArrayList var3 = null;

      ArrayList var6;
      for(int var4 = 0; var4 < var2; var3 = var6) {
         ArrayList var5 = (ArrayList)this.mGraph.valueAt(var4);
         var6 = var3;
         if (var5 != null) {
            var6 = var3;
            if (var5.contains(var1)) {
               var6 = var3;
               if (var3 == null) {
                  var6 = new ArrayList();
               }

               var6.add(this.mGraph.keyAt(var4));
            }
         }

         ++var4;
      }

      return var3;
   }

   public ArrayList<T> getSortedList() {
      this.mSortResult.clear();
      this.mSortTmpMarked.clear();
      int var1 = this.mGraph.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         this.dfs(this.mGraph.keyAt(var2), this.mSortResult, this.mSortTmpMarked);
      }

      return this.mSortResult;
   }

   public boolean hasOutgoingEdges(T var1) {
      int var2 = this.mGraph.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         ArrayList var4 = (ArrayList)this.mGraph.valueAt(var3);
         if (var4 != null && var4.contains(var1)) {
            return true;
         }
      }

      return false;
   }

   int size() {
      return this.mGraph.size();
   }
}
