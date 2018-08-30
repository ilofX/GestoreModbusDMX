/*
 * Copyright 2017 Elia Nasato.
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

import java.util.ArrayList;

/**
 *
 * @author Elia Nasato
 * @version 1.4
 */
public class GiochiPsichedelici {
    
    private static int status=-1;
    public static int status2=-1;
    public static int status3=-1;
    public static int status4=513;
    
    //per ogni gioco cambio prima i valori dell'oggetto poi richiamo la funzione che in base al canale cambia lo stato
    
    /**
    * Returns a byte array with current values of RGB per channel.  
    * <p>
    * This method one called returns a byte array to make all the lights turn on
    * with the current color configuration. This function refreshes the DMX lights
    * 
    * @param v          the array to be filled with the DMX string 
    * @param al         The array list containing the lights in your configuration
    * @return           Returns an array of short, the DMX string to be sent
    */
    public static byte[] refresh(ArrayList<Luce> al,byte v[]){
        return change_state_of_chanel(al, v);
    }
    
    
    /**
    * Increases the current RGB value of 1.  
    * <p>
    * This method takes the current RGB values for each light and
    * increases them by 1 until they reach 255
    * 
    * @param v          the array to be filled with the DMX string 
    * @param al         The array list containing the lights in your configuration
    * @return           Returns an array of short, the DMX string to be sent
    */
    public static byte[] aumento1(ArrayList<Luce> al,byte v[]){
        for(int i=0;i<al.size();i++){
            if(al.get(i).getValoreR()<255){al.get(i).setValoreR(al.get(i).getValoreR()+1);}else{al.get(i).setValoreR(0);}
            if(al.get(i).getValoreG()<255){al.get(i).setValoreG(al.get(i).getValoreG()+1);}else{al.get(i).setValoreG(0);}
            if(al.get(i).getValoreB()<255){al.get(i).setValoreB(al.get(i).getValoreB()+1);}else{al.get(i).setValoreB(0);}
        }
              return change_state_of_chanel(al, v);
        
    }
    
    
    /**
    * Increases the current RGB value of 10.  
    * <p>
    * This method takes the current RGB values for each light and
    * increases them by 10 until they reach 255
    * 
    * @param v          the array to be filled with the DMX string 
    * @param al         The array list containing the lights in your configuration
    * @return           Returns an array of short, the DMX string to be sent
    */
    public static byte[] aumento10(ArrayList<Luce> al,byte v[]){
        for(int i=0;i<al.size();i++){
            if(al.get(i).getValoreR()<255){al.get(i).setValoreR(al.get(i).getValoreR()+10);}else{al.get(i).setValoreR(10);}
            if(al.get(i).getValoreG()<255){al.get(i).setValoreG(al.get(i).getValoreG()+10);}else{al.get(i).setValoreG(10);}
            if(al.get(i).getValoreB()<255){al.get(i).setValoreB(al.get(i).getValoreB()+10);}else{al.get(i).setValoreB(10);}
        }
        return change_state_of_chanel(al, v);
    }
    
    
    /**
    * Increases the current RGB value of 50.  
    * <p>
    * This method takes the current RGB values for each light and
    * increases them by 50 until they reach 255
    * 
    * @param v          the array to be filled with the DMX string 
    * @param al         The array list containing the lights in your configuration
    * @return           Returns an array of short, the DMX string to be sent
    */    
    public static byte[] aumento50(ArrayList<Luce> al,byte v[]){
        for(int i=0;i<al.size();i++){
            if(al.get(i).getValoreR()<255){al.get(i).setValoreR(al.get(i).getValoreR()+50);}else{al.get(i).setValoreR(50);}
            if(al.get(i).getValoreG()<255){al.get(i).setValoreG(al.get(i).getValoreG()+50);}else{al.get(i).setValoreG(50);}
            if(al.get(i).getValoreB()<255){al.get(i).setValoreB(al.get(i).getValoreB()+50);}else{al.get(i).setValoreB(50);}
        }
        return change_state_of_chanel(al, v);
    }
   
    
    /**
    * Set every Light in the DMX chain at specific RGB values.  
    * <p>
    * This method takes the given RGB values and set every light on the DMX
    * chain at that specific color
    * 
    * @param al         The array list containing the lights in your configuration
    * @param v          the array to be filled with the DMX string 
    * @param r          The red component of the color to set
    * @param g          The green component of the color to set
    * @param b          The blue component of the color to be set
    * @return           Returns an array of short, the DMX string to be sent
    */
    public static byte[] Set_every_led_at_Color(ArrayList<Luce> al,byte v[],int r, int g, int b){
        for(int i=0;i<al.size();i++){
           al.get(i).setValoreR(r);
           al.get(i).setValoreG(g);
           al.get(i).setValoreB(b);
        }
        return change_state_of_chanel(al, v);
    }
    
    
    
