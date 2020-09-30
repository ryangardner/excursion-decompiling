/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Deprecated
public class CloudShellCredential
extends GoogleCredential {
    private static final int ACCESS_TOKEN_INDEX = 2;
    protected static final String GET_AUTH_TOKEN_REQUEST = "2\n[]";
    private static final int READ_TIMEOUT_MS = 5000;
    private final int authPort;
    private final JsonFactory jsonFactory;

    public CloudShellCredential(int n, JsonFactory jsonFactory) {
        this.authPort = n;
        this.jsonFactory = jsonFactory;
    }

    @Override
    protected TokenResponse executeRefreshToken() throws IOException {
        Socket socket = new Socket("localhost", this.getAuthPort());
        socket.setSoTimeout(5000);
        TokenResponse tokenResponse = new TokenResponse();
        try {
            Closeable closeable = new PrintWriter(socket.getOutputStream(), true);
            ((PrintWriter)closeable).println(GET_AUTH_TOKEN_REQUEST);
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            closeable = new BufferedReader(inputStreamReader);
            ((BufferedReader)closeable).readLine();
            tokenResponse.setAccessToken(((List)this.jsonFactory.createJsonParser((Reader)closeable).parseArray(LinkedList.class, Object.class)).get(2).toString());
            return tokenResponse;
        }
        finally {
            socket.close();
        }
    }

    protected int getAuthPort() {
        return this.authPort;
    }
}

