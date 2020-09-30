package com.github.mikephil.charting.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class DataSet<T extends Entry> extends BaseDataSet<T> {
   protected List<T> mValues = null;
   protected float mXMax = -3.4028235E38F;
   protected float mXMin = Float.MAX_VALUE;
   protected float mYMax = -3.4028235E38F;
   protected float mYMin = Float.MAX_VALUE;

   public DataSet(List<T> var1, String var2) {
      super(var2);
      this.mValues = var1;
      if (var1 == null) {
         this.mValues = new ArrayList();
      }

      this.calcMinMax();
   }

   public boolean addEntry(T var1) {
      if (var1 == null) {
         return false;
      } else {
         List var2 = this.getValues();
         Object var3 = var2;
         if (var2 == null) {
            var3 = new ArrayList();
         }

         this.calcMinMax(var1);
         return ((List)var3).add(var1);
      }
   }

   public void addEntryOrdered(T var1) {
      if (var1 != null) {
         if (this.mValues == null) {
            this.mValues = new ArrayList();
         }

         this.calcMinMax(var1);
         if (this.mValues.size() > 0) {
            List var2 = this.mValues;
            if (((Entry)var2.get(var2.size() - 1)).getX() > var1.getX()) {
               int var3 = this.getEntryIndex(var1.getX(), var1.getY(), DataSet.Rounding.UP);
               this.mValues.add(var3, var1);
               return;
            }
         }

         this.mValues.add(var1);
      }
   }

   public void calcMinMax() {
      List var1 = this.mValues;
      if (var1 != null && !var1.isEmpty()) {
         this.mYMax = -3.4028235E38F;
         this.mYMin = Float.MAX_VALUE;
         this.mXMax = -3.4028235E38F;
         this.mXMin = Float.MAX_VALUE;
         Iterator var2 = this.mValues.iterator();

         while(var2.hasNext()) {
            this.calcMinMax((Entry)var2.next());
         }
      }

   }

   protected void calcMinMax(T var1) {
      if (var1 != null) {
         this.calcMinMaxX(var1);
         this.calcMinMaxY(var1);
      }
   }

   protected void calcMinMaxX(T var1) {
      if (var1.getX() < this.mXMin) {
         this.mXMin = var1.getX();
      }

      if (var1.getX() > this.mXMax) {
         this.mXMax = var1.getX();
      }

   }

   public void calcMinMaxY(float var1, float var2) {
      List var3 = this.mValues;
      if (var3 != null && !var3.isEmpty()) {
         this.mYMax = -3.4028235E38F;
         this.mYMin = Float.MAX_VALUE;
         int var4 = this.getEntryIndex(var1, Float.NaN, DataSet.Rounding.DOWN);

         for(int var5 = this.getEntryIndex(var2, Float.NaN, DataSet.Rounding.UP); var4 <= var5; ++var4) {
            this.calcMinMaxY((Entry)this.mValues.get(var4));
         }
      }

   }

   protected void calcMinMaxY(T var1) {
      if (var1.getY() < this.mYMin) {
         this.mYMin = var1.getY();
      }

      if (var1.getY() > this.mYMax) {
         this.mYMax = var1.getY();
      }

   }

   public void clear() {
      this.mValues.clear();
      this.notifyDataSetChanged();
   }

   public abstract DataSet<T> copy();

   protected void copy(DataSet var1) {
      super.copy(var1);
   }

   public List<T> getEntriesForXValue(float var1) {
      ArrayList var2 = new ArrayList();
      int var3 = this.mValues.size() - 1;
      int var4 = 0;

      while(var4 <= var3) {
         int var5 = (var3 + var4) / 2;
         Entry var6 = (Entry)this.mValues.get(var5);
         if (var1 == var6.getX()) {
            for(var4 = var5; var4 > 0 && ((Entry)this.mValues.get(var4 - 1)).getX() == var1; --var4) {
            }

            for(var3 = this.mValues.size(); var4 < var3; ++var4) {
               var6 = (Entry)this.mValues.get(var4);
               if (var6.getX() != var1) {
                  return var2;
               }

               var2.add(var6);
            }

            return var2;
         }

         if (var1 > var6.getX()) {
            var4 = var5 + 1;
         } else {
            var3 = var5 - 1;
         }
      }

      return var2;
   }

   public int getEntryCount() {
      return this.mValues.size();
   }

   public T getEntryForIndex(int var1) {
      return (Entry)this.mValues.get(var1);
   }

   public T getEntryForXValue(float var1, float var2) {
      return this.getEntryForXValue(var1, var2, DataSet.Rounding.CLOSEST);
   }

   public T getEntryForXValue(float var1, float var2, DataSet.Rounding var3) {
      int var4 = this.getEntryIndex(var1, var2, var3);
      return var4 > -1 ? (Entry)this.mValues.get(var4) : null;
   }

   public int getEntryIndex(float var1, float var2, DataSet.Rounding var3) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: fail exe a35 = a12\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.Ir2JRegAssignTransformer.transform(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Unknown Source)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Unknown Source)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(Unknown Source)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Unknown Source)\n\tat the.bytecode.club.bytecodeviewer.util.Dex2Jar.dex2Jar(Dex2Jar.java:54)\n\tat the.bytecode.club.bytecodeviewer.BytecodeViewer$8.run(BytecodeViewer.java:957)\nCaused by: java.lang.NullPointerException\n\tat com.googlecode.dex2jar.ir.ts.an.SimpleLiveAnalyze.onUseLocal(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.an.SimpleLiveAnalyze.onUseLocal(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.onUse(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.onUse(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.travel(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.travel(Unknown Source)\n\t... 20 more\n");
   }

   public int getEntryIndex(Entry var1) {
      return this.mValues.indexOf(var1);
   }

   public List<T> getValues() {
      return this.mValues;
   }

   public float getXMax() {
      return this.mXMax;
   }

   public float getXMin() {
      return this.mXMin;
   }

   public float getYMax() {
      return this.mYMax;
   }

   public float getYMin() {
      return this.mYMin;
   }

   public boolean removeEntry(T var1) {
      if (var1 == null) {
         return false;
      } else {
         List var2 = this.mValues;
         if (var2 == null) {
            return false;
         } else {
            boolean var3 = var2.remove(var1);
            if (var3) {
               this.calcMinMax();
            }

            return var3;
         }
      }
   }

   public void setValues(List<T> var1) {
      this.mValues = var1;
      this.notifyDataSetChanged();
   }

   public String toSimpleString() {
      StringBuffer var1 = new StringBuffer();
      StringBuilder var2 = new StringBuilder();
      var2.append("DataSet, label: ");
      String var3;
      if (this.getLabel() == null) {
         var3 = "";
      } else {
         var3 = this.getLabel();
      }

      var2.append(var3);
      var2.append(", entries: ");
      var2.append(this.mValues.size());
      var2.append("\n");
      var1.append(var2.toString());
      return var1.toString();
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(this.toSimpleString());

      for(int var2 = 0; var2 < this.mValues.size(); ++var2) {
         StringBuilder var3 = new StringBuilder();
         var3.append(((Entry)this.mValues.get(var2)).toString());
         var3.append(" ");
         var1.append(var3.toString());
      }

      return var1.toString();
   }

   public static enum Rounding {
      CLOSEST,
      DOWN,
      UP;

      static {
         DataSet.Rounding var0 = new DataSet.Rounding("CLOSEST", 2);
         CLOSEST = var0;
      }
   }
}
