/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package karen16;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author David Discua
 */
public class ShellTest {

    static ArrayList<Filetest> Tester = new ArrayList();
    static String root = "./";
    static String dirActual = "./";

    public static void main(String[] args) {
        FAT fat = new FAT();
        byte[] contents = {0x20, 0x13, 0x49};
        //fat.createFile("abcdefg.exe", "25051995", contents);


        fat.ready();
        DiskManager disk_manager = new DiskManager();
        try {

            File f = new File("disk.bin");

            if (!(f.exists() && !f.isDirectory())) {

                RandomAccessFile raf = new RandomAccessFile(f, "rws");

                raf.setLength(262144000);

                byte b[] = new byte[8192000];

                for (int i = 0; i < 8192000; i++) {
                    b[i] = 0;
                }

                for (int i = 0; i < 32; i++) {
                    raf.write(b);
                }

                raf.close();
            }

            InputStreamReader inReader = new InputStreamReader(System.in);
            BufferedReader bReader = new BufferedReader(inReader);

            System.out.print("ohsi!> ");
            String cmd = bReader.readLine();       // Read from standard input
            String[] arg = cmd.split(" ");
            //Nombre,Ocupado,Fecha,Padre,Tipo,Content
            while (!arg[0].equals("exit")) {
                //validando que no sea un solo comando
                if (arg.length < 2) {
                    System.err.println("Muy pocos argumentos, intente de nuevo");
                    System.out.print("ohsi!> ");
                    cmd = bReader.readLine();
                    arg = cmd.split(" ");
                } else {

                    if (arg[0].equalsIgnoreCase("cat")) {
                        if (arg[1].equals(">")) {
                            //Nombre,Ocupado,Fecha,Padre,Tipo,Content
                            //redirect to file or something like that
                            //escribir a archivo
                            String file_name = arg[2];
                            //System.out.println("File name: "+ file_name);
                            Date date = new Date();
                            String creation_date = date.toString();
                            //System.out.println("Fecha de creacion: " + creation_date);
                            System.out.println("Escriba el contenido del archivo");
                            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                            String content = br.readLine();
                            Filetest F = new Filetest(file_name, "1", creation_date, dirActual, "Archivo", content);
                            byte[] cont = content.getBytes();
                            //System.out.println("bytes:\n"+ cont.toString());                               
                            Tester.add(F);
                            // fat.createFile(file_name, creation_date, cont, disk_manager.getNextFATentry(true));

                        } else {

                            //leer y mostrar el archivo que se manda de parametro        
                            String Argumento = new String(arg[1]);                            
                            catNormalTester(Argumento);

                        }
                    } else if (arg[0].equalsIgnoreCase("ls")) {
                        if (arg[1].equals("-l")) {
                            //listar todo
                            
                            lsTester(dirActual, 1);
                        } else {
                            //listar solo directorio actual                               
                            byte[] file_bytes = disk_manager.readFromDisk(0, 32, contents);
                            for (int i = 0; i < file_bytes.length; i++) {
                                //   System.out.println(file_bytes[i]);
                            }
                            lsTester(dirActual, 0);
                        }
                    } else if (arg[0].equalsIgnoreCase("mkdir")) {

                        System.out.println("Crear directorio");
                        Date date = new Date();
                        String creation_date = date.toString();
                        String file_name = arg[1];
                        Filetest F = new Filetest(file_name, "1", creation_date, dirActual, "Directorio", arg[1]);
                        Tester.add(F);

                    } else if (arg[0].equalsIgnoreCase("rmdir")) {
                        System.out.println("Eliminar directorio");
                        rmdirTester(dirActual, arg[1]);

                    } else if (arg[0].equalsIgnoreCase("rm")) {
                        System.out.println("Eliminar archivo");
                        rmTester(dirActual, arg[1]);
                    } else if (arg[0].equalsIgnoreCase("cd")) {
                        if (arg[1].equalsIgnoreCase("..")) {
                            cdBack();
                        } else {
                            cdTester(dirActual, arg[1]);
                        }

                    } else {
                        System.out.println("Comando no encontrado");
                    }


                }

                System.out.print("ohsi!> ");
                cmd = bReader.readLine();
                arg = cmd.split(" ");
            }


            inReader.close();
            bReader.close();

        } catch (Exception e) {
        }

    }//fin main

