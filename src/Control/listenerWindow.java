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
package Control;

import Model.Connessione.Connection;
import Model.Connessione.ModBusTCPPacket;
import Model.GestioneLuci;
import Model.GiochiPsichedelici;
import Model.SaveManager;
import View.Aggiunta.FrameAggiunta;
import View.Impostazioni.FrameImpostazioni;
import View.MainFrame;
import View.PannelloLuci;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import static java.lang.System.exit;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Filippo Stella
 * @version 1.0
 */
public class listenerWindow implements WindowListener{
    
    private final FrameAggiunta fa;
    private final MainFrame mf;
    private final PannelloLuci pa;
    private final SaveManager sm;
    private final FrameImpostazioni fi;
    private final GestioneLuci gl;
    private final Connection cn;

    /**
    * Constructor for the Listener Window
    * <p>
    * This method is the constructor for the Listener Window
    * 
    * @param fa     The FrameAggiunta this frame is used to add a new light
    * @param mf     The MainFrame the main frame, this is the main interface of this program
    * @param pa     The Connection class is used to manage the TCP connection to a device
    * @param sm     The saveManger class which saver and restore the state of the application
    * @param fi     The FrameImpostazioni is used to set the IP and port of the device to connect to
    * @param gl     The GestioneLuci class which manage the lights
    */
    public listenerWindow(FrameAggiunta fa, MainFrame mf, PannelloLuci pa, SaveManager sm, FrameImpostazioni fi, GestioneLuci gl, Connection cn) {
        this.pa = pa;
        this.mf = mf;
        this.fa = fa;
        this.sm = sm;
        this.fi = fi;
        this.gl = gl;
        this.cn = cn;
        this.fa.listenerWindow(this);
        this.mf.frameListener(this);
    }
    
    @Override
    public void windowOpened(WindowEvent we) {   
        if(we.getSource()==this.mf){
            this.pa.updatePanel();
        }
        else if(we.getSource()==this.fa){
            
        }
        else{
            throw new UnsupportedOperationException("Not supported yet.");
        }
   
    }

    @Override
    public void windowClosing(WindowEvent we) {
        if(JOptionPane.showConfirmDialog(mf, "Sicuro di voler uscire?", "Richiesta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){    
            try {
                this.mf.setVisible(false);
                this.sm.fSave(this.fi.getIP(), this.fi.getPORT().getText(), this.gl.getArray(), this.fi);
            } catch (Exception ex) {
                Logger.getLogger(listenerWindow.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                exit(3);
            } finally {
                if(cn.getConnection()){
                    byte ris[];
                    byte send1[]=new byte[200];
                    byte send2[]=new byte[200];
                    byte send3[]=new byte[112];
                    ModBusTCPPacket mbp;
                    ris=GiochiPsichedelici.Set_every_led_at_Color(gl.getArray(), new byte[512], 0, 0, 0);
                    System.arraycopy(ris, 0, send1, 0, 200);
                    System.arraycopy(ris, 200, send2, 0, 200);
                    System.arraycopy(ris, 400, send2, 0, 112);
                    mbp=new ModBusTCPPacket();
                    mbp.initialization(1, (byte)1, (byte)16, (short)0, (short)200, send1);
                    cn.sendMessage(mbp.getPacket(), 0);
                    if(fi.getDELAY().isSelected()){
                        try {
                            sleep(1);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    mbp=new ModBusTCPPacket();
                    mbp.initialization(2, (byte)1, (byte)16, (short)200, (short)200, send2);
                    cn.sendMessage(mbp.getPacket(), 0);
                    if(fi.getDELAY().isSelected()){
                        try {
                            sleep(1);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    mbp=new ModBusTCPPacket();
                    mbp.initialization(3, (byte)1, (byte)16, (short)400, (short)112, send3);
                    cn.sendMessage(mbp.getPacket(), 0);
                    this.cn.closeConnection();
                    try {
                        this.cn.closeBuffer();
                    } catch (IOException ex) {
                        Logger.getLogger(listenerWindow.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        exit(0);
                    }
                }
                else{
                    exit(0);
                }
            }
        }
    }

    @Override
    public void windowClosed(WindowEvent we) {
       
    }

    @Override
    public void windowIconified(WindowEvent we) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    
    }

    @Override
    public void windowActivated(WindowEvent we) {
        if(we.getSource()==this.mf){
            this.pa.updatePanel();
        }
        else if(we.getSource()==this.fa){
            
        }
        else{
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        
    }
    
}
