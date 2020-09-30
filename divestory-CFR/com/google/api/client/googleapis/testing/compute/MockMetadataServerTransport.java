/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.testing.compute;

import com.google.api.client.googleapis.auth.oauth2.OAuth2Utils;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.api.client.util.GenericData;
import java.io.IOException;

public class MockMetadataServerTransport
extends MockHttpTransport {
    static final JsonFactory JSON_FACTORY;
    private static final String METADATA_SERVER_URL;
    private static final String METADATA_TOKEN_SERVER_URL;
    String accessToken;
    Integer tokenRequestStatusCode;

    static {
        METADATA_SERVER_URL = OAuth2Utils.getMetadataServerUrl();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(METADATA_SERVER_URL);
        stringBuilder.append("/computeMetadata/v1/instance/service-accounts/default/token");
        METADATA_TOKEN_SERVER_URL = stringBuilder.toString();
        JSON_FACTORY = new JacksonFactory();
    }

    public MockMetadataServerTransport(String string2) {
        this.accessToken = string2;
    }

    @Override
    public LowLevelHttpRequest buildRequest(String string2, String string3) throws IOException {
        if (string3.equals(METADATA_TOKEN_SERVER_URL)) {
            return new MockLowLevelHttpRequest(string3){

                @Override
                public LowLevelHttpResponse execute() throws IOException {
                    if (MockMetadataServerTransport.this.tokenRequestStatusCode != null) {
                        return new MockLowLevelHttpResponse().setStatusCode(MockMetadataServerTransport.this.tokenRequestStatusCode).setContent("Token Fetch Error");
                    }
                    if (!"Google".equals(this.getFirstHeaderValue("Metadata-Flavor"))) throw new IOException("Metadata request header not found.");
                    Object object = new GenericJson();
                    ((GenericJson)object).setFactory(JSON_FACTORY);
                    ((GenericData)object).put("access_token", (Object)MockMetadataServerTransport.this.accessToken);
                    ((GenericData)object).put("expires_in", (Object)3600000);
                    ((GenericData)object).put("token_type", (Object)"Bearer");
                    object = ((GenericJson)object).toPrettyString();
                    return new MockLowLevelHttpResponse().setContentType("application/json; charset=UTF-8").setContent((String)object);
                }
            };
        }
        if (!string3.equals(METADATA_SERVER_URL)) return super.buildRequest(string2, string3);
        return new MockLowLevelHttpRequest(string3){

            @Override
            public LowLevelHttpResponse execute() {
                MockLowLevelHttpResponse mockLowLevelHttpResponse = new MockLowLevelHttpResponse();
                mockLowLevelHttpResponse.addHeader("Metadata-Flavor", "Google");
                return mockLowLevelHttpResponse;
            }
        };
    }

    public void setTokenRequestStatusCode(Integer n) {
        this.tokenRequestStatusCode = n;
    }

}

