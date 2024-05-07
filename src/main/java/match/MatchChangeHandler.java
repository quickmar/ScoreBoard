package match;

import scoreboards.Match;

/**
 * Handler that let handel notification when this {@link Match} is finish.
 */
public interface MatchChangeHandler {
    /**
     * Called by given {@link Match} when it is beginning
     *
     * @param match {@link Match} witch has been begun
     */
    void onBegin(Match match);

    /**
     * Called by given {@link Match} when it is finished
     *
     * @param match {@link Match} witch is finalizing
     */
    void onFinish(Match match);
}
