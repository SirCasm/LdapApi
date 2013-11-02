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

package com.thedarkfours.ldap;

import com.thedarkfours.ldap.reflection.LdapAttributeParser;
import com.thedarkfours.ldap.reflection.SearchResultProcessor;
import com.thedarkfours.ldap.schema.LdapObject;
import com.thedarkfours.ldap.schema.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapName;

/**
 *
 * @author rene
 */
public class LdapPersistor {
    private InitialDirContext ctx;
    private final LdapCredentials ldapCredentials;
    private final SearchResultProcessor searchResultProcessor;

    /**
     *
     * @param connectString
     * @param baseDn
     * @param userDn
     * @param password
     * @throws NamingException
     */
    public LdapPersistor(String connectString, String baseDn, String userDn, String password) throws InvalidNameException {
        this(new LdapCredentials(connectString, new LdapName(baseDn), userDn, password));
    }

    public LdapPersistor(LdapCredentials ldapCredentials) {
        this.ldapCredentials = ldapCredentials;
        searchResultProcessor = new SearchResultProcessor();
    }
    
    public void connect() throws NamingException {
        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapCredentials.getConnectString() + "/" + ldapCredentials.getBaseDn());
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, ldapCredentials.getUserDn());
        env.put(Context.SECURITY_CREDENTIALS, ldapCredentials.getPassword());
//        env.put(Context.BATCHSIZE, "10000");
        ctx = new InitialDirContext(env);
    }
    
    public <T extends LdapObject> T getByDn(String dn, Class<T> clazz) throws NamingException {
        NamingEnumeration<SearchResult> search = searchDn(dn, SearchControls.OBJECT_SCOPE);
        Collection<T> newInstance = searchResultProcessor.extractAndInvoke(search, clazz, ldapCredentials);

        if (newInstance.isEmpty()) {
            return null;
        } else if (newInstance.size() == 1) {
            return newInstance.iterator().next();
        }

        throw new NamingException("No result found!");
    }

    private NamingEnumeration<SearchResult> searchDn(String dn, int searchScope) throws NamingException {
        SearchControls sc = new SearchControls();
        sc.setSearchScope(searchScope);
        sc.setCountLimit(10000L);
        sc.setReturningAttributes(new String[]{"*"});
        sc.setReturningObjFlag(false);
        NamingEnumeration<SearchResult> search = ctx.search(dn, "(objectclass=*)", sc);

        return search;
    }
    
    public <T extends LdapObject> Collection<T> search(String dn, Class<T> clazz) throws NamingException, SecurityException {
        NamingEnumeration<SearchResult> search = searchDn(dn, SearchControls.SUBTREE_SCOPE);
        Collection<T> convertedObjects = searchResultProcessor.extractAndInvoke(search, clazz, ldapCredentials);
        return convertedObjects;
    }

    public <T extends LdapObject> void put(String rdn, T ldapEntry) throws InvalidNameException {
        LdapName ldapName = new LdapName(rdn);
    }

    public void disconnect() throws NamingException {
        ctx.close();
    }
}
