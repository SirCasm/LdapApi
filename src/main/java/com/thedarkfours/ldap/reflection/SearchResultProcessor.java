/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thedarkfours.ldap.reflection;

import com.thedarkfours.ldap.LdapBindInformation;
import com.thedarkfours.ldap.LdapPersistor;
import com.thedarkfours.ldap.schema.LdapObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapName;

/**
 *
 * @author rene
 */
public class SearchResultProcessor {

    private HashMap extractAttributes(NamingEnumeration<SearchResult> search, Collection<String> objectClass, LdapBindInformation ldapInfo) throws NamingException {
        HashMap<String, Object> attributeMap = new HashMap<String, Object>();
        SearchResult searchResult = search.next();
        LdapName name = new LdapName(searchResult.getNameInNamespace());
        ldapInfo.removeBaseDn(name);
        attributeMap.put("dn", name.toString());
        Attributes attributes = searchResult.getAttributes();
        NamingEnumeration<? extends Attribute> all = attributes.getAll();
        while (all.hasMore()) {
            Attribute attribute = all.next();
            String id = attribute.getID();
            Object value;
            if (attribute.size() > 1) {
                Object[] attArray = toObjectArray(attribute);
                value = attArray;
            } else {
                value = attribute.get();
            }
            attributeMap.put(id, value);
        }
        return attributeMap;
    }

    private void addAttributeToStringArray(Attribute attribute, Collection<String> objectClass) throws NamingException {
        for (int i = 0; i < attribute.size(); i++) {
            objectClass.add((String) attribute.get(i));
        }
    }

    private Collection processSearchResults(NamingEnumeration<SearchResult> search, Collection<String> objectClass, LdapBindInformation ldapInfo) throws NamingException {
        ArrayList<HashMap<String, Object>> searchResults = new ArrayList<HashMap<String, Object>>();
        while (search.hasMore()) {
            HashMap<String, Object> attributeMap = extractAttributes(search, objectClass, ldapInfo);
            searchResults.add(attributeMap);
        }
        return searchResults;
    }

    private Object[] toObjectArray(Attribute attribute) throws NamingException {
        Object[] attArray = new Object[attribute.size()];
        for (int i = 0; i < attribute.size(); i++) {
            attArray[i] = attribute.get(i);
        }
        return attArray;
    }

    public <T extends LdapObject> Collection extractAndInvoke(NamingEnumeration<SearchResult> search, Class<T> clazz, LdapBindInformation ldapInfo) throws NamingException, SecurityException {
        Collection<String> objectClass = new ArrayList<String>();
        Collection<HashMap<String, Object>> searchResults = processSearchResults(search, objectClass, ldapInfo);
        LdapAttributeParser attributeParser = new LdapAttributeParser();
        ArrayList<T> convertedObjects = new ArrayList<T>();
        for (HashMap<String, Object> extractedAttributes : searchResults) {
            T newInstance = attributeParser.createNewInstance(extractedAttributes, clazz);
            convertedObjects.add(newInstance);
        }
        return convertedObjects;
    }
}
