package scoreboards;

import match.MatchChangeHandler;

import java.util.List;

/**
 * Definition of the Scoreboard. An entity that measures progress of {@link Match}'s.
 */
public interface Scoreboard extends MatchChangeHandler {
    /**
     * Get {@link List} of matches sorted first by the total score of Home {@link match.Team} and Away {@link match.Team}.
     * Then {@link Match}'s are sorted by most recently started.
     *
     * @return {@link List} of {@link Match.Result}'s.
     */
    List<Match.Result> getSummary();
}
