package ch02

import groovy.sql.Sql

/**
 * Created by Darshan on 7/12/16.
 */
//Create an empty ArrayList
def stadiums = []

// Left shift operator << appends objects to the array
stadiums << new Stadium(name:'Angel Stadium',city:'Anaheim',state:'CA',team:'ana')
stadiums << new Stadium(name:'Chase Field',city:'Phoenix',state:'AZ',team:'ari')
stadiums << new Stadium(name:'Rogers Centre',city:'Toronto',state:'ON',team:'tor')
stadiums << new Stadium(name:'Nationals Park',city:'Washington',state:'DC',team:'was')

Sql db = Sql.newInstance(
        'jdbc:h2:tcp://localhost/~/test',
        'sa',
        '',
        'org.h2.Driver'
)

db.execute 'DROP TABLE IF EXISTS stadium;'
db.execute '''
    create table stadium (
        id int not null auto_increment,
        name varchar(200) not null,
        city varchar(200) not null,
        state char(2) not null,
        team char(3) not null,
        latitude double,
        longitude double,
        primary key(id)
    );
'''

Geocoder geo = new Geocoder()
stadiums.each { s ->
    geo.fillInLatLng s
    db.execute """
      insert into stadium (name, city, state, team, latitude, longitude)
      values (${s.name}, ${s.city}, ${s.state}, ${s.team}, ${s.latitude}, ${s.longitude})
    """
}

assert db.rows('select * from stadium').size() == stadiums.size()
db.eachRow('select latitude, longitude from stadium') { row ->
    assert row.latitude > 25 && row.latitude < 48
    assert row.longitude > -123 && row.longitude < -71
}