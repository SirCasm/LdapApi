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

package com.thedarkfours.ldap.reflection.conversion.validator;

import com.thedarkfours.ldap.exception.CastAndValidateException;
import com.thedarkfours.ldap.reflection.conversion.MatchCastAndValidate;

/**
 *
 * @author rene
 */
public class CharacterMatchCastAndValidate implements MatchCastAndValidate {

    @Override
    public boolean matchesType(Class<?> type) {
        return Character.TYPE.isAssignableFrom(type) || Character.class.isAssignableFrom(type);
    }

    @Override
    public Object castAndValidate(Object object) {
        String string = (String)object;
        if (string.length() > 1) {
            throw new CastAndValidateException(
                    String.format("Cannot cast string to charater because it's too long: %s (%d) ", 
                            string, 
                            string.length()));
        }
        return string.charAt(0);
    }
    
}
