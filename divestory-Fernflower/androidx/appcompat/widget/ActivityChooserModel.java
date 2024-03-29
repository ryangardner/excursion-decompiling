package androidx.appcompat.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ActivityChooserModel extends DataSetObservable {
   static final String ATTRIBUTE_ACTIVITY = "activity";
   static final String ATTRIBUTE_TIME = "time";
   static final String ATTRIBUTE_WEIGHT = "weight";
   static final boolean DEBUG = false;
   private static final int DEFAULT_ACTIVITY_INFLATION = 5;
   private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0F;
   public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
   public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
   private static final String HISTORY_FILE_EXTENSION = ".xml";
   private static final int INVALID_INDEX = -1;
   static final String LOG_TAG = ActivityChooserModel.class.getSimpleName();
   static final String TAG_HISTORICAL_RECORD = "historical-record";
   static final String TAG_HISTORICAL_RECORDS = "historical-records";
   private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();
   private static final Object sRegistryLock = new Object();
   private final List<ActivityChooserModel.ActivityResolveInfo> mActivities = new ArrayList();
   private ActivityChooserModel.OnChooseActivityListener mActivityChoserModelPolicy;
   private ActivityChooserModel.ActivitySorter mActivitySorter = new ActivityChooserModel.DefaultSorter();
   boolean mCanReadHistoricalData = true;
   final Context mContext;
   private final List<ActivityChooserModel.HistoricalRecord> mHistoricalRecords = new ArrayList();
   private boolean mHistoricalRecordsChanged = true;
   final String mHistoryFileName;
   private int mHistoryMaxSize = 50;
   private final Object mInstanceLock = new Object();
   private Intent mIntent;
   private boolean mReadShareHistoryCalled = false;
   private boolean mReloadActivities = false;

   private ActivityChooserModel(Context var1, String var2) {
      this.mContext = var1.getApplicationContext();
      if (!TextUtils.isEmpty(var2) && !var2.endsWith(".xml")) {
         StringBuilder var3 = new StringBuilder();
         var3.append(var2);
         var3.append(".xml");
         this.mHistoryFileName = var3.toString();
      } else {
         this.mHistoryFileName = var2;
      }

   }

   private boolean addHistoricalRecord(ActivityChooserModel.HistoricalRecord var1) {
      boolean var2 = this.mHistoricalRecords.add(var1);
      if (var2) {
         this.mHistoricalRecordsChanged = true;
         this.pruneExcessiveHistoricalRecordsIfNeeded();
         this.persistHistoricalDataIfNeeded();
         this.sortActivitiesIfNeeded();
         this.notifyChanged();
      }

      return var2;
   }

   private void ensureConsistentState() {
      boolean var1 = this.loadActivitiesIfNeeded();
      boolean var2 = this.readHistoricalDataIfNeeded();
      this.pruneExcessiveHistoricalRecordsIfNeeded();
      if (var1 | var2) {
         this.sortActivitiesIfNeeded();
         this.notifyChanged();
      }

   }

   public static ActivityChooserModel get(Context var0, String var1) {
      Object var2 = sRegistryLock;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label176: {
         ActivityChooserModel var3;
         try {
            var3 = (ActivityChooserModel)sDataModelRegistry.get(var1);
         } catch (Throwable var24) {
            var10000 = var24;
            var10001 = false;
            break label176;
         }

         ActivityChooserModel var4 = var3;
         if (var3 == null) {
            try {
               var4 = new ActivityChooserModel(var0, var1);
               sDataModelRegistry.put(var1, var4);
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label176;
            }
         }

         label165:
         try {
            return var4;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label165;
         }
      }

      while(true) {
         Throwable var25 = var10000;

         try {
            throw var25;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            continue;
         }
      }
   }

   private boolean loadActivitiesIfNeeded() {
      boolean var1 = this.mReloadActivities;
      int var2 = 0;
      if (var1 && this.mIntent != null) {
         this.mReloadActivities = false;
         this.mActivities.clear();
         List var3 = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);

         for(int var4 = var3.size(); var2 < var4; ++var2) {
            ResolveInfo var5 = (ResolveInfo)var3.get(var2);
            this.mActivities.add(new ActivityChooserModel.ActivityResolveInfo(var5));
         }

         return true;
      } else {
         return false;
      }
   }

   private void persistHistoricalDataIfNeeded() {
      if (this.mReadShareHistoryCalled) {
         if (this.mHistoricalRecordsChanged) {
            this.mHistoricalRecordsChanged = false;
            if (!TextUtils.isEmpty(this.mHistoryFileName)) {
               (new ActivityChooserModel.PersistHistoryAsyncTask()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[]{new ArrayList(this.mHistoricalRecords), this.mHistoryFileName});
            }

         }
      } else {
         throw new IllegalStateException("No preceding call to #readHistoricalData");
      }
   }

   private void pruneExcessiveHistoricalRecordsIfNeeded() {
      int var1 = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
      if (var1 > 0) {
         this.mHistoricalRecordsChanged = true;

         for(int var2 = 0; var2 < var1; ++var2) {
            ActivityChooserModel.HistoricalRecord var3 = (ActivityChooserModel.HistoricalRecord)this.mHistoricalRecords.remove(0);
         }

      }
   }

   private boolean readHistoricalDataIfNeeded() {
      if (this.mCanReadHistoricalData && this.mHistoricalRecordsChanged && !TextUtils.isEmpty(this.mHistoryFileName)) {
         this.mCanReadHistoricalData = false;
         this.mReadShareHistoryCalled = true;
         this.readHistoricalDataImpl();
         return true;
      } else {
         return false;
      }
   }

   private void readHistoricalDataImpl() {
      // $FF: Couldn't be decompiled
   }

   private boolean sortActivitiesIfNeeded() {
      if (this.mActivitySorter != null && this.mIntent != null && !this.mActivities.isEmpty() && !this.mHistoricalRecords.isEmpty()) {
         this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
         return true;
      } else {
         return false;
      }
   }

   public Intent chooseActivity(int var1) {
      Object var2 = this.mInstanceLock;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label208: {
         try {
            if (this.mIntent == null) {
               return null;
            }
         } catch (Throwable var25) {
            var10000 = var25;
            var10001 = false;
            break label208;
         }

         ComponentName var4;
         Intent var26;
         try {
            this.ensureConsistentState();
            ActivityChooserModel.ActivityResolveInfo var3 = (ActivityChooserModel.ActivityResolveInfo)this.mActivities.get(var1);
            var4 = new ComponentName(var3.resolveInfo.activityInfo.packageName, var3.resolveInfo.activityInfo.name);
            var26 = new Intent(this.mIntent);
            var26.setComponent(var4);
            if (this.mActivityChoserModelPolicy != null) {
               Intent var5 = new Intent(var26);
               if (this.mActivityChoserModelPolicy.onChooseActivity(this, var5)) {
                  return null;
               }
            }
         } catch (Throwable var24) {
            var10000 = var24;
            var10001 = false;
            break label208;
         }

         label198:
         try {
            ActivityChooserModel.HistoricalRecord var28 = new ActivityChooserModel.HistoricalRecord(var4, System.currentTimeMillis(), 1.0F);
            this.addHistoricalRecord(var28);
            return var26;
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label198;
         }
      }

      while(true) {
         Throwable var27 = var10000;

         try {
            throw var27;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            continue;
         }
      }
   }

   public ResolveInfo getActivity(int param1) {
      // $FF: Couldn't be decompiled
   }

   public int getActivityCount() {
      // $FF: Couldn't be decompiled
   }

   public int getActivityIndex(ResolveInfo var1) {
      Object var2 = this.mInstanceLock;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label231: {
         List var3;
         int var4;
         try {
            this.ensureConsistentState();
            var3 = this.mActivities;
            var4 = var3.size();
         } catch (Throwable var24) {
            var10000 = var24;
            var10001 = false;
            break label231;
         }

         int var5 = 0;

         while(true) {
            if (var5 >= var4) {
               try {
                  return -1;
               } catch (Throwable var23) {
                  var10000 = var23;
                  var10001 = false;
                  break;
               }
            }

            try {
               if (((ActivityChooserModel.ActivityResolveInfo)var3.get(var5)).resolveInfo == var1) {
                  return var5;
               }
            } catch (Throwable var25) {
               var10000 = var25;
               var10001 = false;
               break;
            }

            ++var5;
         }
      }

      while(true) {
         Throwable var26 = var10000;

         try {
            throw var26;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            continue;
         }
      }
   }

   public ResolveInfo getDefaultActivity() {
      Object var1 = this.mInstanceLock;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label123: {
         try {
            this.ensureConsistentState();
            if (!this.mActivities.isEmpty()) {
               ResolveInfo var15 = ((ActivityChooserModel.ActivityResolveInfo)this.mActivities.get(0)).resolveInfo;
               return var15;
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label123;
         }

         label117:
         try {
            return null;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label117;
         }
      }

      while(true) {
         Throwable var2 = var10000;

         try {
            throw var2;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            continue;
         }
      }
   }

   public int getHistoryMaxSize() {
      // $FF: Couldn't be decompiled
   }

   public int getHistorySize() {
      // $FF: Couldn't be decompiled
   }

   public Intent getIntent() {
      // $FF: Couldn't be decompiled
   }

   public void setActivitySorter(ActivityChooserModel.ActivitySorter var1) {
      Object var2 = this.mInstanceLock;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label197: {
         try {
            if (this.mActivitySorter == var1) {
               return;
            }
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label197;
         }

         try {
            this.mActivitySorter = var1;
            if (this.sortActivitiesIfNeeded()) {
               this.notifyChanged();
            }
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            break label197;
         }

         label187:
         try {
            return;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            break label187;
         }
      }

      while(true) {
         Throwable var23 = var10000;

         try {
            throw var23;
         } catch (Throwable var19) {
            var10000 = var19;
            var10001 = false;
            continue;
         }
      }
   }

   public void setDefaultActivity(int var1) {
      Object var2 = this.mInstanceLock;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label191: {
         ActivityChooserModel.ActivityResolveInfo var3;
         ActivityChooserModel.ActivityResolveInfo var4;
         try {
            this.ensureConsistentState();
            var3 = (ActivityChooserModel.ActivityResolveInfo)this.mActivities.get(var1);
            var4 = (ActivityChooserModel.ActivityResolveInfo)this.mActivities.get(0);
         } catch (Throwable var25) {
            var10000 = var25;
            var10001 = false;
            break label191;
         }

         float var5;
         if (var4 != null) {
            try {
               var5 = var4.weight - var3.weight + 5.0F;
            } catch (Throwable var24) {
               var10000 = var24;
               var10001 = false;
               break label191;
            }
         } else {
            var5 = 1.0F;
         }

         label179:
         try {
            ComponentName var28 = new ComponentName(var3.resolveInfo.activityInfo.packageName, var3.resolveInfo.activityInfo.name);
            ActivityChooserModel.HistoricalRecord var27 = new ActivityChooserModel.HistoricalRecord(var28, System.currentTimeMillis(), var5);
            this.addHistoricalRecord(var27);
            return;
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label179;
         }
      }

      while(true) {
         Throwable var26 = var10000;

         try {
            throw var26;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            continue;
         }
      }
   }

   public void setHistoryMaxSize(int var1) {
      Object var2 = this.mInstanceLock;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label197: {
         try {
            if (this.mHistoryMaxSize == var1) {
               return;
            }
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label197;
         }

         try {
            this.mHistoryMaxSize = var1;
            this.pruneExcessiveHistoricalRecordsIfNeeded();
            if (this.sortActivitiesIfNeeded()) {
               this.notifyChanged();
            }
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label197;
         }

         label187:
         try {
            return;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            break label187;
         }
      }

      while(true) {
         Throwable var3 = var10000;

         try {
            throw var3;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            continue;
         }
      }
   }

   public void setIntent(Intent var1) {
      Object var2 = this.mInstanceLock;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label123: {
         try {
            if (this.mIntent == var1) {
               return;
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label123;
         }

         label117:
         try {
            this.mIntent = var1;
            this.mReloadActivities = true;
            this.ensureConsistentState();
            return;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label117;
         }
      }

      while(true) {
         Throwable var15 = var10000;

         try {
            throw var15;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            continue;
         }
      }
   }

   public void setOnChooseActivityListener(ActivityChooserModel.OnChooseActivityListener param1) {
      // $FF: Couldn't be decompiled
   }

   public interface ActivityChooserModelClient {
      void setActivityChooserModel(ActivityChooserModel var1);
   }

   public static final class ActivityResolveInfo implements Comparable<ActivityChooserModel.ActivityResolveInfo> {
      public final ResolveInfo resolveInfo;
      public float weight;

      public ActivityResolveInfo(ResolveInfo var1) {
         this.resolveInfo = var1;
      }

      public int compareTo(ActivityChooserModel.ActivityResolveInfo var1) {
         return Float.floatToIntBits(var1.weight) - Float.floatToIntBits(this.weight);
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (var1 == null) {
            return false;
         } else if (this.getClass() != var1.getClass()) {
            return false;
         } else {
            ActivityChooserModel.ActivityResolveInfo var2 = (ActivityChooserModel.ActivityResolveInfo)var1;
            return Float.floatToIntBits(this.weight) == Float.floatToIntBits(var2.weight);
         }
      }

      public int hashCode() {
         return Float.floatToIntBits(this.weight) + 31;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("[");
         var1.append("resolveInfo:");
         var1.append(this.resolveInfo.toString());
         var1.append("; weight:");
         var1.append(new BigDecimal((double)this.weight));
         var1.append("]");
         return var1.toString();
      }
   }

   public interface ActivitySorter {
      void sort(Intent var1, List<ActivityChooserModel.ActivityResolveInfo> var2, List<ActivityChooserModel.HistoricalRecord> var3);
   }

   private static final class DefaultSorter implements ActivityChooserModel.ActivitySorter {
      private static final float WEIGHT_DECAY_COEFFICIENT = 0.95F;
      private final Map<ComponentName, ActivityChooserModel.ActivityResolveInfo> mPackageNameToActivityMap = new HashMap();

      DefaultSorter() {
      }

      public void sort(Intent var1, List<ActivityChooserModel.ActivityResolveInfo> var2, List<ActivityChooserModel.HistoricalRecord> var3) {
         Map var10 = this.mPackageNameToActivityMap;
         var10.clear();
         int var4 = var2.size();

         int var5;
         for(var5 = 0; var5 < var4; ++var5) {
            ActivityChooserModel.ActivityResolveInfo var6 = (ActivityChooserModel.ActivityResolveInfo)var2.get(var5);
            var6.weight = 0.0F;
            var10.put(new ComponentName(var6.resolveInfo.activityInfo.packageName, var6.resolveInfo.activityInfo.name), var6);
         }

         var5 = var3.size() - 1;

         float var9;
         for(float var7 = 1.0F; var5 >= 0; var7 = var9) {
            ActivityChooserModel.HistoricalRecord var11 = (ActivityChooserModel.HistoricalRecord)var3.get(var5);
            ActivityChooserModel.ActivityResolveInfo var8 = (ActivityChooserModel.ActivityResolveInfo)var10.get(var11.activity);
            var9 = var7;
            if (var8 != null) {
               var8.weight += var11.weight * var7;
               var9 = var7 * 0.95F;
            }

            --var5;
         }

         Collections.sort(var2);
      }
   }

   public static final class HistoricalRecord {
      public final ComponentName activity;
      public final long time;
      public final float weight;

      public HistoricalRecord(ComponentName var1, long var2, float var4) {
         this.activity = var1;
         this.time = var2;
         this.weight = var4;
      }

      public HistoricalRecord(String var1, long var2, float var4) {
         this(ComponentName.unflattenFromString(var1), var2, var4);
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (var1 == null) {
            return false;
         } else if (this.getClass() != var1.getClass()) {
            return false;
         } else {
            ActivityChooserModel.HistoricalRecord var3 = (ActivityChooserModel.HistoricalRecord)var1;
            ComponentName var2 = this.activity;
            if (var2 == null) {
               if (var3.activity != null) {
                  return false;
               }
            } else if (!var2.equals(var3.activity)) {
               return false;
            }

            if (this.time != var3.time) {
               return false;
            } else {
               return Float.floatToIntBits(this.weight) == Float.floatToIntBits(var3.weight);
            }
         }
      }

      public int hashCode() {
         ComponentName var1 = this.activity;
         int var2;
         if (var1 == null) {
            var2 = 0;
         } else {
            var2 = var1.hashCode();
         }

         long var3 = this.time;
         return ((var2 + 31) * 31 + (int)(var3 ^ var3 >>> 32)) * 31 + Float.floatToIntBits(this.weight);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("[");
         var1.append("; activity:");
         var1.append(this.activity);
         var1.append("; time:");
         var1.append(this.time);
         var1.append("; weight:");
         var1.append(new BigDecimal((double)this.weight));
         var1.append("]");
         return var1.toString();
      }
   }

   public interface OnChooseActivityListener {
      boolean onChooseActivity(ActivityChooserModel var1, Intent var2);
   }

   private final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {
      PersistHistoryAsyncTask() {
      }

      public Void doInBackground(Object... param1) {
         // $FF: Couldn't be decompiled
      }
   }
}
