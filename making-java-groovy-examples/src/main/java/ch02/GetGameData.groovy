package ch02

import groovy.sql.Sql

import java.util.regex.Matcher

/**
 * Created by Darshan on 7/17/16.
 */
class GetGameData {
    def day, month, year
    String base = 'http://gd2.mlb.com/components/game/mlb/'
    Map stadiumMap = [:]

    Map abbrevs = [
            ana: 'Los Angeles (A)', ari: 'Arizona',     atl: 'Atlanta',
            bal: 'Baltimore',       bos: 'Boston',      cha: 'Chicago (A)',
            chn: 'Chicago (N)',     cin: 'Cincinnati',  cle: 'Cleveland',
            col: 'Colorado',        det: 'Detroit',     flo: 'Florida',
            hou: 'Houston',         kca: 'Kansas City', lan: 'Los Angeles (N)',
            mil: 'Milwaukee',       min: 'Minnesota',   nya: 'New York (A)',
            nyn: 'New York (N)',    oak: 'Oakland',     phi: 'Philadelphia',
            pit: 'Pittsburg',       sdn: 'San Diego',   sea: 'Seattle',
            sfn: 'San Francisco',   sln: 'St. Louis',   tba: 'Tampa Bay',
            tex: 'Texas',           tor: 'Toronto',     was: 'Washington'
    ]

    GetGameData() {
//        fillInStadiumMap()
    }

    def fillInStadiumMap() {
        Sql db = Sql.newInstance(
                'jdbc:h2:tcp://localhost/~/test',
                'sa',
                '',
                'org.h2.Driver'
        )
        db.eachRow("select * from stadium") { row ->
            Stadium stadium = new Stadium(
                name: row.name,
                team: row.team,
                latitude: row.latitude,
                longitude: row.longitude
            )

            stadiumMap[stadium.team] = stadium
        }
        db.close()
//        println stadiumMap
    }

/*    public static void main(String[] args) throws Exception {
        GetGameData ggd = new GetGameData()
        ggd.year = "2015"
        ggd.month = "07"
        ggd.day = "10"
        def gameResults = ggd.getGames()
    }*/

    def getGame(away, home, num) {
        println "${abbrevs[away]} at ${abbrevs[home]} on ${month}/${day}/${year}"
        def url = base + "year_${year}/month_${month}/day_${day}/"
        def game = "gid_${year}_${month}_${day}_${away}mlb_${home}mlb_${num}/boxscore.xml"
        def boxscore = new XmlSlurper().parse("$url$game")
        //Show all pitchers that either Won, Lost or a Save was assigned
//        def pitchers = boxscore.pitching.pitcher
//        pitchers.each {p ->
//            if (p.@note && p.@note =~ /W|L|S/) {
//                println "  ${p.@name} ${p.@note}"
//            }
//        }

        def awayName = boxscore.@away_fname
        def awayScore = boxscore.linescore[0].@away_team_runs
        def homeName = boxscore.@home_fname
        def homeScore = boxscore.linescore[0].@home_team_runs
        println "$awayName $awayScore, $homeName $homeScore (game $num)"
        GameResult result = new GameResult(
                away: awayName,
                aScore: awayScore,
                home: homeName,
                hScore: homeScore,
                stadium: stadiumMap[home]
        )
        return result
    }

    def getGames() {
        fillInStadiumMap()
        def gameResults = []
        println "Game for ${month}/${day}/${year}"
        String url = base + "year_${year}/month_${month}/day_${day}"
        String gamePage = url.toURL().text
//        println gamePage
        def pattern = /\"gid_${year}_${month}_${day}_(\w*)mlb_(\w*)mlb_(\d)/
        Matcher m = gamePage =~ pattern
        if (m) {
            m.count.times { line ->
                String away = m[line][1]
                String home = m[line][2]
                String num = m[line][3]
                try {
                    GameResult gr = this.getGame(away, home, num)
                    gameResults << gr
                } catch (Exception e) {
                    println "${abbrevs[away]} at ${abbrevs[home]} not started yet"
                }
            }
        }
        return gameResults
    }
}
