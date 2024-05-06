import match.Match;
import match.ResultBar;
import match.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ResultBarTest {
    private static Team HOME_TEAM;
    private static Team AWAY_TEAM;

    private ResultBar resultBar;

    @BeforeAll
    public static void beforeAll() {
        HOME_TEAM = new Team("Home Team");
        AWAY_TEAM = new Team("Away Team");
    }

    @BeforeEach
    public void beforeEach() {
        resultBar = new ResultBar(HOME_TEAM, AWAY_TEAM);
    }

    @Test
    public void shouldGetInitialResult() {
        Assertions.assertEquals(new Match.Result(HOME_TEAM, AWAY_TEAM, 0, 0), resultBar.getResultSummary());
    }

    @Test
    public void shouldSetHomeTeamScore() {
        resultBar.setHomeTeamScore(1);

        Assertions.assertEquals(new Match.Result(HOME_TEAM, AWAY_TEAM, 1, 0), resultBar.getResultSummary());
    }

    @Test
    public void shouldSetAwayTeamScore() {
        resultBar.setAwayTeamScore(1);

        Assertions.assertEquals(new Match.Result(HOME_TEAM, AWAY_TEAM, 0, 1), resultBar.getResultSummary());
    }

    @Test
    public void shouldThrowWhenHomeTeamScoreSmallerThenZero() {
        Assertions.assertThrows(AssertionError.class, () -> resultBar.setHomeTeamScore(-1));

    }

    @Test
    public void shouldThrowWhenAwayTeamScoreSmallerThenZero() {
        Assertions.assertThrows(AssertionError.class, () -> resultBar.setAwayTeamScore(-1));

    }

    @Test
    public void shouldThrowWhenSetHomeTeamScoreSmallerThenCurrentScore() {
        resultBar.setHomeTeamScore(1);

        Assertions.assertThrows(AssertionError.class, () -> resultBar.setHomeTeamScore(0));
    }

    @Test
    public void shouldThrowWhenSetAwayTeamScoreSmallerThenCurrentScore() {
        resultBar.setAwayTeamScore(1);

        Assertions.assertThrows(AssertionError.class, () -> resultBar.setAwayTeamScore(0));
    }
}
