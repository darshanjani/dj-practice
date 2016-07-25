package ch02

/**
 * Created by Darshan on 7/17/16.
 */
class GameResult {
    String home
    String away
    String hScore
    String aScore
    Stadium stadium

    String toString() { "$home, $hScore, $away, $aScore" }
}
