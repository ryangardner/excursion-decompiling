# excursion-decompiling
Efforts related to decompiling excursion stuff

## How to get at the decompiled sources
The easiest way is to download the bytecode viewer: http://www.cuchazinteractive.com/enigma/

Then download the dive story APK: https://apkplz.net/app/com.crest.divestory 

Drag and drop it onto the window. 

The classes that seem most interesting are in the `com.crest.divestory` package (WatchOp, etc)

## Captured HCI packets
are contained in the "hci_logs" directory

## Example parser for the packets
The [DiveByteParser](DiveByteParser) contains "functioning" code taken from the decompiled
android sources.