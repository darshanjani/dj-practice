package ch02

/**
 * Created by Darshan on 7/12/16.
 */
class Geocoder {
    def base = 'http://maps.googleapis.com/maps/api/geocode/xml?'

    def fillInLatLng(Stadium stadium) {
        def url = base + [sensor: false,
                address: [stadium.name, stadium.city, stadium.state].collect {
                    URLEncoder.encode(it, "UTF-8")
                }.join(',')
        ].collect {k,v -> "$k=$v"}.join('&')

        def response = new XmlSlurper().parse(url)
        stadium.latitude = response.result[0].geometry.location.lat.toDouble()
        stadium.longitude = response.result[0].geometry.location.lng.toDouble()
        return stadium
    }
}
