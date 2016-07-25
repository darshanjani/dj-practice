package ch04

import spock.lang.Specification

/**
 * Created by Darshan on 7/17/16.
 */
class ImmutablePointTest extends Specification {
    ImmutablePoint p = new ImmutablePoint(x: 3, y: 4)

    def "can use map actor for immutables"() {
        expect: [3,4] == [p.x, p.y]
    }

    def "cant change x"() {
        when: p.x = 4
        then: thrown(ReadOnlyPropertyException)
    }

    def "cant change y"() {
        when: p.y = 5
        then: thrown(ReadOnlyPropertyException)
    }
}
