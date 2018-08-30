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
import java.awt.Dimension;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Filippo Stella
 * @version 1.1
 */
public class Disconnect extends Thread {
    
    private final Connection cn;
    private final MainFrame f;
    private final JPanel pannello;
    private final JProgressBar pBar;
    private final JFrame frame;
    
    public Disconnect(Connection cn,MainFrame f) {
        this.cn = cn;
        this.f = f;
        this.pannello=new JPanel();
        this.pannello.setLayout(new BoxLayout(pannello, BoxLayout.Y_AXIS));
        Box box;
        box=Box.createHorizontalBox();
        box.add(new JLabel("Disconnecting... "));
        box.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.pannello.add(box);
        this.pannello.add(Box.createRigidArea(new Dimension(0,5)));
        this.pBar=new JProgressBar();
        this.pBar.setIndeterminate(true);
        box=Box.createHorizontalBox();
        box.add(this.pBar);
        box.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.pannello.add(box);
        this.frame=new JFrame("Disconnecting");
        this.frame.setResizable(false); 
        this.pannello.setMinimumSize(new Dimension(200, 80));
        this.pannello.setPreferredSize(new Dimension(200, 80));
        this.pannello.setMaximumSize(new Dimension(200, 100));
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.setContentPane(this.pannello);
        this.frame.pack();
        this.frame.setLocationRelativeTo(f);
        this.frame.setVisible(false);
    }

    @Override
    public void run() {
        try {
            this.frame.setVisible(true);
            this.cn.getSemPrinter().acquire();
            this.cn.closeBuffer();
            this.cn.getSocket().close();
            this.cn.setConnection(false);
            this.cn.socketToNull();
            this.cn.getSemPrinter().release();
        } catch (IOException ex) {
            this.frame.setVisible(false);
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this.f,"Impossibile Terminare la connessione con "+this.cn.getScheda().getHostAddress()+" sulla porta "+this.cn.getSocket().getPort()+"\n"+ex.getMessage() ,"Errore" , JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException ex) {
            this.frame.setVisible(false);
            Logger.getLogger(Disconnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if(this.cn.getConnection()==false){
                this.f.getCbi_connect().setSelected(false);
                System.out.println("Socket Closed");
            }
            else{
                this.f.getCbi_connect().setSelected(true);
                
            }
            this.frame.setVisible(false);
            this.frame.dispose();
        }
    }
    
    
    
}
