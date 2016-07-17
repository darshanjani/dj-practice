package ch02

import groovy.xml.MarkupBuilder
import org.junit.Test

/**
 * Created by Darshan on 7/17/16.
 */
class GetGameDataTests {
    GetGameData gdd = new GetGameData(month: 10, day: 28, year: 2007)
    @Test
    void testFillInStadium() {
        assert 0 == gdd.stadiumMap.size()
        gdd.fillInStadiumMap()
        def stadiums = gdd.stadiumMap.values();
        assert 6 == gdd.stadiumMap.size()
        stadiums.each {Stadium stadium ->
            assert stadium.latitude > 25 && stadium.latitude < 48
            assert stadium.longitude > -123 && stadium.longitude < 48
        }
    }

    @Test
    void testGetGame() {
        GameResult gr = gdd.getGame 'bos','col','1'
        assert 'Boston Red Sox' == gr.away
        assert 'Colorado Rockies' == gr.home
        assert 4 == gr.aScore.toInteger()
        assert 3 == gr.hScore.toInteger()
    }

    @Test
    void outputGameResultsAsXml() {
        def results = gdd.getGames()
        MarkupBuilder builder = new MarkupBuilder()
        builder.games {
            results.each {g ->
                game (
                        outcome: "$g.away $g.aScore, $g.home, $g.hScore",
                        lat: "${g?.stadium?.latitude}",
                        lon: "${g?.stadium?.longitude}"
                )
            }
        }
        builder.print(System.out)
    }
}
