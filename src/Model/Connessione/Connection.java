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
package Model.Connessione;

import Model.Connessione.Thread.Connect;
import Model.Connessione.Thread.Disconnect;
import Model.Connessione.Thread.Send;
import View.MainFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Filippo Stella
 * @version 1.5
 */
public class Connection{
    
    private final Semaphore semPrinter,semReader;
    private String ris;
    private Connect tCon;
    private Disconnect dCon;
    private Send sMessage;
    private boolean Connection;
    private InputStream inFromServer;
    private OutputStream outToServer;
    private BufferedReader bufferReader;
    private PrintStream printer;
    private Socket socket;
    private InetAddress scheda;
    private final MainFrame f;
    private Integer port;

    public Connection(MainFrame f) {
        this.semPrinter=new Semaphore(0,true);
        this.semReader=new Semaphore(0,true);
        this.ris="";
        this.f=f;
        this.Connection=false;
    }
    
    public Connection() {
        this.semPrinter=new Semaphore(0,true);
        this.semReader=new Semaphore(0,true);
        this.ris="";
        this.f=null;
        this.Connection=false;
    }

    public Socket getSocket() {
        return this.socket;
    }
    public InetAddress getScheda() {
        return this.scheda;
    }
    public boolean getConnection(){
        return this.Connection;
    }
    public void setScheda(InetAddress scheda) {
        this.scheda = scheda;
    }
    public void setConnection(boolean Connection){
        this.Connection = Connection;
    }
    public void setRis(String ris) {
        this.ris = ris;
    }
    public BufferedReader getBufferReader() {
        return this.bufferReader;
    }
    public Semaphore getSemPrinter() {
        return this.semPrinter;
    }
    public Semaphore getSemReader() {
        return this.semReader;
    }
    public Integer getPort() {
        return this.port;
    }
    public PrintStream getPrinter() {
        return this.printer;
    }
    
    
    public void closeBuffer() throws IOException{
        this.bufferReader.close();
        this.inFromServer.close();
        this.printer.close();
        this.outToServer.close();
    }
    
    public void socketToNull(){
        this.socket=null;
    }
    
    public void OpenChannels() throws IOException {
        this.inFromServer=this.socket.getInputStream();
        this.bufferReader=new BufferedReader(new InputStreamReader(this.inFromServer));
        this.outToServer=this.socket.getOutputStream();
        this.printer=new PrintStream(this.outToServer);
    }
    
    public void connectTo(byte ip1, byte ip2, byte ip3, byte ip4,int port){
        this.port=port;
        this.socket=new Socket(); 
        this.tCon=new Connect(this, ip1, ip2, ip3, ip4, port,this.f);
        this.tCon.setName("Connessione");
        this.tCon.setPriority(Thread.MAX_PRIORITY);
        this.tCon.start();
    }
    
    public void connectTo(InetAddress ia,int port){
        this.port=port;
        this.socket=new Socket(); 
        this.tCon=new Connect(this,ia,port,this.f);
        this.tCon.setName("Connessione");
        this.tCon.setPriority(Thread.MAX_PRIORITY);
        this.tCon.start();
    }
    
    public void sendMessage(byte[] message,int time) {
        this.ris = "";
        this.sMessage=new Send(this,message,this.f,this.semPrinter, this.semReader,time);
        this.sMessage.setName("SendMessage");
        this.sMessage.setPriority(Thread.MAX_PRIORITY);
        this.sMessage.start();
    }
    
    public void closeConnection(){
        this.dCon=new Disconnect(this, this.f);
        this.dCon.setName("Disconnessione");
        this.dCon.setPriority(Thread.MAX_PRIORITY);
        this.dCon.start();
    }  
    
}
