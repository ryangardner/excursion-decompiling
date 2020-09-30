/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.nfc.NfcAdapter
 *  android.nfc.Tag
 *  android.nfc.tech.MifareUltralight
 *  android.nfc.tech.NfcF
 *  android.util.Log
 */
package com.syntak.library;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.NfcF;
import android.util.Log;
import java.io.IOException;

public class NfcOp {
    private static NfcAdapter nfcAdapter;
    private String[][] NfcTechLists;
    String TAG = "NfcOp";
    private String action;
    private Activity activity;
    private boolean flag_nfc_available;
    private IntentFilter[] intentFilters;
    int page_size = 4;
    private PendingIntent pendingIntent;
    private Tag tag;
    byte[] tagId;
    int tag_type;

    public NfcOp(Activity activity) {
        NfcAdapter nfcAdapter;
        this.activity = activity;
        NfcOp.nfcAdapter = nfcAdapter = NfcAdapter.getDefaultAdapter((Context)activity);
        if (nfcAdapter == null) {
            this.flag_nfc_available = false;
            return;
        }
        this.flag_nfc_available = true;
        this.pendingIntent = PendingIntent.getActivity((Context)activity, (int)0, (Intent)new Intent((Context)activity, activity.getClass()).addFlags(536870912), (int)0);
        nfcAdapter = new IntentFilter("android.nfc.action.NDEF_DISCOVERED");
        activity = new IntentFilter("android.nfc.action.NDEF_DISCOVERED");
        try {
            nfcAdapter.addDataType("*/*");
            activity.addDataScheme("http");
            this.intentFilters = new IntentFilter[]{nfcAdapter, activity};
        }
        catch (Exception exception) {
            Log.e((String)"TagDispatch", (String)exception.toString());
        }
        this.NfcTechLists = new String[][]{{NfcF.class.getName()}};
    }

    public String getAction() {
        return this.action;
    }

    public Tag getTag() {
        return this.tag;
    }

    public byte[] getTagId() {
        return this.tagId;
    }

    /*
     * Exception decompiling
     */
    public void parseIntent(Intent var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 3[FORLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    public void start() {
        NfcAdapter nfcAdapter = NfcOp.nfcAdapter;
        if (nfcAdapter == null) return;
        nfcAdapter.enableForegroundDispatch(this.activity, this.pendingIntent, this.intentFilters, this.NfcTechLists);
    }

    public void stop() {
        NfcAdapter nfcAdapter = NfcOp.nfcAdapter;
        if (nfcAdapter == null) return;
        nfcAdapter.disableForegroundDispatch(this.activity);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public boolean writeMifareUltralightPages(int n, byte[] arrby) {
        Throwable throwable2222;
        MifareUltralight mifareUltralight;
        block12 : {
            int n2;
            byte[] arrby2;
            byte[] arrby3 = arrby2 = new byte[4];
            arrby3[0] = 0;
            arrby3[1] = 0;
            arrby3[2] = 0;
            arrby3[3] = 0;
            int n3 = arrby.length;
            int n4 = this.page_size;
            n3 = n2 = n3 / n4;
            if (arrby.length % n4 != 0) {
                n3 = n2 + 1;
            }
            mifareUltralight = MifareUltralight.get((Tag)this.tag);
            boolean bl = false;
            mifareUltralight.connect();
            for (n2 = 0; n2 < n3; ++n2) {
                System.arraycopy(arrby, this.page_size * n2, arrby2, 0, this.page_size);
                mifareUltralight.writePage(n + n2, arrby2);
            }
            try {
                mifareUltralight.close();
                return true;
            }
            catch (IOException iOException) {
                Log.e((String)this.TAG, (String)"IOException while closing MifareUltralight...", (Throwable)iOException);
            }
            return true;
            {
                catch (Throwable throwable2222) {
                    break block12;
                }
                catch (IOException iOException) {}
                {
                    Log.e((String)this.TAG, (String)"IOException while closing MifareUltralight...", (Throwable)iOException);
                }
                try {
                    mifareUltralight.close();
                    return bl;
                }
                catch (IOException iOException) {
                    Log.e((String)this.TAG, (String)"IOException while closing MifareUltralight...", (Throwable)iOException);
                }
            }
            return bl;
        }
        try {
            mifareUltralight.close();
            throw throwable2222;
        }
        catch (IOException iOException) {
            Log.e((String)this.TAG, (String)"IOException while closing MifareUltralight...", (Throwable)iOException);
        }
        throw throwable2222;
    }
}

