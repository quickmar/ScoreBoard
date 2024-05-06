package match;


/**
 * Definition of the match.
 * The match is the main entity that encapsulates all changes, events, results, etc. of the competition between two Teams.
 */
public interface Match {

    static FootballMatch createFootballMatch(Team homeTeam, Team awayTeam, MatchChangeHandler handler) {
        return new FootballMatch(new ResultBar(homeTeam, awayTeam), handler);
    }

    /**
     * Represents a current state of the result of the {@link Match}
     *
     * @param homeTeamScore Score of the home team
     * @param awayTeamScore Score of the away team
     */
    record Result(Team homeTeam, Team awayTeam, int homeTeamScore, int awayTeamScore) {
        /**
         * Calculate total of Home {@link Team} Score and Away {@link Team} Score
         *
         * @return sum of teams scores
         */
        public int totalScore() {
            return homeTeamScore + awayTeamScore;
        }

        /**
         * Gets description of the {@link Match} in format HomeTeam int - AwayTeam int.
         * For example, when Uruguay plays against Italy and the result is 1-1,
         * then the description should be in "Uruguay 1 - Italy 1"
         *
         * @return {@link Match} description
         */
        public String description() {
            return new StringBuilder()
                    .append(homeTeam.name())
                    .append(" ")
                    .append(homeTeamScore)
                    .append(" - ")
                    .append(awayTeam.name())
                    .append(" ")
                    .append(awayTeamScore)
                    .toString();
        }
    }

    /**
     * Describe current state of the {@link Match}.
     */
    enum Status {
        /**
         * {@link Match} is created. After is started changes to {@link Match} are not possible.
         */
        CREATED,
        /**
         * {@link Match} is in progress. Changes to the {@link Match} state are possible.
         */
        RUNNING,
        /**
         * {@link Match} is ended. The {@link Match} begin immutable and update of state is impossible.
         */
        FINISHED
    }

    /**
     * Starts the match. And set {@link Status} to RUNNING
     */
    void begin() throws NotModifalbleMatchException;

    /**
     * Ends the match. And set {@link Status} to FINISHED
     */
    void finish() throws NotModifalbleMatchException;

    /**
     * Overrides values of the team scores
     *
     * @param homeTeamScore New score of the home team
     * @param awayTeamScore New score of the away team
     */
    void updateScore(int homeTeamScore, int awayTeamScore) throws NotModifalbleMatchException;

    /**
     * Gets current result of the match
     *
     * @return {@link Result}
     */
    Result getResult();

    /**
     * Gets current status of the match
     *
     * @return {@link Status}
     */
    Status getStatus();
}