    /**
    * This method sets random colors.  
    * <p>
    * This method every time is called sets random colors for each light
    * in the DMX chain
    * 
    * @param al         The array list containing the lights in your configuration
    * @param v          the array to be filled with the DMX string 
    * @return           Returns an array of short, the DMX string to be sent
    */
    public static byte[] random_psichedelico(ArrayList<Luce> al,byte v[]){
        int a=0;
        int i=(int) (Math.random()*al.size());
        
            a=(int) (Math.random()*255);
            al.get(i).setValoreR(a);
            a=(int) (Math.random()*255);
            al.get(i).setValoreG(a);
            a=(int)(Math.random()*255);
            al.get(i).setValoreB(a);
        
        return change_state_of_chanel(al, v);
    }

    public static int[] jump_over_the_rainbow(ArrayList<Luce> al,byte v[], int r, int g, int b){
        int vett[] = new int[3];
        int valore_r,valore_g,valore_b;
        valore_r=r;
        valore_g=g;
        valore_b=b;
             
             if(valore_r==255 && valore_g==0 && valore_b==0) status2=0;
             else if(valore_r==255 && valore_g==255 && valore_b==0) status2=1;
             else if(valore_r==0 && valore_g==222 && valore_b==0) status2=2;
             else if(valore_r==0 && valore_g==0 && valore_b==205) status2=3;
             
             
             switch (status2){
                case 0:
                    //17rip
                    if(valore_g<240)valore_g+=15; else valore_g=255;
                    break;
                case 1:
                    //17rip
                    if(valore_r>15)valore_r-=15; else valore_r=0;
                    if(valore_g>223) valore_g-=2; else valore_g=222;
                    break;
                case 2:
                    //10rip
                    if(valore_g>14) valore_g-=13; else valore_g=0;                   
                    if(valore_b<192) valore_b+=12; else valore_b=205;
                    break;
                case 3:
                    //17rip
                    if(valore_r<240)valore_r+=15; else valore_r=255;
                    if(valore_b>13) valore_b-=11; else valore_b=0;
                    break;
                case 4:
                    
                default:
                    valore_r=255;
                    valore_g=0;
                    valore_b=0;
                    break;
        }

             vett[0]=valore_r;
             vett[1]=valore_g;
             vett[2]=valore_b;
        
        
        
        
        
        return vett;
    }
    
    /**
    * This method turn on the next light in the chain and turn off the previous  
    * <p>
    * This method once called sets the next light to the given color and 
    * turns of the previous
    * 
    * @param al         The array list containing the lights in your configuration
    * @param v          the array to be filled with the DMX string 
    * @param r          The Red color value to be set
    * @param g          The Green color value to be set
    * @param b          The Blue color value to be set
    * @return           Returns an array of short, the DMX string to be sent
    */
    public static byte[] jump(ArrayList<Luce> al,byte v[],int r, int g, int b){
        if(status<al.size()-1 || status<0)status++; else status=0;
            v=Set_every_led_at_Color(al, v, 0, 0, 0);
            al.get(status).setValoreR(r);
            al.get(status).setValoreG(g);
            al.get(status).setValoreB(b);
        return change_state_of_chanel(al,v);
    }
    
