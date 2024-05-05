import match.Match;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import scoreboard.Scoreboard;

import java.util.List;

public class ScoreboardTest {
    private Scoreboard scoreboard;
    private Match uruguayItaly;
    private Match spainBrazil;
    private Match mexicoCanada;
    private Match argentinaAustralia;
    private Match germanyFrance;

    @BeforeEach
    public void beforeEach() {
        scoreboard = new Scoreboard();
        uruguayItaly = Match.createFootballMatch(new Match.Team("Uruguay"), new Match.Team("Italy"), scoreboard);
        spainBrazil = Match.createFootballMatch(new Match.Team("Spain"), new Match.Team("Brazil"), scoreboard);
        mexicoCanada = Match.createFootballMatch(new Match.Team("Mexico"), new Match.Team("Canada"), scoreboard);
        argentinaAustralia = Match.createFootballMatch(new Match.Team("Argentina"), new Match.Team("Australia"), scoreboard);
        germanyFrance = Match.createFootballMatch(new Match.Team("Germany"), new Match.Team("France"), scoreboard);
    }

    @Test
    public void shouldAddNewMatch() {
        var newMatch = mock(Match.class);

        when(newMatch.getResult())
                .thenReturn(new Match.Result(
                        new Match.Team("Home Team"),
                        new Match.Team("Away Team"), 0, 0));

        Assertions.assertDoesNotThrow(() -> scoreboard.onBegin(newMatch));
    }


    @Test
    public void shouldThrowWhenMatchExist() throws Match.NotModifalbleMatchException {
        uruguayItaly.begin();

        Assertions.assertThrows(Match.NotModifalbleMatchException.class, () -> uruguayItaly.begin());
    }

    @Test
    public void shouldThrowWhenTeamExist() throws Match.NotModifalbleMatchException {
        uruguayItaly.begin();

        Assertions.assertThrows(AssertionError.class, () -> Match.createFootballMatch(new Match.Team("Uruguay"), new Match.Team("US"), scoreboard).begin());
    }

    @Test
    public void shouldRemoveTeamWhenMatchFinished() throws Match.NotModifalbleMatchException {
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
    public void shouldSummariseMatchesByTotalScore() throws Match.NotModifalbleMatchException {
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
    public void shouldSummariseMatchesByTotalScoreAndMostRecent() throws Match.NotModifalbleMatchException {
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
        } catch (Match.NotModifalbleMatchException e) {
            Assertions.fail(e);
        }
    }
}
