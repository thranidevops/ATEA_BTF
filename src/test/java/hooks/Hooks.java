// src/test/java/hooks/Hooks.java
package hooks;

import base.BaseTest;
import io.cucumber.java.Before;
import io.cucumber.java.After;

public class Hooks extends BaseTest {

    @Before
    public void setUp() {
        initializeDriver();
    }

    @After
    public void tearDown() {
        //quitDriver();
    }
}
