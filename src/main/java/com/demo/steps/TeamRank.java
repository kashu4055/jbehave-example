package com.demo.steps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class TeamRank {

	private static final Logger logger = Logger.getLogger(TeamRank.class);

	/**
	 * The valid input length of the line with team and score information.
	 *
	 * e.g. input line like
	 * <code> 1. Arsenal         38    26   9   3    79  -  36    87</code>
	 * after split should have the value of
	 *
	 * <code>["","1.","Arsenal","38","26","9","3","79","-","36","87"]</code>
	 * which have the length of 11.
	 */
	final public static int VALID_INPUT_LENGTH = 11;

	/**
	 * The array index that contain the team name value.
	 */
	final public static int VALID_INDEX_TEAM_NAME = 2;

	/**
	 * The array index that contain the 'score for' value.
	 */
	final public static int VALID_INDEX_TEAM_SCORE_FOR = 7;

	/**
	 * The array index that contain the 'score against' value.
	 */
	final public static int VALID_INDEX_TEAM_SCORE_AGAINST = 9;

	/**
	 * Return the associated <code>BufferedReader</code> object from a given
	 * <code>fileName</code>
	 *
	 * @param fileName
	 *            input file
	 * @return
	 * @throws Exception
	 */
	protected BufferedReader getFileReader(String fileName) throws Exception {

		String message;

		if (fileName == null) {
			throw new NullPointerException("fileName must not be null");
		}

		File inputFile = new File(fileName);

		if (!inputFile.exists() || !inputFile.canRead()) {
			message = String.format(
					"Input file %s is either not exist or not readable.",
					fileName);
			logger.error(message);
			throw new IllegalArgumentException(message);
		}

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			message = String.format("Problem processing %s", inputFile);
			logger.error(message, e);
			throw new Exception(message, e);
		}

		return reader;
	}
	/**
	 * Process the input file and calculate the team with lowest delta score
	 *
	 * @param fileName
	 *            the input file for e.g. <code>football.dat</code>
	 * @return <code>Team</code> with lowest delta score
	 * @throws Exception
	 *             will be thrown if we can't process the input file or any
	 *             other conditions
	 */
	public Map<String, Team> extractScores(String fileName)
			throws Exception {

		BufferedReader reader = getFileReader(fileName);

		Map<String, Team> scoreMap = new HashMap<String, Team>();

		String inputLine = null;

		try {
			while ((inputLine = reader.readLine()) != null) {

				String[] inputList = inputLine.split("\\s+", 0);

				Team team = extractScore(inputList);

				// only process the value if it contain a valid score line
				if (team.isValidScoreLine()) {
					scoreMap.put(team.getName(), team);
				} else {
					// Note: for testing purpose, can be remove if required.
					logger.debug(String.format(
							"FYI: [%s] is not valid, will be not process",
							team));
				}
			}
		} catch (IOException e) {
			String message = "Unexpected exception";
			logger.error(message, e);
			throw new Exception(message, e);
		}

		return scoreMap;
	}

	/**
	 * Extract the <code>Team</code> from a given <code>inputList</code>
	 *
	 * @param inputList the full list of field extract from each score line
	 * @return default non-null <code>Team</code> object if the input is
	 *         non-null.
	 *
	 *         If the <code>inputList</code> contain the valid value for the
	 *         score e.g. have valid length, have score for and score against as
	 *         valid number then the <code>Team</code> will be set
	 *         appropriately for the return value.
	 */
	public Team extractScore(String[] inputList) {

		if (inputList == null) {
			throw new NullPointerException("inputList must not be null.");
		}

		Team team = new Team();

		if (inputList.length == VALID_INPUT_LENGTH) {

			String teamName = inputList[VALID_INDEX_TEAM_NAME];
			String forColumn = inputList[VALID_INDEX_TEAM_SCORE_FOR];
			String againstColumn = inputList[VALID_INDEX_TEAM_SCORE_AGAINST];

			try {
				// extract score information from input
				int scoreFor = Integer.parseInt(forColumn);
				int scoreAgainst = Integer.parseInt(againstColumn);
				// save the value to appropriate field.
				team.setName(teamName);
				team.setWin(scoreFor);
				team.setLose(scoreAgainst);

			} catch (NumberFormatException e) {
				// ignore invalid score and don't save any data to the bean
				logger.warn("Invalid data found, ignore input.");
			}
		} else {
			// Note: useful for debugging purpose
			logger.debug(String.format(
					"FYI: inputLength of %s is not valid e.g. not equal %s",
					inputList.length, VALID_INPUT_LENGTH));
		}

		return team;
	}

	/**
	 * Return the team which have the lowest difference in for/against goals.
	 *
	 * @param scoreMap
	 * @return
	 */
	public Team lowestDiffScore(Map<String, Team> scoreMap) {

		Set<Map.Entry<String, Team>> entries = scoreMap.entrySet();

		Team teamWithLowestDiff = null;

		int lowestDiffSoFar = Integer.MAX_VALUE;

		for (Entry<String, Team> entry : entries) {

			Team teamEntry = entry.getValue();

			if (teamEntry.diffScore() < lowestDiffSoFar) {
				teamWithLowestDiff = teamEntry;
				lowestDiffSoFar = teamEntry.diffScore();
			}
		}

		return teamWithLowestDiff;
	}

}
