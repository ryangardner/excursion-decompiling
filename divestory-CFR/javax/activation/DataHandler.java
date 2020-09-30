/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URL;
import javax.activation.CommandInfo;
import javax.activation.CommandMap;
import javax.activation.DataContentHandler;
import javax.activation.DataContentHandlerFactory;
import javax.activation.DataHandlerDataSource;
import javax.activation.DataSource;
import javax.activation.DataSourceDataContentHandler;
import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.activation.ObjectDataContentHandler;
import javax.activation.SecuritySupport;
import javax.activation.URLDataSource;
import javax.activation.UnsupportedDataTypeException;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.Transferable;
import myjava.awt.datatransfer.UnsupportedFlavorException;

public class DataHandler
implements Transferable {
    private static final DataFlavor[] emptyFlavors = new DataFlavor[0];
    private static DataContentHandlerFactory factory;
    private CommandMap currentCommandMap = null;
    private DataContentHandler dataContentHandler = null;
    private DataSource dataSource = null;
    private DataContentHandler factoryDCH = null;
    private DataSource objDataSource = null;
    private Object object = null;
    private String objectMimeType = null;
    private DataContentHandlerFactory oldFactory = null;
    private String shortType = null;
    private DataFlavor[] transferFlavors = emptyFlavors;

    public DataHandler(Object object, String string2) {
        this.object = object;
        this.objectMimeType = string2;
        this.oldFactory = factory;
    }

    public DataHandler(URL uRL) {
        this.dataSource = new URLDataSource(uRL);
        this.oldFactory = factory;
    }

    public DataHandler(DataSource dataSource) {
        this.dataSource = dataSource;
        this.oldFactory = factory;
    }

    static /* synthetic */ Object access$0(DataHandler dataHandler) {
        return dataHandler.object;
    }

    static /* synthetic */ String access$1(DataHandler dataHandler) {
        return dataHandler.objectMimeType;
    }

    private String getBaseType() {
        synchronized (this) {
            if (this.shortType != null) return this.shortType;
            String string2 = this.getContentType();
            try {
                MimeType mimeType = new MimeType(string2);
                this.shortType = mimeType.getBaseType();
                return this.shortType;
            }
            catch (MimeTypeParseException mimeTypeParseException) {
                this.shortType = string2;
            }
            return this.shortType;
        }
    }

    private CommandMap getCommandMap() {
        synchronized (this) {
            if (this.currentCommandMap == null) return CommandMap.getDefaultCommandMap();
            return this.currentCommandMap;
        }
    }

    private DataContentHandler getDataContentHandler() {
        synchronized (this) {
            if (factory != this.oldFactory) {
                this.oldFactory = factory;
                this.factoryDCH = null;
                this.dataContentHandler = null;
                this.transferFlavors = emptyFlavors;
            }
            if (this.dataContentHandler != null) {
                return this.dataContentHandler;
            }
            Object object = this.getBaseType();
            if (this.factoryDCH == null && factory != null) {
                this.factoryDCH = factory.createDataContentHandler((String)object);
            }
            if (this.factoryDCH != null) {
                this.dataContentHandler = this.factoryDCH;
            }
            if (this.dataContentHandler == null) {
                this.dataContentHandler = this.dataSource != null ? this.getCommandMap().createDataContentHandler((String)object, this.dataSource) : this.getCommandMap().createDataContentHandler((String)object);
            }
            this.dataContentHandler = this.dataSource != null ? (object = new DataSourceDataContentHandler(this.dataContentHandler, this.dataSource)) : (object = new ObjectDataContentHandler(this.dataContentHandler, this.object, this.objectMimeType));
            return this.dataContentHandler;
        }
    }

    public static void setDataContentHandlerFactory(DataContentHandlerFactory object) {
        synchronized (DataHandler.class) {
            if (factory == null) {
                SecurityManager securityManager = System.getSecurityManager();
                if (securityManager != null) {
                    try {
                        securityManager.checkSetFactory();
                    }
                    catch (SecurityException securityException) {
                        if (DataHandler.class.getClassLoader() != object.getClass().getClassLoader()) throw securityException;
                    }
                }
                factory = object;
                return;
            }
            object = new Error("DataContentHandlerFactory already defined");
            throw object;
        }
    }

    public CommandInfo[] getAllCommands() {
        if (this.dataSource == null) return this.getCommandMap().getAllCommands(this.getBaseType());
        return this.getCommandMap().getAllCommands(this.getBaseType(), this.dataSource);
    }

    public Object getBean(CommandInfo object) {
        try {
            ClassLoader classLoader;
            ClassLoader classLoader2 = classLoader = SecuritySupport.getContextClassLoader();
            if (classLoader != null) return ((CommandInfo)object).getCommandObject(this, classLoader2);
            classLoader2 = this.getClass().getClassLoader();
            return ((CommandInfo)object).getCommandObject(this, classLoader2);
        }
        catch (IOException | ClassNotFoundException exception) {
            return null;
        }
    }

    public CommandInfo getCommand(String string2) {
        if (this.dataSource == null) return this.getCommandMap().getCommand(this.getBaseType(), string2);
        return this.getCommandMap().getCommand(this.getBaseType(), string2, this.dataSource);
    }

    public Object getContent() throws IOException {
        Object object = this.object;
        if (object == null) return this.getDataContentHandler().getContent(this.getDataSource());
        return object;
    }

    public String getContentType() {
        DataSource dataSource = this.dataSource;
        if (dataSource == null) return this.objectMimeType;
        return dataSource.getContentType();
    }

    public DataSource getDataSource() {
        DataSource dataSource;
        DataSource dataSource2 = dataSource = this.dataSource;
        if (dataSource != null) return dataSource2;
        if (this.objDataSource != null) return this.objDataSource;
        this.objDataSource = new DataHandlerDataSource(this);
        return this.objDataSource;
    }

    public InputStream getInputStream() throws IOException {
        Object object = this.dataSource;
        if (object != null) {
            return object.getInputStream();
        }
        final DataContentHandler dataContentHandler = this.getDataContentHandler();
        if (dataContentHandler == null) {
            object = new StringBuilder("no DCH for MIME type ");
            ((StringBuilder)object).append(this.getBaseType());
            throw new UnsupportedDataTypeException(((StringBuilder)object).toString());
        }
        if (dataContentHandler instanceof ObjectDataContentHandler && ((ObjectDataContentHandler)dataContentHandler).getDCH() == null) {
            object = new StringBuilder("no object DCH for MIME type ");
            ((StringBuilder)object).append(this.getBaseType());
            throw new UnsupportedDataTypeException(((StringBuilder)object).toString());
        }
        final PipedOutputStream pipedOutputStream = new PipedOutputStream();
        object = new PipedInputStream(pipedOutputStream);
        new Thread(new Runnable(){

            /*
             * Unable to fully structure code
             * Enabled unnecessary exception pruning
             */
            @Override
            public void run() {
                try {
                    dataContentHandler.writeTo(DataHandler.access$0(DataHandler.this), DataHandler.access$1(DataHandler.this), pipedOutputStream);
                    ** GOTO lbl11
                }
                catch (Throwable var1_1) {
                    try {
                        pipedOutputStream.close();
                    }
                    catch (IOException var2_4) {
                        throw var1_1;
                    }
                    throw var1_1;
                    catch (IOException var2_2) {}
lbl11: // 2 sources:
                    try {
                        pipedOutputStream.close();
                        return;
                    }
                    catch (IOException var2_3) {
                        return;
                    }
                }
            }
        }, "DataHandler.getInputStream").start();
        return object;
    }

    public String getName() {
        DataSource dataSource = this.dataSource;
        if (dataSource == null) return null;
        return dataSource.getName();
    }

    public OutputStream getOutputStream() throws IOException {
        DataSource dataSource = this.dataSource;
        if (dataSource == null) return null;
        return dataSource.getOutputStream();
    }

    public CommandInfo[] getPreferredCommands() {
        if (this.dataSource == null) return this.getCommandMap().getPreferredCommands(this.getBaseType());
        return this.getCommandMap().getPreferredCommands(this.getBaseType(), this.dataSource);
    }

    @Override
    public Object getTransferData(DataFlavor dataFlavor) throws UnsupportedFlavorException, IOException {
        return this.getDataContentHandler().getTransferData(dataFlavor, this.dataSource);
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        synchronized (this) {
            if (factory != this.oldFactory) {
                this.transferFlavors = emptyFlavors;
            }
            if (this.transferFlavors != emptyFlavors) return this.transferFlavors;
            this.transferFlavors = this.getDataContentHandler().getTransferDataFlavors();
            return this.transferFlavors;
        }
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
        DataFlavor[] arrdataFlavor = this.getTransferDataFlavors();
        int n = 0;
        while (n < arrdataFlavor.length) {
            if (arrdataFlavor[n].equals(dataFlavor)) {
                return true;
            }
            ++n;
        }
        return false;
    }

    public void setCommandMap(CommandMap commandMap) {
        synchronized (this) {
            if (commandMap == this.currentCommandMap) {
                if (commandMap != null) return;
            }
            this.transferFlavors = emptyFlavors;
            this.dataContentHandler = null;
            this.currentCommandMap = commandMap;
            return;
        }
    }

    public void writeTo(OutputStream outputStream2) throws IOException {
        Object object = this.dataSource;
        if (object == null) {
            this.getDataContentHandler().writeTo(this.object, this.objectMimeType, outputStream2);
            return;
        }
        byte[] arrby = new byte[8192];
        object = object.getInputStream();
        try {
            do {
                int n;
                if ((n = ((InputStream)object).read(arrby)) <= 0) {
                    return;
                }
                outputStream2.write(arrby, 0, n);
            } while (true);
        }
        finally {
            ((InputStream)object).close();
        }
    }

}