    /**
    * This method create a rainbow effect with DMX lights.  
    * <p>
    * This method every time is called changes the current RGB value
    * of each light with the next one in the light spectrum
    * 
    * @param al         The array list containing the lights in your configuration
    * @param v          the array to be filled with the DMX string 
    * @return           Returns an array of short, the DMX string to be sent
    */
    public static byte[] over_the_rainbow(ArrayList<Luce> al,byte v[]){
        int valore_r,valore_g,valore_b;
             
        for(int i=0;i<al.size();i++){
             valore_r=al.get(i).getValoreR();
             valore_g=al.get(i).getValoreG();
             valore_b=al.get(i).getValoreB();
             
             if(valore_r==255 && valore_g==0 && valore_b==0) status=0;
             else if(valore_r==255 && valore_g==255 && valore_b==0) status=1;
             else if(valore_r==0 && valore_g==222 && valore_b==0) status=2;
             else if(valore_r==0 && valore_g==0 && valore_b==205) status=3;
             
             switch (status){
                case 0:
                    //17rip
                    if(valore_g<240)valore_g+=15; else valore_g=255;
                    break;
                case 1:
                    //17rip
                    if(valore_r>15)valore_r-=15; else valore_r=0;
                    if(valore_g>223) valore_g-=2; else valore_g=222;
                    break;
                case 2:
                    //10rip
                    if(valore_g>14) valore_g-=13; else valore_g=0;                   
                    if(valore_b<192) valore_b+=12; else valore_b=205;
                    break;
                case 3:
                    //17rip
                    if(valore_r<240)valore_r+=15; else valore_r=255;
                    if(valore_b>13) valore_b-=11; else valore_b=0;
                    break;
                case 4:
                    
                default:
                    valore_r=255;
                    valore_g=0;
                    valore_b=0;
                    break;
        }

             al.get(i).setValoreR(valore_r);
             al.get(i).setValoreG(valore_g);
             al.get(i).setValoreB(valore_b);
             
        }
        
        return change_state_of_chanel(al, v);
    }
    
    /**
    * This method sets every light at white colour.  
    * <p>
    * This method once called sets every light at Maximum value of RGB without modifying
    * the currenRGB values
    * 
    * @param al         The array list containing the lights in your configuration
    * @param v          the array to be filled with the DMX string 
    * @return           Returns an array of short, the DMX string to be sent
    */
    public static byte[] switch_on(ArrayList<Luce> al,byte v[]){
        ArrayList <Luce> al_temp=(ArrayList <Luce>) al.clone();
        for(int i=0;i<al.size();i++){
            al_temp.get(i).setValoreR(255);
            al_temp.get(i).setValoreG(255);
            al_temp.get(i).setValoreB(255);
        }
        
        return change_state_of_chanel(al_temp,v);
    }
    
    
    /**
    * This method makes a visor effect with the chosen colour.  
    * <p>
    * This method once called sets the next light in the DMX chain with a
    * brighter colour tone the the previous one
    * 
    * @param al         The array list containing the lights in your configuration
    * @param v          the array to be filled with the DMX string 
    * @param color      Unused Field
    * @param valore_r   The amount of Red to chose what colour to display
    * @param valore_g   The amount of Green to chose what colour to display
    * @param valore_b   The amount of Blue to chose what colour to display
    * @return           Returns an array of short, the DMX string to be sent
    */
    public static byte[] psycho_delico_line(ArrayList<Luce> al,byte v[],char color, int valore_r,int valore_g, int valore_b){
        //sfuman ogni luce aumentando la frequenza di quel colore(r,g,b) partendo da un'estremo arrivando all'altro
        //alla riga successiva della chiamata di questa funzione nel thread bisogna richiamare 
        // --> Set_every_fucking_led_at_Color(al,v,0,0,0);
       int step=255/al.size();
       if(status<al.size()-1 || status<0)status++; else status=0;
            
            switch (color){
                case 'r':
                   valore_r=al.get(status).getValoreR();
                    valore_r+=step;
                    valore_g=0;
                    valore_b=0;
                    
                      break;
                case 'g':
                    valore_g=al.get(status).getValoreG();
                    valore_g+=step;
                    valore_r=0;
                    valore_b=0;
                     
                      break;  
                case 'b':
                    valore_b=al.get(status).getValoreB();
                    valore_b+=step;
                    valore_r=0;
                    valore_g=0;
                    
                      break;
            
        }
            al.get(status).setValoreR(valore_r);
            al.get(status).setValoreG(valore_g);
            al.get(status).setValoreB(valore_b);
        return change_state_of_chanel(al,v);
    }
    
    /**
    * This method makes a visor effect with the chosen colour.  
    * <p>
    * This method once called sets the next lights from the beginning and the end
    * of the DMX chain to a brighter colour tone the the previous one
    * 
    * @param al         The array list containing the lights in your configuration
    * @param v          the array to be filled with the DMX string 
    * @return           Returns an array of short, the DMX string to be sent
    */
    public static byte[] psycho_delico_double_line(ArrayList<Luce> al, byte v[]){
        //sfuma ogni luce aumentando la frequenza di quel colore(r,g,b) partendo dai due estremi e arrivando in mezzo
        //alla riga successiva della chiamata di questa funzione nel thread bisogna richiamare
        int size=al.size()/2;
        
       if(status3<=0 && status4>=al.size()){status=0; status3=0;status4=al.size()-1;}
        if((status3>size && status4<size) || (status3==size && status4==size+1) || (status3==size-1 && status4==size) ){ status=1;status3=status4=size;}
        
        switch (status){
            case 0:
                al.get(status3).setValoreR(255);
                al.get(status4).setValoreR(255);
                status3++;
                status4--;
                break;
            case 1:
                al.get(status3).setValoreR(0);
                al.get(status4).setValoreR(0);
                status4++;
                status3--;                
                break;
            default:
                v=Set_every_led_at_Color(al, v, 0, 0, 0);
        }
        return change_state_of_chanel(al,v);
    }
 
