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

import java.util.Comparator;

/**
 *
 * @author Filippo Stella
 * @version 1.0
 */
public class StartChannelSort implements Comparator<Luce>{
   
    /**
    * This method is used to sort the ArrayList by start channel
    * <p>
    * This method is used as a sort behavior to sort the ArrayList
    * of lights based by start channel
    * 
    * @param l1 The first Luce that needs to be compared with the second
    * @param l2 The second Luce that needs to be compared with the first
    * @return   Returns an integer to led the sort algorithm decide 
    */    
    @Override
    public int compare(Luce l1, Luce l2) {
        return Integer.compare(l1.getStart(), l2.getStart());
    }
    
}
