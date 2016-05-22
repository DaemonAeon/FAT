/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karen16;

import java.math.BigInteger;
import java.nio.ByteBuffer;

/**
 *
 * @author Daemon
 */
public class FAT {

    public FAT() {

    }

    public void createFile(String name, String date, byte contents[], int dir) {

        if (contents.length < 4096) {
            createFileSingleBlock(name, date, contents, dir);
        } else {
            createFileMultiBlock(name, date, contents, dir);
        }

    }

    private void createFileSingleBlock(String name, String date, byte contents[], int dir) {
        byte[] nameBytes = name.getBytes(), dateBytes = date.getBytes();

        byte[] FATData = new byte[32];

        for (int i = 0; i < 32; i++) {
            if (i >= 0 && i <= 10) {
                FATData[i] = nameBytes[i];
            } else {
                FATData[i] = 0;
            }
        }

        int mytemp = 19;

        for (int i = dateBytes.length - 1; i >= 0; --i) {
            FATData[mytemp] = dateBytes[i];
            mytemp--;
        }

        //position...
        FATData[11] = 0x0020;
        int diskpos = new DiskManager().getNextAvailableDiskCluster();
        System.out.println("hi");
        if (diskpos != -1) {
            final BigInteger bi = BigInteger.valueOf(diskpos / 4096);
            System.out.println(diskpos);
            final byte[] bytes = bi.toByteArray();

            mytemp = 21;
            System.out.println("lele " + bytes.length);
            for (int i = bytes.length - 1; i >= 0; i--) {
                FATData[mytemp] = bytes[i];
                mytemp--;
            }

            //size...
            final BigInteger bi2 = BigInteger.valueOf(contents.length);
            final byte[] bytes2 = bi2.toByteArray();
            mytemp = 25;
            for (int i = bytes2.length - 1; i >= 0; i--) {
                FATData[mytemp] = bytes2[i];
                mytemp--;
            }

            int nextAvalFAT = (dir == 0) ? new DiskManager().getNextFATentry(true) : new DiskManager().getNextFATentry(false);

            final BigInteger bi3 = BigInteger.valueOf(65535);
            final byte[] bytes3 = bi3.toByteArray();

            new DiskManager().writeToDisk(nextAvalFAT, 2, bytes3);
            new DiskManager().writeToDisk(diskpos, 32, FATData);
            new DiskManager().writeToDisk(diskpos + 32, contents.length, contents);
        }

    }

    private void createDirectory(String name, String date, byte contents[], int dir) {
        byte[] nameBytes = name.getBytes(), dateBytes = date.getBytes();

        byte[] FATData = new byte[32];

        for (int i = 0; i < 32; i++) {
            if (i >= 0 && i <= 10) {
                FATData[i] = nameBytes[i];
            } else {
                FATData[i] = 0;
            }
        }

        int mytemp = 19;

        for (int i = dateBytes.length - 1; i >= 0; --i) {
            FATData[mytemp] = dateBytes[i];
            mytemp--;
        }

        //position...
        FATData[11] = 0x0010;
        int diskpos = new DiskManager().getNextAvailableDiskCluster();
        System.out.println("hi");
        if (diskpos != -1) {
            final BigInteger bi = BigInteger.valueOf(diskpos / 4096);
            System.out.println(diskpos);
            final byte[] bytes = bi.toByteArray();

            mytemp = 21;
            System.out.println("lele " + bytes.length);
            for (int i = bytes.length - 1; i >= 0; i--) {
                FATData[mytemp] = bytes[i];
                mytemp--;
            }

            //size...
            final BigInteger bi2 = BigInteger.valueOf(contents.length);
            final byte[] bytes2 = bi2.toByteArray();
            mytemp = 25;
            for (int i = bytes2.length - 1; i >= 0; i--) {
                FATData[mytemp] = bytes2[i];
                mytemp--;
            }

            int nextAvalFAT = (dir == 0) ? new DiskManager().getNextFATentry(true) : new DiskManager().getNextFATentry(false);

            final BigInteger bi3 = BigInteger.valueOf(65535);
            final byte[] bytes3 = bi3.toByteArray();

            new DiskManager().writeToDisk(nextAvalFAT, 2, bytes3);
            new DiskManager().writeToDisk(diskpos, 32, FATData);
            new DiskManager().writeToDisk(diskpos + 32, contents.length, contents);
        }

    }

    private void deleteFileSingleBlock(String name) {

    }

    private void createFileMultiBlock(String name, String date, byte contents[], int dir) {

    }

    private int fileExists() {

        return 0;
    }

    public void writy() {
        DiskManager dm = new DiskManager();
        byte b[] = {0x0020, 0x0010};
        dm.writeToDisk(0, 2, b);
    }

    public void ready() {
        DiskManager dm = new DiskManager();
        byte b[] = new byte[4096 - 32];
        dm.readFromDisk(188416 + 32, 4096 - 32, b);
        for (int i = 0; i < b.length; i++) {
            //System.out.println("Napoleon " + b[i]);
        }

    }

}
