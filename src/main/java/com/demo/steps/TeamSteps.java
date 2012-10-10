package com.demo.steps;

import java.util.Map;

import junit.framework.Assert;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

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

}
