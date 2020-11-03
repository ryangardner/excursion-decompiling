package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        DataStruct.DiveLog log = new DataStruct.DiveLog("serialnumber", ByteOp.fromHexString(args[0]));
        //DataStruct.DiveLog log2 = new DataStruct.DiveLog("serialnumber", ByteOp.fromHexString(args[1]));

        DataStruct.DiveProfileData data = new DataStruct.DiveProfileData(log, ByteOp.fromHexString(args[1]), "serialNumber");
        System.out.println(log);
        System.out.println(data);
    }
}
