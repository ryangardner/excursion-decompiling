package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;

public abstract class EntityBuffer<T> extends AbstractDataBuffer<T> {
   private boolean zaa = false;
   private ArrayList<Integer> zab;

   protected EntityBuffer(DataHolder var1) {
      super(var1);
   }

   private final int zaa(int var1) {
      if (var1 >= 0 && var1 < this.zab.size()) {
         return (Integer)this.zab.get(var1);
      } else {
         StringBuilder var2 = new StringBuilder(53);
         var2.append("Position ");
         var2.append(var1);
         var2.append(" is out of bounds for this buffer");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   private final void zaa() {
      synchronized(this){}

      Throwable var10000;
      boolean var10001;
      label707: {
         label712: {
            int var1;
            ArrayList var2;
            try {
               if (this.zaa) {
                  break label712;
               }

               var1 = ((DataHolder)Preconditions.checkNotNull(this.mDataHolder)).getCount();
               var2 = new ArrayList();
               this.zab = var2;
            } catch (Throwable var78) {
               var10000 = var78;
               var10001 = false;
               break label707;
            }

            if (var1 > 0) {
               String var3;
               int var4;
               String var80;
               try {
                  var2.add(0);
                  var3 = this.getPrimaryDataMarkerColumn();
                  var4 = this.mDataHolder.getWindowIndex(0);
                  var80 = this.mDataHolder.getString(var3, 0, var4);
               } catch (Throwable var77) {
                  var10000 = var77;
                  var10001 = false;
                  break label707;
               }

               String var7;
               for(var4 = 1; var4 < var1; var80 = var7) {
                  int var5;
                  String var6;
                  try {
                     var5 = this.mDataHolder.getWindowIndex(var4);
                     var6 = this.mDataHolder.getString(var3, var4, var5);
                  } catch (Throwable var76) {
                     var10000 = var76;
                     var10001 = false;
                     break label707;
                  }

                  if (var6 == null) {
                     try {
                        var1 = String.valueOf(var3).length();
                        StringBuilder var83 = new StringBuilder(var1 + 78);
                        var83.append("Missing value for markerColumn: ");
                        var83.append(var3);
                        var83.append(", at row: ");
                        var83.append(var4);
                        var83.append(", for window: ");
                        var83.append(var5);
                        NullPointerException var81 = new NullPointerException(var83.toString());
                        throw var81;
                     } catch (Throwable var73) {
                        var10000 = var73;
                        var10001 = false;
                        break label707;
                     }
                  }

                  var7 = var80;

                  label699: {
                     try {
                        if (var6.equals(var80)) {
                           break label699;
                        }

                        this.zab.add(var4);
                     } catch (Throwable var79) {
                        var10000 = var79;
                        var10001 = false;
                        break label707;
                     }

                     var7 = var6;
                  }

                  ++var4;
               }
            }

            try {
               this.zaa = true;
            } catch (Throwable var75) {
               var10000 = var75;
               var10001 = false;
               break label707;
            }
         }

         label677:
         try {
            return;
         } catch (Throwable var74) {
            var10000 = var74;
            var10001 = false;
            break label677;
         }
      }

      while(true) {
         Throwable var82 = var10000;

         try {
            throw var82;
         } catch (Throwable var72) {
            var10000 = var72;
            var10001 = false;
            continue;
         }
      }
   }

   public final T get(int var1) {
      this.zaa();
      int var2 = this.zaa(var1);
      byte var3 = 0;
      int var4 = var3;
      if (var1 >= 0) {
         if (var1 == this.zab.size()) {
            var4 = var3;
         } else {
            int var5;
            if (var1 == this.zab.size() - 1) {
               var4 = ((DataHolder)Preconditions.checkNotNull(this.mDataHolder)).getCount();
               var5 = (Integer)this.zab.get(var1);
            } else {
               var4 = (Integer)this.zab.get(var1 + 1);
               var5 = (Integer)this.zab.get(var1);
            }

            var4 -= var5;
            if (var4 == 1) {
               var1 = this.zaa(var1);
               var5 = ((DataHolder)Preconditions.checkNotNull(this.mDataHolder)).getWindowIndex(var1);
               String var6 = this.getChildDataMarkerColumn();
               if (var6 != null && this.mDataHolder.getString(var6, var1, var5) == null) {
                  var4 = var3;
               }
            }
         }
      }

      return this.getEntry(var2, var4);
   }

   protected String getChildDataMarkerColumn() {
      return null;
   }

   public int getCount() {
      this.zaa();
      return this.zab.size();
   }

   protected abstract T getEntry(int var1, int var2);

   protected abstract String getPrimaryDataMarkerColumn();
}
