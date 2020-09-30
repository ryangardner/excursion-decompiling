package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.TextBuffer;

public class IOContext {
   protected byte[] _base64Buffer;
   protected final BufferRecycler _bufferRecycler;
   protected char[] _concatCBuffer;
   protected JsonEncoding _encoding;
   protected final boolean _managedResource;
   protected char[] _nameCopyBuffer;
   protected byte[] _readIOBuffer;
   protected final Object _sourceRef;
   protected char[] _tokenCBuffer;
   protected byte[] _writeEncodingBuffer;

   public IOContext(BufferRecycler var1, Object var2, boolean var3) {
      this._bufferRecycler = var1;
      this._sourceRef = var2;
      this._managedResource = var3;
   }

   private IllegalArgumentException wrongBuf() {
      return new IllegalArgumentException("Trying to release buffer smaller than original");
   }

   protected final void _verifyAlloc(Object var1) {
      if (var1 != null) {
         throw new IllegalStateException("Trying to call same allocXxx() method second time");
      }
   }

   protected final void _verifyRelease(byte[] var1, byte[] var2) {
      if (var1 != var2 && var1.length < var2.length) {
         throw this.wrongBuf();
      }
   }

   protected final void _verifyRelease(char[] var1, char[] var2) {
      if (var1 != var2 && var1.length < var2.length) {
         throw this.wrongBuf();
      }
   }

   public byte[] allocBase64Buffer() {
      this._verifyAlloc(this._base64Buffer);
      byte[] var1 = this._bufferRecycler.allocByteBuffer(3);
      this._base64Buffer = var1;
      return var1;
   }

   public byte[] allocBase64Buffer(int var1) {
      this._verifyAlloc(this._base64Buffer);
      byte[] var2 = this._bufferRecycler.allocByteBuffer(3, var1);
      this._base64Buffer = var2;
      return var2;
   }

   public char[] allocConcatBuffer() {
      this._verifyAlloc(this._concatCBuffer);
      char[] var1 = this._bufferRecycler.allocCharBuffer(1);
      this._concatCBuffer = var1;
      return var1;
   }

   public char[] allocNameCopyBuffer(int var1) {
      this._verifyAlloc(this._nameCopyBuffer);
      char[] var2 = this._bufferRecycler.allocCharBuffer(3, var1);
      this._nameCopyBuffer = var2;
      return var2;
   }

   public byte[] allocReadIOBuffer() {
      this._verifyAlloc(this._readIOBuffer);
      byte[] var1 = this._bufferRecycler.allocByteBuffer(0);
      this._readIOBuffer = var1;
      return var1;
   }

   public byte[] allocReadIOBuffer(int var1) {
      this._verifyAlloc(this._readIOBuffer);
      byte[] var2 = this._bufferRecycler.allocByteBuffer(0, var1);
      this._readIOBuffer = var2;
      return var2;
   }

   public char[] allocTokenBuffer() {
      this._verifyAlloc(this._tokenCBuffer);
      char[] var1 = this._bufferRecycler.allocCharBuffer(0);
      this._tokenCBuffer = var1;
      return var1;
   }

   public char[] allocTokenBuffer(int var1) {
      this._verifyAlloc(this._tokenCBuffer);
      char[] var2 = this._bufferRecycler.allocCharBuffer(0, var1);
      this._tokenCBuffer = var2;
      return var2;
   }

   public byte[] allocWriteEncodingBuffer() {
      this._verifyAlloc(this._writeEncodingBuffer);
      byte[] var1 = this._bufferRecycler.allocByteBuffer(1);
      this._writeEncodingBuffer = var1;
      return var1;
   }

   public byte[] allocWriteEncodingBuffer(int var1) {
      this._verifyAlloc(this._writeEncodingBuffer);
      byte[] var2 = this._bufferRecycler.allocByteBuffer(1, var1);
      this._writeEncodingBuffer = var2;
      return var2;
   }

   public TextBuffer constructTextBuffer() {
      return new TextBuffer(this._bufferRecycler);
   }

   public JsonEncoding getEncoding() {
      return this._encoding;
   }

   public Object getSourceReference() {
      return this._sourceRef;
   }

   public boolean isResourceManaged() {
      return this._managedResource;
   }

   public void releaseBase64Buffer(byte[] var1) {
      if (var1 != null) {
         this._verifyRelease(var1, this._base64Buffer);
         this._base64Buffer = null;
         this._bufferRecycler.releaseByteBuffer(3, var1);
      }

   }

   public void releaseConcatBuffer(char[] var1) {
      if (var1 != null) {
         this._verifyRelease(var1, this._concatCBuffer);
         this._concatCBuffer = null;
         this._bufferRecycler.releaseCharBuffer(1, var1);
      }

   }

   public void releaseNameCopyBuffer(char[] var1) {
      if (var1 != null) {
         this._verifyRelease(var1, this._nameCopyBuffer);
         this._nameCopyBuffer = null;
         this._bufferRecycler.releaseCharBuffer(3, var1);
      }

   }

   public void releaseReadIOBuffer(byte[] var1) {
      if (var1 != null) {
         this._verifyRelease(var1, this._readIOBuffer);
         this._readIOBuffer = null;
         this._bufferRecycler.releaseByteBuffer(0, var1);
      }

   }

   public void releaseTokenBuffer(char[] var1) {
      if (var1 != null) {
         this._verifyRelease(var1, this._tokenCBuffer);
         this._tokenCBuffer = null;
         this._bufferRecycler.releaseCharBuffer(0, var1);
      }

   }

   public void releaseWriteEncodingBuffer(byte[] var1) {
      if (var1 != null) {
         this._verifyRelease(var1, this._writeEncodingBuffer);
         this._writeEncodingBuffer = null;
         this._bufferRecycler.releaseByteBuffer(1, var1);
      }

   }

   public void setEncoding(JsonEncoding var1) {
      this._encoding = var1;
   }

   public IOContext withEncoding(JsonEncoding var1) {
      this._encoding = var1;
      return this;
   }
}
