/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thedarkfours.ldap.annotation;

import com.thedarkfours.ldap.reflection.LdapAttributeParser;
import com.thedarkfours.ldap.annotation.testdata.LdapAttributeParserTestObject;
import com.thedarkfours.ldap.schema.LdapObject;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rene
 */
public class LdapAttributeParserTest {
    
    private HashMap<String, Object> searchResult;
    
    public LdapAttributeParserTest() {
        searchResult = new HashMap<String, Object>();
        searchResult.put("longValue", "1234");
        searchResult.put("integerValue", "123");
        searchResult.put("boolValue", "true");
        searchResult.put("stringValue", "test");
        searchResult.put("charValue", "a");
        searchResult.put("floatValue", "13423.454");
        searchResult.put("doubleValue", "12345.231");
        searchResult.put("shortValue", "123");
        searchResult.put("intValue", "1");
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of buildMethodName method, of class LdapAttributeParser.
     */
    @Test
    public void testBuildMethodName() {
        LdapAttributeParser instance = new LdapAttributeParser();
        System.out.println("buildMethodName");
        String name = "method";
        String expResult = "setMethod";
        String result = instance.buildSetterName(name);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSetData() {
        LdapAttributeParser instance = new LdapAttributeParser();
        LdapAttributeParserTestObject result = instance.createNewInstance(searchResult, LdapAttributeParserTestObject.class);
        assertNotNull(result);
        assertTrue(result.getBoolValue());
        assertTrue(result.getLongValue().equals(1234L));
        assertTrue(result.getIntegerValue().equals(123));
        assertTrue(result.getShortValue() == 123);
        assertTrue(result.getCharValue().equals('a'));
        assertTrue(result.getStringValue().equals("test"));
        assertTrue(result.getDoubleValue().equals(12345.231d));
        assertTrue(result.getFloatValue().equals(13423.454f));
        assertEquals(1, result.getIntValue());
    }    
}
