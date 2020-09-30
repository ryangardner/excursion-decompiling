/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.res.AssetManager
 *  android.os.Environment
 *  android.util.Log
 */
package com.github.mikephil.charting.utils;

import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.Entry;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileUtils {
    private static final String LOG = "MPChart-FileUtils";

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public static List<BarEntry> loadBarEntriesFromAssets(AssetManager object, String object2) {
        Object object3;
        void var0_4;
        block13 : {
            ArrayList<BarEntry> arrayList;
            block14 : {
                Object assetManager;
                arrayList = new ArrayList<BarEntry>();
                Object var3_10 = null;
                object3 = assetManager = null;
                object3 = assetManager;
                object3 = assetManager;
                InputStreamReader inputStreamReader = new InputStreamReader(object.open((String)object2), "UTF-8");
                object3 = assetManager;
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                for (object = bufferedReader.readLine(); object != null; object = object.split((String)"#")) {
                    object2 = new BarEntry(Float.parseFloat((String)object[1]), Float.parseFloat((String)object[0]));
                    arrayList.add((BarEntry)object2);
                    object = bufferedReader.readLine();
                }
                bufferedReader.close();
                return arrayList;
                catch (Throwable throwable) {
                    object3 = bufferedReader;
                    break block13;
                }
                catch (IOException iOException) {
                    object = bufferedReader;
                    break block14;
                }
                catch (Throwable throwable) {
                    break block13;
                }
                catch (IOException iOException) {
                    object = var3_10;
                }
            }
            object3 = object;
            {
                Log.e((String)LOG, (String)((Throwable)object2).toString());
                if (object == null) return arrayList;
            }
            try {
                ((BufferedReader)object).close();
                return arrayList;
            }
            catch (IOException iOException) {
                Log.e((String)LOG, (String)iOException.toString());
            }
            return arrayList;
        }
        if (object3 == null) throw var0_4;
        try {
            ((BufferedReader)object3).close();
            throw var0_4;
        }
        catch (IOException iOException) {
            Log.e((String)LOG, (String)iOException.toString());
        }
        throw var0_4;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public static List<Entry> loadEntriesFromAssets(AssetManager object, String arrf) {
        Object object2;
        void var0_4;
        block15 : {
            ArrayList<Entry> arrayList;
            block16 : {
                Object assetManager;
                arrayList = new ArrayList<Entry>();
                Object var3_10 = null;
                object2 = assetManager = null;
                object2 = assetManager;
                object2 = assetManager;
                InputStreamReader inputStreamReader = new InputStreamReader(object.open((String)arrf), "UTF-8");
                object2 = assetManager;
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                try {
                    object = bufferedReader.readLine();
                    do {
                        block19 : {
                            int n;
                            block20 : {
                                block17 : {
                                    block18 : {
                                        if (object == null) break block17;
                                        object = ((String)object).split("#");
                                        n = ((Object)object).length;
                                        if (n > 2) break block18;
                                        arrf = new Entry(Float.parseFloat((String)object[1]), Float.parseFloat((String)object[0]));
                                        arrayList.add((Entry)arrf);
                                        break block19;
                                    }
                                    n = ((Object)object).length - 1;
                                    arrf = new float[n];
                                    break block20;
                                }
                                bufferedReader.close();
                                return arrayList;
                            }
                            for (int i = 0; i < n; ++i) {
                                arrf[i] = Float.parseFloat((String)object[i]);
                            }
                            object2 = new BarEntry((float)Integer.parseInt((String)object[((Object)object).length - 1]), (float[])arrf);
                            arrayList.add((Entry)object2);
                        }
                        object = bufferedReader.readLine();
                    } while (true);
                }
                catch (Throwable throwable) {
                    object2 = bufferedReader;
                    break block15;
                }
                catch (IOException iOException) {
                    object = bufferedReader;
                    break block16;
                }
                catch (Throwable throwable) {
                    break block15;
                }
                catch (IOException iOException) {
                    object = var3_10;
                }
            }
            object2 = object;
            {
                Log.e((String)LOG, (String)((Throwable)arrf).toString());
                if (object == null) return arrayList;
            }
            try {
                ((BufferedReader)object).close();
                return arrayList;
            }
            catch (IOException iOException) {
                Log.e((String)LOG, (String)iOException.toString());
            }
            return arrayList;
        }
        if (object2 == null) throw var0_4;
        try {
            ((BufferedReader)object2).close();
            throw var0_4;
        }
        catch (IOException iOException) {
            Log.e((String)LOG, (String)iOException.toString());
        }
        throw var0_4;
    }

    public static List<Entry> loadEntriesFromFile(String object) {
        String[] arrstring = new File(Environment.getExternalStorageDirectory(), (String)object);
        object = new ArrayList();
        try {
            Object object2 = new FileReader((File)arrstring);
            BufferedReader bufferedReader = new BufferedReader((Reader)object2);
            while ((arrstring = bufferedReader.readLine()) != null) {
                arrstring = arrstring.split("#");
                int n = arrstring.length;
                if (n <= 2) {
                    object2 = new Entry(Float.parseFloat(arrstring[0]), Integer.parseInt(arrstring[1]));
                    object.add(object2);
                    continue;
                }
                n = arrstring.length - 1;
                float[] arrf = new float[n];
                for (int i = 0; i < n; ++i) {
                    arrf[i] = Float.parseFloat(arrstring[i]);
                }
                object2 = new BarEntry((float)Integer.parseInt(arrstring[arrstring.length - 1]), arrf);
                object.add(object2);
            }
            return object;
        }
        catch (IOException iOException) {
            Log.e((String)LOG, (String)iOException.toString());
        }
        return object;
    }

    public static void saveToSdCard(List<Entry> object, String object2) {
        Object object3 = new File(Environment.getExternalStorageDirectory(), (String)object2);
        if (!((File)object3).exists()) {
            try {
                ((File)object3).createNewFile();
            }
            catch (IOException iOException) {
                Log.e((String)LOG, (String)iOException.toString());
            }
        }
        try {
            Appendable appendable = new FileWriter((File)object3, true);
            object2 = new BufferedWriter((Writer)appendable);
            object = object.iterator();
            do {
                if (!object.hasNext()) {
                    ((BufferedWriter)object2).close();
                    return;
                }
                object3 = (Entry)object.next();
                appendable = new StringBuilder();
                ((StringBuilder)appendable).append(((BaseEntry)object3).getY());
                ((StringBuilder)appendable).append("#");
                ((StringBuilder)appendable).append(((Entry)object3).getX());
                ((Writer)object2).append(((StringBuilder)appendable).toString());
                ((BufferedWriter)object2).newLine();
            } while (true);
        }
        catch (IOException iOException) {
            Log.e((String)LOG, (String)iOException.toString());
        }
    }
}

