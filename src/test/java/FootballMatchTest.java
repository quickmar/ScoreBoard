import match.FootballMatch;
import match.Match;
import match.ResultBar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        resultBar = Mockito.mock(ResultBar.class);
        match = new FootballMatch(resultBar);
    }

    @Test
    public void shouldGetInitialResult() {
        Assertions.assertEquals(new Match.Result(HOME_TEAM, AWAY_TEAM, 0, 0), match.getResult());
    }

    @Test
    public void shouldUpdatesScore() throws Match.NotModifalbleAfterFinishException {
        match.updateScore(1, 1);

        var result = match.getResult();
        Assertions.assertInstanceOf(Match.Result.class, result);
        Assertions.assertEquals(new Match.Result(HOME_TEAM, AWAY_TEAM, 1, 1), result);
    }

    @Test
    public void shouldGetInitialStatus() {
        Assertions.assertEquals(Match.Status.RUNNING, match.getStatus());
    }

    @Test
    public void shouldFishTheMatch() {
        match.finish();

        Assertions.assertEquals(Match.Status.FINISHED, match.getStatus());
    }

    @Test
    public void shouldNotInvokeMethodAfterFinish() {
        match.finish();

        Assertions.assertThrows(Match.NotModifalbleAfterFinishException.class, () -> match.updateScore(1, 1));
    }

    @Test
    public void shouldGetMatchDescription() {
        Assertions.assertEquals("Home Team 0 - Away Team 0", match.getDescription());
    }

}
