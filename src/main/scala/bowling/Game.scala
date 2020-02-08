package bowling



trait Game {
    def roll(pins: Int): Unit
    def score(): Int
}