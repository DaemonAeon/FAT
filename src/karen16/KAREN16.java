/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karen16;

import java.io.File;
import java.io.*;
import java.util.Date;

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
        DiskManager disk_manager = new DiskManager();
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
            
            
            InputStreamReader inReader = new InputStreamReader(System.in);
            BufferedReader bReader = new BufferedReader(inReader);

            System.out.print("ohsi!> ");
            String cmd = bReader.readLine();       // Read from standard input
            String[] arg = cmd.split(" ");
                
            while (!arg[0].equals("exit"))
            {
                //validando que no sea un solo comando
                if (arg.length < 2) {
                    System.err.println("Muy pocos argumentos, intente de nuevo");
                    System.out.print("ohsi!> ");
                    cmd = bReader.readLine(); 
                    arg = cmd.split(" ");
                }else{
                    switch (arg[0])
                    {
                        case "cat":
                        {
                            if (arg[1].equals(">")) {
                                //redirect to file or something like that
                                //escribir a archivo
                                String file_name = arg[2];
                                System.out.println("File name: "+ file_name);
                                Date date = new Date();
                                String creation_date = date.toString();
                                System.out.println("Fecha de creacion: " + creation_date);
                                System.out.println("Escriba el contenido del archivo");
                                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                String content = br.readLine();
                                byte[] cont = content.getBytes();
                                System.out.println("bytes:\n"+ cont.toString());
                                
                                fat.createFile(file_name,creation_date, cont, disk_manager.getNextFATentry(true));
                                
                                
                            }else{
                                System.out.println("cat normal");
                                //leer y mostrar el archivo que se manda de parametro 
                                
                            }
                        }break;
                        case "ls":
                        {
                            if (arg[1].equals("-l")) {
                                //listar todo
                                System.out.println("Lista too' con too' y la fecha, tipo, tamaño");
                            }else{
                                //listar solo directorio actual
                                System.out.println("Señar directorio actual");
                                byte[] file_bytes = disk_manager.readFromDisk(0, 32, contents);
                                for (int i = 0; i < file_bytes.length; i++) {
                                    System.out.println(file_bytes[i]);
                                }
                                
                            }
                        }break;
                        case "mkdir":
                        {
                            System.out.println("Crear directorio");
                        }break;
                        case "rmdir":
                        {
                            System.out.println("Eliminar directorio");
                            
                        }break;
                        case "rm":
                        {
                            System.out.println("Eliminar archivo");
                        }break;
                        case "cd":
                        {
                            System.out.println("Cambiar directorio");
                        }break;
                        default:
                        {
                            System.out.println("Kezezo?");
                        } break;
                        
                    }
                    System.out.print("ohsi!> ");
                    cmd = bReader.readLine(); 
                    arg = cmd.split(" ");
                }
            }
            
            inReader.close();
            bReader.close();
            
        }catch(Exception e){
        
        }
        
    }
    
}
