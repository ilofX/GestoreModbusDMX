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

/**
 *
 * @author Filippo Stella
 * @version 1.0
 */
public class Luce {
    
    private String tipo;
    private Integer nCanali;
    private Integer start;
    private Integer canaleR,canaleG,canaleB;
    private Integer valoreR,valoreG,valoreB;
     
    /**
    * This is the default contructo for a Luce
    * <p>
    * This constructor creates a new light and sets all of its parameters
    * 
    * @param start      This is the start DMX channel for this light
    * @param nCanali    This is the number of DMX channel s occupied by this light
    * @param tipo       The type of light you are adding
    * @param canaleR    The Red chanel of the lamp you are setting
    * @param canaleG    The Green chanel of the lamp you are setting
    * @param canaleB    The Blue channel of the lamp you are setting
    */
    public Luce(Integer start, Integer nCanali, String tipo, Integer canaleR, Integer canaleG, Integer canaleB) {
        this.tipo=tipo;
        this.start=start;
        this.nCanali=nCanali;
        this.canaleR=canaleR;
        this.canaleG=canaleG;
        this.canaleB=canaleB;
        this.valoreR=0;
        this.valoreG=0;
        this.valoreB=0;
    }

    public String getTipo() {
        return this.tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Integer getnCanali() {
        return this.nCanali;
    }
    public void setnCanali(Integer nCanali) {
        this.nCanali = nCanali;
    }
    public Integer getStart() {
        return this.start;
    }
    public void setStart(Integer start) {
        this.start = start;
    }
    public Integer getCanaleR() {
        return this.canaleR;
    }
    public void setCanaleR(Integer canaleR) {
        this.canaleR = canaleR;
    }
    public Integer getCanaleG() {
        return this.canaleG;
    }
    public void setCanaleG(Integer canaleG) {
        this.canaleG = canaleG;
    }
    public Integer getCanaleB() {
        return this.canaleB;
    }
    public void setCanaleB(Integer canaleB) {
        this.canaleB = canaleB;
    }
    public Integer getValoreR() {
        return this.valoreR;
    }
    public void setValoreR(Integer valoreR) {
        this.valoreR = valoreR;
    }
    public Integer getValoreG() {
        return this.valoreG;
    }
    public void setValoreG(Integer valoreG) {
        this.valoreG = valoreG;
    }
    public Integer getValoreB() {
        return this.valoreB;
    }
    public void setValoreB(Integer valoreB) {
        this.valoreB = valoreB;
    }
}
