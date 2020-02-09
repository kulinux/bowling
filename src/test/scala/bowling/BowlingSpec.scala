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
        GameImpl.score(first) shouldBe(10)

        val second = GameImpl.roll(first, 5, 2)
        GameImpl.score(second) shouldBe(22)
    }
    "Bowling Zero" should "work with sample" in {
        val score = Seq(
            ((1, 4), 5),
            ((4, 5), 14),
            ((6, 4), 29),
            ((5, 5), 40),
            ((1, 0), 41)
        )

        def partida(round: Int) = score.take(round + 1).foldLeft(GameState.empty)(
            (acc, ele) => GameImpl.roll(acc, ele._1._1, ele._1._2)
        )

        GameImpl.score(partida(5)) shouldBe(score(5 - 1)._2)

    }

}