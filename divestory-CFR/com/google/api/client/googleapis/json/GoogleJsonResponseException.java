/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.json;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import com.google.api.client.util.Strings;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class GoogleJsonResponseException
extends HttpResponseException {
    private static final long serialVersionUID = 409811126989994864L;
    private final transient GoogleJsonError details;

    public GoogleJsonResponseException(HttpResponseException.Builder builder, GoogleJsonError googleJsonError) {
        super(builder);
        this.details = googleJsonError;
    }

    public static HttpResponse execute(JsonFactory jsonFactory, HttpRequest httpRequest) throws GoogleJsonResponseException, IOException {
        Preconditions.checkNotNull(jsonFactory);
        boolean bl = httpRequest.getThrowExceptionOnExecuteError();
        if (bl) {
            httpRequest.setThrowExceptionOnExecuteError(false);
        }
        HttpResponse httpResponse = httpRequest.execute();
        httpRequest.setThrowExceptionOnExecuteError(bl);
        if (!bl) return httpResponse;
        if (!httpResponse.isSuccessStatusCode()) throw GoogleJsonResponseException.from(jsonFactory, httpResponse);
        return httpResponse;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static GoogleJsonResponseException from(JsonFactory var0, HttpResponse var1_1) {
        block30 : {
            block31 : {
                block26 : {
                    block29 : {
                        block28 : {
                            block27 : {
                                var2_2 = new HttpResponseException.Builder(var1_1.getStatusCode(), var1_1.getStatusMessage(), var1_1.getHeaders());
                                Preconditions.checkNotNull(var0);
                                var3_3 = null;
                                var4_5 = null;
                                var5_30 = null;
                                if (var1_1.isSuccessStatusCode() || !HttpMediaType.equalsIgnoreParameters("application/json; charset=UTF-8", var1_1.getContentType()) || (var6_34 = var1_1.getContent()) == null) break block26;
                                var6_34 = var0.createJsonParser(var1_1.getContent());
                                var4_6 = var6_34.getCurrentToken();
                                var0 = var4_6;
                                if (var4_6 == null) {
                                    var0 = var6_34.nextToken();
                                }
                                if (var0 == null) break block27;
                                var6_34.skipToKey("error");
                                if (var6_34.getCurrentToken() == JsonToken.VALUE_STRING) {
                                    var0 = var6_34.getText();
                                    break block28;
                                }
                                if (var6_34.getCurrentToken() != JsonToken.START_OBJECT) break block27;
                                var0 = var6_34.parseAndClose(GoogleJsonError.class);
                                try {
                                    var4_7 = var0.toPrettyString();
                                    var5_30 = var0;
                                    var0 = var4_7;
                                    break block28;
                                }
                                catch (Throwable var5_31) {
                                    var3_3 = var6_34;
                                    ** GOTO lbl80
                                }
                                catch (IOException var4_8) {
                                    var5_30 = var6_34;
                                    break block29;
                                }
                            }
                            var0 = null;
                        }
                        if (var6_34 != null) ** GOTO lbl43
                        try {
                            var1_1.ignore();
                            var4_10 = var5_30;
                            var3_3 = var0;
                            break block30;
lbl43: // 1 sources:
                            var4_12 = var5_30;
                            var3_3 = var0;
                            if (var5_30 == null) {
                                var6_34.close();
                                var4_13 = var5_30;
                                var3_3 = var0;
                            }
                            break block30;
                        }
                        catch (IOException var4_11) {
                            break block31;
                        }
                        catch (Throwable var5_32) {
                            var0 = null;
                            var3_3 = var6_34;
                            ** GOTO lbl80
                        }
                        catch (IOException var4_14) {
                            var0 = null;
                            var5_30 = var6_34;
                            break block29;
                        }
                        catch (Throwable var5_33) {
                            var0 = var3_3 = null;
                            ** GOTO lbl80
                        }
                        catch (IOException var4_15) {
                            var0 = var5_30 = null;
                        }
                    }
                    var4_16.printStackTrace();
                    if (var5_30 != null) ** GOTO lbl73
                    var4_17 = var0;
                    try {
                        block32 : {
                            var1_1.ignore();
                            break block32;
lbl73: // 1 sources:
                            if (var0 == null) {
                                var4_19 = var0;
                                var5_30.close();
                            }
                            break block32;
                            catch (Throwable var4_22) {
                                var3_3 = var5_30;
                                var5_30 = var4_22;
                            }
lbl80: // 4 sources:
                            if (var3_3 != null) {
                                if (var0 == null) {
                                    var4_23 = var0;
                                    var3_3.close();
                                }
                            } else {
                                var4_24 = var0;
                                var1_1.ignore();
                            }
                            var4_25 = var0;
                            throw var5_30;
                        }
                        var3_3 = null;
                        var4_21 = var0;
                        break block30;
                    }
                    catch (IOException var3_4) {
                        var0 = null;
                        var5_30 = var4_17;
                        var4_18 = var3_4;
                        break block31;
                    }
                }
                try {
                    var3_3 = var0 = var1_1.parseAsString();
                    break block30;
                }
                catch (IOException var4_26) {
                    var0 = null;
                    var5_30 = var3_3;
                }
            }
            var4_27.printStackTrace();
            var3_3 = var0;
            var4_28 = var5_30;
        }
        var0 = HttpResponseException.computeMessageBuffer(var1_1);
        if (!Strings.isNullOrEmpty((String)var3_3)) {
            var0.append(StringUtils.LINE_SEPARATOR);
            var0.append((String)var3_3);
            var2_2.setContent((String)var3_3);
        }
        var2_2.setMessage(var0.toString());
        return new GoogleJsonResponseException(var2_2, (GoogleJsonError)var4_29);
    }

    public final GoogleJsonError getDetails() {
        return this.details;
    }
}

