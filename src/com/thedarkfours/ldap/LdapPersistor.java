/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thedarkfours.ldap;

import com.thedarkfours.ldap.annotation.LdapAttribute;
import com.thedarkfours.ldap.reflection.LdapAttributeParser;
import com.thedarkfours.ldap.schema.LdapObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
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
        
        ctx = new InitialDirContext(env);
    }
    
    public void getByDn(String dn) throws NamingException {
        NamingEnumeration<SearchResult> search = searchDn(dn);

        while (search.hasMore()) {
            SearchResult next = search.next();
            Attributes attributes = next.getAttributes();
            System.out.println(attributes);
        }
    }

    private NamingEnumeration<SearchResult> searchDn(String dn) throws NamingException {
        SearchControls sc = new SearchControls();
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        sc.setReturningAttributes(new String[]{"*"});
        NamingEnumeration<SearchResult> search = ctx.search(dn, "(objectclass=*)", sc);
        return search;
    }
    
    public <T extends LdapObject> T search(String dn, Class<T> clazz) throws NamingException, SecurityException {
        NamingEnumeration<SearchResult> search = searchDn(dn);
        Collection<String> objectClass = new ArrayList<String>();
        HashMap<String, Object> extractedAttributes = extractAttributes(search, objectClass);
        
        LdapAttributeParser ldapAttributeParser = new LdapAttributeParser();
        
        T newInstance = ldapAttributeParser.createNewInstance(extractedAttributes, clazz);
        newInstance.setDn(dn);
        newInstance.setObjectClass(objectClass);
        
        return newInstance;
    }

    private HashMap<String, Object> extractAttributes(NamingEnumeration<SearchResult> search, Collection<String> objectClass) throws NamingException {
        HashMap<String, Object> attributeMap = new HashMap<String, Object>();
        while (search.hasMore()) {
            SearchResult next = search.next();
            Attributes attributes = next.getAttributes();
            NamingEnumeration<? extends Attribute> all = attributes.getAll();
            while (all.hasMore()) {
                Attribute attribute = all.next();
                String id = attribute.getID();
                
                if (id.equals("objectClass")) {
                    for (int i = 0; i < attribute.size(); i++) {
                        objectClass.add((String)attribute.get(i));
                    }
                }
                
                attributeMap.put(id, attribute.get());
            }
        }
        return attributeMap;
    }

    
    
}
