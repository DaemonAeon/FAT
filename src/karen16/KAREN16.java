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
public class KAREN16 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FAT fat = new FAT();
        byte[] contents = {0x20, 0x13, 0x49};
        //fat.createFile("abcdefg.exe", "25051995", contents);
        fat.ready();
        
        try{
            
            File f = new File("disk.bin");
            if(f.exists() && !f.isDirectory()) {
                
            }else{
                RandomAccessFile raf = new RandomAccessFile("disk.bin", "rws");
            
                raf.setLength(262144000);
                
                byte b[] = new byte[8192000];
                
                for(int i = 0; i < 8192000; i++){
                    b[i] = 0;
                }
                
                for(int i = 0; i < 32; i++){
                    raf.write(b);
                }
                
                raf.close();
            }
            
            
        }catch(Exception e){
        
        }
        
    }
    
}
