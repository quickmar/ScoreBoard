import match.FootballMatch;
import match.Match;
import match.ResultBar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class FootballMatchTest {
    private static Match.Team HOME_TEAM;
    private static Match.Team AWAY_TEAM;

    private Match match;
    private ResultBar resultBar;
    private Match.MatchChangeHandler handler;

    @BeforeAll
    public static void beforeAll() {
        HOME_TEAM = new Match.Team("Home Team");
        AWAY_TEAM = new Match.Team("Away Team");
    }

    @BeforeEach
    public void beforeEach() {
        resultBar = mock(ResultBar.class);
        handler = mock(Match.MatchChangeHandler.class);
        match = new FootballMatch(resultBar, handler);
    }

    @Test
    public void shouldUpdatesScore() throws Match.NotModifalbleMatchException {
        match.begin();

        match.updateScore(1, 1);

        verify(resultBar, times(1)).setHomeTeamScore(anyInt());
        verify(resultBar, times(1)).setAwayTeamScore(anyInt());
    }

    @Test
    public void shouldThrowWhenUpdatesScoreOnNotStartedMatch() {
        when(resultBar.getResultSummary()).thenReturn(new Match.Result(HOME_TEAM, AWAY_TEAM, 0, 0));

        Assertions.assertThrows(Match.NotModifalbleMatchException.class, () -> match.updateScore(1, 1));
    }

    @Test
    public void shouldGetInitialStatus() {
        Assertions.assertEquals(Match.Status.CREATED, match.getStatus());
    }

    @Test
    public void shouldBeginMatch() throws Match.NotModifalbleMatchException {
        match.begin();

        Assertions.assertEquals(Match.Status.RUNNING, match.getStatus());
        verify(handler, times(1)).onBegin(any(Match.class));
    }

    @Test
    public void shouldFishTheMatch() throws Match.NotModifalbleMatchException {
        match.begin();

        match.finish();

        Assertions.assertEquals(Match.Status.FINISHED, match.getStatus());
        verify(handler, times(1)).onFinish(any(Match.class));
    }

    @Test
    public void shouldNotUpdateScoreBeforeBegin() {
        when(resultBar.getResultSummary()).thenReturn(new Match.Result(HOME_TEAM, AWAY_TEAM, 0, 0));

        Assertions.assertThrows(Match.NotModifalbleMatchException.class, () -> match.updateScore(1, 1));
    }

    @Test
    public void shouldNotUpdateScoreAfterFinish() throws Match.NotModifalbleMatchException {
        match.begin();

        match.finish();
        when(resultBar.getResultSummary()).thenReturn(new Match.Result(HOME_TEAM, AWAY_TEAM, 0, 0));

        Assertions.assertThrows(Match.NotModifalbleMatchException.class, () -> match.updateScore(1, 1));
        verify(handler, times(1)).onFinish(any(Match.class));
    }

    @Test
    public void shouldGetMatchDescription() {
        when(resultBar.getResultSummary()).thenReturn(new Match.Result(HOME_TEAM, AWAY_TEAM, 0, 0));

        Assertions.assertEquals("Home Team 0 - Away Team 0", match.getResult().description());
    }

}
