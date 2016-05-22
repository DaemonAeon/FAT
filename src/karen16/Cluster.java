/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karen16;

/**
 *
 * @author Daemon
 */
public class Cluster {//los Cluster son de 4 KB i.e. 4096 bytes
    private byte[] bytes;
    
    public Cluster(byte[] bytes){
        this.bytes = bytes;
    }
    
    public Cluster(){
        bytes = new byte[4096];
        for(byte b:bytes){
            b = 0;
        }
    }
}
