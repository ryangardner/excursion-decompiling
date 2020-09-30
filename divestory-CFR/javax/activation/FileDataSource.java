/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataSource;
import javax.activation.FileTypeMap;

public class FileDataSource
implements DataSource {
    private File _file = null;
    private FileTypeMap typeMap = null;

    public FileDataSource(File file) {
        this._file = file;
    }

    public FileDataSource(String string2) {
        this(new File(string2));
    }

    @Override
    public String getContentType() {
        FileTypeMap fileTypeMap = this.typeMap;
        if (fileTypeMap != null) return fileTypeMap.getContentType(this._file);
        return FileTypeMap.getDefaultFileTypeMap().getContentType(this._file);
    }

    public File getFile() {
        return this._file;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this._file);
    }

    @Override
    public String getName() {
        return this._file.getName();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return new FileOutputStream(this._file);
    }

    public void setFileTypeMap(FileTypeMap fileTypeMap) {
        this.typeMap = fileTypeMap;
    }
}

