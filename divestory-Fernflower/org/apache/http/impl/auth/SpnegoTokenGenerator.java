package org.apache.http.impl.auth;

import java.io.IOException;

public interface SpnegoTokenGenerator {
   byte[] generateSpnegoDERObject(byte[] var1) throws IOException;
}
