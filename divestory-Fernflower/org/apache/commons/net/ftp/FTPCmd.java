package org.apache.commons.net.ftp;

public enum FTPCmd {
   ABOR;

   public static final FTPCmd ABORT;
   public static final FTPCmd ACCOUNT;
   ACCT,
   ALLO;

   public static final FTPCmd ALLOCATE;
   APPE;

   public static final FTPCmd APPEND;
   CDUP;

   public static final FTPCmd CHANGE_TO_PARENT_DIRECTORY;
   public static final FTPCmd CHANGE_WORKING_DIRECTORY;
   CWD;

   public static final FTPCmd DATA_PORT;
   DELE;

   public static final FTPCmd DELETE;
   EPRT,
   EPSV,
   FEAT;

   public static final FTPCmd FEATURES;
   public static final FTPCmd FILE_STRUCTURE;
   public static final FTPCmd GET_MOD_TIME;
   HELP,
   LIST;

   public static final FTPCmd LOGOUT;
   public static final FTPCmd MAKE_DIRECTORY;
   MDTM,
   MFMT,
   MKD,
   MLSD,
   MLST,
   MODE;

   public static final FTPCmd MOD_TIME;
   public static final FTPCmd NAME_LIST;
   NLST,
   NOOP,
   PASS;

   public static final FTPCmd PASSIVE;
   public static final FTPCmd PASSWORD;
   PASV,
   PORT;

   public static final FTPCmd PRINT_WORKING_DIRECTORY;
   PWD,
   QUIT,
   REIN;

   public static final FTPCmd REINITIALIZE;
   public static final FTPCmd REMOVE_DIRECTORY;
   public static final FTPCmd RENAME_FROM;
   public static final FTPCmd RENAME_TO;
   public static final FTPCmd REPRESENTATION_TYPE;
   REST;

   public static final FTPCmd RESTART;
   RETR;

   public static final FTPCmd RETRIEVE;
   RMD,
   RNFR,
   RNTO;

   public static final FTPCmd SET_MOD_TIME;
   SITE;

   public static final FTPCmd SITE_PARAMETERS;
   SMNT,
   STAT;

   public static final FTPCmd STATUS;
   STOR;

   public static final FTPCmd STORE;
   public static final FTPCmd STORE_UNIQUE;
   STOU,
   STRU;

   public static final FTPCmd STRUCTURE_MOUNT;
   SYST;

   public static final FTPCmd SYSTEM;
   public static final FTPCmd TRANSFER_MODE;
   TYPE,
   USER;

   public static final FTPCmd USERNAME;

   static {
      FTPCmd var0 = new FTPCmd("USER", 39);
      USER = var0;
      FTPCmd var1 = ABOR;
      FTPCmd var2 = ACCT;
      FTPCmd var3 = ALLO;
      FTPCmd var4 = APPE;
      FTPCmd var5 = CDUP;
      FTPCmd var6 = CWD;
      FTPCmd var7 = DELE;
      FTPCmd var8 = EPRT;
      FTPCmd var9 = EPSV;
      FTPCmd var10 = FEAT;
      FTPCmd var11 = HELP;
      FTPCmd var12 = LIST;
      FTPCmd var13 = MDTM;
      FTPCmd var14 = MFMT;
      FTPCmd var15 = MKD;
      FTPCmd var16 = MLSD;
      FTPCmd var17 = MLST;
      FTPCmd var18 = MODE;
      FTPCmd var19 = NLST;
      FTPCmd var20 = NOOP;
      FTPCmd var21 = PASS;
      FTPCmd var22 = PASV;
      FTPCmd var23 = PORT;
      FTPCmd var24 = PWD;
      FTPCmd var25 = QUIT;
      FTPCmd var26 = REIN;
      FTPCmd var27 = REST;
      FTPCmd var28 = RETR;
      FTPCmd var29 = RMD;
      FTPCmd var30 = RNFR;
      FTPCmd var31 = RNTO;
      FTPCmd var32 = SITE;
      FTPCmd var33 = SMNT;
      FTPCmd var34 = STAT;
      FTPCmd var35 = STOR;
      FTPCmd var36 = STOU;
      FTPCmd var37 = STRU;
      FTPCmd var38 = SYST;
      FTPCmd var39 = TYPE;
      ABORT = var1;
      ACCOUNT = var2;
      ALLOCATE = var3;
      APPEND = var4;
      CHANGE_TO_PARENT_DIRECTORY = var5;
      CHANGE_WORKING_DIRECTORY = var6;
      DATA_PORT = var23;
      DELETE = var7;
      FEATURES = var10;
      FILE_STRUCTURE = var37;
      GET_MOD_TIME = var13;
      LOGOUT = var25;
      MAKE_DIRECTORY = var15;
      MOD_TIME = var13;
      NAME_LIST = var19;
      PASSIVE = var22;
      PASSWORD = var21;
      PRINT_WORKING_DIRECTORY = var24;
      REINITIALIZE = var26;
      REMOVE_DIRECTORY = var29;
      RENAME_FROM = var30;
      RENAME_TO = var31;
      REPRESENTATION_TYPE = var39;
      RESTART = var27;
      RETRIEVE = var28;
      SET_MOD_TIME = var14;
      SITE_PARAMETERS = var32;
      STATUS = var34;
      STORE = var35;
      STORE_UNIQUE = var36;
      STRUCTURE_MOUNT = var33;
      SYSTEM = var38;
      TRANSFER_MODE = var18;
      USERNAME = var0;
   }

   public final String getCommand() {
      return this.name();
   }
}
