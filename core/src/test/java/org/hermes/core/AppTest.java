/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.hermes.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test public void testGetMessage() {
        assertEquals("Hello      World!", MessageUtils.getMessage());
    }
}
