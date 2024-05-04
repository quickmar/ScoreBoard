import match.FootballMatch;
import match.Match;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FootballMatchTest {
    private Match match;

    @BeforeEach
    public void beforeEach() {
        match = new FootballMatch();
    }

    @Test
    public void shouldGetInitialResult() {
        Assertions.assertEquals(new Match.Result(0, 0), match.getResult());
    }

    @Test
    public void shouldUpdatesScore() throws Match.NotModifalbleAfterFinishException {
        match.updateScore(1, 1);

        var result = match.getResult();
        Assertions.assertInstanceOf(Match.Result.class, result);
        Assertions.assertEquals(new Match.Result(1, 1), result);
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
