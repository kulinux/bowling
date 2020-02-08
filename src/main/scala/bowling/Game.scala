package bowling

import cats.data.`package`.State


case class Roll(pins: Int)
case class Frame(roll1: Option[Roll], roll2: Option[Roll])
case class TenthFrame(extraRolls: Seq[Roll])
case class GameState(frames: Seq[Frame])

object Frame {
    def first(pins: Int) = Frame(Some(Roll(pins)), Option.empty)
}
object GameState {
    val empty = GameState(Seq())
}

trait Game {
    def isFinished(fr: Frame): Boolean = {
        fr match {
            case Frame(Some(Roll(r1)), None) if r1 == 10 => true
            case Frame(Some(_), Some(_)) => true
            case _ => false
        }
    }
    def roll(gs: GameState, pins: Int): GameState = {
        val another : Boolean =
            gs.frames.lastOption.map( isFinished(_) )
            .getOrElse(true)

        if(another) {
            GameState(gs.frames :+ Frame.first(pins))
        } else {
            val last = gs.frames.last
            GameState(gs.frames.init :+ Frame(last.roll1, Some(Roll(pins))))
        }

    }

    def score(state: GameState): Int = 0
}

object GameImpl extends Game
