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

import Control.EditListener;
import Control.ListenerRadio;
import Control.ListenerStart;
import Control.MenuListener;
import Control.MoveListener;
import Control.SliderListener;
import Control.listenerBottoni;
import Control.listenerCombo;
import Control.listenerWindow;
import Model.Connessione.Connection;
import Model.GestioneLuci;
import Model.SaveManager;
import View.Aggiunta.FrameAggiunta;
import View.Aggiunta.PannelloAggiunta;
import View.Impostazioni.FrameImpostazioni;
import View.MainFrame;
import View.MainPannello;
import View.Modifica.FrameModifica;
import View.Modifica.PannelloModifica;
import View.PannelloLuci;
import java.awt.HeadlessException;
import static java.lang.System.exit;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Filippo Stella
 * @version 1.5
 */
public class Main {
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            if((System.getProperty("os.name").toLowerCase()).contains("windows") || (System.getProperty("os.name").toLowerCase()).contains("mac") || (System.getProperty("os.name").toLowerCase()).contains("linux")){
                if(System.getProperty("java.version").startsWith("1.1.") || System.getProperty("java.version").startsWith("1.2.")|| System.getProperty("java.version").startsWith("1.3.")|| System.getProperty("java.version").startsWith("1.4.")|| System.getProperty("java.version").startsWith("1.5.")|| System.getProperty("java.version").startsWith("1.6.")){
                    JOptionPane.showMessageDialog(null, "Java version: "+System.getProperty("java.version")+" is not supported.\nPlease upgrade your java enviroment", "Errore", JOptionPane.ERROR_MESSAGE);
                    System.err.println("Java version: "+System.getProperty("java.version")+" is not supported.\nPlease upgrade your java enviroment");
                    exit(5);   
                }
                else{
                    SaveManager sm=new SaveManager();
                    FrameImpostazioni fi=new FrameImpostazioni();
                    GestioneLuci gl=new GestioneLuci(fi);
                    MainPannello mp=new MainPannello();
                    PannelloLuci pl=new PannelloLuci(gl);
                    MainFrame mf=new MainFrame(gl, pl, mp);
                    PannelloAggiunta pa=new PannelloAggiunta();
                    FrameAggiunta fa=new FrameAggiunta(mf,pa);
                    PannelloModifica pm=new PannelloModifica();
                    FrameModifica fm=new FrameModifica(mf, pm);
                    Connection cn=new Connection(mf);
                    listenerBottoni lb=new listenerBottoni(mf, fa, fm, fi, pa, pm, pl, mp, gl, cn);
                    ListenerStart ls=new ListenerStart(pa, pm);
                    listenerCombo lc=new listenerCombo(pa, pm, gl);
                    listenerWindow lw=new listenerWindow(fa, mf, pl, sm, fi, gl, cn);
                    MoveListener mol=new MoveListener(gl, pl);
                    MenuListener ml=new MenuListener(mf, pl, fa, cn, fi);
                    EditListener el=new EditListener(pl, gl, fm);
                    ListenerRadio lr=new ListenerRadio(mp, gl);
                    SliderListener sl=new SliderListener(mf, mp);
                    if(sm.read()){
                        fi.setIP(sm.getIP());
                        fi.getPORT().setText(Integer.toString(sm.getPort()));
                        gl.setArray(sm.getAl());
                        fi.getDELAY().setSelected(sm.getDelay());
                        fi.getSORT().setSelected(sm.getIsSort());
                        pl.updatePanel();
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Operating System NOT Supported", "Errore", JOptionPane.ERROR_MESSAGE);
                System.err.println("Operating System NOT Supported");
                exit(6);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ParseException | HeadlessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            exit(1);
        } catch (Exception ex) {           
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }           
    }
    
}

/*
Error codes:
1.  Error during initzialization
2.  Error while restoring saves from file
3.  Error while saving settings of the application
4.  Error while terminating a running Effect Thread
5.  Java version not supported (below 1.7.x)
6.  Operating System Not supported yet
*/
