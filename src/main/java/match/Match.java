package match;


/**
 * Definition of the match.
 * The match is the main entity that encapsulates all changes, events, results, etc. of the competition between two Teams.
 */
public interface Match {

    /**
     * Exception that is thrown whenever state of {@link Match} is changed after it is over.
     */
    class NotModifalbleAfterFinishException extends Exception {
        public NotModifalbleAfterFinishException(Match match) {
            super(match.getDescription() + " has been finished. Can not update state of this match.");
        }
    }

    /**
     * Represents a current state of the result of the {@link Match}
     *
     * @param homeTeamScore Score of the home team
     * @param awayTeamScore Score of the away team
     */
    record Result(int homeTeamScore, int awayTeamScore) {
    }

    /**
     * Describe current state of the {@link Match}.
     */
    enum Status {
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
     * Ends the match. And set {@link Status} to FINISHED
     */
    void finish();

    /**
     * Overrides values of the team scores
     *
     * @param homeTeamScore New score of the home team
     * @param awayTeamScore New score of the away team
     */
    void updateScore(int homeTeamScore, int awayTeamScore) throws NotModifalbleAfterFinishException;

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

    /**
     * Gets description of the {@link Match} in format HomeTeam int - AwayTeam int.
     * For example, when Uruguay plays against Italy and the result is 1-1,
     * then the description should be in "Uruguay 1 - Italy 1"
     *
     * @return {@link Match} description
     */
    String getDescription();
}
