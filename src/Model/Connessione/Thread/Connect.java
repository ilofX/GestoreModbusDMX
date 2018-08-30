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
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
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
public class Connect extends Thread{
    
    private final Connection cn;
    private final byte ip[];
    private final int port;
    private final MainFrame f;
    private InetAddress ia;
    private final JPanel pannello;
    private final JProgressBar pBar;
    private final JFrame frame;

    public Connect(Connection cn,byte Ip1,byte Ip2,byte Ip3,byte Ip4,int port,MainFrame f) {
        this.cn = cn;
        this.ip = new byte[4];
        this.ip[0] = Ip1;
        this.ip[1] = Ip2;
        this.ip[2] = Ip3;
        this.ip[3] = Ip4;
        this.port = port;
        this.f = f;
        this.pannello=new JPanel();
        this.pannello.setLayout(new BoxLayout(pannello, BoxLayout.Y_AXIS));
        Box box;
        box=Box.createHorizontalBox();
        box.add(new JLabel("Connecting... "));
        box.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.pannello.add(box);
        this.pannello.add(Box.createRigidArea(new Dimension(0,5)));
        this.pBar=new JProgressBar();
        this.pBar.setIndeterminate(true);
        box=Box.createHorizontalBox();
        box.add(this.pBar);
        box.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.pannello.add(box);
        this.frame=new JFrame("Connecting");
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
    
    public Connect(Connection cn,InetAddress ia,int port,MainFrame f) {
        this.cn = cn;
        this.ip = new byte[4];
        this.ia = ia;
        this.port = port;
        this.f = f;
        this.pannello=null;
        this.pBar=new JProgressBar();
        this.frame=null;
    }

    @Override
    public void run() {
        try {
            if(this.frame!=null){
                this.frame.setVisible(true);
            }
            if(ia==null){
                this.ia=InetAddress.getByAddress(this.ip);
            }
            this.cn.setScheda(this.ia);
            this.cn.getSocket().connect(new InetSocketAddress(this.cn.getScheda(), this.port));
            this.cn.getSocket().setKeepAlive(true);
            this.cn.OpenChannels();
            this.cn.setConnection(true);
        } catch (UnknownHostException ex) {
            this.frame.setVisible(false);
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this.f, "Impossibile Connettersi all'indirizzo "+this.cn.getScheda().getHostAddress()+" sulla porta "+this.port+"\n"+ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            this.frame.setVisible(false);
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this.f, "Impossibile Connettersi all'indirizzo "+this.cn.getScheda().getHostAddress()+" sulla porta "+this.port+"\n"+ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        } finally{
            if(this.cn.getConnection()){
                this.f.getCbi_connect().setSelected(true);
                this.cn.getSemPrinter().release();
                System.out.println("Connected to "+this.cn.getScheda().getHostAddress()+" on port "+this.port);
            }
            else{
                this.f.getCbi_connect().setSelected(false);
            }
            if(this.frame!=null){
                this.frame.setVisible(false);
                this.frame.dispose();
            }
        }
    }
   
}
