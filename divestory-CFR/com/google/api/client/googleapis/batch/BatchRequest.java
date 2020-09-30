/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.batch;

import com.google.api.client.googleapis.batch.BatchCallback;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public final class BatchRequest {
    private static final String GLOBAL_BATCH_ENDPOINT = "https://www.googleapis.com/batch";
    private static final String GLOBAL_BATCH_ENDPOINT_WARNING = "You are using the global batch endpoint which will soon be shut down. Please instantiate your BatchRequest via your service client's `batch(HttpRequestInitializer)` method. For an example, please see https://github.com/googleapis/google-api-java-client#batching.";
    private static final Logger LOGGER = Logger.getLogger(BatchRequest.class.getName());
    private GenericUrl batchUrl = new GenericUrl("https://www.googleapis.com/batch");
    private final HttpRequestFactory requestFactory;
    List<RequestInfo<?, ?>> requestInfos = new ArrayList();
    private Sleeper sleeper = Sleeper.DEFAULT;

    @Deprecated
    public BatchRequest(HttpTransport object, HttpRequestInitializer httpRequestInitializer) {
        object = httpRequestInitializer == null ? ((HttpTransport)object).createRequestFactory() : ((HttpTransport)object).createRequestFactory(httpRequestInitializer);
        this.requestFactory = object;
    }

    /*
     * Exception decompiling
     */
    public void execute() throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[DOLOOP]], but top level block is 0[TRYBLOCK]
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

    public GenericUrl getBatchUrl() {
        return this.batchUrl;
    }

    public Sleeper getSleeper() {
        return this.sleeper;
    }

    public <T, E> BatchRequest queue(HttpRequest httpRequest, Class<T> class_, Class<E> class_2, BatchCallback<T, E> batchCallback) throws IOException {
        Preconditions.checkNotNull(httpRequest);
        Preconditions.checkNotNull(batchCallback);
        Preconditions.checkNotNull(class_);
        Preconditions.checkNotNull(class_2);
        this.requestInfos.add(new RequestInfo<T, E>(batchCallback, class_, class_2, httpRequest));
        return this;
    }

    public BatchRequest setBatchUrl(GenericUrl genericUrl) {
        this.batchUrl = genericUrl;
        return this;
    }

    public BatchRequest setSleeper(Sleeper sleeper) {
        this.sleeper = Preconditions.checkNotNull(sleeper);
        return this;
    }

    public int size() {
        return this.requestInfos.size();
    }

    class BatchInterceptor
    implements HttpExecuteInterceptor {
        private HttpExecuteInterceptor originalInterceptor;

        BatchInterceptor(HttpExecuteInterceptor httpExecuteInterceptor) {
            this.originalInterceptor = httpExecuteInterceptor;
        }

        @Override
        public void intercept(HttpRequest object) throws IOException {
            HttpExecuteInterceptor httpExecuteInterceptor = this.originalInterceptor;
            if (httpExecuteInterceptor != null) {
                httpExecuteInterceptor.intercept((HttpRequest)object);
            }
            Iterator<RequestInfo<?, ?>> iterator2 = BatchRequest.this.requestInfos.iterator();
            while (iterator2.hasNext()) {
                object = iterator2.next();
                httpExecuteInterceptor = ((RequestInfo)object).request.getInterceptor();
                if (httpExecuteInterceptor == null) continue;
                httpExecuteInterceptor.intercept(((RequestInfo)object).request);
            }
        }
    }

    static class RequestInfo<T, E> {
        final BatchCallback<T, E> callback;
        final Class<T> dataClass;
        final Class<E> errorClass;
        final HttpRequest request;

        RequestInfo(BatchCallback<T, E> batchCallback, Class<T> class_, Class<E> class_2, HttpRequest httpRequest) {
            this.callback = batchCallback;
            this.dataClass = class_;
            this.errorClass = class_2;
            this.request = httpRequest;
        }
    }

}

