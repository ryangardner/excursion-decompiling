package org.apache.commons.net.ftp.parser;

import java.text.ParseException;
import java.util.Calendar;

public interface FTPTimestampParser {
   String DEFAULT_RECENT_SDF = "MMM d HH:mm";
   String DEFAULT_SDF = "MMM d yyyy";

   Calendar parseTimestamp(String var1) throws ParseException;
}
