/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thedarkfours.ldap.reflection;

import com.thedarkfours.ldap.annotation.LdapAttribute;
import com.thedarkfours.ldap.exception.AttributeMustBeSetException;
import com.thedarkfours.ldap.schema.LdapObject;
import java.lang.reflect.Field;
import java.util.Collection;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

/**
 * Unwrapps the attributes from objects extending LdapObject
 *
 * @author rene
 */
public class LdapAttributeUnwrapper {

    /**
     * Unwraps a LdapObject object and returns them as attributes
     *
     * @param <T> A class extending the LdapObject interface
     * @param ldapObject The ldapObject
     * @return The content of ldapObject as Attributes
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws AttributeMustBeSetException when a mandatory attribute was not
     * set
     */
    public <T extends LdapObject> Attributes unwrapObjectAttributes(T ldapObject) throws IllegalArgumentException, IllegalAccessException {
        BasicAttributes returnAttributes = new BasicAttributes();
        Class<?> aClass = ldapObject.getClass();

        do {

        Field[] declaredFields = ldapObject.getClass().getDeclaredFields();
            extractAttributes(declaredFields, ldapObject, returnAttributes);
            aClass = aClass.getSuperclass();
        } while (aClass != null);

        return returnAttributes;
    }

    private <T extends LdapObject> void extractAttributes(Field[] declaredFields, T ldapObject, BasicAttributes returnAttributes) throws IllegalAccessException, IllegalArgumentException, SecurityException, AttributeMustBeSetException {
        for (Field field : declaredFields) {
            field.setAccessible(true);
            LdapAttribute annotation = field.getAnnotation(LdapAttribute.class);
            Object value = field.get(ldapObject);
            if (value == null && !annotation.optional()) {
                throw new AttributeMustBeSetException("Field " + field.getName() + " is not optional.");
            }
            String attributeName = annotation.name().isEmpty() ? field.getName() : annotation.name();

            returnAttributes.put(attributeName, value == null ? "" : value);
        }
    }
}
