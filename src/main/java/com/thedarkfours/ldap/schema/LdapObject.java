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

package com.thedarkfours.ldap.schema;

import java.util.Collection;

/**
 *
 * @author rene
 */
public interface LdapObject {

    /**
     * Get the distinguished name for the LdapObject
     *
     * @return a string containing the name of the ldap object.
     */
    String getDn();

    /**
     * Get the object class of the Ldap object
     *
     * @return a collection of strings containing the object classes
     */
    Collection<String> getObjectClass();

    /**
     * Set the distinguished name for the LdapObject.
     *
     * @param dn
     */
    void setDn(String dn);

    /**
     * Set the distinguished name
     *
     * @param objectClass an array of strings representing the object classes
     */
    void setObjectClass(Collection<String> objectClass);
    
}
