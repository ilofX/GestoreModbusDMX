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
import Model.Luce;
import Model.ThreadGiochi;
import Model.ThreadTimer;
import View.Aggiunta.FrameAggiunta;
import View.Aggiunta.PannelloAggiunta;
import View.Impostazioni.FrameImpostazioni;
import View.MainFrame;
import View.MainPannello;
import View.Modifica.FrameModifica;
import View.Modifica.PannelloModifica;
import View.PannelloLuci;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author Filippo Stella
 * @version 1.0
 */
public class listenerBottoni implements ActionListener{

    private final Connection cn;
    private final PannelloAggiunta pa;
    private final PannelloLuci pl;
    private final GestioneLuci gl;
    private final FrameAggiunta fa;
    private final PannelloModifica pm;
    private final FrameModifica fm;
    private final MainPannello mp;
    private final FrameImpostazioni fi;
    private final MainFrame mf;
    private ThreadGiochi t;
    private ThreadTimer tt;
    private Timer time;

    public listenerBottoni(MainFrame mf, FrameAggiunta fa, FrameModifica fm, FrameImpostazioni fi, PannelloAggiunta pa, PannelloModifica pm, PannelloLuci pl, MainPannello mp, GestioneLuci gl, Connection cn) {
        this.fm = fm;
        this.fa = fa;
        this.gl = gl;
        this.pa = pa;
        this.pm = pm;
        this.mp = mp;
        this.cn = cn;
        this.fi = fi;
        this.pl = pl;
        this.mf = mf;
        this.mp.listenerBottoni(this);
        this.pm.listenerBottoni(this);
        this.pa.listenerBottoni(this);
        this.fi.listenerBottoni(this);
        this.pl.listenerAggiungi(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==this.pa.getB_annulla()){
            this.fa.dispose();
        }
        else if(ae.getSource()==this.pa.getB_ok()){
            this.gl.addLuce(new Luce(((Integer)this.pa.getS_start().getValue()), ((Integer)this.pa.getS_canali().getValue()), ((String)this.pa.getCb_tipo().getSelectedItem()), ((Integer)this.pa.getS_canaleR().getValue()), ((Integer)this.pa.getS_canaleG().getValue()), ((Integer)this.pa.getS_canaleB().getValue())));
            this.fa.dispose();
            System.out.println("New Light Added");
        }
        else if(ae.getSource()==this.pm.getB_annulla()){
            this.fm.dispose();
        }
        else if(ae.getSource()==this.pm.getB_elimina()){
            this.gl.removeLuce(this.pm.getI());
            this.fm.dispose();
            System.out.println("Light deleted");
        }
        else if(ae.getSource()==this.pm.getB_modifica()){
            this.gl.editLuce(this.pm.getI(), ((String)this.pm.getCb_tipo().getSelectedItem()), ((Integer)this.pm.getS_start().getValue()), ((Integer)this.pm.getS_canali().getValue()), ((Integer)this.pm.getS_canaleR().getValue()), ((Integer)this.pm.getS_canaleG().getValue()), ((Integer)this.pm.getS_canaleB().getValue()));
            this.fm.dispose();
        }
        else if(ae.getSource()==this.fi.getB_ok()){
            this.fi.setVisible(false);
        }
        else if(ae.getSource()==this.fi.getB_annulla()){
            this.fi.setVisible(false);
        }
        else if(ae.getSource()==this.pl.getB_aggiungi()){
            this.pa.init();
            this.fa.setLocationRelativeTo(this.mf);
            this.fa.setVisible(true);
        }
        else if(ae.getSource()==this.mp.getB_applica()){
            if(this.cn.getConnection()){
                if(this.mp.getR_solid().isSelected()){
                    if(this.t!=null){
                        this.t.fine();
                    }
                    
                    byte ris[];
                    byte send1[]=new byte[200];
                    byte send2[]=new byte[200];
                    byte send3[]=new byte[112];
                    ModBusTCPPacket mbp;
                    ris=GiochiPsichedelici.Set_every_led_at_Color(gl.getArray(), new byte[512], mp.getSl_r().getValue(), mp.getSl_g().getValue(), mp.getSl_b().getValue());
                    System.arraycopy(ris, 0, send1, 0, 200);
                    System.arraycopy(ris, 200, send2, 0, 200);
                    System.arraycopy(ris, 400, send2, 0, 112);
                    mbp=new ModBusTCPPacket();
                    mbp.initialization(1, (byte)1, (byte)16, (short)0, (short)200, send1);
                    cn.sendMessage(mbp.getPacket(), 0);
                    if(fi.getDELAY().isSelected()){
                        try {
                            sleep(2);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if(gl.getLastUsed()<199){
                        mbp=new ModBusTCPPacket();
                        mbp.initialization(2, (byte)1, (byte)16, (short)200, (short)200, send2);
                        cn.sendMessage(mbp.getPacket(), 0);
                    }
                    if(fi.getDELAY().isSelected()){
                        try {
                            sleep(2);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if(gl.getLastUsed()<399){
                        mbp=new ModBusTCPPacket();
                        mbp.initialization(3, (byte)1, (byte)16, (short)400, (short)112, send3);
                        cn.sendMessage(mbp.getPacket(), 0);
                    }
                }
                else if(this.mp.getR_rainbow().isSelected()){
                    if(this.t!=null){
                        this.t.fine();
                    }
                    
                    this.t=new ThreadGiochi(){

                        @Override
                        public void run() {
                            
                            InetAddress ia;
                            Integer port;
                            ia=cn.getScheda();
                            port=cn.getPort();
                            int quantity=0;
                            byte ris[];
                            Integer i=0;
                            byte send1[]=new byte[200];
                            byte send2[]=new byte[200];
                            byte send3[]=new byte[112];
                            ModBusTCPPacket mbp;
                            GiochiPsichedelici.Set_every_led_at_Color(gl.getArray(), new byte[512], 255, 0, 0);
                            
                            
                            while(termina){
                                try {
                                    if(quantity>=1000){
                                        cn.closeConnection();
                                        cn.closeBuffer();
                                        quantity=0;
                                        sleep(100);
                                        cn.connectTo(ia, port);
                                        cn.OpenChannels(); 
                                    }
                                    else{
                                        ris=GiochiPsichedelici.over_the_rainbow(gl.getArray(), new byte[512]);
                                        System.arraycopy(ris, 0, send1, 0, 200);
                                        System.arraycopy(ris, 200, send2, 0, 200);
                                        System.arraycopy(ris, 400, send2, 0, 112);
                                        mbp=new ModBusTCPPacket();
                                        mbp.initialization(1, (byte)1, (byte)16, (short)0, (short)200, send1);
                                        cn.sendMessage(mbp.getPacket(), 0);
                                        i++;
                                        if(fi.getDELAY().isSelected()){
                                            try {
                                                sleep(2);
                                            } catch (InterruptedException ex) {
                                                Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        if(gl.getLastUsed()<199){
                                            mbp=new ModBusTCPPacket();
                                            mbp.initialization(2, (byte)1, (byte)16, (short)200, (short)200, send2);
                                            cn.sendMessage(mbp.getPacket(), 0);
                                        }
                                        i++;
                                        if(fi.getDELAY().isSelected()){
                                            try {
                                                sleep(2);
                                            } catch (InterruptedException ex) {
                                                Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        if(gl.getLastUsed()<399){
                                            mbp=new ModBusTCPPacket();
                                            mbp.initialization(3, (byte)1, (byte)16, (short)400, (short)112, send3);
                                            cn.sendMessage(mbp.getPacket(), 0);
                                        }
                                        i++;
                                        quantity++;
                                        sleep((Integer)mp.getSp_time().getValue());
                                    }
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                    JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                                } catch (IOException ex) {
                                    Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }  
                    };
                    this.t.setName("RaInBoW");
                    this.t.setPriority(Thread.NORM_PRIORITY);
                    t.start();
                }
                else if(this.mp.getR_random().isSelected()){
                    if(this.t!=null){
                        this.t.fine();
                    }
                    
                    this.t=new ThreadGiochi(){

                        @Override
                        public void run() {
                            super.run();
                            
                            InetAddress ia;
                            Integer port;
                            ia=cn.getScheda();
                            port=cn.getPort();
                            int quantity=0;
                            byte ris[];
                            Integer i=0;
                            byte send1[]=new byte[200];
                            byte send2[]=new byte[200];
                            byte send3[]=new byte[112];
                            ModBusTCPPacket mbp;
                            GiochiPsichedelici.Set_every_led_at_Color(gl.getArray(), new byte[512], 0, 0, 0);
                            
                            while(termina){

                                try {
                                    if(quantity>=1000){
                                        cn.closeConnection();
                                        cn.closeBuffer();
                                        quantity=0;
                                        sleep(100);
                                        cn.connectTo(ia, port);
                                        cn.OpenChannels(); 
                                    }
                                    ris=GiochiPsichedelici.random_psichedelico(gl.getArray(), new byte[512]);
                                    System.arraycopy(ris, 0, send1, 0, 200);
                                    System.arraycopy(ris, 200, send2, 0, 200);
                                    System.arraycopy(ris, 400, send2, 0, 112);
                                    mbp=new ModBusTCPPacket();
                                    mbp.initialization(1, (byte)1, (byte)16, (short)0, (short)200, send1);
                                    cn.sendMessage(mbp.getPacket(), 0);
                                    i++;
                                    if(fi.getDELAY().isSelected()){
                                        try {
                                            sleep(2);
                                        } catch (InterruptedException ex) {
                                            Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                    if(gl.getLastUsed()>199){
                                        mbp=new ModBusTCPPacket();
                                        mbp.initialization(2, (byte)1, (byte)16, (short)200, (short)200, send2);
                                        cn.sendMessage(mbp.getPacket(), 0);
                                    }
                                    i++;
                                    if(fi.getDELAY().isSelected()){
                                        try {
                                            sleep(2);
                                        } catch (InterruptedException ex) {
                                            Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                    if(gl.getLastUsed()<399){
                                        mbp=new ModBusTCPPacket();
                                        mbp.initialization(3, (byte)1, (byte)16, (short)400, (short)112, send3);
                                        cn.sendMessage(mbp.getPacket(), 0);
                                    }
                                    i++;
                                    quantity++;
                                    sleep((Integer)mp.getSp_time().getValue());
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                }  
                            }
                        }

                    };
                    this.t.setName("RaNdOm");
                    this.t.setPriority(Thread.NORM_PRIORITY);
                    t.start();
                    
                }
                else if(this.mp.getR_rotate().isSelected()){
                    if(this.t!=null){
                        this.t.fine();
                    }
                    
                    this.t=new ThreadGiochi(){

                        @Override
                        public void run() {
                            super.run();
                            
                            InetAddress ia;
                            Integer port;
                            ia=cn.getScheda();
                            port=cn.getPort();
                            int quantity=0;
                            byte ris[];
                            Integer i=0,k=0;
                            byte send1[]=new byte[200];
                            byte send2[]=new byte[200];
                            byte send3[]=new byte[112];
                            ModBusTCPPacket mbp;
                            ris=GiochiPsichedelici.Set_every_led_at_Color(gl.getArray(), new byte[512], 0, 0, 0);
                            
                            while(termina){
                                try {
                                    if(quantity>=1000){
                                        cn.closeConnection();
                                        cn.closeBuffer();
                                        quantity=0;
                                        sleep(100);
                                        cn.connectTo(ia, port);
                                        cn.OpenChannels(); 
                                    }
                                    if(k%gl.getSize()+1==0){
                                        ris=GiochiPsichedelici.Set_every_led_at_Color(gl.getArray(), new byte[512], 0, 0, 0);
                                        System.arraycopy(ris, 0, send1, 0, 200);
                                        System.arraycopy(ris, 200, send2, 0, 200);
                                        System.arraycopy(ris, 400, send2, 0, 112);                                        
                                        mbp=new ModBusTCPPacket();
                                        mbp.initialization(1, (byte)1, (byte)16, (short)0, (short)200, send1);
                                        cn.sendMessage(mbp.getPacket(), 0);
                                        i++;
                                        if(fi.getDELAY().isSelected()){
                                            try {
                                                sleep(2);
                                            } catch (InterruptedException ex) {
                                                Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        if(gl.getLastUsed()>199) {                           
                                            mbp=new ModBusTCPPacket();
                                            mbp.initialization(2, (byte)1, (byte)16, (short)200, (short)200, send2);
                                            cn.sendMessage(mbp.getPacket(), 0);
                                        }
                                        i++;
                                        if(fi.getDELAY().isSelected()){
                                            try {
                                                sleep(2);
                                            } catch (InterruptedException ex) {
                                                Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        if(gl.getLastUsed()<399){
                                            mbp=new ModBusTCPPacket();
                                            mbp.initialization(3, (byte)1, (byte)16, (short)400, (short)112, send3);
                                            cn.sendMessage(mbp.getPacket(), 0);
                                        }
                                        i++;

                                        k++;
                                    }else{
                                        int r=mp.getSl_r().getValue();
                                        int g = mp.getSl_g().getValue();
                                        int b= mp.getSl_b().getValue();

                                        if(r>g && r>b){
                                            ris=GiochiPsichedelici.psycho_delico_line(gl.getArray(),new byte[512],'r',r,g,b);
                                        }
                                        else if(g>r && g>b){
                                            ris=GiochiPsichedelici.psycho_delico_line(gl.getArray(),new byte[512],'g',r,g,b);
                                        }
                                        else{
                                            ris=GiochiPsichedelici.psycho_delico_line(gl.getArray(),new byte[512],'b',r,g,b);
                                        }
                                        
                                        System.arraycopy(ris, 0, send1, 0, 200);
                                        System.arraycopy(ris, 200, send2, 0, 200);
                                        System.arraycopy(ris, 400, send2, 0, 112);
                                        mbp=new ModBusTCPPacket();
                                        mbp.initialization(1, (byte)1, (byte)16, (short)0, (short)200, send1);
                                        cn.sendMessage(mbp.getPacket(), 0);
                                        i++;
                                        if(fi.getDELAY().isSelected()){
                                            try {
                                                sleep(2);
                                            } catch (InterruptedException ex) {
                                                Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        if(gl.getLastUsed()>199) {
                                            mbp=new ModBusTCPPacket();
                                            mbp.initialization(2, (byte)1, (byte)16, (short)200, (short)200, send2);
                                            cn.sendMessage(mbp.getPacket(), 0);
                                        }
                                        i++;
                                        if(fi.getDELAY().isSelected()){
                                            try {
                                                sleep(2);
                                            } catch (InterruptedException ex) {
                                                Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        if(gl.getLastUsed()<399){
                                            mbp=new ModBusTCPPacket();
                                            mbp.initialization(3, (byte)1, (byte)16, (short)400, (short)112, send3);
                                            cn.sendMessage(mbp.getPacket(), 0);
                                        }
                                        i++;
                                    }
                                    quantity++;
                                    sleep((Integer)mp.getSp_time().getValue());
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                }  
                            }
                        }       
                    };
                    this.t.setName("RoTaTe");
                    this.t.setPriority(Thread.NORM_PRIORITY);
                    t.start();
                }
                else if(this.mp.getR_jump().isSelected()){
                    if(this.t!=null){
                        this.t.fine();
                    }
                    
                    this.t=new ThreadGiochi(){

                        @Override
                        public void run() {
                            super.run();

                            InetAddress ia;
                            Integer port;
                            ia=cn.getScheda();
                            port=cn.getPort();
                            int quantity=0;
                            byte ris[];
                            Integer i=0,k=0;
                            byte send1[]=new byte[200];
                            byte send2[]=new byte[200];
                            byte send3[]=new byte[112];
                            ModBusTCPPacket mbp;

                            while(termina){
                                try {
                                    if(quantity>=1000){
                                        cn.closeConnection();
                                        cn.closeBuffer();
                                        quantity=0;
                                        sleep(100);
                                        cn.connectTo(ia, port);
                                        cn.OpenChannels(); 
                                    }
                                    if(k%gl.getSize()+1==0){
                                        ris=GiochiPsichedelici.set_only_one_lamp_at_color(gl.getArray(), new byte[512], mp.getSl_r().getValue(), mp.getSl_g().getValue(), mp.getSl_b().getValue(), 0);
                                        System.arraycopy(ris, 0, send1, 0, 200);
                                        System.arraycopy(ris, 200, send2, 0, 200);
                                        System.arraycopy(ris, 400, send2, 0, 112);
                                        mbp=new ModBusTCPPacket();
                                        mbp.initialization(1, (byte)1, (byte)16, (short)0, (short)200, send1);
                                        cn.sendMessage(mbp.getPacket(), 0);
                                        i++;
                                        if(fi.getDELAY().isSelected()){
                                            try {
                                                sleep(2);
                                            } catch (InterruptedException ex) {
                                                Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        if(gl.getLastUsed()>199) {
                                            mbp=new ModBusTCPPacket();
                                            mbp.initialization(2, (byte)1, (byte)16, (short)200, (short)200, send2);
                                            cn.sendMessage(mbp.getPacket(), 0);
                                        }
                                        i++;
                                        if(fi.getDELAY().isSelected()){
                                            try {
                                                sleep(2);
                                            } catch (InterruptedException ex) {
                                                Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        if(gl.getLastUsed()<399){
                                            mbp=new ModBusTCPPacket();
                                            mbp.initialization(3, (byte)1, (byte)16, (short)400, (short)112, send3);
                                            cn.sendMessage(mbp.getPacket(), 0);
                                        }
                                        i++;    k++;
                                    }else{
                                        ris=GiochiPsichedelici.jump(gl.getArray(), new byte[512] ,mp.getSl_r().getValue(), mp.getSl_g().getValue(), mp.getSl_b().getValue());
                                        System.arraycopy(ris, 0, send1, 0, 200);
                                        System.arraycopy(ris, 200, send2, 0, 200);
                                        System.arraycopy(ris, 400, send2, 0, 112);
                                        mbp=new ModBusTCPPacket();
                                        mbp.initialization(1, (byte)1, (byte)16, (short)0, (short)200, send1);
                                        cn.sendMessage(mbp.getPacket(), 0);
                                        i++;
                                        if(fi.getDELAY().isSelected()){
                                            try {
                                                sleep(2);
                                            } catch (InterruptedException ex) {
                                                Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        if(gl.getLastUsed()>199) {
                                            mbp=new ModBusTCPPacket();
                                            mbp.initialization(2, (byte)1, (byte)16, (short)200, (short)200, send2);
                                            cn.sendMessage(mbp.getPacket(), 0);
                                        }
                                        i++;
                                        if(fi.getDELAY().isSelected()){
                                            try {
                                                sleep(2);
                                            } catch (InterruptedException ex) {
                                                Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        if(gl.getLastUsed()<399){
                                            mbp=new ModBusTCPPacket();
                                            mbp.initialization(3, (byte)1, (byte)16, (short)400, (short)112, send3);
                                            cn.sendMessage(mbp.getPacket(), 0);
                                        }
                                        i++;    k++;
                                    }
                                    quantity++;
                                    sleep((Integer)mp.getSp_time().getValue());
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                }  
                            }
                        }       
                    };
                    this.t.setName("JuMp");
                    this.t.setPriority(Thread.NORM_PRIORITY);
                    t.start();
                }
                else if(this.mp.getR_sfuma().isSelected()){
                    if(this.t!=null){
                        this.t.fine();
                    }
                    
                    this.t=new ThreadGiochi(){

                        @Override
                        public void run() {
                            super.run();

                            InetAddress ia;
                            Integer port;
                            ia=cn.getScheda();
                            port=cn.getPort();
                            int quantity=0;
                            byte ris[];
                            Integer i=0;
                            byte send1[]=new byte[200];
                            byte send2[]=new byte[200];
                            byte send3[]=new byte[112];
                            ModBusTCPPacket mbp;
                            
                            GiochiPsichedelici.Set_every_led_at_Color(gl.getArray(), new byte[512], 0, 0, 0);
                            while(termina){
                                try {
                                    if(quantity>=1000){
                                        cn.closeConnection();
                                        cn.closeBuffer();
                                        quantity=0;
                                        sleep(100);
                                        cn.connectTo(ia, port);
                                        cn.OpenChannels(); 
                                    }
                                    ris=GiochiPsichedelici.aumento1(gl.getArray(), new byte[512] );
                                    System.arraycopy(ris, 0, send1, 0, 200);
                                    System.arraycopy(ris, 200, send2, 0, 200);
                                    System.arraycopy(ris, 400, send2, 0, 112);
                                    mbp=new ModBusTCPPacket();
                                    mbp.initialization(1, (byte)1, (byte)16, (short)0, (short)200, send1);
                                    cn.sendMessage(mbp.getPacket(), 0);
                                    i++;
                                    if(fi.getDELAY().isSelected()){
                                        try {
                                            sleep(2);
                                        } catch (InterruptedException ex) {
                                            Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                    if(gl.getLastUsed()>199) {
                                        mbp=new ModBusTCPPacket();
                                        mbp.initialization(2, (byte)1, (byte)16, (short)200, (short)200, send2);
                                        cn.sendMessage(mbp.getPacket(), 0);
                                    }
                                    i++;
                                    if(fi.getDELAY().isSelected()){
                                        try {
                                            sleep(2);
                                        } catch (InterruptedException ex) {
                                            Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                    if(gl.getLastUsed()<399){
                                        mbp=new ModBusTCPPacket();
                                        mbp.initialization(3, (byte)1, (byte)16, (short)400, (short)112, send3);
                                        cn.sendMessage(mbp.getPacket(), 0);
                                    }
                                    i++;
                                    quantity++;
                                    sleep((Integer)mp.getSp_time().getValue());
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                }  
                            }
                        }  
                    };
                    this.t.setName("SfUmA");
                    this.t.setPriority(Thread.NORM_PRIORITY);
                    t.start();
                }
                else if(this.mp.getR_jumpRainbow().isSelected()){
                    if(this.t!=null){
                        this.t.fine();
                    }
                    
                    this.t=new ThreadGiochi(){

                        @Override
                        public void run() {
                            super.run();

                            InetAddress ia;
                            Integer port;
                            ia=cn.getScheda();
                            port=cn.getPort();
                            int quantity=0;
                            byte ris[];
                            Integer i=0,k=0;
                            byte send1[]=new byte[200];
                            byte send2[]=new byte[200];
                            byte send3[]=new byte[112];
                            ModBusTCPPacket mbp;
                            int v[] = new int[3];
                            v[0]=255;
                            v[1]=0;
                            v[2]=0;
                            ris=GiochiPsichedelici.Set_every_led_at_Color(gl.getArray(), new byte[512], 0, 0, 0);
                            while(termina){
                                try {
                                    if(quantity>=1000){
                                        cn.closeConnection();
                                        cn.closeBuffer();
                                        quantity=0;
                                        sleep(100);
                                        cn.connectTo(ia, port);
                                        cn.OpenChannels(); 
                                    }
                                    v=GiochiPsichedelici.jump_over_the_rainbow(gl.getArray(), new byte[512], v[0], v[1], v[2]);
                                    ris=GiochiPsichedelici.jump(gl.getArray(), new byte[512], v[0], v[1], v[2]);

                                    System.arraycopy(ris, 0, send1, 0, 200);
                                    System.arraycopy(ris, 200, send2, 0, 200);
                                    System.arraycopy(ris, 400, send2, 0, 112);
                                    mbp=new ModBusTCPPacket();
                                    mbp.initialization(1, (byte)1, (byte)16, (short)0, (short)200, send1);
                                    cn.sendMessage(mbp.getPacket(), 0);
                                    i++;
                                    if(fi.getDELAY().isSelected()){
                                        try {
                                            sleep(2);
                                        } catch (InterruptedException ex) {
                                            Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                    if(gl.getLastUsed()>199) {
                                        mbp=new ModBusTCPPacket();
                                        mbp.initialization(2, (byte)1, (byte)16, (short)200, (short)200, send2);
                                        cn.sendMessage(mbp.getPacket(), 0);
                                    }
                                    i++;
                                    if(fi.getDELAY().isSelected()){
                                        try {
                                            sleep(2);
                                        } catch (InterruptedException ex) {
                                            Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                    if(gl.getLastUsed()<399){
                                        mbp=new ModBusTCPPacket();
                                        mbp.initialization(3, (byte)1, (byte)16, (short)400, (short)112, send3);
                                        cn.sendMessage(mbp.getPacket(), 0);
                                    }
                                    i++;
                                    quantity++;
                                    sleep((Integer)mp.getSp_time().getValue());
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                }  
                            }
                        }  
                    };
                    this.t.setName("RaInBoWJuMPp");
                    this.t.setPriority(Thread.NORM_PRIORITY);
                    t.start();
                }
                else if(this.mp.getR_kit().isSelected()){
                    if(this.t!=null){
                        this.t.fine();
                    }
                    
                    this.t=new ThreadGiochi(){

                        @Override
                        public void run() {
                            super.run();

                            InetAddress ia;
                            Integer port;
                            ia=cn.getScheda();
                            port=cn.getPort();
                            int quantity=0;
                            byte ris[];
                            Integer i=0,k=0;
                            byte send1[]=new byte[200];
                            byte send2[]=new byte[200];
                            byte send3[]=new byte[112];
                            ModBusTCPPacket mbp;
                            int v[] = new int[3];
                            v[0]=255;
                            v[1]=0;
                            v[2]=0;
                            ris=GiochiPsichedelici.Set_every_led_at_Color(gl.getArray(), new byte[512], 0, 0, 0);
                            while(termina){
                                try {
                                    if(quantity>=1000){
                                        cn.closeConnection();
                                        cn.closeBuffer();
                                        quantity=0;
                                        sleep(100);
                                        cn.connectTo(ia, port);
                                        cn.OpenChannels(); 
                                    }
                                    if(k%gl.getSize()+1==0){
                                        ris=GiochiPsichedelici.psycho_delico_double_line(gl.getArray(), new byte[512]);

                                        System.arraycopy(ris, 0, send1, 0, 200);
                                        System.arraycopy(ris, 200, send2, 0, 200);
                                        System.arraycopy(ris, 400, send2, 0, 112);
                                        mbp=new ModBusTCPPacket();
                                        mbp.initialization(1, (byte)1, (byte)16, (short)0, (short)200, send1);
                                        cn.sendMessage(mbp.getPacket(), 0);
                                        i++;
                                        if(fi.getDELAY().isSelected()){
                                            try {
                                                sleep(2);
                                            } catch (InterruptedException ex) {
                                                Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        if(gl.getLastUsed()>199) {
                                            mbp=new ModBusTCPPacket();
                                            mbp.initialization(2, (byte)1, (byte)16, (short)200, (short)200, send2);
                                            cn.sendMessage(mbp.getPacket(), 0);
                                        }
                                        i++;
                                        if(fi.getDELAY().isSelected()){
                                            try {
                                                sleep(2);
                                            } catch (InterruptedException ex) {
                                                Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        if(gl.getLastUsed()<399){
                                            mbp=new ModBusTCPPacket();
                                            mbp.initialization(3, (byte)1, (byte)16, (short)400, (short)112, send3);
                                            cn.sendMessage(mbp.getPacket(), 0);
                                        }
                                        i++; k++;
                                    }else{
                                        ris=GiochiPsichedelici.psycho_delico_double_line(gl.getArray(), new byte[512]);
                                        System.arraycopy(ris, 0, send1, 0, 200);
                                        System.arraycopy(ris, 200, send2, 0, 200);
                                        System.arraycopy(ris, 400, send2, 0, 112);
                                        mbp=new ModBusTCPPacket();
                                        mbp.initialization(1, (byte)1, (byte)16, (short)0, (short)200, send1);
                                        cn.sendMessage(mbp.getPacket(), 0);
                                        i++;
                                        if(fi.getDELAY().isSelected()){
                                            try {
                                                sleep(2);
                                            } catch (InterruptedException ex) {
                                                Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        if(gl.getLastUsed()>199) {
                                            mbp=new ModBusTCPPacket();
                                            mbp.initialization(2, (byte)1, (byte)16, (short)200, (short)200, send2);
                                            cn.sendMessage(mbp.getPacket(), 0);
                                        }    
                                        i++;
                                        if(fi.getDELAY().isSelected()){
                                            try {
                                                sleep(2);
                                            } catch (InterruptedException ex) {
                                                Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        if(gl.getLastUsed()<399){
                                            mbp=new ModBusTCPPacket();
                                            mbp.initialization(3, (byte)1, (byte)16, (short)400, (short)112, send3);
                                            cn.sendMessage(mbp.getPacket(), 0);
                                        }
                                        i++; k++;
                                    }
                                    quantity++;
                                    sleep((Integer)mp.getSp_time().getValue());
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                }  
                            }
                        }  
                    };
                    this.t.setName("KiT");
                    this.t.setPriority(Thread.NORM_PRIORITY);
                    t.start();
                }
                else if(this.mp.getR_auto().isSelected()){
                    if(this.t!=null){
                        this.t.fine();
                    }
                       
                    this.t=new ThreadGiochi(){
                        
                        @Override
                        public void run() {
                            super.run();

                            InetAddress ia;
                            Integer port;
                            ia=cn.getScheda();
                            port=cn.getPort();
                            int quantity=0;
                            byte ris[];
                            int i=0;
                            byte send1[]=new byte[200];
                            byte send2[]=new byte[200];
                            byte send3[]=new byte[112];
                            ModBusTCPPacket mbp;
                            Integer tempo=Integer.parseInt(JOptionPane.showInputDialog(mf, "Ogni quanto far cambiare gli effetti", "Richiestra", JOptionPane.QUESTION_MESSAGE));
                            Timer time=new Timer();
                            time.scheduleAtFixedRate(new TimerTask() {
                                
                               private ThreadGiochi thread=t;
                                
                                @Override
                                public void run() {
                                    if(thread.getStato()>7){
                                        thread.setStato(0);
                                    }
                                    else{
                                        stato++;
                                    }
                                }
                            }, tempo, tempo);
                            int v[] = new int[3];
                            v[0]=255;
                            v[1]=0;
                            v[2]=0;
                            ris=GiochiPsichedelici.Set_every_led_at_Color(gl.getArray(), new byte[512], 0, 0, 0);
                            stato=0;
                            
                            while(termina){
                                if(quantity>=1000){
                                    try {
                                        cn.closeConnection();
                                        cn.closeBuffer();
                                        quantity=0;
                                        sleep(100);
                                        cn.connectTo(ia, port);
                                        cn.OpenChannels(); 
                                    } catch (IOException ex) {
                                        Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                switch(stato){
                                    case 0:
                                        ris=GiochiPsichedelici.over_the_rainbow(gl.getArray(), new byte[512]);
                                        break;
                                    case 1:
                                        ris=GiochiPsichedelici.random_psichedelico(gl.getArray(), new byte[512]);
                                        break;
                                    case 2:
                                        ris=GiochiPsichedelici.psycho_delico_line(gl.getArray(),new byte[512],'r',255,0,0);
                                        break;
                                    case 3:
                                        ris=GiochiPsichedelici.jump(gl.getArray(), new byte[512] ,mp.getSl_r().getValue(), mp.getSl_g().getValue(), mp.getSl_b().getValue());
                                        break;
                                    case 4:
                                        ris=GiochiPsichedelici.psycho_delico_line(gl.getArray(),new byte[512],'g',0,255,0);
                                        break;
                                    case 5:
                                        ris=GiochiPsichedelici.aumento1(gl.getArray(), new byte[512]);
                                        break;
                                    case 6:
                                        ris=GiochiPsichedelici.psycho_delico_line(gl.getArray(),new byte[512],'b',0,0,255);
                                        break;
                                    case 7:
                                        v=GiochiPsichedelici.jump_over_the_rainbow(gl.getArray(), new byte[512], v[0], v[1], v[2]);
                                        ris=GiochiPsichedelici.jump(gl.getArray(), new byte[512], v[0], v[1], v[2]);
                                        break;
                                    default:
                                        ris=GiochiPsichedelici.Set_every_led_at_Color(gl.getArray(), new byte[512], 0, 0, 0);
                                        break;
                                }
                                System.arraycopy(ris, 0, send1, 0, 200);
                                System.arraycopy(ris, 200, send2, 0, 200);
                                System.arraycopy(ris, 400, send2, 0, 112);
                                mbp=new ModBusTCPPacket();
                                mbp.initialization(1, (byte)1, (byte)16, (short)0, (short)200, send1);
                                cn.sendMessage(mbp.getPacket(), 0);
                                i++;
                                if(fi.getDELAY().isSelected()){
                                    try {
                                        sleep(2);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                if(gl.getLastUsed()>199) {
                                    mbp=new ModBusTCPPacket();
                                    mbp.initialization(2, (byte)1, (byte)16, (short)200, (short)200, send2);
                                    cn.sendMessage(mbp.getPacket(), 0);
                                }
                                i++;
                                if(fi.getDELAY().isSelected()){
                                    try {
                                        sleep(2);
                                    } catch (InterruptedException ex) {
                                       Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                        
                                
                                if(gl.getLastUsed()<399){
                                    mbp=new ModBusTCPPacket();
                                    mbp.initialization(3, (byte)1, (byte)16, (short)400, (short)112, send3);
                                    cn.sendMessage(mbp.getPacket(), 0);
                                }
                                i++;
                                try {
                                    quantity++;
                                    sleep((Integer)mp.getSp_time().getValue());
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(listenerBottoni.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            time.cancel();
                            time.purge();
                        }  
                    };
                    this.t.setName("AuTo");
                    this.t.setPriority(Thread.NORM_PRIORITY);
                    this.t.start();
                } 
                else{
                    throw new UnsupportedOperationException("Mode not supported yet.");
                } 
            }
            else{
                JOptionPane.showMessageDialog(this.mf, "Non si è connssi ad alcuna scheda", "Avvio", JOptionPane.WARNING_MESSAGE);
                throw new HeadlessException("Connection not Established");
            }
        }
        else{
            throw new UnsupportedOperationException("Not supported yet.");
        }
    } 
}
