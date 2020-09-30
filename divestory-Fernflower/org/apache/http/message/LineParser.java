package org.apache.http.message;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.util.CharArrayBuffer;

public interface LineParser {
   boolean hasProtocolVersion(CharArrayBuffer var1, ParserCursor var2);

   Header parseHeader(CharArrayBuffer var1) throws ParseException;

   ProtocolVersion parseProtocolVersion(CharArrayBuffer var1, ParserCursor var2) throws ParseException;

   RequestLine parseRequestLine(CharArrayBuffer var1, ParserCursor var2) throws ParseException;

   StatusLine parseStatusLine(CharArrayBuffer var1, ParserCursor var2) throws ParseException;
}
