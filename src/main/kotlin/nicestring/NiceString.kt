package nicestring


fun String.isNice(): Boolean {
    // Check for condition #1, invalid substring
    val invalidSubstring: Boolean = this.zipWithNext { f, s ->
        f == 'b' && (s == 'u' || s == 'a' || s == 'e')}.contains(true)

    // Check for condition #2, three vowels required
    val vowelCount: Int = this.count { it in "aeiou" }
    val invalidVowels: Boolean = vowelCount < 3

    // Check for condition #3, double letter sequence required
    val q = this.zipWithNext { f, s -> f == s }
        .count { it }
    val invalidDoubleLetter: Boolean = q < 1

    // Count the number of passing conditions
    val invalidConditions: List<Boolean> = listOf(invalidSubstring, invalidVowels, invalidDoubleLetter)
    val totalPassing = invalidConditions.count { !it }

    // There need to be >=2 passing conditions for the string to be a "nice" string.
    return totalPassing > 1
}

fun main() {
    println("bqcuu".isNice())
}
