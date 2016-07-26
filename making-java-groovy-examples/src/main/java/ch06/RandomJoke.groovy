package ch06

import groovy.json.JsonSlurper

/**
 * Created by Darshan on 7/25/16.
 */

def result = 'http://api.icndb.com/jokes/random'.toURL().text
def json = new JsonSlurper().parseText(result)
def joke = json?.value?.joke
assert joke
println joke