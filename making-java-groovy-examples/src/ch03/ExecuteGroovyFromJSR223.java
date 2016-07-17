package ch03;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.util.List;

/**
 * Created by Darshan on 7/17/16.
 */
public class ExecuteGroovyFromJSR223 {
    public static void main(String[] args) throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
        try {
            engine.eval("println 'Hello Groovy from eval!'");
            engine.eval(new FileReader("C:\\temp\\hello.groovy"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getListOfAvailableScriptingEngines() {
        List<ScriptEngineFactory> factories = new ScriptEngineManager().getEngineFactories();
        for (ScriptEngineFactory factory : factories) {
            System.out.println(factory.getLanguageName());
            System.out.println(factory.getEngineName());
            System.out.println(factory.getNames().toString());
        }
    }
}
