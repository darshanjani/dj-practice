package ch04

import groovy.transform.Immutable

/**
 * Created by Darshan on 7/17/16.
 */
@Immutable
class ImmutablePoint {
    double x
    double y

    String toString() { "($x,$y)"}
}
