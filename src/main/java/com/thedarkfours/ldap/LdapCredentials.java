/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thedarkfours.ldap;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;

/**
 *
 * @author rene
 */
public class LdapCredentials {

    private final String connectString;
    private final LdapName baseDn;
    private final String userDn;
    private final String password;

    public LdapCredentials(String connectString, LdapName baseDn, String userDn, String password) {
        this.connectString = connectString;
        this.baseDn = baseDn;
        this.userDn = userDn;
        this.password = password;
    }

    public String getConnectString() {
        return connectString;
    }

    public LdapName getBaseDn() {
        return baseDn;
    }

    public String getUserDn() {
        return userDn;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Removes the baseDn from the ldapName
     *
     * NOTE: This is probably the wrong place for this method, but I haven't
     * found the right one yet.
     *
     * @param name the name containing the baseDn
     */
    public void removeBaseDn(LdapName name) {
        if (name.startsWith(baseDn)) {
            for (int i = 0; i < baseDn.size(); i++) {
                try {
                    name.remove(i);
                } catch (InvalidNameException ex) {
                    Logger.getLogger(LdapCredentials.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
