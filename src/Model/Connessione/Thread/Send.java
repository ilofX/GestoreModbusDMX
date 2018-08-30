/*
 * Copyright 2017 Filippo Stella.
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
import View.MainFrame;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Filippo Stella
 * @version 1.2
 */
public class Send extends Thread {
    
    private final Semaphore semPrinter,semReader,semSend;
    private final Connection cn;
    private final byte[] message;
    private final MainFrame f;
    private final int time;

    public Send(Connection cn, byte[] message, MainFrame f,Semaphore sem,Semaphore ser,int time) {
        this.cn = cn;
        this.message = message;
        this.f = f;
        this.semPrinter = sem;
        this.semReader = ser;
        this.time = time;
        this.semSend=new Semaphore(1);
    }

    @Override
    public void run() {
        try {
            this.semSend.acquire();
                this.cn.getPrinter().flush();
                if(cn.getConnection()){
                    this.cn.getPrinter().write(this.message);
                }
                this.sleep(1,5);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Errore durante l'invio della stringa MODBus al dispositivo", "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally{
            if(this.time>0){
                try {
                    this.semSend.release();
                    this.sleep(this.time);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } 
    }
    
}
