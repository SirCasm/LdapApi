/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thedarkfours.ldap.schema;

import com.thedarkfours.ldap.annotation.LdapAttribute;
import java.util.Collection;

/**
 *
 * @author rene
 */
public class Person implements LdapObject {
    
    private Collection<String> objectClass;
    private String dn;
    @LdapAttribute(name = "sn")
    private String surname;
    @LdapAttribute()
    private String description;
    @LdapAttribute()
    private String telephoneNumber;
    @LdapAttribute()
    private String seeAlso;
    @LdapAttribute()
    private String userPassword;

    @Override
    public Collection<String> getObjectClass() {
        return objectClass;
    }

    @Override
    public void setObjectClass(Collection<String> objectClass) {
        this.objectClass = objectClass;
    }

    @Override
    public String getDn() {
        return dn;
    }

    @Override
    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getSeeAlso() {
        return seeAlso;
    }

    public void setSeeAlso(String seeAlso) {
        this.seeAlso = seeAlso;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }   
}
