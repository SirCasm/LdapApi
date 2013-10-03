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
package com.thedarkfours.ldap.reflection;

import com.thedarkfours.ldap.LdapPersistor;
import com.thedarkfours.ldap.annotation.LdapAttribute;
import com.thedarkfours.ldap.schema.LdapObject;
import com.thedarkfours.ldap.util.StringUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rene
 */
public class LdapAttributeParser {

    public void parse(Class<? extends LdapObject> clazz) {
        Field[] fields = clazz.getFields();

        for (Field field : fields) {
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
        }
    }

    public <T extends LdapObject> T createNewInstance(HashMap<String, Object> searchResult, Class<T> clazz) throws SecurityException {

        T newInstance = null;
        try {
            newInstance = clazz.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(LdapPersistor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(LdapPersistor.class.getName()).log(Level.SEVERE, null, ex);
        }
        Field[] fields = newInstance.getClass().getDeclaredFields();
        for (Field field : fields) {
            invokeMethod(field, newInstance, searchResult);
        }
        return newInstance;
    }

    private <T extends LdapObject> void invokeMethod(Field field, T newInstance, HashMap<String, Object> searchResult) throws RuntimeException {
        LdapAttribute annotation = field.getAnnotation(LdapAttribute.class);
        if (annotation != null) {
            String name = field.getName();
            String ldapName = annotation.name().isEmpty() ? field.getName() : annotation.name();

            String setterName;
            setterName = buildSetterName(name);

            Method method = null;
            final Class<?> type = field.getType();
            try {
                method = newInstance.getClass().getMethod(setterName, type);
                System.out.println("");
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(LdapPersistor.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("There is no method: " + setterName);
            }
            Object attValue = searchResult.get(ldapName);
            CastTypeAndInvokeMethod(type, method, newInstance, attValue);
        }
    }

    private <T extends LdapObject> void CastTypeAndInvokeMethod(Class<?> type, Method method, T newInstance, Object attValue) {
        try {
            if (attValue != null && attValue.getClass().isArray()) {
                System.out.println("Array");
            } else
            if (Short.TYPE.isAssignableFrom(type)) {
                method.invoke(newInstance, Short.valueOf((String)attValue));
            } else if (Boolean.TYPE.isAssignableFrom(type)) {
                method.invoke(newInstance, Boolean.valueOf((String)attValue));
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(LdapAttributeParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(LdapAttributeParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(LdapAttributeParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String buildSetterName(String name) {
        String setterName;
        setterName = "set" + StringUtils.captitalizeFirstCharacter(name);
        return setterName;
    }
}
