import scoreboards.Match;
import scoreboards.NotModifalbleMatchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import scoreboards.Scoreboard;
import scoreboards.Scoreboards;

import java.util.List;

public class ScoreboardTest {
    private Scoreboard scoreboard;
    private Scoreboards.ScoreBoardFactory factory;
    private Match uruguayItaly;
    private Match spainBrazil;
    private Match mexicoCanada;
    private Match argentinaAustralia;
    private Match germanyFrance;

    @BeforeEach
    public void beforeEach() {
        factory = Scoreboards.createFactory();
        scoreboard = factory.getScoreboard();
        uruguayItaly = factory.createFootballMatch("Uruguay", "Italy");
        spainBrazil = factory.createFootballMatch("Spain", "Brazil");
        mexicoCanada = factory.createFootballMatch("Mexico", "Canada");
        argentinaAustralia = factory.createFootballMatch("Argentina", "Australia");
        germanyFrance = factory.createFootballMatch("Germany", "France");
    }

    @Test
    public void shouldBeginMatch() {
        Assertions.assertDoesNotThrow(uruguayItaly::begin);
    }


    @Test
    public void shouldThrowWhenMatchExist() throws NotModifalbleMatchException {
        uruguayItaly.begin();

        Assertions.assertThrows(NotModifalbleMatchException.class, () -> uruguayItaly.begin());
    }

    @Test
    public void shouldThrowWhenTeamExist() throws NotModifalbleMatchException {
        uruguayItaly.begin();

        Assertions.assertThrows(AssertionError.class, () -> factory.createFootballMatch("Uruguay", "US").begin());
    }

    @Test
    public void shouldRemoveTeamWhenMatchFinished() throws NotModifalbleMatchException {
        initialiseMatches();

        uruguayItaly.finish();

        Assertions.assertEquals(
                List.of(argentinaAustralia.getResult(), germanyFrance.getResult(), spainBrazil.getResult(), mexicoCanada.getResult()),
                scoreboard.getSummary());

    }

    @Test
    public void shouldSummariseMatchesByMostRecent() {
        initialiseMatches();

        Assertions.assertEquals(
                List.of(argentinaAustralia.getResult(), uruguayItaly.getResult(), germanyFrance.getResult(), spainBrazil.getResult(), mexicoCanada.getResult()),
                scoreboard.getSummary());
    }

    @Test
    public void shouldSummariseMatchesByTotalScore() throws NotModifalbleMatchException {
        initialiseMatches();

        uruguayItaly.updateScore(2, 1);
        spainBrazil.updateScore(1, 0);
        mexicoCanada.updateScore(1, 1);
        argentinaAustralia.updateScore(3, 6);

        Assertions.assertEquals(
                List.of(argentinaAustralia.getResult(), uruguayItaly.getResult(), mexicoCanada.getResult(), spainBrazil.getResult(), germanyFrance.getResult()),
                scoreboard.getSummary());
    }

    @Test
    public void shouldSummariseMatchesByTotalScoreAndMostRecent() throws NotModifalbleMatchException {
        initialiseMatches();

        uruguayItaly.updateScore(6, 6);
        spainBrazil.updateScore(10, 2);
        mexicoCanada.updateScore(0, 5);
        argentinaAustralia.updateScore(3, 1);
        germanyFrance.updateScore(2, 2);


        Assertions.assertEquals(
                List.of(uruguayItaly.getResult(), spainBrazil.getResult(), mexicoCanada.getResult(), argentinaAustralia.getResult(), germanyFrance.getResult()),
                scoreboard.getSummary());
    }


    private void initialiseMatches() {
        try {
            mexicoCanada.begin();
            spainBrazil.begin();
            germanyFrance.begin();
            uruguayItaly.begin();
            argentinaAustralia.begin();
        } catch (NotModifalbleMatchException e) {
            Assertions.fail(e);
        }
    }
}
