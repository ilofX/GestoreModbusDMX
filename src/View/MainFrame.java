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
package View;

import Model.GestioneLuci;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;

/**
 *
 * @author Filippo Stella
 * @version 1.0
 */
public class MainFrame extends JFrame {
    
    private JMenuBar menuBar;
    private JMenu m_Nuovo,m_impostazioni,m_questionmark;
    private JCheckBoxMenuItem cbi_connect,cbi_modifica;
    private JMenuItem i_NuovaLampada,i_impostazioni,i_info;
    private JSplitPane sPanel;
    private JScrollPane spPanel;
    
    public MainFrame(GestioneLuci gl, PannelloLuci pl, MainPannello mp) {
        super("GestioneModbusDMX");
        this.setIconImage(new ImageIcon(this.getClass().getResource("/Icon/Stage.png")).getImage());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.generasPanel(gl, pl, mp);
        this.setResizable(true);
        this.setLocationByPlatform(true);
        this.setContentPane(this.sPanel);
        this.generaMenu();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JCheckBoxMenuItem getCbi_connect() {
        return this.cbi_connect;
    }
    public JMenuItem getI_NuovaLampada() {
        return this.i_NuovaLampada;
    }
    public JMenuItem getI_impostazioni() {
        return this.i_impostazioni;
    }
    public JMenuItem getI_info() {
        return this.i_info;
    }
    public JCheckBoxMenuItem getCbi_modifica() {
        return this.cbi_modifica;
    }
    
    private void generasPanel(GestioneLuci gl, PannelloLuci pl, MainPannello mp){
        this.spPanel=new JScrollPane(pl);
        this.spPanel.setMinimumSize(new Dimension(250, 350));
        this.spPanel.setMaximumSize(new Dimension(250, 350));
        this.spPanel.setPreferredSize(new Dimension(250, 350));
        this.sPanel=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, this.spPanel, mp);
        this.sPanel.setDividerSize(4);
    }
        
    private void generaMenu(){
        this.menuBar= new JMenuBar();
        this.m_Nuovo=new JMenu("Nuovo");
        this.i_NuovaLampada=new JMenuItem("Lampada");
        this.m_Nuovo.add(this.i_NuovaLampada);
        this.m_impostazioni=new JMenu("Impostazioni");
        this.i_impostazioni=new JMenuItem("Impostazioni");
        this.cbi_connect=new JCheckBoxMenuItem("Connetti");
        this.cbi_modifica=new JCheckBoxMenuItem("Abilita Modifica");
        this.m_impostazioni.add(this.i_impostazioni);
        this.m_impostazioni.add(new JSeparator(JSeparator.HORIZONTAL));
        this.m_impostazioni.add(this.cbi_connect);
        //this.m_impostazioni.add(this.cbi_modifica);
        this.m_questionmark=new JMenu("?");
        this.i_info=new JMenuItem("Info");
        this.m_questionmark.add(this.i_info);
        this.menuBar.add(this.m_Nuovo);
        this.menuBar.add(this.m_impostazioni);
        this.menuBar.add(this.m_questionmark);
        this.setJMenuBar(this.menuBar);
    }
    
    public void MenuListener(ActionListener al){
        this.i_NuovaLampada.addActionListener(al);
        this.i_impostazioni.addActionListener(al);
        this.i_info.addActionListener(al);
        this.cbi_connect.addActionListener(al);
        this.cbi_modifica.addActionListener(al);
    }
    
    public void frameListener(WindowListener wl){
        this.addWindowListener(wl);
    }
    
}
