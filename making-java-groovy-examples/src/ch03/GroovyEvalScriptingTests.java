package ch03;

import groovy.util.Eval;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by Darshan on 7/17/16.
 */
public class GroovyEvalScriptingTests {
    @Test
    public void testEvalNoParams() {
        String result = (String)Eval.me("'abc' - 'b'");
        assertEquals("ac", result);
    }

    @Test
    public void testEvalOneParam() {
        String result = (String)Eval.x("a", "'abc' - x");
        assertEquals("bc", result);
    }

    @Test
    public void testEvalTwoParams() {
        String result = (String)Eval.xy("a", "b", "'abc' - x - y");
        assertEquals("c", result);
    }

    @Test
    public void testEvalThreeParams() {
        String result = (String)Eval.xyz("a", "b", "d", "'abc' - x - y + z");
        assertEquals("cd", result);
    }
}
