/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thedarkfours.ldap.exception;

/**
 *
 * @author rene
 */
public class AttributeMustBeSetException extends RuntimeException {

    /**
     * Creates a new instance of <code>AttributeMustBeSetException</code>
     * without detail message.
     */
    public AttributeMustBeSetException() {
    }

    /**
     * Constructs an instance of <code>AttributeMustBeSetException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public AttributeMustBeSetException(String msg) {
        super(msg);
    }
}
