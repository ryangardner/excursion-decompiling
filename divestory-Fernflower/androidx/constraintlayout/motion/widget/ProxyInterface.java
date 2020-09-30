package androidx.constraintlayout.motion.widget;

interface ProxyInterface {
   int designAccess(int var1, String var2, Object var3, float[] var4, int var5, float[] var6, int var7);

   float getKeyFramePosition(Object var1, int var2, float var3, float var4);

   Object getKeyframeAtLocation(Object var1, float var2, float var3);

   Boolean getPositionKeyframe(Object var1, Object var2, float var3, float var4, String[] var5, float[] var6);

   long getTransitionTimeMs();

   void setAttributes(int var1, String var2, Object var3, Object var4);

   void setKeyFrame(Object var1, int var2, String var3, Object var4);

   boolean setKeyFramePosition(Object var1, int var2, int var3, float var4, float var5);

   void setToolPosition(float var1);
}
