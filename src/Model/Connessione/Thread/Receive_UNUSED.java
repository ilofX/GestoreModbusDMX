/*
 * Copyright 2017 Filippo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package Model.Connessione.Thread;

import Model.Connessione.Connection;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Filippo Stella
 * @version 1.0
 */
public class Receive_UNUSED extends Thread {

    private String ris;
    private final Semaphore semReceive;
    private final Connection cn;
    private final BufferedReader read;
    
    public Receive_UNUSED(Semaphore semReceive, Connection cn, BufferedReader read) {
        this.ris="";
        this.semReceive = semReceive;
        this.cn = cn;
        this.read = read;
    }
    
    @Override
    public void run() {
        try {
            while(true){
                if(this.semReceive.tryAcquire()){
                    this.semReceive.acquire();
                    break;
                }
            }
            while(this.read.ready()){ 
                System.out.println(this.read.readLine());
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Receive_UNUSED.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            this.semReceive.release();
        }
        
        
        
    }
     
}
