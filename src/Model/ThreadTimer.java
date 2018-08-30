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
package Model;

import Control.listenerBottoni;
import View.MainFrame;
import static java.lang.System.exit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Filippo
 */
public class ThreadTimer extends Thread {

    public boolean termina=true;
    private final ThreadGiochi tg;
    private final Integer sleep;
    
    public ThreadTimer(ThreadGiochi tg, MainFrame mf) {
        this.tg = tg;
        this.sleep=Integer.parseInt(JOptionPane.showInputDialog(mf, "Tempo tra effetti", "Opzione", JOptionPane.QUESTION_MESSAGE));
    }
    
    @Override
    public void run() {
        super.run();
        while(this.termina){
            try {
                sleep(this.sleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadTimer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if(this.tg.stato>7){
                    this.tg.stato=0;
                }
                else{
                    this.tg.stato++;
                }
            }
        }
    }
    
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
