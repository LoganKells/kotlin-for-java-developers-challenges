package mastermind

import java.lang.Integer.max

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuessFunctional(secret: String, guess: String): Evaluation {
    // Count the number of letters that occur at the same place as in the 'secret' and the 'guess'
    val rightPositions = secret.zip(guess).count { (secretChar, guessChar) -> secretChar == guessChar }

    // Count number of letters guessed correctly, but where the position of the correctly guessed letter is incorrect.
    // .sumof results in the following when secret=BCDF and guess=ACEB
    // A: 0, 1, -> 0
    // B: 1, 1, -> 1
    // C: 1, 1 -> 1
    // D: 1, 0 -> 0
    // E: 0, 1 -> 0
    // F: 1, 0 -> 0
    // then .sumof() = 1 + 1 = 2
    val commonLetters = "ABCDEF".sumOf { ch ->
        Math.min(secret.count { secretChar -> ch == secretChar }, guess.count { guessChar -> ch == guessChar })
    }

    // The wrong position count is always less than the number of letters in common (no additional 'wrong' counting
    // when the same letter is repeated in the guess)
    // See rules - https://en.wikipedia.org/wiki/Mastermind_(board_game)
    val wrongPositions: Int = commonLetters - rightPositions

    return Evaluation(rightPositions, wrongPositions)
}

fun evaluateGuess(secret: String, guess: String): Evaluation {
    // Counters
    var rightPosition = 0
    var wrongPosition = 0
    val wrongPositionChar = HashMap<Char, Int>()
    val rightPositionChar = HashMap<Char, Int>()
    val secretChar = HashMap<Char, Int>()

    // We need a count of the number of each character in the 'secret'. We can only iterate wrongPosition
    // for as many counts of characters exist in the 'secret'.
    //
    // E.g.
    // Wrong evaluation for secret=ABCD, guess=EAAA
    // Expected Result: Evaluation(rightPosition=0, wrongPosition=1)
    //
    // There is more information of how scoring works for Mastermind in the wikipedia page:
    // https://en.wikipedia.org/wiki/Mastermind_(board_game), which includes this:
    // If there are duplicate colours in the guess, they cannot all be awarded a key peg unless
    // they correspond to the same number of duplicate colours in the hidden code.
    for (n: Char in secret) {
        secretChar.putIfAbsent(n, 0)
        rightPositionChar.putIfAbsent(n, 0)
        secretChar[n] = secretChar[n]!! + 1
    }
    for (n: Char in guess) {
        if (n in secretChar.keys) {
            wrongPositionChar.putIfAbsent(n, 0)
        }

    }

    for ((i: Int, n: Char) in guess.withIndex()) {
        if (secret[i] == guess[i]) {
            rightPositionChar.putIfAbsent(n, 0)
            rightPositionChar[n] = rightPositionChar[n]!! + 1
            rightPosition += 1
        } else {
            if (n in secret) {
                wrongPositionChar.putIfAbsent(n, 0)
                if (wrongPositionChar[n]!! < secretChar[n]!!) {
                    wrongPositionChar[n] = wrongPositionChar[n]!! + 1
                }
            }
        }
    }
    var secretCount: Int
    var rightPositionCount: Int
    for ((n: Char, wrongPositionCount: Int) in wrongPositionChar.entries) {
        secretCount = secretChar[n]!!
        rightPositionCount = rightPositionChar[n]!!
        if (wrongPositionCount + rightPositionCount <= secretCount) {

            wrongPosition += wrongPositionCount
        } else {
            wrongPosition += max(0, (wrongPositionCount - rightPositionCount))
        }

    }

    return Evaluation(rightPosition, wrongPosition)
}

fun main() {
    /* This main() function contains some examples of calling evaluateGuess(secret, guess) */
    var mySecret: String
    var myGuess: String
    var result: Evaluation
    var expectedResult: Evaluation

    // Example 1
    mySecret = "ABCD"
    myGuess = "ABCD"
    result = evaluateGuess(mySecret, myGuess)
    expectedResult = Evaluation(4, 0)
    evaluateGuessFunctional(mySecret, myGuess) eq expectedResult
    print("Example 1 Expected: $expectedResult\n")
    print("Example 1: $result\n\n")

    // Validation 1
    mySecret = "ABCD"
    myGuess = "EAAA"
    result = evaluateGuess(mySecret, myGuess)
    expectedResult = Evaluation(0, 1)
    evaluateGuess(mySecret, myGuess) eq expectedResult
    print("Validation 1 Expected: $expectedResult\n")
    print("Validation 1 Actual: $result\n\n")

    // Validation 2
    mySecret = "AABC"
    myGuess = "ADFA"
    result = evaluateGuess(mySecret, myGuess)
    expectedResult = Evaluation(1, 1)
    evaluateGuess(mySecret, myGuess) eq expectedResult
    print("Validation 2 Expected: $expectedResult\n")
    print("Validation 2 Actual: $result\n\n")

    // Validation 3
    mySecret = "BDAD"
    myGuess = "AAAE"
    result = evaluateGuess(mySecret, myGuess)
    expectedResult = Evaluation(1, 0)
    evaluateGuess(mySecret, myGuess) eq expectedResult
    print("Validation 3 Expected: $expectedResult\n")
    print("Validation 3 Actual: $result\n\n")

    // Validation 4
    mySecret = "ECDE"
    myGuess = "CEEE"
    result = evaluateGuess(mySecret, myGuess)
    expectedResult = Evaluation(1, 2)
    evaluateGuess(mySecret, myGuess) eq expectedResult
    print("Validation 4 Expected: $expectedResult\n")
    print("Validation 4 Actual: $result\n\n")
}

infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}
