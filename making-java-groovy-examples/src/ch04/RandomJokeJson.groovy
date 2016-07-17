package ch04

import groovy.json.JsonSlurper

/**
 * Created by Darshan on 7/17/16.
 */

String url = 'http://api.icndb.com/jokes/random?limitTo=[nerdy]'
String jsonTxt = url.toURL().text
def json = new JsonSlurper().parseText(jsonTxt)
def joke = json?.value?.joke
println joke