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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Filippo Stella
 * @version 1.0
 */
public class MainPannello extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private JRadioButton r_rainbow,r_random,r_solid,r_jump,r_rotate,r_sfuma,r_jumpRainbow,r_kit,r_auto;
    private ButtonGroup radioGroup;
    private JSlider sl_r,sl_g,sl_b;
    private JTextField tf_color;
    private JSpinner sp_time;
    private JLabel l_R,l_G,l_B,l_time,l_effects;
    private JButton b_applica;

    public MainPannello() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.buildComponent();
        this.addComponent();
    }

    private void buildComponent(){
        this.l_effects=new JLabel("Modalità");
        this.l_effects.setMinimumSize(new Dimension(40, 20));
        this.l_effects.setMaximumSize(new Dimension(40, 20));
        this.l_effects.setPreferredSize(new Dimension(40, 20));
        this.r_random=new JRadioButton("Random Colors");
        this.r_rainbow=new JRadioButton("Rainbow Effect");
        this.r_solid=new JRadioButton("Solid Color");
        this.r_jump=new JRadioButton("Jump Effect");
        this.r_rotate=new JRadioButton("Running Lights");
        this.r_sfuma=new JRadioButton("Fading Lights");
        this.r_jumpRainbow=new JRadioButton("Rainbow Jump");
        this.r_kit=new JRadioButton("Supercar Visor");
        this.r_auto=new JRadioButton("Auto");
        this.radioGroup=new ButtonGroup();
        this.radioGroup.add(this.r_solid);
        this.radioGroup.add(this.r_rainbow);
        this.radioGroup.add(this.r_random);
        this.radioGroup.add(this.r_jump);
        this.radioGroup.add(this.r_rotate);
        this.radioGroup.add(this.r_sfuma);
        this.radioGroup.add(this.r_jumpRainbow);
        this.radioGroup.add(this.r_kit);
        this.radioGroup.add(this.r_auto);
        this.l_time=new JLabel("Delay:");
        this.l_time.setMinimumSize(new Dimension(40, 20));
        this.l_time.setMaximumSize(new Dimension(40, 20));
        this.l_time.setPreferredSize(new Dimension(40, 20));
        this.sp_time=new JSpinner();
        this.sp_time.setMinimumSize(new Dimension(80, 20));
        this.sp_time.setMaximumSize(new Dimension(80, 20));
        this.sp_time.setPreferredSize(new Dimension(80, 20));
        this.sp_time.setModel(new SpinnerNumberModel(50, 50, 50000, 1));
        this.sp_time.setEnabled(false);
        this.l_R=new JLabel("Red");
        this.l_G=new JLabel("Green");
        this.l_B=new JLabel("Blue");
        this.sl_r=new JSlider(JSlider.VERTICAL, 0, 255, 0);
        this.sl_g=new JSlider(JSlider.VERTICAL, 0, 255, 0);
        this.sl_b=new JSlider(JSlider.VERTICAL, 0, 255, 0);
        this.sl_r.setMajorTickSpacing(255);
        this.sl_r.setMinorTickSpacing(51);
        this.sl_r.setPaintLabels(true);
        this.sl_r.setPaintTicks(true);
        this.sl_r.setPaintTrack(true);
        this.sl_r.setEnabled(false);
        this.sl_g.setMajorTickSpacing(255);
        this.sl_g.setMinorTickSpacing(51);
        this.sl_g.setPaintLabels(true);
        this.sl_g.setPaintTicks(true);
        this.sl_g.setPaintTrack(true);
        this.sl_g.setEnabled(false);
        this.sl_b.setMajorTickSpacing(255);
        this.sl_b.setMinorTickSpacing(51);
        this.sl_b.setPaintLabels(true);
        this.sl_b.setPaintTicks(true);
        this.sl_b.setPaintTrack(true);
        this.sl_b.setEnabled(false);
        this.tf_color=new JTextField();
        this.tf_color.setMinimumSize(new Dimension(50, 100));
        this.tf_color.setMaximumSize(new Dimension(50, 100));
        this.tf_color.setPreferredSize(new Dimension(50, 100));
        this.tf_color.setEditable(false);
        this.tf_color.setBackground(Color.BLACK);
        this.b_applica=new JButton("Applica");
        this.b_applica.setMinimumSize(new Dimension(80, 20));
        this.b_applica.setMaximumSize(new Dimension(80, 20));
        this.b_applica.setPreferredSize(new Dimension(80, 20));
        this.b_applica.setEnabled(false);
    }
    
    private void addComponent(){
        Box box = Box.createHorizontalBox();
        box.add(this.l_effects);
        box.setBorder(new javax.swing.border.EmptyBorder(8, 8, 8, 0));
        this.add(box);
        
        box = Box.createHorizontalBox();
        box.add(this.r_solid);
        box.add(this.r_rainbow);
        box.add(this.r_random);
        box.setBorder(new javax.swing.border.EmptyBorder(8,8,8,8));
        this.add(box);
        box = Box.createHorizontalBox();
        box.add(this.r_jump);
        box.add(this.r_rotate);
        box.add(this.r_sfuma);
        box.setBorder(new javax.swing.border.EmptyBorder(8,8,8,8));
        this.add(box);
        box = Box.createHorizontalBox();
        box.add(this.r_jumpRainbow);
        box.add(this.r_kit);
        box.add(this.r_auto);
        this.add(box);
        
        box = Box.createHorizontalBox();
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(new JSeparator(JSeparator.HORIZONTAL));
        box.setBorder(new javax.swing.border.EmptyBorder(2,0,0,2));
        this.add(box);
        
        box = Box.createHorizontalBox();
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(this.l_time);
        box.add(this.sp_time);
        box.setBorder(new javax.swing.border.EmptyBorder(8,8,8,8));
        this.add(box);
        
        box = Box.createHorizontalBox();
        Box box2 = Box.createVerticalBox();
        box2.add(this.sl_r);
        box.add(Box.createRigidArea(new Dimension(0, 5)));
        box2.add(this.l_R);
        box.add(box2);
        box2 = Box.createVerticalBox();
        box2.add(this.sl_g);
        box.add(Box.createRigidArea(new Dimension(0, 5)));
        box2.add(this.l_G);
        box.add(box2);
        box2 = Box.createVerticalBox();
        box2.add(this.sl_b);
        box.add(Box.createRigidArea(new Dimension(0, 5)));
        box2.add(this.l_B);
        box.add(box2);
        box.add(Box.createRigidArea(new Dimension(20, 0)));
        box.add(this.tf_color);
        box.setBorder(new javax.swing.border.EmptyBorder(8,8,8,8));
        this.add(box);
        
        box = Box.createHorizontalBox();
        box.add(this.b_applica);
        box.setBorder(new javax.swing.border.EmptyBorder(8,8,8,8));
        this.add(box);
    }

    public JRadioButton getR_rainbow() {
        return this.r_rainbow;
    }
    public JRadioButton getR_random() {
        return this.r_random;
    }
    public JRadioButton getR_solid() {
        return this.r_solid;
    }
    public JRadioButton getR_jump() {
        return this.r_jump;
    }
    public JRadioButton getR_rotate() {
        return this.r_rotate;
    }
    public JRadioButton getR_sfuma() {
        return this.r_sfuma;
    }
    public JButton getB_applica() {
        return this.b_applica;
    }
    public JSlider getSl_r() {
        return this.sl_r;
    }
    public JSlider getSl_g() {
        return this.sl_g;
    }
    public JSlider getSl_b() {
        return this.sl_b;
    }
    public JTextField getTf_color() {
        return this.tf_color;
    }
    public JSpinner getSp_time() {
        return this.sp_time;
    }
    public JRadioButton getR_jumpRainbow() {
        return this.r_jumpRainbow;
    }
    public JRadioButton getR_kit() {
        return this.r_kit;
    }
    public JRadioButton getR_auto() {
        return this.r_auto;
    }
           
    public void listenerRadio(ActionListener al){
        this.r_solid.addActionListener(al);
        this.r_rainbow.addActionListener(al);
        this.r_random.addActionListener(al);
        this.r_jump.addActionListener(al);
        this.r_rotate.addActionListener(al);
        this.r_sfuma.addActionListener(al);
        this.r_jumpRainbow.addActionListener(al);
        this.r_kit.addActionListener(al);
        this.r_auto.addActionListener(al);
    }
    
    public void listenerSlider(ChangeListener cl){
        this.sl_r.addChangeListener(cl);
        this.sl_g.addChangeListener(cl);
        this.sl_b.addChangeListener(cl);
    }
    
    public void listenerBottoni(ActionListener al){
        this.b_applica.addActionListener(al);
    }
      
}
