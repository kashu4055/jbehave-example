# Simple example of how to use JBehave for BDD testing in Java
This is my implementation of this [coding problem](http://goo.gl/RNnBb)

## Installation

First thing first you will need Maven

```
$mvn clean install
```

Then import to your favourite IDE (mined is Eclipse)

```
$mvn eclipse:eclipse 
```

then from Eclipse, File->Import 

## Story and step definition files

```
src/main/resources/com/demo/stories/team.story
```

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

Step definition file:

```
src/main/java/com/demo/steps/TeamSteps.java
```

    public class TeamSteps {

      private TeamRank ranking;

      private Map<String, Team> scoreMap;

      private Team teamWithLowestDiff;

      @Given("we have the input score from file $scoreFile")
      public void readInputFromFile(@Named("scoreFile") String scoreFile) {

        ranking = new TeamRank();

        try {
          scoreMap = ranking.extractScores(scoreFile);
        } catch (Exception e) {
          e.printStackTrace();
          Assert.fail("Unexpected exception");
        }
      }

      @When("the final score is calculated")
      public void whenTheFinalScoreIsCalculated() {
        teamWithLowestDiff = ranking.lowestDiffScore(scoreMap);
      }

      @Then("team with lowest difference goals will be $teamName with score of $scoreDiff")
      public void scoreResult(@Named("teamName") String teamName, @Named("scoreDiff") int scoreDiff) {
        Assert.assertEquals(teamName, teamWithLowestDiff.getName());
        Assert.assertEquals(scoreDiff, teamWithLowestDiff.diffScore());
      }

      private Team currentTeam = new Team();

      @Given("team <team_name> with no for score of <F> and against score of <A>")
      public void inputTeamScore(@Named("team_name") String teamName,
          @Named("F") int forScore, @Named("A") int againstScore) {
        currentTeam = new Team(teamName, forScore, againstScore);
      }

      @When("team complete all the matches")
      public void whenTeamCompleteAllTheMatches() {
        teamWithLowestDiff = ranking.lowestDiffScore(scoreMap);
      }

      @Then("score difference should be <diff> with lowest indicator value of <lowest>")
      public void outcome(@Named("diff") int diffScore, @Named("lowest") String isLowest) {
        Assert.assertEquals(diffScore, currentTeam.diffScore());
        Assert.assertEquals(isLowest, isTheSameName(currentTeam, teamWithLowestDiff));
      }

      // TODO: this should be done using JBehave's parameter converters
      // see: http://jbehave.org/reference/preview/parameter-converters.html
      private String isTheSameName(Team team1, Team team2) {
        if (team1.getName().equals(team2.getName())) {
          return "true";
        } else {
          return "false";
        }
      }

## Notes

To make log4j work with properly with JBehave, 
i had to added the following line to the POM file

    <plugins>
      <plugin>
        <groupId>org.jbehave</groupId>
        <artifactId>jbehave-maven-plugin</artifactId>
        <version>${jbehave.core.version}</version>
        <dependencies>
          <dependency>
          <groupId>log4j</groupId>
          <artifactId>log4j</artifactId>
          <version>1.2.16</version>
         </dependency>
        </dependencies>
        ...
      </plugin>
    </plugins> 

## Author

[Burin Choomnuan](https://github.com/agilecoders)