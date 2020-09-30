/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp.parser;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.net.ftp.Configurable;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFileEntryParser;
import org.apache.commons.net.ftp.parser.CompositeFileEntryParser;
import org.apache.commons.net.ftp.parser.FTPFileEntryParserFactory;
import org.apache.commons.net.ftp.parser.MVSFTPEntryParser;
import org.apache.commons.net.ftp.parser.MacOsPeterFTPEntryParser;
import org.apache.commons.net.ftp.parser.NTFTPEntryParser;
import org.apache.commons.net.ftp.parser.NetwareFTPEntryParser;
import org.apache.commons.net.ftp.parser.OS2FTPEntryParser;
import org.apache.commons.net.ftp.parser.OS400FTPEntryParser;
import org.apache.commons.net.ftp.parser.ParserInitializationException;
import org.apache.commons.net.ftp.parser.UnixFTPEntryParser;
import org.apache.commons.net.ftp.parser.VMSVersioningFTPEntryParser;

public class DefaultFTPFileEntryParserFactory
implements FTPFileEntryParserFactory {
    private static final String JAVA_IDENTIFIER = "\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*";
    private static final String JAVA_QUALIFIED_NAME = "(\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*\\.)+\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*";
    private static final Pattern JAVA_QUALIFIED_NAME_PATTERN = Pattern.compile("(\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*\\.)+\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*");

    private FTPFileEntryParser createFileEntryParser(String string2, FTPClientConfig object) {
        FTPFileEntryParser fTPFileEntryParser;
        Object object2;
        block27 : {
            if (JAVA_QUALIFIED_NAME_PATTERN.matcher(string2).matches()) {
                try {
                    object2 = Class.forName(string2);
                    try {
                        fTPFileEntryParser = (FTPFileEntryParser)((Class)object2).newInstance();
                        break block27;
                    }
                    catch (ExceptionInInitializerError exceptionInInitializerError) {
                        object2 = new ParserInitializationException("Error initializing parser", exceptionInInitializerError);
                        throw object2;
                    }
                    catch (Exception exception) {
                        ParserInitializationException parserInitializationException = new ParserInitializationException("Error initializing parser", exception);
                        throw parserInitializationException;
                    }
                    catch (ClassCastException classCastException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(((Class)object2).getName());
                        stringBuilder.append(" does not implement the interface ");
                        stringBuilder.append("org.apache.commons.net.ftp.FTPFileEntryParser.");
                        ParserInitializationException parserInitializationException = new ParserInitializationException(stringBuilder.toString(), classCastException);
                        throw parserInitializationException;
                    }
                }
                catch (ClassNotFoundException classNotFoundException) {}
            }
            fTPFileEntryParser = null;
        }
        object2 = fTPFileEntryParser;
        if (fTPFileEntryParser == null) {
            object2 = string2.toUpperCase(Locale.ENGLISH);
            if (((String)object2).indexOf("UNIX") >= 0) {
                object2 = new UnixFTPEntryParser((FTPClientConfig)object, false);
            } else if (((String)object2).indexOf("UNIX_LTRIM") >= 0) {
                object2 = new UnixFTPEntryParser((FTPClientConfig)object, true);
            } else if (((String)object2).indexOf("VMS") >= 0) {
                object2 = new VMSVersioningFTPEntryParser((FTPClientConfig)object);
            } else if (((String)object2).indexOf("WINDOWS") >= 0) {
                object2 = this.createNTFTPEntryParser((FTPClientConfig)object);
            } else if (((String)object2).indexOf("OS/2") >= 0) {
                object2 = new OS2FTPEntryParser((FTPClientConfig)object);
            } else if (((String)object2).indexOf("OS/400") < 0 && ((String)object2).indexOf("AS/400") < 0) {
                if (((String)object2).indexOf("MVS") >= 0) {
                    object2 = new MVSFTPEntryParser();
                } else if (((String)object2).indexOf("NETWARE") >= 0) {
                    object2 = new NetwareFTPEntryParser((FTPClientConfig)object);
                } else if (((String)object2).indexOf("MACOS PETER") >= 0) {
                    object2 = new MacOsPeterFTPEntryParser((FTPClientConfig)object);
                } else {
                    if (((String)object2).indexOf("TYPE: L8") < 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unknown parser type: ");
                        ((StringBuilder)object).append(string2);
                        throw new ParserInitializationException(((StringBuilder)object).toString());
                    }
                    object2 = new UnixFTPEntryParser((FTPClientConfig)object);
                }
            } else {
                object2 = this.createOS400FTPEntryParser((FTPClientConfig)object);
            }
        }
        if (!(object2 instanceof Configurable)) return object2;
        ((Configurable)object2).configure((FTPClientConfig)object);
        return object2;
    }

    private FTPFileEntryParser createNTFTPEntryParser(FTPClientConfig fTPClientConfig) {
        boolean bl;
        if (fTPClientConfig != null && "WINDOWS".equals(fTPClientConfig.getServerSystemKey())) {
            return new NTFTPEntryParser(fTPClientConfig);
        }
        NTFTPEntryParser nTFTPEntryParser = new NTFTPEntryParser(fTPClientConfig);
        boolean bl2 = bl = false;
        if (fTPClientConfig == null) return new CompositeFileEntryParser(new FTPFileEntryParser[]{nTFTPEntryParser, new UnixFTPEntryParser(fTPClientConfig, bl2)});
        bl2 = bl;
        if (!"UNIX_LTRIM".equals(fTPClientConfig.getServerSystemKey())) return new CompositeFileEntryParser(new FTPFileEntryParser[]{nTFTPEntryParser, new UnixFTPEntryParser(fTPClientConfig, bl2)});
        bl2 = true;
        return new CompositeFileEntryParser(new FTPFileEntryParser[]{nTFTPEntryParser, new UnixFTPEntryParser(fTPClientConfig, bl2)});
    }

    private FTPFileEntryParser createOS400FTPEntryParser(FTPClientConfig fTPClientConfig) {
        boolean bl;
        if (fTPClientConfig != null && "OS/400".equals(fTPClientConfig.getServerSystemKey())) {
            return new OS400FTPEntryParser(fTPClientConfig);
        }
        OS400FTPEntryParser oS400FTPEntryParser = new OS400FTPEntryParser(fTPClientConfig);
        boolean bl2 = bl = false;
        if (fTPClientConfig == null) return new CompositeFileEntryParser(new FTPFileEntryParser[]{oS400FTPEntryParser, new UnixFTPEntryParser(fTPClientConfig, bl2)});
        bl2 = bl;
        if (!"UNIX_LTRIM".equals(fTPClientConfig.getServerSystemKey())) return new CompositeFileEntryParser(new FTPFileEntryParser[]{oS400FTPEntryParser, new UnixFTPEntryParser(fTPClientConfig, bl2)});
        bl2 = true;
        return new CompositeFileEntryParser(new FTPFileEntryParser[]{oS400FTPEntryParser, new UnixFTPEntryParser(fTPClientConfig, bl2)});
    }

    @Override
    public FTPFileEntryParser createFileEntryParser(String string2) {
        if (string2 == null) throw new ParserInitializationException("Parser key cannot be null");
        return this.createFileEntryParser(string2, null);
    }

    @Override
    public FTPFileEntryParser createFileEntryParser(FTPClientConfig fTPClientConfig) throws ParserInitializationException {
        return this.createFileEntryParser(fTPClientConfig.getServerSystemKey(), fTPClientConfig);
    }

    public FTPFileEntryParser createMVSEntryParser() {
        return new MVSFTPEntryParser();
    }

    public FTPFileEntryParser createNTFTPEntryParser() {
        return this.createNTFTPEntryParser(null);
    }

    public FTPFileEntryParser createNetwareFTPEntryParser() {
        return new NetwareFTPEntryParser();
    }

    public FTPFileEntryParser createOS2FTPEntryParser() {
        return new OS2FTPEntryParser();
    }

    public FTPFileEntryParser createOS400FTPEntryParser() {
        return this.createOS400FTPEntryParser(null);
    }

    public FTPFileEntryParser createUnixFTPEntryParser() {
        return new UnixFTPEntryParser();
    }

    public FTPFileEntryParser createVMSVersioningFTPEntryParser() {
        return new VMSVersioningFTPEntryParser();
    }
}

