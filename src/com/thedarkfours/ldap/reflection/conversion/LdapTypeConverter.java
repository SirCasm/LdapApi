/*
 * Copyright (c) 2013, rene
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
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
 * @author rene
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
