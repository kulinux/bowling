package bowling

import monocle.macros.GenLens

import monocle.function.all._


case class Roll(pins: Int)
case class Frame(roll1: Option[Roll], roll2: Option[Roll])
case class TenthFrame(extraRolls: Seq[Roll])
case class GameState(frames: Seq[Frame])

object DataImplicits {
    implicit class RollImpl(roll: Option[Roll]) {
        def is10() = roll.filter(_.pins == 10).isDefined
        def sum() = roll.map(_.pins).getOrElse(0)
    }
    implicit class FrameImpl(frame: Frame) {
        def isStrike() = frame.roll1.is10()
        def isSpare() = ( frame.roll1.sum() + frame.roll2.sum() ) == 10
        def sum() = {
            if( isSpare() ) 10
            else frame.roll1.sum() + frame.roll2.sum()
        }
        def isFinished(): Boolean = {
            frame match {
                case Frame(Some(Roll(r1)), None) if r1 == 10 => true
                case Frame(Some(_), Some(_)) => true
                case _ => false
            }
        }
    }
}

object Frame {
    def first(pins: Int) = Frame(Some(Roll(pins)), Option.empty)
}
object GameState {
    val empty = GameState(Seq())
}

trait Game {
    import DataImplicits._

    def roll(gs: GameState, pin1: Int, pin2: Int): GameState = {
        roll( roll(gs, pin1), pin2 )
    }
    def roll(gs: GameState, pins: Int): GameState = {
        val another : Boolean =
            gs.frames.lastOption.map( _.isFinished() )
            .getOrElse(true)

        val frames = GenLens[GameState](_.frames)

        if(another) {
            frames.modify(_ :+ Frame.first(pins))(gs)
        } else {
            val last = gs.frames.last
            frames.modify(fr => fr.init :+ Frame(last.roll1, Some(Roll(pins))))(gs)
        }

    }


    def score(state: GameState): Int = {

        def aheadSum(head: Frame, tail: Seq[Frame]): Int = {
            if(head.isStrike()) {
                head.sum() +
                    tail.headOption.map(f => f.roll1.sum()).getOrElse(0) +
                    tail.headOption.map(f => f.roll2.sum()).getOrElse(0)

            } else if(head.isSpare()) {
                head.sum() + tail.headOption.map(f => f.roll1.sum()).getOrElse(0)
            } else head.sum()
        }
        def score(ahead: Seq[Frame]): Int = {
            ahead match {
                case head :: tail =>  {
                    aheadSum(head, tail) + score(tail)
                }
                case Nil => 0
            }
        }

        score(state.frames)
    }
}

object GameImpl extends Game
