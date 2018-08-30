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

import Control.EditListener;
import Control.MoveListener;
import Model.GestioneLuci;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Filippo Stella
 * @version 1.0
 */
public class PannelloLuci extends JPanel{

    private static final long serialVersionUID = 1L;
    private EditListener el=null;
    private MoveListener ml=null;
    private final ArrayList<JLabel> luci;
    private final ArrayList<JButton> edit;
    private final ArrayList<JButton> bUp;
    private final ArrayList<JButton> bDown;
    private final GestioneLuci gl;
    private final JButton b_aggiungi;
    private boolean firstRun=true;
    
    public PannelloLuci(GestioneLuci gl) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.gl = gl;
        this.luci=new ArrayList<>();
        this.edit=new ArrayList<>();
        this.b_aggiungi=new JButton("Aggiungi Luce");
        this.bUp=new ArrayList<>();
        this.bDown=new ArrayList<>();
    }

    public void setMl(MoveListener ml) {
        if(this.ml==null){
            this.ml = ml;
        }
    }
    public void setEl(EditListener el){
        if(this.el==null)
            this.el = el;
    }
    
    
    public Integer getSizeOfButton(){
        return this.edit.size();
    }    
    public Integer getSizeOfbUp(){
        return this.bUp.size();
    }
    public Integer getSizeOfbDown(){
        return this.bDown.size();
    }
    public JButton getB_aggiungi() {
        return this.b_aggiungi;
    }
   
    public void updatePanel(){
        this.clearPanel();
        this.generaComponenti();
        this.el.addListener();
        this.ml.addListener();
        this.aggiungiComponenti();
        this.revalidate();
        this.repaint();
    }

    private void clearPanel(){
        this.removeAll();
        this.revalidate();
        this.repaint();
    }
   
    public JButton getEdit (Integer i){
        return this.edit.get(i);
    }
    public JButton getbUp (Integer i){
        return this.bUp.get(i);
    }
    public JButton getbDown (Integer i){
        return this.bDown.get(i);
    }
    
    private void aggiungiComponenti(){
        Box box,box2;
        for(int i=0;i<this.luci.size() && i<this.edit.size();i++){
            box= Box.createHorizontalBox();
            box2=Box.createVerticalBox();
            this.bUp.get(i).setBorder(new EmptyBorder(5, 6, 5, 6));
            if(i>0){
                box2.add(this.bUp.get(i));
            }
            this.bDown.get(i).setBorder(new EmptyBorder(5, 6, 5, 6));
            if(i<this.gl.getSize()-1){
                box2.add(this.bDown.get(i));
            }
            box.add(box2);
            box.add(Box.createRigidArea(new Dimension(10, 0)));
            box.add(this.luci.get(i));
            box.add(Box.createRigidArea(new Dimension(10, 0)));
            box.add(this.edit.get(i));
            box.setBorder(new EmptyBorder(8,8,8,8));
            this.add(box);
        }
        box= Box.createHorizontalBox();
        box.add(this.b_aggiungi);
        box.setBorder(new EmptyBorder(10,10,10,10));
        this.add(box);
    }
    
    private void generaComponenti(){
        this.luci.clear();
        this.edit.clear();
        this.bUp.clear();
        this.bDown.clear();
        for(int i=0;i<this.gl.getSize();i++){
            this.luci.add(new JLabel("Luce "+this.gl.getLuce(i).getTipo()+" (Start:"+this.gl.getLuce(i).getStart().toString()+")"));
            this.edit.add(new JButton("Edit"));
            this.bUp.add(new JButton("▲"));
            this.bDown.add(new JButton("▼"));
        }
    }

    public void listenerMove(ActionListener al){
        if(this.ml!=null){
            for(int i=0;i<this.bDown.size() && i<this.bUp.size();i++){
                this.bUp.get(i).addActionListener(al);
                this.bDown.get(i).addActionListener(al);
            }
        }
    }
    
    public void listenerEdit(ActionListener al){
        if(this.el!=null){
            for(int i=0;i<this.edit.size();i++){
                this.edit.get(i).addActionListener(al);
            }
        }
    }
    
    public void listenerAggiungi(ActionListener al){
        this.b_aggiungi.addActionListener(al);
    }
    
}
