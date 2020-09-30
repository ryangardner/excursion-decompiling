/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.testing.auth.oauth2;

import com.google.api.client.googleapis.testing.TestUtils;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.api.client.util.GenericData;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MockTokenServerTransport
extends MockHttpTransport {
    static final String EXPECTED_GRANT_TYPE = "urn:ietf:params:oauth:grant-type:jwt-bearer";
    static final JsonFactory JSON_FACTORY;
    private static final String LEGACY_TOKEN_SERVER_URL = "https://accounts.google.com/o/oauth2/token";
    private static final Logger LOGGER;
    Map<String, String> clients = new HashMap<String, String>();
    Map<String, String> refreshTokens = new HashMap<String, String>();
    Map<String, String> serviceAccounts = new HashMap<String, String>();
    final String tokenServerUrl;

    static {
        LOGGER = Logger.getLogger(MockTokenServerTransport.class.getName());
        JSON_FACTORY = new JacksonFactory();
    }

    public MockTokenServerTransport() {
        this("https://oauth2.googleapis.com/token");
    }

    public MockTokenServerTransport(String string2) {
        this.tokenServerUrl = string2;
    }

    private MockLowLevelHttpRequest buildTokenRequest(String string2) {
        return new MockLowLevelHttpRequest(string2){

            @Override
            public LowLevelHttpResponse execute() throws IOException {
                Object object;
                Object object2 = TestUtils.parseQuery(this.getContentAsString());
                String string2 = object2.get("client_id");
                if (string2 != null) {
                    if (!MockTokenServerTransport.this.clients.containsKey(string2)) throw new IOException("Client ID not found.");
                    object = object2.get("client_secret");
                    string2 = MockTokenServerTransport.this.clients.get(string2);
                    if (object == null) throw new IOException("Client secret not found.");
                    if (!((String)object).equals(string2)) throw new IOException("Client secret not found.");
                    if (!MockTokenServerTransport.this.refreshTokens.containsKey(object2 = (String)object2.get("refresh_token"))) throw new IOException("Refresh Token not found.");
                    object2 = MockTokenServerTransport.this.refreshTokens.get(object2);
                } else {
                    if (!object2.containsKey("grant_type")) throw new IOException("Unknown token type.");
                    if (!MockTokenServerTransport.EXPECTED_GRANT_TYPE.equals(object2.get("grant_type"))) throw new IOException("Unexpected Grant Type.");
                    object2 = object2.get("assertion");
                    object = JsonWebSignature.parse(JSON_FACTORY, (String)object2);
                    object2 = ((JsonWebToken)object).getPayload().getIssuer();
                    if (!MockTokenServerTransport.this.serviceAccounts.containsKey(object2)) throw new IOException("Service Account Email not found as issuer.");
                    object2 = MockTokenServerTransport.this.serviceAccounts.get(object2);
                    if ((object = (String)((JsonWebToken)object).getPayload().get("scope")) == null) throw new IOException("Scopes not found.");
                    if (((String)object).length() == 0) throw new IOException("Scopes not found.");
                }
                object = new GenericJson();
                ((GenericJson)object).setFactory(JSON_FACTORY);
                ((GenericData)object).put("access_token", object2);
                ((GenericData)object).put("expires_in", (Object)3600);
                ((GenericData)object).put("token_type", (Object)"Bearer");
                object2 = ((GenericJson)object).toPrettyString();
                return new MockLowLevelHttpResponse().setContentType("application/json; charset=UTF-8").setContent((String)object2);
            }
        };
    }

    public void addClient(String string2, String string3) {
        this.clients.put(string2, string3);
    }

    public void addRefreshToken(String string2, String string3) {
        this.refreshTokens.put(string2, string3);
    }

    public void addServiceAccount(String string2, String string3) {
        this.serviceAccounts.put(string2, string3);
    }

    @Override
    public LowLevelHttpRequest buildRequest(String string2, String string3) throws IOException {
        if (string3.equals(this.tokenServerUrl)) {
            return this.buildTokenRequest(string3);
        }
        if (!string3.equals(LEGACY_TOKEN_SERVER_URL)) return super.buildRequest(string2, string3);
        LOGGER.warning("Your configured token_uri is using a legacy endpoint. You may want to redownload your credentials.");
        return this.buildTokenRequest(string3);
    }

}

