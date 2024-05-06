package scoreboards;


import match.FootballMatch;
import match.Match;
import match.ResultBar;
import match.Team;
import scoreboard.Scoreboard;

final class ScoreBoardFactoryInternal implements Scoreboards.ScoreBoardFactory {
    private final Scoreboard scoreboard;

    public ScoreBoardFactoryInternal() {
        this.scoreboard = new Scoreboard();
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
 * Facade for interaction with {@link Scoreboard} and {@link Match}
 */
public interface Scoreboards {
    /**
     * Definition of {@link Scoreboard} Factory. Instance of this will construct {@link Scoreboard}.
     * And is able to provide new {@link Match} associated with {@link Scoreboard}.
     */
    sealed interface ScoreBoardFactory permits ScoreBoardFactoryInternal {
        /**
         * Gets {@link Scoreboard}
         *
         * @return {@link Scoreboard}
         */
        Scoreboard getScoreboard();

        /**
         * Creates {@link Match} associated with {@link Scoreboard}
         *
         * @param homeTeamName name of a home team
         * @param awayTeamName name of an away team
         * @return new {@link Match} in {@link match.Match.Status} CREATED
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
