package ch06

/**
 * Created by Darshan on 7/25/16.
 */
class ScriptsShellTests extends GroovyShellTestCase {
    String base = 'src/main/java'

    void testRandomJoke() {
        shell.evaluate(new File("$base/ch06/RandomJoke.groovy"))
    }

    void testHelloWorld() {
        def content = new StringWriter()
        withBinding([out: new PrintWriter(content)]) {
            shell.evaluate(new File("$base/ch06/HelloWorld.groovy"))
            assert "Hello, World" == content.toString().trim()
        }
    }

    void testHelloName() {
        def content = new StringWriter()
        withBinding([out: new PrintWriter(content), name: "Darshan"]) {
            shell.evaluate(new File("$base/ch06/HelloName.groovy"))
            assert "Hello, Darshan" == content.toString().trim()
        }
    }

    void testCalc() {
        def result = withBinding([x:3, y:4]) {
            shell.evaluate(new File("$base/ch06/Calc.groovy"))
            shell.context.z
        }
        assert 7 == result
    }
}