    /**
    * This method make a fade effect on the DMX Lights chain.  
    * <p>
    * This method once called increases or decreases the RGB values of a light
    * n times
    * 
    * @param al         The array list containing the lights in your configuration
    * @param v          the array to be filled with the DMX string 
    * @param changing   TRUE=Increase FALSE=decrease
    * @param quantity   How many times does the colour should be augmented
    * @return           Returns an array of short, the DMX string to be sent
    */
    public static byte[]sfuma(ArrayList<Luce> al,byte v[], char color, boolean changing, int quantity){
      //incrementa,decrementa quantity volte il canale rgb changing, simile a psycho_delico_double_line(...);
      //questa funzione deve essere chiamata in un thread in un for(int i=0;i<quantity/2;i++)
      //alla riga successiva del for(...) thread bisogna richiamare 
      // --> Set_every_fucking_led_at_Color(al,v,0,0,0);
        for(int i=0;i<al.size();i++){
            
            switch (color){
                case 'r':
                    int valore_r=al.get(i).getValoreR();
                        if(changing==true){
                            valore_r+=quantity;
                        }else{
                            valore_r-=quantity;
                        }
                        al.get(i).setValoreR(valore_r);
                      break;
                case 'g':
                    int valore_g=al.get(i).getValoreG();
                        if(changing==true){
                            valore_g+=quantity;
                        }else{
                            valore_g-=quantity;
                        }
                        al.get(i).setValoreG(valore_g);
                      break;  
                case 'b':
                    int valore_b=al.get(i).getValoreB();
                        if(changing==true){
                            valore_b+=quantity;
                        }else{
                            valore_b-=quantity;
                        }
                        al.get(i).setValoreB(valore_b);
                      break;

                    }

                 
           }
        return change_state_of_chanel(al,v);
    }
    
    
    
    /**
    * This method sets the given lamp at the given colour 
    * <p>
    * This method once called sets given lamp in the lights array
    * at the given RGB colour
    * 
    * @param al         The array list containing the lights in your configuration
    * @param v          the array to be filled with the DMX string 
    * @param r          The Red colour value to be set
    * @param g          The Green colour value to be set
    * @param b          The Blue colour value to be set
    * @param index      The index of the light to set
    * @return           Returns an array of short, the DMX string to be sent
    */
    public static byte[] set_only_one_lamp_at_color (ArrayList<Luce> al,byte v[],int r, int g, int b, int index){
        for(int i=0;i<al.size();i++){
            if(i==index){
                al.get(i).setValoreR(r);
                al.get(i).setValoreG(g);
                al.get(i).setValoreB(b);
            }
            else{
                al.get(i).setValoreR(0);
                al.get(i).setValoreG(0);
                al.get(i).setValoreB(0);
            }
        }
        return change_state_of_chanel(al,v);
    }
    
    
    /**
    * This method fills the short array.  
    * <p>
    * This method once called fills the short array with RGB values
    * 
    * @param al         The array list containing the lights in your configuration
    * @param v          the array to be filled with the DMX string 
    * @return           Returns an array of short, the DMX string to be sent
    */
    public static byte[] change_state_of_chanel(ArrayList<Luce> al, byte v[]){
        int canale_r,canale_g,canale_b;
        int valore_r,valore_g,valore_b;
        for(int i=0;i<al.size();i++){
             canale_r=al.get(i).getCanaleR();
             canale_g=al.get(i).getCanaleG();
             canale_b=al.get(i).getCanaleB();
             valore_r=al.get(i).getValoreR();
             valore_g=al.get(i).getValoreG();
             valore_b=al.get(i).getValoreB();
             
             v[canale_r-1]=(byte) valore_r;
             v[canale_g-1]=(byte) valore_g;
             v[canale_b-1]=(byte) valore_b;
        }
        //System.out.println(Arrays.toString(v));
        return v;
    }
}
