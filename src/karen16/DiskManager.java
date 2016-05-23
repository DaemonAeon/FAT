/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karen16;

import java.io.File;
import java.io.RandomAccessFile;

/**
 *
 * @author Daemon
 */
public class DiskManager {

    public DiskManager() {
    }

    public int getNextFATentry(boolean root) {
        int retVal = 0;
        int i = 0;
        byte b[] = new byte[2];

        for (int h = 0; h < 2; h++) {
            b[0] = 0;
        }

        //validaciones de root, etc en FAT no aquí
        try {

            RandomAccessFile raf = new RandomAccessFile("disk.bin", "rws");

            while (true) {
                raf.seek(i * 2);
                raf.readFully(b, 0, 2);

                if (b[0] == 0) {
                    retVal = i * 2;
                    raf.close();
                    break;
                } else {
                    i++;
                    if (root) {
                        if (i * 2 >= 1024) {
                            retVal = -1;
                            raf.close();
                            break;
                        }
                    } else {
                        if (i * 2 >= 131072) {
                            retVal = -1;
                            raf.close();
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
        }

        return retVal;
    }

    public int getNextAvailableDiskCluster() {
        int retVal = 0;

        int i = 64;

        byte b[] = new byte[4096];

        for (int h = 0; h < 4096; h++) {
            b[0] = 0;
        }

        try {

            RandomAccessFile raf = new RandomAccessFile("disk.bin", "rws");

            while (true) {
                raf.seek(i * 4096);
                raf.readFully(b, 0, 4096);

                if (b[0] == 0) {
                    retVal = i * 4096;
                    raf.close();
                    break;
                } else {
                    i++;
                    if (i * 4096 >= 262144000) {
                        retVal = -1;
                        raf.close();
                        break;
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return retVal;
    }

    public int getNextDirEntry(int dir) {

        int retVal =  retVal = -1;//no hay mas entraadas
        int FATEntrySize = 2;
        int DirEntrySize = 32;
        int DirMaxEntry = 128;
        int DirEntryInit = dir * DirEntrySize;
        try {
            byte b[] = new byte[DirEntrySize];

            for (int h = 0; h < DirEntrySize; h++) {
                b[0] = 0;
            }

            RandomAccessFile raf = new RandomAccessFile("disk.bin", "rws");
            raf.seek(DirEntryInit);
            for (int i = 0; i < DirMaxEntry; i++) {

                raf.seek(i * DirEntrySize);
                raf.readFully(b, 0, DirEntrySize);

                if (b[0] == 0) {
                    retVal = i * DirEntrySize;
                    raf.close();
                    return retVal;
                }

            }

        } catch (Exception e) {
        }
       

        return retVal;
    }

    public void writeToDisk(int pos, int len, byte[] contents) {
        try {

            RandomAccessFile raf = new RandomAccessFile("disk.bin", "rws");
            raf.seek(pos);
            raf.write(contents, 0, len);

            raf.close();
        } catch (Exception e) {
        }
    }

    public byte[] readFromDisk(int pos, int len, byte[] contents) {
        try {

            RandomAccessFile raf = new RandomAccessFile("disk.bin", "rws");
            raf.seek(pos);
            raf.readFully(contents, 0, len);
            raf.close();

        } catch (Exception e) {
        }

        return contents;
    }
}
