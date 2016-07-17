package ch03

/**
 * Created by Darshan on 7/17/16.
 */
//def street = "Neptune Living Point"
//def city = "Mumbai"
//def state = "Maharashtra"
String address = [street, city, state].collect{
    URLEncoder.encode(it, 'UTF-8')
}.join(',+')
def params=[address:address, sensor:false]
String base = "https://maps.googleapis.com/maps/api/geocode/xml?"
String url = base + params.collect {k,v -> "$k=$v"}.join('&')
//def output = url.toURL().text
def xml = new XmlSlurper().parse(url)
status = xml.status.toString()
lat = xml.result.geometry.location.lat.toString()
lng = xml.result.geometry.location.lng.toString()
locationType = xml.result.geometry.location_type.toString()
//println "$status, $lat, $lng, $locationType"