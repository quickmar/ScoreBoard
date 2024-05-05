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

    @BeforeAll
    public static void beforeAll() {
        HOME_TEAM = new Match.Team("Home Team");
        AWAY_TEAM = new Match.Team("Away Team");
    }

    @BeforeEach
    public void beforeEach() {
        resultBar = mock(ResultBar.class);
        match = new FootballMatch(resultBar);
    }

    @Test
    public void shouldUpdatesScore() throws Match.NotModifalbleMatchException {
        match.begin(0);

        match.updateScore(1, 1);

        verify(resultBar, times(1)).setHomeTeamScore(anyInt());
        verify(resultBar, times(1)).setAwayTeamScore(anyInt());
    }

    @Test
    public void shouldThrowWhenUpdatesScoreOnNotStartedMatch() throws Match.NotModifalbleMatchException {
        when(resultBar.getResultSummary()).thenReturn(new Match.Result(HOME_TEAM, AWAY_TEAM, 0, 0));

        Assertions.assertThrows(Match.NotModifalbleMatchException.class, () -> match.updateScore(1, 1));
    }

    @Test
    public void shouldGetInitialStatus() {
        Assertions.assertEquals(Match.Status.CREATED, match.getStatus());
    }

    @Test
    public void shouldBeginMatch() throws Match.NotModifalbleMatchException {
        match.begin(0);

        Assertions.assertEquals(Match.Status.RUNNING, match.getStatus());
    }

    @Test
    public void shouldFishTheMatch() {
        match.finish();

        Assertions.assertEquals(Match.Status.FINISHED, match.getStatus());
    }

    @Test
    public void shouldNotUpdateScoreBeforeBegin() {
        when(resultBar.getResultSummary()).thenReturn(new Match.Result(HOME_TEAM, AWAY_TEAM, 0, 0));

        Assertions.assertThrows(Match.NotModifalbleMatchException.class, () -> match.updateScore(1, 1));
    }

    @Test
    public void shouldNotUpdateScoreAfterFinish() {
        match.finish();

        when(resultBar.getResultSummary()).thenReturn(new Match.Result(HOME_TEAM, AWAY_TEAM, 0, 0));

        Assertions.assertThrows(Match.NotModifalbleMatchException.class, () -> match.updateScore(1, 1));
    }

    @Test
    public void shouldGetMatchDescription() {
        when(resultBar.getResultSummary()).thenReturn(new Match.Result(HOME_TEAM, AWAY_TEAM, 0, 0));

        Assertions.assertEquals("Home Team 0 - Away Team 0", match.getDescription());
    }

}
