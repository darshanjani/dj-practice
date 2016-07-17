package ch01

import spock.lang.Specification


/**
 * Created by Darshan on 7/10/16.
 */
class StringSorterTest extends Specification {
    StringSorter sorter = new StringSorter()
    def strings = ['this', 'is', 'a', 'list', 'of', 'strings']

    def "lexicographical sort returns alphabetical"() {
        when:
        sorter.sortLexicographically(strings)

        then:
        println "first test run"
        strings == ['a', 'is', 'list', 'of', 'strings', 'this']
    }

    def "reverse sort by length should be decreasing size"() {
        when:
        sorter.sortByDecreasingLength(strings)

        then:
        strings*.size() == [7, 4, 4, 2, 2, 1]
    }
}
