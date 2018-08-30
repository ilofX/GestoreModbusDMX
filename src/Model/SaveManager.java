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

import View.Impostazioni.FrameImpostazioni;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Filippo Stella
 * @version 1.0
 */
public class SaveManager {
    
    private File save;
    private String IP="0/0/0/0";
    private Integer Port=0;
    private ArrayList<Luce> al;
    private BufferedWriter bw=null;
    private BufferedReader br=null;
    private PrintWriter pr=null;
    private boolean firstOpen=false;
    private boolean isDelay=false;
    private boolean isSort=false;

    /**
    * This is the default constructor for the SaveManger class
    * <p>
    * This constructor creates the save manager
    * 
    */
    public SaveManager() {
        this.al=new ArrayList<>(); 
        try { 
            if((System.getProperty("os.name").toLowerCase()).contains("windows")){
                if(!(this.save=new File(System.getenv("APPDATA")+"/ADF-ModBusConnector")).exists()){
                    this.save.mkdir();
                    this.firstOpen=true;
                }
                if(!(this.save=new File(System.getenv("APPDATA")+"/ADF-ModBusConnector/Save.sav")).exists()){
                    this.save.createNewFile();
                    this.firstOpen=true;
                }
            }
            else if((System.getProperty("os.name").toLowerCase()).contains("mac")){
                if(!(this.save=new File(System.getenv("HOME")+"/Library/Preferences/ADF-ModBusConnector")).exists()){
                    this.save.mkdir();
                    this.firstOpen=true;
                }
                if(!(this.save=new File(System.getenv("HOME")+"/Library/Preferences/ADF-ModBusConnector/Save.sav")).exists()){
                    this.save.createNewFile();
                    this.firstOpen=true;
                }
            }
            else if((System.getProperty("os.name").toLowerCase()).contains("linux")){
                if(!(this.save=new File(System.getenv("HOME")+"/.ADF-ModBusConnector")).exists()){
                    this.save.mkdir();
                    this.firstOpen=true;
                }
                if(!(this.save=new File(System.getenv("HOME")+"/.ADF-ModBusConnector/Save.sav")).exists()){
                    this.save.createNewFile();
                    this.firstOpen=true;
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Operating System NOT Supported", "Errore", JOptionPane.ERROR_MESSAGE);
                System.err.println("Operating System NOT Supported");
                exit(6);
            }
        } catch (IOException ex) {
            Logger.getLogger(SaveManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
    * This is a getter for the restored IP
    * <p>
    * This is a getter for the restored IP
    * 
    * @return this returns a string with the IP red from the save file
    */
    public String getIP() {
        return this.IP;
    }
    
    /**
    * This is a getter for the restored port
    * <p>
    * This is a getter for the restored port
    * 
    * @return this returns an Integer with the port red from the save file
    */
    public Integer getPort() {
        return this.Port;
    }
    
    /**
    * This is a getter for the restored Arraylist of luci
    * <p>
    * This is a getter for the restored Arraylist of luci
    * 
    * @return this returns an ArrayList of Luce red for the save file
    */
    public ArrayList<Luce> getAl() {
        return this.al;
    }

    /**
    * This is a getter for the restored state of the Delay flag
    * <p>
    * This is a getter for the restored state of the Delay flag
    * 
    * @return this returns the saved state of the delay flag
    */
    public boolean getDelay() {
        return this.isDelay;
    }

    public boolean getIsSort() {
        return this.isSort;
    }

    /**
    * This method saves the current state of the program in a file
    * <p>
    * This method saves the IP, the port and the list of lights
    * in a save file located in the given folder inside the constructor
    * of this class
    * 
    * @param IP             String containing the IP address with instead of '.' byte separator characters '/'
    * @param port           String containing the port
    * @param al             ArrayList containing the lights to save
    * @param fi             The frame which is used to set the application parameters
    * @exception Exception  This method can trow an exception if you don't have enough permission on the file to write 
    * @return               TRUE=settings saved   FALSE=error while saving
    */
    public boolean fSave(String IP, String port, ArrayList<Luce> al, FrameImpostazioni fi) throws Exception{
        boolean ris=false;
        this.bw= new BufferedWriter(new FileWriter(this.save));
        this.pr= new PrintWriter(this.bw);
         
        if(this.save.canWrite()){
            
            if(IP.compareTo("///")!=0 && port.compareTo("")!=0){
                this.pr.println(IP);
                this.pr.println(port);
            }
            else{
                this.pr.println("");
                this.pr.println("");
            }
            
            if(fi.getDELAY().isSelected()){
                this.pr.println("true");
            }
            else{
                this.pr.println("false");
            }
            
            if(fi.getSORT().isSelected()){
                this.pr.println("true");
            }
            else{
                this.pr.println("false");
            }
            
            for(int i=0;i<al.size();i++){
                this.pr.println("luce"+i+"{");
                this.pr.print("Tipo:");
                this.pr.println(al.get(i).getTipo());
                this.pr.print("Start:");
                this.pr.println(Integer.toString(al.get(i).getStart()));
                this.pr.print("Canali:");
                this.pr.println(Integer.toString(al.get(i).getnCanali()));
                this.pr.print("R:");
                this.pr.println(Integer.toString(al.get(i).getCanaleR()));
                this.pr.print("G:");
                this.pr.println(Integer.toString(al.get(i).getCanaleG()));
                this.pr.print("B:");
                this.pr.println(Integer.toString(al.get(i).getCanaleB()));
                this.pr.println("}");
            }
            ris=true;
        }
        else{
            throw new Exception("Insufficent Permission: Unable to Write to file");                   
        } 
        this.pr.println("#");
        this.bw.flush();
        this.pr.close();
        this.bw.close();
        
        return ris;
    }
    
    /**
    * This method reads the save file and import the values into the variables of this class
    * <p>
    * This method restore the setting of the application to
    * the last state they were before closing
    * 
    * @exception Exception  This method can trow an exception if you don't have enough permission on the file to write 
    * @return               TRUE=settings red   FALSE=error while reading save file
    */
    public boolean read() throws Exception{
        boolean ris=false;
        String temp="";
        String Tipo="";
        String temporanea[]=new String[2];
        Integer Start=0,nCanali=0,canaleR=0,canaleG=0,canaleB=0;
        this.br= new BufferedReader(new FileReader(this.save));
        if(this.save.canRead()){
            if(this.save.length()!=0 && this.firstOpen==false){
                ris=true;
                if((temp=this.br.readLine()).length()>5){
                    this.IP=temp;
                }
                if((temp=this.br.readLine()).compareTo("")!=0){
                    this.Port=Integer.parseInt(temp);
                }
                
                if((temp=this.br.readLine()).contains("true")){
                    this.isDelay=true;
                }
                else{
                    this.isDelay=false;
                }
                
                if((temp=this.br.readLine()).contains("true")){
                    this.isSort=true;
                }
                else{
                    this.isSort=false;
                }

                while(!temp.contains("#")){
                    while(!(temp=this.br.readLine()).contains("}") && (!temp.contains("#"))){
                        if(temp.contains("luce"));
                        else{
                            temporanea=(temp.split(":"));

                            if(temporanea[0].compareTo("Tipo")==0){
                                Tipo=temporanea[1];
                            }
                            else if(temporanea[0].compareTo("Start")==0){
                                Start=Integer.parseInt(temporanea[1]);
                            }
                            else if(temporanea[0].compareTo("Canali")==0){
                                nCanali=Integer.parseInt(temporanea[1]);
                            }
                            else if(temporanea[0].compareTo("R")==0){
                                canaleR=Integer.parseInt(temporanea[1]);
                            }
                            else if(temporanea[0].compareTo("G")==0){
                                canaleG=Integer.parseInt(temporanea[1]);
                            }
                            else if(temporanea[0].compareTo("B")==0){
                                canaleB=Integer.parseInt(temporanea[1]);
                            }
                        }
                    }
                    if(!temp.contains("#")){
                        this.al.add(new Luce(Start, nCanali, Tipo, canaleR, canaleG, canaleB));
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Nessuna configurazione trovata\ncaricamento parametri di default...   ", "Avviso", JOptionPane.WARNING_MESSAGE);
                ris=false;
            }
        }
        else{
            throw new Exception("Insufficent Permission: Unable to Read from file");                   
        }       
        this.br.close();
        return ris;
    }
       
}
