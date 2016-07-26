package ch06

import java.util.logging.Level

/**
 * Created by Darshan on 7/25/16.
 */
class CalcWithLoggerTests extends GroovyLogTestCase {

    String base = 'src/main/java'

    void testAddition() {
        def result = stringLog(Level.INFO, CalcWithLogger.class.name) {
            Binding b = new Binding()
            b.x = 3; b.y = 4
            GroovyShell shell = new GroovyShell(b)
            shell.evaluate(new File("$base/ch06/CalcWithLogger.groovy"))
            assert 7 == shell.context.z
        }
        assert result.contains('INFO: Received (x,y) = (3,4)')
    }
}
