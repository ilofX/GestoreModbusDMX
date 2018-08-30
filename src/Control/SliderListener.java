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
package Control;

import View.MainFrame;
import View.MainPannello;
import java.awt.Color;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 *
 * @author Filippo Stella
 * @version 1.0
 */
public class SliderListener implements ChangeListener{
    
    private final MainFrame mf;
    private final MainPannello mp;
    
    /**
    * Contructor for the Slider Listener
    * <p>
    * This method is the constructor for the Slider Listener
    * it creates the behavior of the RGB Sliders
    * 
    * @param mf     The MainFrame the main frame, this is the main interface of this program
    * @param mp     The MainPannello this panel contains the mode selection, the rgb slider, the delay selector and the apply button
    */
    public SliderListener(MainFrame mf, MainPannello mp) {
        this.mf = mf;
        this.mp = mp;
        this.mp.listenerSlider(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if((e.getSource()==this.mp.getSl_r()) || (e.getSource()==this.mp.getSl_g()) || (e.getSource()==this.mp.getSl_b())){
            this.mp.getTf_color().setBackground(new Color(this.mp.getSl_r().getValue(), this.mp.getSl_g().getValue(), this.mp.getSl_b().getValue()));
        }
        else{
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }


}
