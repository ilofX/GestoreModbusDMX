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
package View.Impostazioni;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Filippo Stella
 * @version 1.0
 */
public class MaxCharactersTextField extends PlainDocument {

    private final Integer length;

    public MaxCharactersTextField(Integer length) {
        super();
        this.length = length;
    }

    @Override
    public void insertString(int i, String string, AttributeSet as) throws BadLocationException{
        try {
            if(string == null)
                throw new Exception("Stringa Vuota");
            else{
                if(getLength() + string.length() <= this.length){
                    super.insertString(i, string, as);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MaxCharactersTextField.class.getName()).log(Level.WARNING, null, ex);
        }
    }
    
    
    
}
