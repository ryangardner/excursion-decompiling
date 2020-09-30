/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.syntak.library;

import android.content.Context;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.BackOff;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.Drive;
import com.syntak.library.FileOp;
import com.syntak.library.MediaOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class GoogleDriveRestOp {
    private static final String[] SCOPES = new String[]{"https://www.googleapis.com/auth/drive.metadata.readonly"};
    String app_name;
    Context context;
    private Drive driveService = null;
    private GoogleAccountCredential mCredential;

    public GoogleDriveRestOp(Context context, String string2) {
        this.context = context;
        this.app_name = string2;
        this.mCredential = GoogleAccountCredential.usingOAuth2(context, Arrays.asList(SCOPES)).setBackOff(new ExponentialBackOff());
        this.driveService = ((Drive.Builder)new Drive.Builder(AndroidHttp.newCompatibleTransport(), JacksonFactory.getDefaultInstance(), this.mCredential).setApplicationName(string2)).build();
    }

    public static boolean isGooglePlayServicesAvailable(Context context) {
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) != 0) return false;
        return true;
    }

    public void downloadFile(String string2, String string3) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(string3);
            this.driveService.files().get(string2).executeMediaAndDownloadTo(fileOutputStream);
            fileOutputStream.flush();
            ((OutputStream)fileOutputStream).close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    /*
     * Exception decompiling
     */
    public List<com.google.api.services.drive.model.File> getFileListWithAppProperty(String var1_1, String var2_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[DOLOOP]], but top level block is 0[TRYBLOCK]
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

    public boolean isDriveServiceAvailable() {
        if (this.driveService == null) return false;
        return true;
    }

    public String searchFileWithAppProperty(String string2, String string3) {
        return null;
    }

    public void stop() {
        this.driveService = null;
    }

    public void uploadFileWithAppProperty(String object, String string2, String string3) {
        String string4 = FileOp.getFilenameFromPath((String)object);
        String string5 = MediaOp.getMimeType((String)object);
        Object object2 = new com.google.api.services.drive.model.File();
        ((com.google.api.services.drive.model.File)object2).setName(string4);
        object = new FileContent(string5, new File((String)object));
        try {
            object = this.driveService.files().create((com.google.api.services.drive.model.File)object2, (AbstractInputStreamContent)object).setFields("id");
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("appProperties, {");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(",");
            ((StringBuilder)object2).append(string3);
            ((StringBuilder)object2).append("}");
            object = (com.google.api.services.drive.model.File)((Drive.Files.Create)object).setFields(((StringBuilder)object2).toString()).execute();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }
}

