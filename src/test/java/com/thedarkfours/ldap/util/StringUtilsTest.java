/*
 * The MIT License
 *
 * Copyright 2013 rene.
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

package com.thedarkfours.ldap.util;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rene
 */
public class StringUtilsTest {

    public StringUtilsTest() {
    }

    /**
     * Test of isNullOrEmpty method, of class StringUtils.
     */
    @Test
    public void testIsNull() {
        System.out.println("isNull");
        String string = null;
        Boolean expResult = true;
        Boolean result = StringUtils.isNullOrEmpty(string);
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of isNullOrEmpty method, of class StringUtils.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        String string = "";
        Boolean expResult = true;
        Boolean result = StringUtils.isNullOrEmpty(string);
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of isNullOrEmpty method, of class StringUtils.
     */
    @Test
    public void testIsNullOrEmptyWhitespaces() {
        System.out.println("isNullOrEmptyWhitespaces");
        String string = "    ";
        Boolean expResult = true;
        Boolean result = StringUtils.isNullOrEmpty(string);
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of captitalizeFirstCharacter method, of class StringUtils.
     */
    @Test
    public void testCaptitalizeFirstCharacter() {
        System.out.println("captitalizeFirstCharacter");
        String name = "caps";
        String expResult = "Caps";
        String result = StringUtils.captitalizeFirstCharacter(name);
        Assert.assertEquals(expResult, result);
    }

}
