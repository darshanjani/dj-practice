package ch01

import groovy.transform.EqualsAndHashCode

/**
 * Created by Darshan on 7/10/16.
 */
@EqualsAndHashCode
class TaskGroovy {
    String name
    int priority
    Date startDate
    Date endDate

    String toString() { "TaskGroovy: ($name, $priority, $startDate, $endDate)" }

    public static void main(String[] args) {
        TaskGroovy tg = new TaskGroovy()
        tg.setName("Darshan")
        tg.setPriority(5)
        tg.setStartDate(new Date() - 5)
        tg.setEndDate(new Date() + 10)
        println tg
    }
}
