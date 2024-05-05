import match.FootballMatch;
import match.Match;
import match.ResultBar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ResultBarTest {
    private static Match.Team HOME_TEAM;
    private static Match.Team AWAY_TEAM;

    private ResultBar resultBar;

    @BeforeAll
    public static void beforeAll() {
        HOME_TEAM = new Match.Team("Home Team");
        AWAY_TEAM = new Match.Team("Away Team");
    }

    @BeforeEach
    public void beforeEach() {
        resultBar = new ResultBar(HOME_TEAM, AWAY_TEAM);
    }
    @Test
    public void shouldGetInitialResult() {
        Assertions.assertEquals(new Match.Result(HOME_TEAM, AWAY_TEAM, 0, 0), resultBar.getResultSummary());
    }
}
