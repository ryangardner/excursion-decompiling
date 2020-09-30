package org.apache.harmony.awt.datatransfer;

import java.awt.Point;
import java.awt.dnd.DragSourceContext;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;

public class DragSourceEventProxy implements Runnable {
   public static final int DRAG_ACTION_CHANGED = 3;
   public static final int DRAG_DROP_END = 6;
   public static final int DRAG_ENTER = 1;
   public static final int DRAG_EXIT = 5;
   public static final int DRAG_MOUSE_MOVED = 4;
   public static final int DRAG_OVER = 2;
   private final DragSourceContext context;
   private final int modifiers;
   private final boolean success;
   private final int targetActions;
   private final int type;
   private final int userAction;
   private final int x;
   private final int y;

   public DragSourceEventProxy(DragSourceContext var1, int var2, int var3, int var4, Point var5, int var6) {
      this.context = var1;
      this.type = var2;
      this.userAction = var3;
      this.targetActions = var4;
      this.x = var5.x;
      this.y = var5.y;
      this.modifiers = var6;
      this.success = false;
   }

   public DragSourceEventProxy(DragSourceContext var1, int var2, int var3, boolean var4, Point var5, int var6) {
      this.context = var1;
      this.type = var2;
      this.userAction = var3;
      this.targetActions = var3;
      this.x = var5.x;
      this.y = var5.y;
      this.modifiers = var6;
      this.success = var4;
   }

   private DragSourceDragEvent newDragSourceDragEvent() {
      return new DragSourceDragEvent(this.context, this.userAction, this.targetActions, this.modifiers, this.x, this.y);
   }

   public void run() {
      switch(this.type) {
      case 1:
         this.context.dragEnter(this.newDragSourceDragEvent());
         break;
      case 2:
         this.context.dragOver(this.newDragSourceDragEvent());
         break;
      case 3:
         this.context.dropActionChanged(this.newDragSourceDragEvent());
         break;
      case 4:
         this.context.dragMouseMoved(this.newDragSourceDragEvent());
         break;
      case 5:
         this.context.dragExit(new DragSourceEvent(this.context, this.x, this.y));
         break;
      case 6:
         this.context.dragExit(new DragSourceDropEvent(this.context, this.userAction, this.success, this.x, this.y));
      }

   }
}
