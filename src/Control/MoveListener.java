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

import Model.GestioneLuci;
import View.PannelloLuci;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Filippo Stella
 * @version 1.0
 */
public class MoveListener implements ActionListener{
    
    private boolean done;
    private final GestioneLuci gl;
    private final PannelloLuci pl;

    public MoveListener(GestioneLuci gl, PannelloLuci pl) {
        this.gl = gl;
        this.pl = pl;
        this.pl.setMl(this);
        this.done=false;
    }

    public void addListener(){
        this.pl.listenerMove(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0;i<this.pl.getSizeOfbUp() && i<this.pl.getSizeOfbDown();i++){
            if(e.getSource()==this.pl.getbUp(i)){
                this.done=true;
                System.out.println("Up "+i);
                this.gl.swapPrevious(i);;
                this.pl.updatePanel();
                return;
            }
            else if(e.getSource()==this.pl.getbDown(i)){
                this.done=true;
                System.out.println("Down "+i);
                this.gl.swapNext(i);
                this.pl.updatePanel();
                return;
            }
            else{
                this.done=false;
            }
        }
        
        if(this.done==false){
            throw new UnsupportedOperationException("Not supported yet.");
        }
        else{
            this.done=false;
        }
    }


}
