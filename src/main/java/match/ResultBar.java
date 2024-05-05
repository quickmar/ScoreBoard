package match;

/**
 * Keeps track of the result of {@link Match} between Home Team and Away Team.
 */
public class ResultBar {
    private int homeTeamScore;
    private int awayTeamScore;
    private final Match.Team homeTeam;
    private final Match.Team awayTeam;

    /**
     * Creates new {@link ResultBar}.
     *
     * @param homeTeam {@link Match.Team} which is playing on it own ground
     * @param awayTeam {@link  Match.Team} playing away from its home ground
     */
    public ResultBar(Match.Team homeTeam, Match.Team awayTeam) {
        this.awayTeam = awayTeam;
        this.homeTeam = homeTeam;
        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
    }

    /**
     * Gets current {@link Match.Result} of the {@link Match}
     *
     * @return {@link Match.Result}
     */
    public Match.Result getResultSummary() {
        return new Match.Result(homeTeam, awayTeam, homeTeamScore, awayTeamScore);
    }

    /**
     * Sets new score of Home Team.
     */
    public void setHomeTeamScore(int homeTeamScore) {
        assertScoreNotSmaller(this.homeTeamScore, homeTeamScore);
        this.homeTeamScore = homeTeamScore;
    }

    /**
     * Sets new score of Away Team.
     */
    public void setAwayTeamScore(int awayTeamScore) {
        assertScoreNotSmaller(this.awayTeamScore, awayTeamScore);
        this.awayTeamScore = awayTeamScore;
    }

    private static void assertScoreNotSmaller(int currentScore, int newScore) {
        assert newScore - currentScore > 0;
    }
}
