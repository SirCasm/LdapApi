/*
 * Copyright (c) 2013, rene
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.thedarkfours.ldap.reflection.conversion.validator;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author rene
 */
public class DoubleMatchCastAndValidateTest {
    
    public DoubleMatchCastAndValidateTest() {
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
     * Test of matchesType method, of class DoubleMatchCastAndValidate.
     */
    @Test
    public void testMatchesType() {
        System.out.println("matchesType");
        Class type = Double.TYPE;
        DoubleMatchCastAndValidate instance = new DoubleMatchCastAndValidate();
        boolean expResult = true;
        boolean result = instance.matchesType(type);
        assertEquals(expResult, result);
    }

    /**
     * Test of matchesType method, of class DoubleMatchCastAndValidate.
     */
    @Test
    public void testMatchesClass() {
        System.out.println("matchesClass");
        Class type = Double.class;
        DoubleMatchCastAndValidate instance = new DoubleMatchCastAndValidate();
        boolean expResult = true;
        boolean result = instance.matchesType(type);
        assertEquals(expResult, result);
    }

    /**
     * Test of castAndValidate method, of class DoubleMatchCastAndValidate.
     */
    @Test
    public void testCastAndValidate() {
        System.out.println("castAndValidate");
        Object object = String.valueOf(Double.MAX_VALUE);
        DoubleMatchCastAndValidate instance = new DoubleMatchCastAndValidate();
        Object expResult = Double.MAX_VALUE;
        Object result = instance.castAndValidate(object);
        assertEquals(expResult, result);
    }
    
}