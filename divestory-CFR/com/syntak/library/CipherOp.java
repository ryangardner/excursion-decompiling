/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.syntak.library;

import android.text.TextUtils;
import android.util.Log;
import com.syntak.library.FileOp;
import com.syntak.library.MathOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CipherOp {
    public static final String DECRIPTION = "decription";
    public static final String ENCRIPTION = "encription";
    static final int MAX_DISTANCE = 240;
    static final int MIN_CIPHER_DATA_LENGTH = 256;
    static final int MIN_DISTANCE = 20;
    static final int OBSTACLE_LENGTH = 16;
    private static final String TAG_MD5 = "MD5";
    static int iterationCount;
    static AlgorithmParameterSpec paramSpec;
    static byte[] salt;

    static {
        salt = new byte[]{-87, -101, -56, 50, 86, 52, -29, 3};
        iterationCount = 19;
    }

    public static void createMapFiles(String object, String string2) {
        int n;
        byte[] arrby = new byte[256];
        byte[] arrby2 = new byte[256];
        Object object2 = new boolean[256];
        for (n = 0; n < 256; ++n) {
            object2[n] = false;
        }
        n = 0;
        do {
            int n2;
            int n3;
            int n4;
            if (n < 255) {
                n3 = MathOp.randomInteger(0, 255 - n);
                n2 = 0;
            } else {
                for (n = 0; n < 256; ++n) {
                    if (object2[n]) continue;
                    arrby2[n] = (byte)-1;
                    arrby[255] = (byte)n;
                }
                FileOp.deleteFile((String)object);
                FileOp.deleteFile(string2);
                try {
                    object2 = new FileOutputStream((String)object);
                    object = new FileOutputStream(string2);
                    ((OutputStream)object2).write(arrby, 0, 256);
                    ((OutputStream)object).write(arrby2, 0, 256);
                    ((OutputStream)object2).flush();
                    ((OutputStream)object2).close();
                    ((OutputStream)object).flush();
                    ((OutputStream)object).close();
                    return;
                }
                catch (FileNotFoundException | IOException iOException) {
                    return;
                }
            }
            for (n4 = 0; n4 < 256; ++n4) {
                int n5 = n2;
                if (!object2[n4]) {
                    if (n2 >= n3) break;
                    n5 = n2 + 1;
                }
                n2 = n5;
            }
            object2[n4] = true;
            arrby[n] = (byte)n4;
            arrby2[n4] = (byte)n;
            ++n;
        } while (true);
    }

    public static byte[] decriptData(SecretKey secretKey, byte[] arrby) throws Exception {
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(2, (Key)secretKey, paramSpec);
        return cipher.doFinal(arrby);
    }

    public static byte[] decriptData(byte[] arrby, int n, CipherParameter cipherParameter) {
        int n2;
        if (n < cipherParameter.minCipherDataLength) {
            return arrby;
        }
        int n3 = n2 = arrby[cipherParameter.obstacleLength - 1];
        if (n2 < 0) {
            n3 = n2 + 256;
        }
        n = n - cipherParameter.obstacleLength * 2 - n3;
        byte[] arrby2 = new byte[n];
        n2 = cipherParameter.obstacleLength;
        System.arraycopy(arrby, n2, arrby2, 0, n3);
        System.arraycopy(arrby, n2 + (cipherParameter.obstacleLength + n3), arrby2, n3 + 0, n - n3);
        return arrby2;
    }

    public static byte[] decriptData(byte[] object, byte[] arrby) throws Exception {
        object = new SecretKeySpec((byte[])object, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, (Key)object);
        return cipher.doFinal(arrby);
    }

    public static byte[] encriptData(SecretKey secretKey, byte[] arrby) throws Exception {
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(1, (Key)secretKey, paramSpec);
        return cipher.doFinal(arrby);
    }

    public static byte[] encriptData(byte[] arrby, int n, CipherParameter arrby2) {
        if (n < arrby2.minCipherDataLength) {
            return arrby;
        }
        int n2 = arrby2.obstacleLength;
        byte[] arrby3 = new byte[n2];
        int n3 = arrby2.obstacleLength;
        byte[] arrby4 = new byte[n3];
        int n4 = 0;
        do {
            if (n4 >= arrby2.obstacleLength) {
                n4 = MathOp.randomInteger(arrby2.minObstacleDistance, arrby2.maxObstacleDistance);
                arrby3[arrby2.obstacleLength - 1] = (byte)n4;
                arrby2 = new byte[arrby2.obstacleLength * 2 + n + n4];
                System.arraycopy(arrby3, 0, arrby2, 0, n2);
                System.arraycopy(arrby, 0, arrby2, n2 += 0, n4);
                System.arraycopy(arrby4, 0, arrby2, n2 += n4, n3);
                System.arraycopy(arrby, n4, arrby2, n2 + n3, n - n4);
                return arrby2;
            }
            arrby3[n4] = (byte)MathOp.randomInteger(0, 255);
            arrby4[n4] = (byte)MathOp.randomInteger(0, 255);
            ++n4;
        } while (true);
    }

    public static byte[] encriptData(byte[] object, byte[] arrby) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec((byte[])object, "AES");
        object = Cipher.getInstance("AES");
        ((Cipher)object).init(1, secretKeySpec);
        return ((Cipher)object).doFinal(arrby);
    }

    public static SecretKey generateKey(String object) throws Exception {
        object = new PBEKeySpec(((String)object).toCharArray(), salt, iterationCount);
        object = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret((KeySpec)object);
        paramSpec = new PBEParameterSpec(salt, iterationCount);
        return object;
    }

    public static byte[] mappingData(byte[] arrby, byte[] arrby2, int n) {
        byte[] arrby3 = new byte[n];
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int n4 = n3 = arrby2[n2];
            if (n3 < 0) {
                n4 = n3 + 256;
            }
            arrby3[n2] = arrby[n4];
            ++n2;
        }
        return arrby3;
    }

    public static byte[] readMapFile(String string2) {
        if (string2 == null) {
            return null;
        }
        if (!FileOp.checkFileExist(string2)) {
            return null;
        }
        byte[] arrby = new byte[256];
        try {
            FileInputStream fileInputStream = new FileInputStream(string2);
            ((InputStream)fileInputStream).read(arrby, 0, 256);
            return arrby;
        }
        catch (FileNotFoundException fileNotFoundException) {
            return null;
        }
        catch (IOException iOException) {
            return arrby;
        }
    }

    public boolean checkMD5(String string2, File object) {
        if (!TextUtils.isEmpty((CharSequence)string2) && object != null) {
            if ((object = this.getMD5((File)object)) == null) {
                Log.e((String)TAG_MD5, (String)"calculatedMD5 null");
                return false;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Calculated MD5: ");
            stringBuilder.append((String)object);
            Log.v((String)TAG_MD5, (String)stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("Provided MD5: ");
            stringBuilder.append(string2);
            Log.v((String)TAG_MD5, (String)stringBuilder.toString());
            return ((String)object).equalsIgnoreCase(string2);
        }
        Log.e((String)TAG_MD5, (String)"MD5 string empty or updateFile null");
        return false;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public String getMD5(File object) {
        int n;
        Throwable throwable2222;
        Object object2 = MessageDigest.getInstance(TAG_MD5);
        object = new FileInputStream((File)object);
        Object object3 = new byte[8192];
        while ((n = ((InputStream)object).read((byte[])object3)) > 0) {
            ((MessageDigest)object2).update((byte[])object3, 0, n);
        }
        object3 = ((MessageDigest)object2).digest();
        object2 = new BigInteger(1, (byte[])object3);
        object2 = String.format("%32s", ((BigInteger)object2).toString(16)).replace(' ', '0');
        try {
            ((InputStream)object).close();
            return object2;
        }
        catch (IOException iOException) {
            Log.e((String)TAG_MD5, (String)"Exception on closing MD5 input stream", (Throwable)iOException);
        }
        return object2;
        catch (FileNotFoundException fileNotFoundException) {
            Log.e((String)TAG_MD5, (String)"Exception while getting FileInputStream", (Throwable)fileNotFoundException);
            return null;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Log.e((String)TAG_MD5, (String)"Exception while getting digest", (Throwable)noSuchAlgorithmException);
            return null;
        }
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            {
                object3 = new RuntimeException("Unable to process file for MD5", iOException);
                throw object3;
            }
        }
        try {
            ((InputStream)object).close();
            throw throwable2222;
        }
        catch (IOException iOException) {
            Log.e((String)TAG_MD5, (String)"Exception on closing MD5 input stream", (Throwable)iOException);
        }
        throw throwable2222;
    }

    public static class CipherParameter {
        public int maxObstacleDistance = 240;
        public int minCipherDataLength = 256;
        public int minObstacleDistance = 20;
        public int obstacleLength = 16;
    }

}

