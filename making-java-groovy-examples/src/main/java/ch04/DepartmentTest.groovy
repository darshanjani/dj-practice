package ch04

import spock.lang.Specification

/**
 * Created by Darshan on 7/17/16.
 */
class DepartmentTest extends Specification {
    private Department dept;
    def setup() { dept = new Department(name: 'IT') }

    def "add employee to dept should increase size by 1"() {
        given:
        Employee fred = new Employee(id: 1, name: 'Fred')

        when:
        dept = dept + fred

        then:
        dept.employees.size() == old(dept.employees.size()) + 1
    }

    def "add 2 employees via chained plus"() {
        given:
        Employee fred = new Employee(id: 1, name: 'Fred')
        Employee barney = new Employee(id: 2, name: 'Barney')

        when:
        dept = dept + fred + barney

        then:
        dept.employees.size() == 2
    }

    def "substract employee should reduce by 1"() {
        given:
        Employee fred = new Employee(id: 1, name: 'Fred')
        dept.hire fred

        when:
        dept = dept - fred

        then:
        dept.employees.size() == old(dept.employees.size()) - 1
    }

    def "remove 2 employees via chained minus"() {
        given:
        Employee fred = new Employee(id: 1, name: 'Fred')
        Employee barney = new Employee(id: 2, name: 'Barney')
        dept.hire fred; dept.hire barney;

        when:
        dept = dept - fred - barney

        then:
        dept.employees.size() == 0
    }

    def "left shift should increase employee total by 1"() {
        given:
        Employee fred = new Employee(id: 1, name: 'Fred')

        when:
        dept = dept << fred

        then:
        dept.employees.size() == old(dept.employees.size()) + 1
    }

    def "add 2 employees via chained leftshift"() {
        given:
        Employee fred = new Employee(id: 1, name: 'Fred')
        Employee barney = new Employee(id: 2, name: 'Barney')

        when:
        dept = dept << fred << barney

        then:
        dept.employees.size() == 2
    }
}
