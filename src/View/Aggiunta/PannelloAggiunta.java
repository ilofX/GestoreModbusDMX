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
package View.Aggiunta;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Filippo Stella
 * @version 1.0
 */
public class PannelloAggiunta extends JPanel{
    
    private JLabel l_tipo,l_start,l_canali,l_canaleR,l_canaleG,l_canaleB;
    private JSpinner s_canali,s_start,s_canaleR,s_canaleG,s_canaleB;
    private JComboBox cb_tipo;
    private JButton b_ok,b_annulla;
    private Vector<String> strings;

    public PannelloAggiunta() {
        this.setLayout(null);
        this.buildComponenti();
        this.setComponenti();
        this.addComponenti();
    }
    
    private void buildComponenti(){
        this.l_tipo=new JLabel("Tipo Lampada");
        this.l_start=new JLabel("Primo canale utilizzato");
        this.l_canali=new JLabel("Numero Canali Utilizzati");
        this.l_canaleR=new JLabel("Canale R");
        this.l_canaleG=new JLabel("Canale G");
        this.l_canaleB=new JLabel("Canale B");
        this.strings=new Vector<>();
        this.strings.add("-----------------------");
        this.strings.add("RGB");
        this.strings.add("Generic Lamp");
        this.strings.add("Karma DJ359LED");
        this.cb_tipo=new JComboBox<>(this.strings);
        this.s_canali=new JSpinner();
        this.s_canali.setModel(new SpinnerNumberModel(0, 0, 512, 1));
        this.s_canali.setEnabled(false);
        this.s_start=new JSpinner();
        this.s_start.setEnabled(false);
        this.s_canaleR=new JSpinner();
        this.s_canaleR.setModel(new SpinnerNumberModel(0, 0, 255, 1));
        this.s_canaleR.setEnabled(false);
        this.s_canaleG=new JSpinner();
        this.s_canaleG.setModel(new SpinnerNumberModel(0, 0, 255, 1));
        this.s_canaleG.setEnabled(false);
        this.s_canaleB=new JSpinner();
        this.s_canaleB.setModel(new SpinnerNumberModel(0, 0, 255, 1));
        this.s_canaleB.setEnabled(false);
        this.b_annulla=new JButton("Annulla");
        this.b_ok=new JButton("Aggiungi");
        this.b_ok.setEnabled(false);
    }
    
    private void setComponenti(){
        this.l_tipo.setBounds(20, 10, 150, 20);
        this.cb_tipo.setBounds(180, 10, 120, 20);
        this.l_canali.setBounds(20, 40, 150, 20);
        this.s_canali.setBounds(180, 40, 120, 20);
        this.l_start.setBounds(20, 70, 150, 20);
        this.s_start.setBounds(180, 70, 120, 20);
        this.l_canaleR.setBounds(60, 120, 60, 20);
        this.s_canaleR.setBounds(130, 120, 60, 20);
        this.l_canaleG.setBounds(60, 150, 60, 20);
        this.s_canaleG.setBounds(130, 150, 60, 20);
        this.l_canaleB.setBounds(60, 180, 60, 20);
        this.s_canaleB.setBounds(130, 180, 60, 20);
        this.b_ok.setBounds(50, 220, 90, 20);
        this.b_annulla.setBounds(170, 220, 90, 20);
    }
    
    private void addComponenti(){
        this.add(this.l_tipo);
        this.add(this.cb_tipo);
        this.add(this.l_canali);
        this.add(this.s_canali);
        this.add(this.l_start);
        this.add(this.s_start);
        this.add(this.l_canaleR);
        this.add(this.s_canaleR);
        this.add(this.l_canaleG);
        this.add(this.s_canaleG);
        this.add(this.l_canaleB);
        this.add(this.s_canaleB);
        this.add(this.b_annulla);
        this.add(this.b_ok);
    }
    
    public void listenerStart(ChangeListener cl){
        this.s_start.addChangeListener(cl);
    }
    
    public void listenerBottoni(ActionListener al){
        this.b_annulla.addActionListener(al);
        this.b_ok.addActionListener(al);
    }
    
    public void listenerCombo(ItemListener il){
        this.cb_tipo.addItemListener(il);
    }

    public void init(){
        this.cb_tipo.setSelectedIndex(0);
        this.s_start.setValue(0);
        this.s_canali.setValue(0);
        this.s_canaleR.setValue(0);
        this.s_canaleG.setValue(0);
        this.s_canaleB.setValue(0);
    }
    
    public JSpinner getS_canali() {
        return this.s_canali;
    }
    public JSpinner getS_start() {
        return this.s_start;
    }
    public JSpinner getS_canaleR() {
        return this.s_canaleR;
    }
    public JSpinner getS_canaleG() {
        return this.s_canaleG;
    }
    public JSpinner getS_canaleB() {
        return this.s_canaleB;
    }
    public JComboBox getCb_tipo() {
        return this.cb_tipo;
    }
    public JButton getB_ok() {
        return this.b_ok;
    }
    public JButton getB_annulla() {
        return this.b_annulla;
    }
    
}
