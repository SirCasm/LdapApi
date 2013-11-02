/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thedarkfours.ldap.reflection;

import com.thedarkfours.ldap.exception.AttributeMustBeSetException;
import com.thedarkfours.ldap.schema.Person;
import javax.naming.directory.Attributes;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rene
 */
public class LdapAttributeUnwrapperTest {

    public LdapAttributeUnwrapperTest() {
    }

    /**
     * Test of unwrapObjectAttributes method, of class LdapAttributeUnwrapper.
     */
    @Test
    public void testUnwrapObjectAttributes() throws Exception {
        System.out.println("unwrapObjectAttributes");
        Person person = new Person();
        person.setCommonName("ttest");
        person.setDescription("desc");
        person.setDn("cn=ttest,ou=test");
        person.setSurname("Test");

        LdapAttributeUnwrapper instance = new LdapAttributeUnwrapper();
        Attributes result = instance.unwrapObjectAttributes(person);
        assertEquals(person.getCommonName(), result.get("cn").get());
        assertEquals(person.getDescription(), result.get("description").get());
        assertEquals(person.getSurname(), result.get("sn").get());

    }

    /**
     * Test of unwrapObjectAttributes method, of class LdapAttributeUnwrapper.
     */
    @Test(expected = AttributeMustBeSetException.class)
    public void testUnwrapObjectAttributesMandatoryException() throws Exception {
        System.out.println("unwrapObjectAttributesMandatoryException");
        Person person = new Person();
        person.setDescription("desc");
        person.setSurname("Test");

        LdapAttributeUnwrapper instance = new LdapAttributeUnwrapper();
        Attributes result = instance.unwrapObjectAttributes(person);
    }

}
