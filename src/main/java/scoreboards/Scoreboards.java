package scoreboards;


import match.FootballMatch;
import match.ResultBar;
import match.Team;
import scoreboard.ScoreboardInternal;

final class ScoreBoardFactoryInternal implements Scoreboards.ScoreBoardFactory {
    private final ScoreboardInternal scoreboard;

    public ScoreBoardFactoryInternal() {
        this.scoreboard = new ScoreboardInternal();
    }

    @Override
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    @Override
    public Match createFootballMatch(String homeTeamName, String awayTeamName) {
        var resultBar = new ResultBar(new Team(homeTeamName), new Team(awayTeamName));
        return new FootballMatch(resultBar, this.scoreboard);
    }
}

/**
 * Facade for interaction with {@link ScoreboardInternal} and {@link Match}
 */
public interface Scoreboards {
    /**
     * Definition of {@link ScoreboardInternal} Factory.
     * Instance of this factory will construct {@link Scoreboard} internally.
     * And is able to provide new {@link Match} associated with {@link Scoreboard}.
     */
    sealed interface ScoreBoardFactory permits ScoreBoardFactoryInternal {
        /**
         * Gets {@link ScoreboardInternal}
         *
         * @return {@link ScoreboardInternal}
         */
        Scoreboard getScoreboard();

        /**
         * Creates {@link Match} associated with {@link ScoreboardInternal}
         *
         * @param homeTeamName name of a home team
         * @param awayTeamName name of an away team
         * @return new {@link Match} in {@link Match.Status} CREATED
         */
        Match createFootballMatch(String homeTeamName, String awayTeamName);
    }

    /**
     * Creates new {@link ScoreBoardFactory}
     *
     * @return {@link ScoreBoardFactory}
     */
    static ScoreBoardFactory createFactory() {
        return new ScoreBoardFactoryInternal();
    }
}
