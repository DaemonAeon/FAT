/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karen16;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daemon
 */
public class FAT {

    public static final char eof = 0x1;

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

    private void deleteFileSingleBlock(String name, int dir) {

        byte[] nameBytes = name.getBytes();
        int DirEntrySize = 32;
        int DirMaxEntry = 128;
        int FirstByte = 1;
        int NameSize = 10;
        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile("disk.bin", "rws");
            raf.seek(dir);//Directorio donde se encuentra el directorio
            //moverse al seek del archivo
            raf.write(0);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FAT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FAT.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private void deleteFileMultiBlock(String name, int dir) {

        byte[] nameBytes = name.getBytes();
        int DirEntrySize = 32;

    }

    private void createFileMultiBlock(String name, String date, byte contents[], int dir) {

        //partir bloques

        int BlocksNumber = (int) Math.ceil(contents.length / 4096);
        byte[][] BytesInBlocks = SplitInBlocks(contents, 4096);
        for (int k = 0; k < BlocksNumber; k++) {

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
            if (diskpos != -1) {
                final BigInteger bi = BigInteger.valueOf(diskpos / 4096);
                System.out.println(diskpos);
                final byte[] bytes = bi.toByteArray();

                mytemp = 21;
                for (int i = bytes.length - 1; i >= 0; i--) {
                    FATData[mytemp] = bytes[i];
                    mytemp--;
                }

                //size...

                final BigInteger bi2 = BigInteger.valueOf(BytesInBlocks[k].length);
                final byte[] bytes2 = bi2.toByteArray();
                mytemp = 25;
                for (int i = bytes2.length - 1; i >= 0; i--) {
                    FATData[mytemp] = bytes2[i];
                    mytemp--;
                }

                int nextAvalFAT = (dir == 0) ? new DiskManager().getNextFATentry(true) : new DiskManager().getNextFATentry(false);

                final BigInteger bi3 = BigInteger.valueOf(65535);
                final byte[] bytes3 = bi3.toByteArray();


                if ((k + 1) == BlocksNumber) {
                    String temp ;
                                
                }
                byte BytesToWrite[];
                BytesToWrite = BytesInBlocks[k];
                new DiskManager().writeToDisk(nextAvalFAT, 2, bytes3);
                new DiskManager().writeToDisk(diskpos, 32, FATData);
                new DiskManager().writeToDisk(diskpos + 32, BytesInBlocks[k].length, BytesInBlocks[k]);


            }

        }
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

    public static byte[][] SplitInBlocks(byte[] source, int chunksize) {

        byte[][] ret = new byte[(int) Math.ceil(source.length / (double) chunksize)][chunksize];

        int start = 0;

        for (int i = 0; i < ret.length; i++) {
            if (start + chunksize > source.length) {
                System.arraycopy(source, start, ret[i], 0, source.length - start);
            } else {
                System.arraycopy(source, start, ret[i], 0, chunksize);
            }
            start += chunksize;
        }


        return ret;
    }
}
