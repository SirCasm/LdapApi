/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thedarkfours.ldap.schema;

import java.util.Collection;

/**
 *
 * @author rene
 */
public interface LdapObject {

    String getDn();

    Collection<String> getObjectClass();

    void setDn(String dn);

    void setObjectClass(Collection<String> objectClass);
    
}
