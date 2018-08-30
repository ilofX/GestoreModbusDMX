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
package Model;

import Control.listenerBottoni;
import static java.lang.System.exit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Filippo
 * @version 1.0
 */
public class ThreadGiochi extends Thread{
    
    public boolean termina=true;
    public Integer stato;

    public Integer getStato() {
        return stato;
    }

    public void setStato(Integer stato) {
        this.stato = stato;
    }
    
    
    
    /**
    * This method is used to terminate an active ThreadGiochi
    * <p>
    * This method sets termina to false and allow the thread to
    * exit from the active loop, allowing him to terminate
    * 
    */ 
    public void fine(){
        try {
            this.termina=false;
            this.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            exit(4);
        }
    }
    
}
