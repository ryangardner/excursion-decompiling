/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.database.DataSetObservable
 *  android.os.AsyncTask
 *  android.text.TextUtils
 *  android.util.Log
 *  android.util.Xml
 */
package androidx.appcompat.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import org.xmlpull.v1.XmlPullParserException;

class ActivityChooserModel
extends DataSetObservable {
    static final String ATTRIBUTE_ACTIVITY = "activity";
    static final String ATTRIBUTE_TIME = "time";
    static final String ATTRIBUTE_WEIGHT = "weight";
    static final boolean DEBUG = false;
    private static final int DEFAULT_ACTIVITY_INFLATION = 5;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final String HISTORY_FILE_EXTENSION = ".xml";
    private static final int INVALID_INDEX = -1;
    static final String LOG_TAG = ActivityChooserModel.class.getSimpleName();
    static final String TAG_HISTORICAL_RECORD = "historical-record";
    static final String TAG_HISTORICAL_RECORDS = "historical-records";
    private static final Map<String, ActivityChooserModel> sDataModelRegistry;
    private static final Object sRegistryLock;
    private final List<ActivityResolveInfo> mActivities = new ArrayList<ActivityResolveInfo>();
    private OnChooseActivityListener mActivityChoserModelPolicy;
    private ActivitySorter mActivitySorter = new DefaultSorter();
    boolean mCanReadHistoricalData = true;
    final Context mContext;
    private final List<HistoricalRecord> mHistoricalRecords = new ArrayList<HistoricalRecord>();
    private boolean mHistoricalRecordsChanged = true;
    final String mHistoryFileName;
    private int mHistoryMaxSize = 50;
    private final Object mInstanceLock = new Object();
    private Intent mIntent;
    private boolean mReadShareHistoryCalled = false;
    private boolean mReloadActivities = false;

    static {
        sRegistryLock = new Object();
        sDataModelRegistry = new HashMap<String, ActivityChooserModel>();
    }

    private ActivityChooserModel(Context object, String string2) {
        this.mContext = object.getApplicationContext();
        if (!TextUtils.isEmpty((CharSequence)string2) && !string2.endsWith(HISTORY_FILE_EXTENSION)) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(HISTORY_FILE_EXTENSION);
            this.mHistoryFileName = ((StringBuilder)object).toString();
            return;
        }
        this.mHistoryFileName = string2;
    }

    private boolean addHistoricalRecord(HistoricalRecord historicalRecord) {
        boolean bl = this.mHistoricalRecords.add(historicalRecord);
        if (!bl) return bl;
        this.mHistoricalRecordsChanged = true;
        this.pruneExcessiveHistoricalRecordsIfNeeded();
        this.persistHistoricalDataIfNeeded();
        this.sortActivitiesIfNeeded();
        this.notifyChanged();
        return bl;
    }

    private void ensureConsistentState() {
        boolean bl = this.loadActivitiesIfNeeded();
        boolean bl2 = this.readHistoricalDataIfNeeded();
        this.pruneExcessiveHistoricalRecordsIfNeeded();
        if (!(bl | bl2)) return;
        this.sortActivitiesIfNeeded();
        this.notifyChanged();
    }

    public static ActivityChooserModel get(Context context, String string2) {
        Object object = sRegistryLock;
        synchronized (object) {
            ActivityChooserModel activityChooserModel;
            ActivityChooserModel activityChooserModel2 = activityChooserModel = sDataModelRegistry.get(string2);
            if (activityChooserModel != null) return activityChooserModel2;
            activityChooserModel2 = new ActivityChooserModel(context, string2);
            sDataModelRegistry.put(string2, activityChooserModel2);
            return activityChooserModel2;
        }
    }

    private boolean loadActivitiesIfNeeded() {
        boolean bl = this.mReloadActivities;
        int n = 0;
        if (!bl) return false;
        if (this.mIntent == null) return false;
        this.mReloadActivities = false;
        this.mActivities.clear();
        List list = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        int n2 = list.size();
        while (n < n2) {
            ResolveInfo resolveInfo = (ResolveInfo)list.get(n);
            this.mActivities.add(new ActivityResolveInfo(resolveInfo));
            ++n;
        }
        return true;
    }

    private void persistHistoricalDataIfNeeded() {
        if (!this.mReadShareHistoryCalled) throw new IllegalStateException("No preceding call to #readHistoricalData");
        if (!this.mHistoricalRecordsChanged) {
            return;
        }
        this.mHistoricalRecordsChanged = false;
        if (TextUtils.isEmpty((CharSequence)this.mHistoryFileName)) return;
        new PersistHistoryAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[]{new ArrayList<HistoricalRecord>(this.mHistoricalRecords), this.mHistoryFileName});
    }

    private void pruneExcessiveHistoricalRecordsIfNeeded() {
        int n = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
        if (n <= 0) {
            return;
        }
        this.mHistoricalRecordsChanged = true;
        int n2 = 0;
        while (n2 < n) {
            HistoricalRecord historicalRecord = this.mHistoricalRecords.remove(0);
            ++n2;
        }
    }

    private boolean readHistoricalDataIfNeeded() {
        if (!this.mCanReadHistoricalData) return false;
        if (!this.mHistoricalRecordsChanged) return false;
        if (TextUtils.isEmpty((CharSequence)this.mHistoryFileName)) return false;
        this.mCanReadHistoricalData = false;
        this.mReadShareHistoryCalled = true;
        this.readHistoricalDataImpl();
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private void readHistoricalDataImpl() {
        var1_1 = this.mContext.openFileInput(this.mHistoryFileName);
        try {
            block18 : {
                var2_5 = Xml.newPullParser();
                var2_5.setInput(var1_1, "UTF-8");
                var3_7 = 0;
                while (var3_7 != 1 && var3_7 != 2) {
                    var3_7 = var2_5.next();
                }
                if (!"historical-records".equals(var2_5.getName())) {
                    var2_5 = new XmlPullParserException("Share records file does not start with historical-records tag.");
                    throw var2_5;
                }
                break block18;
                catch (FileNotFoundException var1_2) {
                    return;
                }
            }
            var4_8 = this.mHistoricalRecords;
            var4_8.clear();
            do {
                if ((var3_7 = var2_5.next()) == 1) {
                    if (var1_1 == null) return;
                    break;
                }
                if (var3_7 == 3 || var3_7 == 4) continue;
                if (!"historical-record".equals(var2_5.getName())) {
                    var2_5 = new XmlPullParserException("Share records file not well-formed.");
                    throw var2_5;
                }
                var5_9 = var2_5.getAttributeValue(null, "activity");
                var6_10 = Long.parseLong(var2_5.getAttributeValue(null, "time"));
                var8_11 = Float.parseFloat(var2_5.getAttributeValue(null, "weight"));
                var9_12 = new HistoricalRecord(var5_9, var6_10, var8_11);
                var4_8.add(var9_12);
            } while (true);
        }
        catch (Throwable var2_6) {
        }
        catch (IOException var9_13) {
            var2_5 = ActivityChooserModel.LOG_TAG;
            var4_8 = new StringBuilder();
            var4_8.append("Error reading historical recrod file: ");
            var4_8.append(this.mHistoryFileName);
            Log.e((String)var2_5, (String)var4_8.toString(), (Throwable)var9_13);
            if (var1_1 == null) return;
        }
        catch (XmlPullParserException var9_14) {
            var4_8 = ActivityChooserModel.LOG_TAG;
            var2_5 = new StringBuilder();
            var2_5.append("Error reading historical recrod file: ");
            var2_5.append(this.mHistoryFileName);
            Log.e((String)var4_8, (String)var2_5.toString(), (Throwable)var9_14);
            if (var1_1 == null) return;
            ** continue;
        }
lbl34: // 3 sources:
        do {
            try {
                var1_1.close();
                return;
            }
            catch (IOException var1_3) {
                return;
            }
            break;
        } while (true);
        if (var1_1 == null) throw var2_6;
        try {
            var1_1.close();
        }
        catch (IOException var1_4) {
            throw var2_6;
        }
        throw var2_6;
    }

    private boolean sortActivitiesIfNeeded() {
        if (this.mActivitySorter == null) return false;
        if (this.mIntent == null) return false;
        if (this.mActivities.isEmpty()) return false;
        if (this.mHistoricalRecords.isEmpty()) return false;
        this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
        return true;
    }

    public Intent chooseActivity(int n) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            Object object2;
            if (this.mIntent == null) {
                return null;
            }
            this.ensureConsistentState();
            ActivityResolveInfo activityResolveInfo = this.mActivities.get(n);
            ComponentName componentName = new ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name);
            activityResolveInfo = new Intent(this.mIntent);
            activityResolveInfo.setComponent(componentName);
            if (this.mActivityChoserModelPolicy != null && this.mActivityChoserModelPolicy.onChooseActivity(this, (Intent)(object2 = new Intent((Intent)activityResolveInfo)))) {
                return null;
            }
            object2 = new HistoricalRecord(componentName, System.currentTimeMillis(), 1.0f);
            this.addHistoricalRecord((HistoricalRecord)object2);
            return activityResolveInfo;
        }
    }

    public ResolveInfo getActivity(int n) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            return this.mActivities.get((int)n).resolveInfo;
        }
    }

    public int getActivityCount() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            return this.mActivities.size();
        }
    }

    public int getActivityIndex(ResolveInfo resolveInfo) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            List<ActivityResolveInfo> list = this.mActivities;
            int n = list.size();
            int n2 = 0;
            while (n2 < n) {
                if (list.get((int)n2).resolveInfo == resolveInfo) {
                    return n2;
                }
                ++n2;
            }
            return -1;
        }
    }

    public ResolveInfo getDefaultActivity() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            if (this.mActivities.isEmpty()) return null;
            return this.mActivities.get((int)0).resolveInfo;
        }
    }

    public int getHistoryMaxSize() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            return this.mHistoryMaxSize;
        }
    }

    public int getHistorySize() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            return this.mHistoricalRecords.size();
        }
    }

    public Intent getIntent() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            return this.mIntent;
        }
    }

    public void setActivitySorter(ActivitySorter activitySorter) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            if (this.mActivitySorter == activitySorter) {
                return;
            }
            this.mActivitySorter = activitySorter;
            if (!this.sortActivitiesIfNeeded()) return;
            this.notifyChanged();
            return;
        }
    }

    public void setDefaultActivity(int n) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            Object object2 = this.mActivities.get(n);
            ActivityResolveInfo activityResolveInfo = this.mActivities.get(0);
            float f = activityResolveInfo != null ? activityResolveInfo.weight - ((ActivityResolveInfo)object2).weight + 5.0f : 1.0f;
            activityResolveInfo = new ComponentName(object2.resolveInfo.activityInfo.packageName, object2.resolveInfo.activityInfo.name);
            object2 = new HistoricalRecord((ComponentName)activityResolveInfo, System.currentTimeMillis(), f);
            this.addHistoricalRecord((HistoricalRecord)object2);
            return;
        }
    }

    public void setHistoryMaxSize(int n) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            if (this.mHistoryMaxSize == n) {
                return;
            }
            this.mHistoryMaxSize = n;
            this.pruneExcessiveHistoricalRecordsIfNeeded();
            if (!this.sortActivitiesIfNeeded()) return;
            this.notifyChanged();
            return;
        }
    }

    public void setIntent(Intent intent) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            if (this.mIntent == intent) {
                return;
            }
            this.mIntent = intent;
            this.mReloadActivities = true;
            this.ensureConsistentState();
            return;
        }
    }

    public void setOnChooseActivityListener(OnChooseActivityListener onChooseActivityListener) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.mActivityChoserModelPolicy = onChooseActivityListener;
            return;
        }
    }

    public static interface ActivityChooserModelClient {
        public void setActivityChooserModel(ActivityChooserModel var1);
    }

    public static final class ActivityResolveInfo
    implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(ResolveInfo resolveInfo) {
            this.resolveInfo = resolveInfo;
        }

        @Override
        public int compareTo(ActivityResolveInfo activityResolveInfo) {
            return Float.floatToIntBits(activityResolveInfo.weight) - Float.floatToIntBits(this.weight);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (ActivityResolveInfo)object;
            if (Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo)object).weight)) return true;
            return false;
        }

        public int hashCode() {
            return Float.floatToIntBits(this.weight) + 31;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append("resolveInfo:");
            stringBuilder.append(this.resolveInfo.toString());
            stringBuilder.append("; weight:");
            stringBuilder.append(new BigDecimal(this.weight));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    public static interface ActivitySorter {
        public void sort(Intent var1, List<ActivityResolveInfo> var2, List<HistoricalRecord> var3);
    }

    private static final class DefaultSorter
    implements ActivitySorter {
        private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
        private final Map<ComponentName, ActivityResolveInfo> mPackageNameToActivityMap = new HashMap<ComponentName, ActivityResolveInfo>();

        DefaultSorter() {
        }

        @Override
        public void sort(Intent object, List<ActivityResolveInfo> list, List<HistoricalRecord> list2) {
            Object object2;
            int n;
            object = this.mPackageNameToActivityMap;
            object.clear();
            int n2 = list.size();
            for (n = 0; n < n2; ++n) {
                object2 = list.get(n);
                ((ActivityResolveInfo)object2).weight = 0.0f;
                object.put(new ComponentName(object2.resolveInfo.activityInfo.packageName, object2.resolveInfo.activityInfo.name), object2);
            }
            n = list2.size() - 1;
            float f = 1.0f;
            do {
                if (n < 0) {
                    Collections.sort(list);
                    return;
                }
                object2 = list2.get(n);
                ActivityResolveInfo activityResolveInfo = (ActivityResolveInfo)object.get((Object)((HistoricalRecord)object2).activity);
                float f2 = f;
                if (activityResolveInfo != null) {
                    activityResolveInfo.weight += ((HistoricalRecord)object2).weight * f;
                    f2 = f * 0.95f;
                }
                --n;
                f = f2;
            } while (true);
        }
    }

    public static final class HistoricalRecord {
        public final ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(ComponentName componentName, long l, float f) {
            this.activity = componentName;
            this.time = l;
            this.weight = f;
        }

        public HistoricalRecord(String string2, long l, float f) {
            this(ComponentName.unflattenFromString((String)string2), l, f);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (HistoricalRecord)object;
            ComponentName componentName = this.activity;
            if (componentName == null ? ((HistoricalRecord)object).activity != null : !componentName.equals((Object)((HistoricalRecord)object).activity)) {
                return false;
            }
            if (this.time != ((HistoricalRecord)object).time) {
                return false;
            }
            if (Float.floatToIntBits(this.weight) == Float.floatToIntBits(((HistoricalRecord)object).weight)) return true;
            return false;
        }

        public int hashCode() {
            ComponentName componentName = this.activity;
            int n = componentName == null ? 0 : componentName.hashCode();
            long l = this.time;
            return ((n + 31) * 31 + (int)(l ^ l >>> 32)) * 31 + Float.floatToIntBits(this.weight);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append("; activity:");
            stringBuilder.append((Object)this.activity);
            stringBuilder.append("; time:");
            stringBuilder.append(this.time);
            stringBuilder.append("; weight:");
            stringBuilder.append(new BigDecimal(this.weight));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    public static interface OnChooseActivityListener {
        public boolean onChooseActivity(ActivityChooserModel var1, Intent var2);
    }

    private final class PersistHistoryAsyncTask
    extends AsyncTask<Object, Void, Void> {
        PersistHistoryAsyncTask() {
        }

        /*
         * Exception decompiling
         */
        public Void doInBackground(Object ... var1_1) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
            // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
            throw new IllegalStateException("Decompilation failed");
        }
    }

}

