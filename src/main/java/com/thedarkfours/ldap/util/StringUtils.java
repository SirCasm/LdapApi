/*
 * The MIT License
 *
 * Copyright 2013 René Döbele.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.thedarkfours.ldap.util;

/**
 * The obligatory StringUtil class
 * 
 * @author rene
 */
public class StringUtils {

    /**
     * Checks if a String is empty or null
     * 
     * @param string
     * @return true if string is null or empty false otherwise
     */
    public static Boolean isNullOrEmpty(String string) {
        if (string == null) {
            return true;
        }
        return string.trim().isEmpty();
    }
    /**
     * Capitalizes the first letter of the input String.
     * 
     * @param name
     * @return a string with the first letter capitalized
     */
    public static String captitalizeFirstCharacter(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
    }
            
}
