# Kotlin for Java Developers Challenges
This repo contains several challenge problems implemented using Kotlin from the 
**Kotlin for Java Developers** course.

#### References:
1. https://www.coursera.org/learn/kotlin-for-java-developers/home/welcome

## Challenge #1 Mastermind game

[Mastermind](https://en.wikipedia.org/wiki/Mastermind_(board_game)) is a board game for two players.
The first player invents a code and the second player tries to guess this code.
A code is made up 4 coloured pins and their position.
There are 6 colours to choose from and the same colour can be repeated multiple times.

Examples of different codes:

* Red Green Blue Yellow
* Red Green Yellow Blue
* Black White Red Orange
* Red Red Blue White

(note that while the first two codes use the same colours, they are different as Blue and Yellow occupy different positions)

The game play is as follows:

The second player (the one that is guessing) sets out a series of pins in order to guess the code.
The first player (that defined the code) then provides some feedback to the player in light of how close they are to the correct combination.

The feedback is as follows:

- Number of pins that are both the right colour and position
- Number of pins that are correct in colour but in the wrong position

Note that the rest pins in the code will pins that are neither correct in colour or position.

Your task is to evaluate a guess made by player two of the code set by player one.
For the sake of simplicity, we use  uppercase letters from A to F instead of colours.

You can test your solution and play as a second player using `playMastermind`.

### Different Letters

#### Example 1

Secret `ABCD` and guess `ABCD` must be evaluated to: `rightPosition = 4, wrongPosition = 0`.
All letters are guessed correctly in respect to their positions.

#### Example 2

Secret `ABCD` and guess `CDBA` must be evaluated to: `rightPosition = 0, wrongPosition = 4`.
All letters are guessed correctly, but none has the right position.

#### Example 3

Secret `ABCD` and guess `ABDC` must be evaluated to: `rightPosition = 2, wrongPosition = 2`.
`A` and `B` letters and their positions are guessed correctly.
`C` and `D` letters are guessed correctly, but their positions are wrong.

At first, you can implement this easier task, when all the letters are different,
and only after that start with the next part, when letters can be repeated.
Run `MastermindTestDifferentLetters` to make sure you've implemented this part correctly.

### Repeated Letters

#### Example 4

Secret `AABC` and guess `ADFE` must be evaluated to: `rightPosition = 1, wrongPosition = 0`.
`A` at the first position is guessed correctly with its position.
If we remove the first `A` from consideration (comparing the remaining `ABC` and
`DFE` only), that will give us no more common letters or positions.

#### Example 5

Secret `AABC` and guess `ADFA` must be evaluated to: `rightPosition = 1, wrongPosition = 1`.
The first `A` letter is guessed correctly with its position. If we remove this letter from consideration
(comparing the remaining `ABC` and `DFA`), we find the second `A` letter which is guessed correctly
but stays at the wrong position.

#### Example 6

Secret `AABC` and guess `DFAA` must be evaluated to: `rightPosition = 0, wrongPosition = 2`.
No letters are guessed correctly concerning their positions.
When we compare the letters without positions, `A` is guessed correctly.
Since `A` is present twice in both guess and secret, it must be counted two times.

#### Example 7

Secret `AABC` and guess `DEFA` must be evaluated to: `rightPosition = 0, wrongPosition = 1`.
The letter `A` occurs only once in the second string, that's why it counted only once as staying at the wrong position.

After implementing the task for repeated letters, run `MastermindTestDifferentLetters` to make sure
it works correctly.

## Challenge #2 Nice String

A string is nice if *at least two* of the following conditions are satisfied:

1. It doesn't contain substrings `bu`, `ba` or `be`;
2. It contains at least three vowels (vowels are `a`, `e`, `i`, `o` and `u`);
3. It contains a double letter (at least two similar letters following one
   another), like `b` in `"abba"`.

Your task is to check whether a given string is nice.
Strings for this task will consist of lowercase letters only.
Note that for the purpose of this task, you don't need to consider 'y' as a vowel.

Note that any two conditions might be satisfied to make a string nice.
For instance, `"aei"` satisfies only the conditions #1 and #2, and
```"nn"` satisfies the conditions #1 and #3, which means both strings are nice.

#### Example 1

`"bac"` isn't nice. No conditions are satisfied: it contains a `ba` substring,
contains only one vowel and no doubles.

#### Example 2

`"aza"` isn't nice. Only the first condition is satisfied, but the string
doesn't contain enough vowels or doubles.

#### Example 3

`"abaca"` isn't nice. The second condition is satisfied: it contains three
vowels `a`, but the other two aren't satisfied: it contains `ba` and no
doubles.

#### Example 4

`"baaa"` is nice. The conditions #2 and #3 are satisfied: it contains
three vowels `a` and a double `a`.

#### Example 5

`"aaab"` is nice, because all three conditions are satisfied.

Run `TestNiceString` to check your solution.

## Challenge #3 Taxi Park

The `TaxiPark` class stores information about registered drivers, passengers,
and their trips. Your task is to implement six functions which collect
different statistics about the data.

#### Task 1

```
fun TaxiPark.findFakeDrivers(): Collection<Driver>
```

Find all the drivers who didn't perform any trips.


#### Task 2

```
fun TaxiPark.findFaithfulPassengers(minTrips: Int): List<Passenger>
```

Find all the clients who completed at least the given number of trips.

#### Task 3

```
fun TaxiPark.findFrequentPassengers(driver: Driver): List<Passenger>
```

Find all the passengers who were driven by a certain driver more than once.

#### Task 4

```
fun TaxiPark.findSmartPassengers(): Collection<Passenger>
```

If we consider "smart", a passenger who had a discount for the majority of the trips they made or took part in
(including the trips with more than one passenger), find all the "smart" passengers.
A "smart" passenger should have strictly more trips with discount than trips without discount,
the equal amounts of trips with and without discount isn't enough.

Note that the discount can't be `0.0`, it's always non-zero if it's recorded.

#### Task 5

```
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange?
```

Find the most frequent trip duration period among minute periods 0..9, 10..19, 20..29, and so on.
Return any suitable period if many are the most frequent, return `null` if there're no trips.


#### Task 6

```
fun TaxiPark.checkParetoPrinciple(): Boolean
```

Check whether no more than 20% of the drivers contribute 80% of the income.
The function should return true if the top 20% drivers (meaning the top 20% best
performers) represent 80% or more of all trips total income, or false if not.
The drivers that have no trips should be considered as contributing zero income.
If the taxi park contains no trips, the result should be `false`.

For example, if there're 39 drivers in the taxi park, we need to check that no more than
20% of the most successful ones, which is seven drivers (39 * 0.2 = 7.8), contribute
at least 80% of the total income. Note that eight drivers out of 39 is 20.51% which
is more than 20%, so we check the income of seven the most successful drivers.

To find the total income sum up all the trip costs. Note that the discount is already
applied while calculating the cost.  
