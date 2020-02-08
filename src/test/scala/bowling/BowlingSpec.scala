package bowling

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class BowlingSpec extends FlatSpec with Matchers {
    "Bowling Zero" should "give 0 points" in {

        val gs = (0 to 5).foldLeft(GameState.empty) ( (ac, b) => GameImpl.roll(ac, 0) )
        println (gs)
        GameImpl.score(gs) shouldBe(0)
    }

}