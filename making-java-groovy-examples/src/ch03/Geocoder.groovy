package ch03

/**
 * Created by Darshan on 7/17/16.
 */
class Geocoder {
    String base = "https://maps.googleapis.com/maps/api/geocode/xml?"

    def fillInLatLng(Location loc) {
        def addressFields = [loc.street, loc.city, loc.state]
         def address = addressFields.collect{
            URLEncoder.encode(it, 'UTF-8')
        }.join(',+')
        def params=[address:address, sensor:false]
        String url = base + params.collect {k,v -> "$k=$v"}.join('&')
        def xml = new XmlSlurper().parse(url)
        def status = xml.status.toString()
        loc.latitude = xml.result.geometry.location.lat.toDouble()
        loc.longitude = xml.result.geometry.location.lng.toDouble()
        def locationType = xml.result.geometry.location_type.toString()
    }
}
