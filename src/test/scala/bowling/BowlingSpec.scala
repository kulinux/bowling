package bowling

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class BowlingSpec extends FlatSpec with Matchers {
    "Bowling Zero" should "give 0 points with empty" in {
        val gs = (1 to 20).foldLeft(GameState.empty) ( (ac, b) => GameImpl.roll(ac, 0) )
        GameImpl.score(gs) shouldBe(0)
    }
    "Bowling Zero" should "20 points with 1 each" in {
        val gs = (1 to 20).foldLeft(GameState.empty) ( (ac, b) => GameImpl.roll(ac, 1) )
        GameImpl.score(gs) shouldBe(20)
    }

    "Bowling Zero" should "sum spare" in {
        val gs = GameState.empty
        val first = GameImpl.roll(gs, 5, 5)
        GameImpl.score(first) shouldBe(0)

        val second = GameImpl.roll(first, 5, 2)
        GameImpl.score(second) shouldBe(14)

    }

}