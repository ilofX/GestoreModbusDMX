/*
 * Copyright 2017 Francesco Soppera.
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
package View.Impostazioni;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Francesco Soppera
 * @version 1.0
 */

public class FrameImpostazioni extends JFrame{
    
    private final JPanel pannello_impostazioni= new JPanel() ;
    private JTextField IP1,IP2,IP3,IP4;
    private JTextField PORT;
    private JLabel adress,port;
    private JButton b_ok,b_annulla;
    private JCheckBox DELAY,SORT;
    
    public FrameImpostazioni() throws ParseException{
        this.setLocation(300,400);
        this.setTitle("Impostazioni");
        this.setIconImage(new ImageIcon(this.getClass().getResource("/Icon/Stage.png")).getImage());
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.FrameBuilding();
        this.pack();
        this.setVisible(false);
        
    }    

    public JTextField getIP1() {
        return this.IP1;
    }
    public JTextField getIP2() {
        return this.IP2;
    }
    public JTextField getIP3() {
        return this.IP3;
    }
    public JTextField getIP4() {
        return this.IP4;
    }
    public JTextField getPORT() {
        return this.PORT;
    }
    public JButton getB_ok() {
        return this.b_ok;
    }
    public JButton getB_annulla() {
        return this.b_annulla;
    }
    public JCheckBox getDELAY() {
        return this.DELAY;
    }
    public JCheckBox getSORT() {
        return this.SORT;
    }
    
    public void FrameBuilding(){

        this.IP1= new JTextField();
        this.IP2= new JTextField();
        this.IP3= new JTextField();
        this.IP4= new JTextField();
        
        this.adress= new JLabel("INDIRIZZO IP");
        this.port= new JLabel("PORTA");
        
        this.PORT = new JTextField();

        this.b_ok = new JButton("Conferma");
        this.b_annulla = new JButton("Annulla");

        this.IP1.setBounds(10,40,50,20);
        this.IP2.setBounds(70,40,50,20);
        this.IP3.setBounds(130,40,50,20);
        this.IP4.setBounds(190,40,50,20);
        this.PORT.setBounds(10,110,50,20);
        this.adress.setBounds(10,10,100,10);
        this.port.setBounds(10,60,50,50);
        
        this.b_ok.setBounds(10, 210, 90, 20);
        this.b_annulla.setBounds(150, 210, 90, 20);
        
        
        this.IP1.setDocument(new MaxCharactersTextField(3));
        this.IP2.setDocument(new MaxCharactersTextField(3));
        this.IP3.setDocument(new MaxCharactersTextField(3));
        this.IP4.setDocument(new MaxCharactersTextField(3));
        this.PORT.setDocument(new MaxCharactersTextField(5));
        
        this.pannello_impostazioni.add(this.IP1);
        this.pannello_impostazioni.add(this.IP2);
        this.pannello_impostazioni.add(this.IP3);
        this.pannello_impostazioni.add(this.IP4);
        this.pannello_impostazioni.add(this.PORT);
        this.pannello_impostazioni.add(this.adress);
        this.pannello_impostazioni.add(this.port);
        this.pannello_impostazioni.add(this.b_ok);
        this.pannello_impostazioni.add(this.b_annulla);
        
        this.DELAY=new JCheckBox("Delay between packets (1ms)");
        this.DELAY.setBounds(10, 150, 200, 20);
        this.pannello_impostazioni.add(this.DELAY);

        this.SORT=new JCheckBox("Auto Sort lights by start channel");
        this.SORT.setBounds(10, 180, 200, 20);
        this.pannello_impostazioni.add(this.SORT);
        
        this.pannello_impostazioni.setLayout(null);
        this.pannello_impostazioni.setPreferredSize(new Dimension(260, 240));
        
        

        this.add(this.pannello_impostazioni);
    }

    public void setIP(String IP){
        if(IP.length()>3){
            String ip[]=new String[4];
            ip=IP.split("/");
            this.IP1.setText(ip[0]);
            this.IP2.setText(ip[1]);
            this.IP3.setText(ip[2]);
            this.IP4.setText(ip[3]);
        }
    }
    
    public String getIP(){
        String ris="";
        ris+=((String)this.IP1.getText())+"/";
        ris+=((String)this.IP2.getText())+"/";
        ris+=((String)this.IP3.getText())+"/";
        ris+=((String)this.IP4.getText());
        return ris;
    }
    
    public void listenerBottoni(ActionListener al){
        this.b_ok.addActionListener(al);
        this.b_annulla.addActionListener(al);
    }
    
}
