package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.io.NumberInput;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public final class TextBuffer {
   static final int MAX_SEGMENT_LEN = 65536;
   static final int MIN_SEGMENT_LEN = 500;
   static final char[] NO_CHARS = new char[0];
   private final BufferRecycler _allocator;
   private char[] _currentSegment;
   private int _currentSize;
   private boolean _hasSegments;
   private char[] _inputBuffer;
   private int _inputLen;
   private int _inputStart;
   private char[] _resultArray;
   private String _resultString;
   private int _segmentSize;
   private ArrayList<char[]> _segments;

   public TextBuffer(BufferRecycler var1) {
      this._allocator = var1;
   }

   protected TextBuffer(BufferRecycler var1, char[] var2) {
      this._allocator = var1;
      this._currentSegment = var2;
      this._currentSize = var2.length;
      this._inputStart = -1;
   }

   private char[] buf(int var1) {
      BufferRecycler var2 = this._allocator;
      return var2 != null ? var2.allocCharBuffer(2, var1) : new char[Math.max(var1, 500)];
   }

   private char[] carr(int var1) {
      return new char[var1];
   }

   private void clearSegments() {
      this._hasSegments = false;
      this._segments.clear();
      this._segmentSize = 0;
      this._currentSize = 0;
   }

   private void expand(int var1) {
      if (this._segments == null) {
         this._segments = new ArrayList();
      }

      char[] var2 = this._currentSegment;
      this._hasSegments = true;
      this._segments.add(var2);
      this._segmentSize += var2.length;
      this._currentSize = 0;
      var1 = var2.length;
      int var3 = var1 + (var1 >> 1);
      if (var3 < 500) {
         var1 = 500;
      } else {
         var1 = var3;
         if (var3 > 65536) {
            var1 = 65536;
         }
      }

      this._currentSegment = this.carr(var1);
   }

   public static TextBuffer fromInitial(char[] var0) {
      return new TextBuffer((BufferRecycler)null, var0);
   }

   private char[] resultArray() {
      String var1 = this._resultString;
      if (var1 != null) {
         return var1.toCharArray();
      } else {
         int var2 = this._inputStart;
         int var3;
         if (var2 >= 0) {
            var3 = this._inputLen;
            if (var3 < 1) {
               return NO_CHARS;
            } else {
               return var2 == 0 ? Arrays.copyOf(this._inputBuffer, var3) : Arrays.copyOfRange(this._inputBuffer, var2, var3 + var2);
            }
         } else {
            var2 = this.size();
            if (var2 < 1) {
               return NO_CHARS;
            } else {
               char[] var7 = this.carr(var2);
               ArrayList var4 = this._segments;
               int var6;
               if (var4 != null) {
                  int var5 = var4.size();
                  var3 = 0;
                  var2 = 0;

                  while(true) {
                     var6 = var2;
                     if (var3 >= var5) {
                        break;
                     }

                     char[] var8 = (char[])this._segments.get(var3);
                     var6 = var8.length;
                     System.arraycopy(var8, 0, var7, var2, var6);
                     var2 += var6;
                     ++var3;
                  }
               } else {
                  var6 = 0;
               }

               System.arraycopy(this._currentSegment, 0, var7, var6, this._currentSize);
               return var7;
            }
         }
      }
   }

   private void unshare(int var1) {
      int var2 = this._inputLen;
      this._inputLen = 0;
      char[] var3 = this._inputBuffer;
      this._inputBuffer = null;
      int var4 = this._inputStart;
      this._inputStart = -1;
      var1 += var2;
      char[] var5 = this._currentSegment;
      if (var5 == null || var1 > var5.length) {
         this._currentSegment = this.buf(var1);
      }

      if (var2 > 0) {
         System.arraycopy(var3, var4, this._currentSegment, 0, var2);
      }

      this._segmentSize = 0;
      this._currentSize = var2;
   }

   public void append(char var1) {
      if (this._inputStart >= 0) {
         this.unshare(16);
      }

      this._resultString = null;
      this._resultArray = null;
      char[] var2 = this._currentSegment;
      char[] var3 = var2;
      if (this._currentSize >= var2.length) {
         this.expand(1);
         var3 = this._currentSegment;
      }

      int var4 = this._currentSize++;
      var3[var4] = (char)var1;
   }

   public void append(String var1, int var2, int var3) {
      if (this._inputStart >= 0) {
         this.unshare(var3);
      }

      this._resultString = null;
      this._resultArray = null;
      char[] var4 = this._currentSegment;
      int var5 = var4.length;
      int var6 = this._currentSize;
      int var7 = var5 - var6;
      if (var7 >= var3) {
         var1.getChars(var2, var2 + var3, var4, var6);
         this._currentSize += var3;
      } else {
         int var8 = var2;
         var5 = var3;
         if (var7 > 0) {
            var8 = var2 + var7;
            var1.getChars(var2, var8, var4, var6);
            var5 = var3 - var7;
         }

         while(true) {
            this.expand(var5);
            var3 = Math.min(this._currentSegment.length, var5);
            var2 = var8 + var3;
            var1.getChars(var8, var2, this._currentSegment, 0);
            this._currentSize += var3;
            var5 -= var3;
            if (var5 <= 0) {
               return;
            }

            var8 = var2;
         }
      }
   }

   public void append(char[] var1, int var2, int var3) {
      if (this._inputStart >= 0) {
         this.unshare(var3);
      }

      this._resultString = null;
      this._resultArray = null;
      char[] var4 = this._currentSegment;
      int var5 = var4.length;
      int var6 = this._currentSize;
      int var7 = var5 - var6;
      if (var7 >= var3) {
         System.arraycopy(var1, var2, var4, var6, var3);
         this._currentSize += var3;
      } else {
         int var8 = var2;
         var5 = var3;
         if (var7 > 0) {
            System.arraycopy(var1, var2, var4, var6, var7);
            var8 = var2 + var7;
            var5 = var3 - var7;
         }

         do {
            this.expand(var5);
            var2 = Math.min(this._currentSegment.length, var5);
            System.arraycopy(var1, var8, this._currentSegment, 0, var2);
            this._currentSize += var2;
            var8 += var2;
            var2 = var5 - var2;
            var5 = var2;
         } while(var2 > 0);

      }
   }

   public char[] contentsAsArray() {
      char[] var1 = this._resultArray;
      char[] var2 = var1;
      if (var1 == null) {
         var2 = this.resultArray();
         this._resultArray = var2;
      }

      return var2;
   }

   public BigDecimal contentsAsDecimal() throws NumberFormatException {
      char[] var1 = this._resultArray;
      if (var1 != null) {
         return NumberInput.parseBigDecimal(var1);
      } else {
         int var2 = this._inputStart;
         if (var2 >= 0) {
            var1 = this._inputBuffer;
            if (var1 != null) {
               return NumberInput.parseBigDecimal(var1, var2, this._inputLen);
            }
         }

         if (this._segmentSize == 0) {
            var1 = this._currentSegment;
            if (var1 != null) {
               return NumberInput.parseBigDecimal(var1, 0, this._currentSize);
            }
         }

         return NumberInput.parseBigDecimal(this.contentsAsArray());
      }
   }

   public double contentsAsDouble() throws NumberFormatException {
      return NumberInput.parseDouble(this.contentsAsString());
   }

   public int contentsAsInt(boolean var1) {
      int var2 = this._inputStart;
      if (var2 >= 0) {
         char[] var3 = this._inputBuffer;
         if (var3 != null) {
            if (var1) {
               return -NumberInput.parseInt(var3, var2 + 1, this._inputLen - 1);
            }

            return NumberInput.parseInt(var3, var2, this._inputLen);
         }
      }

      return var1 ? -NumberInput.parseInt(this._currentSegment, 1, this._currentSize - 1) : NumberInput.parseInt(this._currentSegment, 0, this._currentSize);
   }

   public long contentsAsLong(boolean var1) {
      int var2 = this._inputStart;
      if (var2 >= 0) {
         char[] var3 = this._inputBuffer;
         if (var3 != null) {
            if (var1) {
               return -NumberInput.parseLong(var3, var2 + 1, this._inputLen - 1);
            }

            return NumberInput.parseLong(var3, var2, this._inputLen);
         }
      }

      return var1 ? -NumberInput.parseLong(this._currentSegment, 1, this._currentSize - 1) : NumberInput.parseLong(this._currentSegment, 0, this._currentSize);
   }

   public String contentsAsString() {
      if (this._resultString == null) {
         if (this._resultArray != null) {
            this._resultString = new String(this._resultArray);
         } else {
            int var1 = this._inputStart;
            String var2 = "";
            if (var1 >= 0) {
               if (this._inputLen < 1) {
                  this._resultString = "";
                  return "";
               }

               this._resultString = new String(this._inputBuffer, this._inputStart, this._inputLen);
            } else {
               int var3 = this._segmentSize;
               var1 = this._currentSize;
               if (var3 == 0) {
                  if (var1 != 0) {
                     var2 = new String(this._currentSegment, 0, var1);
                  }

                  this._resultString = var2;
               } else {
                  StringBuilder var5 = new StringBuilder(var3 + var1);
                  ArrayList var4 = this._segments;
                  if (var4 != null) {
                     var3 = var4.size();

                     for(var1 = 0; var1 < var3; ++var1) {
                        char[] var6 = (char[])this._segments.get(var1);
                        var5.append(var6, 0, var6.length);
                     }
                  }

                  var5.append(this._currentSegment, 0, this._currentSize);
                  this._resultString = var5.toString();
               }
            }
         }
      }

      return this._resultString;
   }

   public int contentsToWriter(Writer var1) throws IOException {
      char[] var2 = this._resultArray;
      if (var2 != null) {
         var1.write(var2);
         return this._resultArray.length;
      } else {
         String var7 = this._resultString;
         if (var7 != null) {
            var1.write(var7);
            return this._resultString.length();
         } else {
            int var3 = this._inputStart;
            int var4;
            if (var3 >= 0) {
               var4 = this._inputLen;
               if (var4 > 0) {
                  var1.write(this._inputBuffer, var3, var4);
               }

               return var4;
            } else {
               ArrayList var8 = this._segments;
               int var6;
               if (var8 != null) {
                  int var5 = var8.size();
                  var6 = 0;
                  var4 = 0;

                  while(true) {
                     var3 = var4;
                     if (var6 >= var5) {
                        break;
                     }

                     var2 = (char[])this._segments.get(var6);
                     var3 = var2.length;
                     var1.write(var2, 0, var3);
                     var4 += var3;
                     ++var6;
                  }
               } else {
                  var3 = 0;
               }

               var6 = this._currentSize;
               var4 = var3;
               if (var6 > 0) {
                  var1.write(this._currentSegment, 0, var6);
                  var4 = var3 + var6;
               }

               return var4;
            }
         }
      }
   }

   public char[] emptyAndGetCurrentSegment() {
      this._inputStart = -1;
      this._currentSize = 0;
      this._inputLen = 0;
      this._inputBuffer = null;
      this._resultString = null;
      this._resultArray = null;
      if (this._hasSegments) {
         this.clearSegments();
      }

      char[] var1 = this._currentSegment;
      char[] var2 = var1;
      if (var1 == null) {
         var2 = this.buf(0);
         this._currentSegment = var2;
      }

      return var2;
   }

   public void ensureNotShared() {
      if (this._inputStart >= 0) {
         this.unshare(16);
      }

   }

   public char[] expandCurrentSegment() {
      char[] var1 = this._currentSegment;
      int var2 = var1.length;
      int var3 = (var2 >> 1) + var2;
      int var4 = var3;
      if (var3 > 65536) {
         var4 = (var2 >> 2) + var2;
      }

      var1 = Arrays.copyOf(var1, var4);
      this._currentSegment = var1;
      return var1;
   }

   public char[] expandCurrentSegment(int var1) {
      char[] var2 = this._currentSegment;
      if (var2.length >= var1) {
         return var2;
      } else {
         var2 = Arrays.copyOf(var2, var1);
         this._currentSegment = var2;
         return var2;
      }
   }

   public char[] finishCurrentSegment() {
      if (this._segments == null) {
         this._segments = new ArrayList();
      }

      this._hasSegments = true;
      this._segments.add(this._currentSegment);
      int var1 = this._currentSegment.length;
      this._segmentSize += var1;
      this._currentSize = 0;
      int var2 = var1 + (var1 >> 1);
      if (var2 < 500) {
         var1 = 500;
      } else {
         var1 = var2;
         if (var2 > 65536) {
            var1 = 65536;
         }
      }

      char[] var3 = this.carr(var1);
      this._currentSegment = var3;
      return var3;
   }

   public char[] getBufferWithoutReset() {
      return this._currentSegment;
   }

   public char[] getCurrentSegment() {
      if (this._inputStart >= 0) {
         this.unshare(1);
      } else {
         char[] var1 = this._currentSegment;
         if (var1 == null) {
            this._currentSegment = this.buf(0);
         } else if (this._currentSize >= var1.length) {
            this.expand(1);
         }
      }

      return this._currentSegment;
   }

   public int getCurrentSegmentSize() {
      return this._currentSize;
   }

   public char[] getTextBuffer() {
      if (this._inputStart >= 0) {
         return this._inputBuffer;
      } else {
         char[] var1 = this._resultArray;
         if (var1 != null) {
            return var1;
         } else {
            String var3 = this._resultString;
            if (var3 != null) {
               var1 = var3.toCharArray();
               this._resultArray = var1;
               return var1;
            } else if (!this._hasSegments) {
               char[] var2 = this._currentSegment;
               var1 = var2;
               if (var2 == null) {
                  var1 = NO_CHARS;
               }

               return var1;
            } else {
               return this.contentsAsArray();
            }
         }
      }
   }

   public int getTextOffset() {
      int var1 = this._inputStart;
      if (var1 < 0) {
         var1 = 0;
      }

      return var1;
   }

   public boolean hasTextAsCharacters() {
      return this._inputStart >= 0 || this._resultArray != null || this._resultString == null;
   }

   public void releaseBuffers() {
      if (this._allocator == null) {
         this.resetWithEmpty();
      } else if (this._currentSegment != null) {
         this.resetWithEmpty();
         char[] var1 = this._currentSegment;
         this._currentSegment = null;
         this._allocator.releaseCharBuffer(2, var1);
      }

   }

   public void resetWith(char var1) {
      this._inputStart = -1;
      this._inputLen = 0;
      this._resultString = null;
      this._resultArray = null;
      if (this._hasSegments) {
         this.clearSegments();
      } else if (this._currentSegment == null) {
         this._currentSegment = this.buf(1);
      }

      this._currentSegment[0] = (char)var1;
      this._segmentSize = 1;
      this._currentSize = 1;
   }

   public void resetWithCopy(String var1, int var2, int var3) {
      this._inputBuffer = null;
      this._inputStart = -1;
      this._inputLen = 0;
      this._resultString = null;
      this._resultArray = null;
      if (this._hasSegments) {
         this.clearSegments();
      } else if (this._currentSegment == null) {
         this._currentSegment = this.buf(var3);
      }

      this._segmentSize = 0;
      this._currentSize = 0;
      this.append(var1, var2, var3);
   }

   public void resetWithCopy(char[] var1, int var2, int var3) {
      this._inputBuffer = null;
      this._inputStart = -1;
      this._inputLen = 0;
      this._resultString = null;
      this._resultArray = null;
      if (this._hasSegments) {
         this.clearSegments();
      } else if (this._currentSegment == null) {
         this._currentSegment = this.buf(var3);
      }

      this._segmentSize = 0;
      this._currentSize = 0;
      this.append(var1, var2, var3);
   }

   public void resetWithEmpty() {
      this._inputStart = -1;
      this._currentSize = 0;
      this._inputLen = 0;
      this._inputBuffer = null;
      this._resultString = null;
      this._resultArray = null;
      if (this._hasSegments) {
         this.clearSegments();
      }

   }

   public void resetWithShared(char[] var1, int var2, int var3) {
      this._resultString = null;
      this._resultArray = null;
      this._inputBuffer = var1;
      this._inputStart = var2;
      this._inputLen = var3;
      if (this._hasSegments) {
         this.clearSegments();
      }

   }

   public void resetWithString(String var1) {
      this._inputBuffer = null;
      this._inputStart = -1;
      this._inputLen = 0;
      this._resultString = var1;
      this._resultArray = null;
      if (this._hasSegments) {
         this.clearSegments();
      }

      this._currentSize = 0;
   }

   public String setCurrentAndReturn(int var1) {
      this._currentSize = var1;
      if (this._segmentSize > 0) {
         return this.contentsAsString();
      } else {
         String var2;
         if (var1 == 0) {
            var2 = "";
         } else {
            var2 = new String(this._currentSegment, 0, var1);
         }

         this._resultString = var2;
         return var2;
      }
   }

   public void setCurrentLength(int var1) {
      this._currentSize = var1;
   }

   public int size() {
      if (this._inputStart >= 0) {
         return this._inputLen;
      } else {
         char[] var1 = this._resultArray;
         if (var1 != null) {
            return var1.length;
         } else {
            String var2 = this._resultString;
            return var2 != null ? var2.length() : this._segmentSize + this._currentSize;
         }
      }
   }

   public String toString() {
      return this.contentsAsString();
   }
}
