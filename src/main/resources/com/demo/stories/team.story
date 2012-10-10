Scenario: Score reading from file
Given we have the input score from file football.dat
When the final score is calculated
Then team with lowest difference goals will be Leicester with score of -34

Scenario: Calculate score for each team
Given team <team_name> with no for score of <F> and against score of <A>
When team complete all the matches
Then score difference should be <diff> with lowest indicator value of <lowest>

Examples:
| no  | team_name     | P  | W  |  L |  D | F   | A  | Pts|diff|lowest  |
|  1. | Arsenal       | 38 | 26 |  9 |  3 | 79  | 36 | 87 | 43 |false   |
|  2. | Liverpool     | 38 | 24 |  8 |  6 | 67  | 30 | 80 | 37 |false   |
|  3. | Manchester_U  | 38 | 24 |  5 |  9 | 87  | 45 | 77 | 42 |false   |
|  4. | Newcastle     | 38 | 21 |  8 |  9 | 74  | 52 | 71 | 22 |false   |
|  5. | Leeds         | 38 | 18 | 12 |  8 | 53  | 37 | 66 | 16 |false   |
|  6. | Chelsea       | 38 | 17 | 13 |  8 | 66  | 38 | 64 | 28 |false   |
|  7. | West_Ham      | 38 | 15 |  8 | 15 | 48  | 57 | 53 | -9 |false   |
|  8. | Aston_Villa   | 38 | 12 | 14 | 12 | 46  | 47 | 50 | -1 |false   |
|  9. | Tottenham     | 38 | 14 |  8 | 16 | 49  | 53 | 50 | -4 |false   |
| 10. | Blackburn     | 38 | 12 | 10 | 16 | 55  | 51 | 46 |  4 |false   |
| 11. | Southampton   | 38 | 12 |  9 | 17 | 46  | 54 | 45 | -8 |false   |
| 12. | Middlesbrough | 38 | 12 |  9 | 17 | 35  | 47 | 45 | -12|false   |
| 13. | Fulham        | 38 | 10 | 14 | 14 | 36  | 44 | 44 | -8 |false   |
| 14. | Charlton      | 38 | 10 | 14 | 14 | 38  | 49 | 44 | -11|false   |
| 15. | Everton       | 38 | 11 | 10 | 17 | 45  | 57 | 43 | -12|false   |
| 16. | Bolton        | 38 | 9  | 13 | 16 | 44  | 62 | 40 | -18|false   |
| 17. | Sunderland    | 38 | 10 | 10 | 18 | 29  | 51 | 40 | -22|false   |
| 18. | Ipswich       | 38 | 9  |  9 | 20 | 41  | 64 | 36 | -23|false   |
| 19. | Derby         | 38 | 8  |  6 | 24 | 33  | 63 | 30 | -30|false   |
| 20. | Leicester     | 38 | 5  | 13 | 20 | 30  | 64 | 28 | -34|true    |