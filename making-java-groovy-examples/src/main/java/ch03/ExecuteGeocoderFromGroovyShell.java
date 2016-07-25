package ch03;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.junit.Assert;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

/**
 * Created by Darshan on 7/17/16.
 */
public class ExecuteGeocoderFromGroovyShell {
    @Test
    public void executeGeocoderFromGroovyTest() throws Exception {
        Binding binding = new Binding();
        binding.setVariable("street", "Neptune Living Point");
        binding.setVariable("city", "Mumbai");
        binding.setVariable("state", "Maharashtra");
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(new FileReader("C:\\Users\\Darshan\\IdeaProjects\\DJsIdea\\GroovyTest\\src\\ch03\\geocoder_script_script.groovy"));
        Assert.assertEquals("OK",(String)binding.getVariable("status"));
    }
}