    private static void catNormalTester(String Nombre) {


        for (int i = 0; i < Tester.size(); i++) {

            if (Tester.get(i).getNombre().equals(Nombre)) {
                if (Tester.get(i).getTipo().equalsIgnoreCase("Directorio")) {
                    System.out.println(Nombre + "No es un archivo");
                    break;
                } else {
                    System.out.println(Tester.get(i).Content);
                    break;
                }
            }
        }

    }

    private static void mkdirTester() {
        

    }

    private static void rmdirTester(String dir, String nombre) {


        for (int i = 0; i < Tester.size(); i++) {

            if (Tester.get(i).getPadre().equals(dir)) {

                if (Tester.get(i).getNombre().equals(nombre)) {
                    if (Tester.get(i).getTipo().equalsIgnoreCase("Directorio")) {
                        boolean vacio = true;
                        for (int j = 0; j < Tester.size(); j++) {

                            if (Tester.get(j).getPadre().equals(nombre)) {
                                vacio = false;
                                break;
                            }
                        }

                        if (vacio) {
                            Tester.remove(i);
                        } else {
                            System.out.println("El Directorio no está vacío");
                            break;
                        }
                    }
                }

            }

        }
    }

    private static void rmTester(String dir,String nombre) {
        
        boolean veredicto =true;
        for (int i = 0; i <Tester.size(); i++) {
                       
          
            if(Tester.get(i).getNombre().equalsIgnoreCase(nombre) && Tester.get(i).getPadre().equalsIgnoreCase(dir)){
                 veredicto = false;
                 Tester.remove(i);
            }
            
        }
        
        if(veredicto){
            System.out.println("No se encontró el archivo");
        }
    }

    private static void cdTester(String actual, String nombre) {

        boolean veredicto = false;

        for (int i = 0; i < Tester.size(); i++) {

            if (Tester.get(i).getPadre().equalsIgnoreCase(actual)) {


                if (Tester.get(i).getNombre().equals(nombre)) {
                    veredicto = true;

                    if (Tester.get(i).getTipo().equalsIgnoreCase("Archivo")) {
                        System.out.println(nombre + " no es un directorio");
                    } else {
                        dirActual = nombre;
                    }


                }



            }

        }
        if (!veredicto) {
            System.out.println("No se encontró el directorio");
        }

    }

    private static void cdBack() {


        for (int i = 0; i < Tester.size(); i++) {

            if (Tester.get(i).getNombre().equalsIgnoreCase(dirActual)) {

                dirActual = Tester.get(i).getPadre();
                break;
            }

        }

    }

    private static void lsTester(String dir, int type) {

        int justOne = 0;
        for (int i = 0; i < Tester.size(); i++) {

            if (type == 0) {
                if (Tester.get(i).getPadre().equals(dir)) {
                    System.out.println(Tester.get(i).getNombre());

                }

            } else if (type == 1) {

                justOne++;
                if (justOne == 1) {
                    System.out.printf("%-30.30s %-30.30s %-30.30s %-30.30s%n", "NombreArchivo", "TipoDeArchivo", "FechaCreación", "Tamaño");
                }

                if (Tester.get(i).getPadre().equals(dir)) {
                    int Tamaño = 0;
                    
                    for (int j = 0; j < Tester.get(i).Content.getBytes().length; j++) {
                        Tamaño++;
                    }
                    
                    System.out.println(System.out.printf("%-30.30s %-30.30s %-30.30s %-30.30s%n", Tester.get(i).getNombre(), Tester.get(i).getTipo(),Tester.get(i).getFecha(),Integer.toString(Tamaño)));

                }

            }

        }


    }
}
