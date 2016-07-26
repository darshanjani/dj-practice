package ch06;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Darshan on 7/25/16.
 */
public class ScriptTests {
    @Test
    public void testRandomJokeScript() throws Exception {
        GroovyShell shell = new GroovyShell();
        shell.evaluate(new File("src/main/java/ch06/RandomJoke.groovy"));
    }

    @Test
    public void testBindingScript() throws Exception {
        Binding binding = new Binding();
        binding.setVariable("name", "Darshan");
        StringWriter content = new StringWriter();
        PrintWriter writer = new PrintWriter(content);
        binding.setVariable("out", writer);
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(new File("src/main/java/ch06/HelloWorld.groovy"));
        assertEquals("Hello, Darshan", content.toString().trim());
    }
}
