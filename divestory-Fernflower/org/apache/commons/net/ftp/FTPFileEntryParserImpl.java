package org.apache.commons.net.ftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public abstract class FTPFileEntryParserImpl implements FTPFileEntryParser {
   public List<String> preParse(List<String> var1) {
      return var1;
   }

   public String readNextEntry(BufferedReader var1) throws IOException {
      return var1.readLine();
   }
}
