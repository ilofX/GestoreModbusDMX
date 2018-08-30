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
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

/**
 *
 * @author Filippo Stella
 * @version 1.0
 */
public class GestioneLuci {

    private ArrayList<Luce> luci;
    private final ListIterator litr;
    private final Integer startChannel;
    private final FrameImpostazioni fi;

    public GestioneLuci(FrameImpostazioni fi) {
        this.luci=new ArrayList<>();
        this.litr=this.luci.listIterator();
        this.startChannel=1;
        this.fi = fi;
    }

    /**
    * This method returns an integer that correspond to the first channel.  
    * <p>
    * This method once called returns the firs used channel by the first DMX light
    * 
    * @return     the first channel used.
    */
    public Integer getStartChannel() {
        return this.startChannel;
    }
    
    /**
    * This method add an additional light.  
    * <p>
    * this method add a light and sort them by start chennels.
    *
    * @param l  The light that needs to be added. 
    */
    public void addLuce(Luce l){
        this.luci.add(l);
        if(this.fi.getSORT().isSelected()){
            Collections.sort(luci, new StartChannelSort());
        }
    }
    
    /**
    * This method remove an additional light.  
    * <p>
    * this method remove a light and sort them by start chennels.
    *
    * @param i  The light that needs to be remove.
    */
    public void removeLuce(int i){
        this.luci.remove(i);
        if(this.fi.getSORT().isSelected()){
            Collections.sort(luci, new StartChannelSort());
        }
    }
    
    
    /**
    * This method return an arraylist with the list of all the lights.  
    * <p>
    * This method returns the arraylist containing all the current lights
    * 
    * @return arraylist of lights.
    */
    public ArrayList<Luce> getArray(){
        return this.luci;
    }
    
    /**
    * This method is used to restore the arraylist lights.  
    * <p>
    * This method is used to restore the arraylist of lughts
    * using the saved one
    * 
    * @param al The arraylist containing the restored lights.
    */
    public void setArray(ArrayList<Luce> al){
        this.luci=al;
        if(fi.getSORT().isSelected()){
            Collections.sort(luci, new StartChannelSort());
        }
    }
    
    
    /**
    * This method return the first unused channel.  
    * <p>
    * This method once called returns the first available channel
    * 
    * @return ris The first unused channel.
    */
    public Integer getLastChannel(){
        Integer ris=1;
        if(luci.size()>0){
            if((luci.get(0).getStart())>1){
                ris=1;
            }
            else{
                ris=(luci.get(luci.size()-1)).getStart()+(luci.get(luci.size()-1)).getnCanali();
            }   
        }
        return ris;
    }
    
    /**
    * This method is used for change the setting for every lights RGB.
    * <p>
    * @param i          The index of the light that needs to be edited
    * @param tipo       The new tipo that will overwrite the old one
    * @param start      The new start DMX channel that will overwrite he old one
    * @param nCanali    The new number of DMX channels that will overwrite the old one
    * @param canaleR    The new Red chanel that will overwrite the old one
    * @param canaleG    The new Green chanel that will overwrite the old one
    * @param canaleB    The new Blue channel taht will overwrite the old one
    */ 
    public void editLuce(Integer i, String tipo, Integer start, Integer nCanali, Integer canaleR, Integer canaleG, Integer canaleB){
        this.luci.get(i).setTipo(tipo);
        this.luci.get(i).setStart(start);
        this.luci.get(i).setnCanali(nCanali);
        this.luci.get(i).setCanaleR(canaleR);
        this.luci.get(i).setCanaleG(canaleG);
        this.luci.get(i).setCanaleB(canaleB);
    }
    
    public Integer getLastUsed(){
        return this.luci.get(this.luci.size()-1).getStart()+this.luci.get(this.luci.size()-1).getnCanali()-1;
    }
    
    /**
    * This method returns the light in the ArrayList
    * <p>
    * 
    * @param i  The index of the light you want to get
    * @return   The lihgt at the io position in the ArrayList
    */
    public Luce getLuce(Integer i){
       return this.luci.get(i);
    }
    
    public Integer getSize(){
        return this.luci.size();
    }

    public void swapPrevious(Integer i){
        Luce l;
        if(i>0){
            l=this.luci.get(i-1);
            this.luci.set(i-1, this.luci.get(i));
            this.luci.set(i, l);
        }
    }
    
    public void swapNext(Integer i){
        Luce l;
        if(i<this.luci.size()-1){
            l=this.luci.get(i);
            this.luci.set(i ,this.luci.get(i+1));
            this.luci.set(i+1 ,l);
        }
        
    }
    
}
