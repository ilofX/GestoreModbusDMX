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

import View.MainFrame;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Filippo Stella
 * @version 1.0
 */
public class FrameAggiunta extends JFrame{

    public FrameAggiunta(MainFrame mf,PannelloAggiunta pa) {
        super("Nuova Lampada");
        this.setIconImage(new ImageIcon(this.getClass().getResource("/Icon/Stage.png")).getImage());
        this.setContentPane(pa);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        this.setMinimumSize(new Dimension(320, 290));
        this.setMaximumSize(new Dimension(320, 290));
        this.setPreferredSize(new Dimension(320, 290));
        this.setResizable(true);
        this.pack();
        this.setVisible(false);
    }
    
    public FrameAggiunta(PannelloAggiunta pa) {
        super("Nuova Lampada");
        this.setContentPane(pa);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(320, 290));
        this.setMaximumSize(new Dimension(320, 290));
        this.setPreferredSize(new Dimension(320, 290));
        this.setResizable(false);        
        this.pack();
        this.setVisible(this.rootPaneCheckingEnabled);
    }
    
    public void changeName(String name){
        this.setName(name);
    }   
    
    public void listenerWindow(WindowListener wl){
        this.addWindowListener(wl);
    }
    
    
}
