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

package com.thedarkfours.ldap.reflection.conversion;

import com.thedarkfours.ldap.reflection.conversion.validator.IntegerMatchCastAndValidate;
import com.thedarkfours.ldap.reflection.conversion.validator.CharacterMatchCastAndValidate;
import com.thedarkfours.ldap.reflection.conversion.validator.StringMatchCastAndValidate;
import com.thedarkfours.ldap.reflection.conversion.validator.LongMatchCastAndValidate;
import com.thedarkfours.ldap.reflection.conversion.validator.ShortMatchCastAndValidate;
import com.thedarkfours.ldap.reflection.conversion.validator.FloatMatchCastAndValidate;
import com.thedarkfours.ldap.reflection.conversion.validator.DoubleMatchCastAndValidate;
import com.thedarkfours.ldap.exception.CastAndValidateException;
import com.thedarkfours.ldap.reflection.conversion.validator.BooleanMatchCastAndValidate;
import java.util.ArrayList;

/**
 *
 * @author René Döbele
 */
public class LdapTypeConverter {
    public ArrayList<MatchCastAndValidate> validators = 
            new ArrayList<MatchCastAndValidate>();

    public LdapTypeConverter() {
        validators.add(new StringMatchCastAndValidate());
        validators.add(new CharacterMatchCastAndValidate());
        validators.add(new BooleanMatchCastAndValidate());
        validators.add(new ShortMatchCastAndValidate());
        validators.add(new IntegerMatchCastAndValidate());
        validators.add(new LongMatchCastAndValidate());
        validators.add(new FloatMatchCastAndValidate());
        validators.add(new DoubleMatchCastAndValidate());
    }
    
    public Object convert(Class<?> type, Object object) {
        for (MatchCastAndValidate validator : validators) {
            if (validator.matchesType(type)) {
                return validator.castAndValidate(object);
            }
        }
        throw new CastAndValidateException("No fitting validator found for type " + type.getName());
    }
}
