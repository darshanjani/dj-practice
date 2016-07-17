package ch04

/**
 * Created by Darshan on 7/17/16.
 */
println "Groundhog sees shadow --> 6 more weeks of winter"
def c = Calendar.instance
c.set 2016, Calendar.FEBRUARY, 2
def groundhogDay = c.time
c.set 2016, Calendar.MARCH, 20
def firstDayOfSpring = c.time
def days = firstDayOfSpring - groundhogDay
assert days == (firstDayOfSpring..groundhogDay).size() - 1
println """
There are ${(int)(days/7)} weeks  and ${days % 7} days between Groundhog Day
and the first day of Spring (March, 20), so Spring
comes early if the groundhog sees his shadow.
"""