/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thedarkfours.ldap;

import javax.naming.ldap.LdapName;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rene
 */
public class LdapBindInformationTest {

    public LdapBindInformationTest() {
    }

    /**
     * Test of removeBaseDn method, of class LdapBindInformation.
     * @throws java.lang.Exception
     */
    @Test
    public void testRemoveBaseDn() throws Exception {
        System.out.println("removeBaseDn");
        LdapName name = new LdapName("ou=test,dc=thedarkfours,dc=com");
        LdapBindInformation instance = new LdapBindInformation("ldap://", new LdapName("dc=thedarkfours,dc=com"), "", "");
        instance.removeBaseDn(name);

        assertEquals(1, name.size());
        assertEquals("ou=test", name.toString());
    }

    @Test
    public void testRemoveAnotherBaseDn() throws Exception {
        System.out.println("removeAnotherBaseDn");
        LdapName name = new LdapName("cn=blablub,ou=test,dc=thedarkfours,dc=com");
        LdapBindInformation instance = new LdapBindInformation("ldap://", new LdapName("dc=thedarkfours,dc=com"), "", "");
        instance.removeBaseDn(name);

        assertEquals(2, name.size());
        assertEquals("cn=blablub,ou=test", name.toString());
    }

    @Test
    public void testRemoveBaseDnFromBaseDn() throws Exception {
        System.out.println("removeBaseDnFromBaseDn");
        LdapName name = new LdapName("dc=thedarkfours,dc=com");
        LdapBindInformation instance = new LdapBindInformation("ldap://", new LdapName("dc=thedarkfours,dc=com"), "", "");
        instance.removeBaseDn(name);

        assertEquals(0, name.size());
        assertEquals("", name.toString());
    }

    @Test
    public void testRemoveBaseBaseDnNotIncluded() throws Exception {
        System.out.println("removeBaseBaseDnNotIncluded");
        LdapName name = new LdapName("dc=thedarkfarts,dc=com");
        LdapBindInformation instance = new LdapBindInformation("ldap://", new LdapName("dc=thedarkfours,dc=com"), "", "");
        instance.removeBaseDn(name);

        assertEquals(2, name.size());
        assertEquals("dc=thedarkfarts,dc=com", name.toString());
    }
}
