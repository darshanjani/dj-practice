package ch03;

import org.junit.Assert;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

/**
 * Created by Darshan on 7/17/16.
 */
public class ExecuteGeocoderFromJSR {
    @Test
    public void executeGeocoderFromGroovyTest() throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
        engine.put("street","Neptune Living Point");
        engine.put("city", "Mumbai");
        engine.put("state", "Maharashtra");
        engine.eval(new FileReader("C:\\Users\\Darshan\\IdeaProjects\\DJsIdea\\GroovyTest\\src\\ch03\\geocoder_script.groovy.groovy"));
        Assert.assertEquals("OK",(String)engine.get("status"));
    }
}
