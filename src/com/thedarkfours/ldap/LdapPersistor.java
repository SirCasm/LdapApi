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
import com.thedarkfours.ldap.schema.LdapObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 *
 * @author rene
 */
public class LdapPersistor {
    private final String connectString;
    private final String baseDn;
    private final String userDn;
    private final String password;
    private InitialDirContext ctx;

    /**
     *
     * @param connectString
     * @param baseDn
     * @param userDn
     * @param password
     * @throws NamingException
     */
    public LdapPersistor(String connectString, String baseDn, String userDn, String password) {
        this.connectString = connectString;
        this.baseDn = baseDn;
        this.userDn = userDn;
        this.password = password;
    }
    
    public void connect() throws NamingException {
        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, connectString + "/" + baseDn);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, userDn);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.BATCHSIZE, "10000");
        
        ctx = new InitialDirContext(env);
    }
    
    public <T extends LdapObject> T getByDn(String dn, Class<T> clazz) throws NamingException {
        NamingEnumeration<SearchResult> search = searchDn(dn, SearchControls.OBJECT_SCOPE);

        Collection<T> newInstance = extractAndInvoke(search, clazz, dn);

        if (newInstance.isEmpty()) {
            return null;
        } else if (newInstance.size() == 1) {
            for (T t : newInstance) {
                return t;
            }
        }

        throw new NamingException("No result found!");
    }

    private NamingEnumeration<SearchResult> searchDn(String dn, int searchScope) throws NamingException {
        SearchControls sc = new SearchControls();
        sc.setSearchScope(searchScope);
        sc.setCountLimit(10000L);
        sc.setReturningAttributes(new String[]{"*"});
        sc.setReturningObjFlag(true);

        NamingEnumeration<SearchResult> search = ctx.search(dn, "(objectclass=*)", sc);

        return search;
    }
    
    public <T extends LdapObject> Collection<T> search(String dn, Class<T> clazz) throws NamingException, SecurityException {
        NamingEnumeration<SearchResult> search = searchDn(dn, SearchControls.SUBTREE_SCOPE);
        Collection<T> convertedObjects = extractAndInvoke(search, clazz, dn);
        return convertedObjects;
    }

    private <T extends LdapObject> Collection<T> extractAndInvoke(NamingEnumeration<SearchResult> search, Class<T> clazz, String dn) throws NamingException, SecurityException {
        Collection<String> objectClass = new ArrayList<String>();
        Collection<HashMap<String, Object>> searchResults = extractAttributes(search, objectClass);
        LdapAttributeParser attributeParser = new LdapAttributeParser();

        ArrayList<T> convertedObjects = new ArrayList<T>();
        for (HashMap<String, Object> extractedAttributes : searchResults) {
            T newInstance = attributeParser.createNewInstance(extractedAttributes, clazz);
            newInstance.setDn(dn);
            newInstance.setObjectClass(objectClass);
            convertedObjects.add(newInstance);
        }

        return convertedObjects;
    }

    private Collection<HashMap<String, Object>> extractAttributes(NamingEnumeration<SearchResult> search, Collection<String> objectClass) throws NamingException {
        ArrayList<HashMap<String, Object>> searchResults = new ArrayList<HashMap<String, Object>>();
        while (search.hasMore()) {
            HashMap<String, Object> attributeMap = new HashMap<String, Object>();
            SearchResult next = search.next();
            Attributes attributes = next.getAttributes();
            NamingEnumeration<? extends Attribute> all = attributes.getAll();
            while (all.hasMore()) {
                Attribute attribute = all.next();
                String id = attribute.getID();

                Object value = null;
                
                if (id.equals("objectClass")) {
                    for (int i = 0; i < attribute.size(); i++) {
                        objectClass.add((String)attribute.get(i));
                    }
                    continue;
                }

                if (attribute.size() > 1) {
                    Object[] attArray = new Object[attribute.size()];
                    for (int i = 0; i < attribute.size(); i++) {
                        attArray[i] = attribute.get(i);
                    }
                    value = attArray;
                } else {
                    value = attribute.get();
                }
                
                attributeMap.put(id, value);

            }
            searchResults.add(attributeMap);
        }
        return searchResults;
    }

    
    
}
