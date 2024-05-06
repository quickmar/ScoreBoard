import match.Match;
import match.NotModifalbleMatchException;
import match.Team;
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
        uruguayItaly = Match.createFootballMatch(new Team("Uruguay"), new Team("Italy"), scoreboard);
        spainBrazil = Match.createFootballMatch(new Team("Spain"), new Team("Brazil"), scoreboard);
        mexicoCanada = Match.createFootballMatch(new Team("Mexico"), new Team("Canada"), scoreboard);
        argentinaAustralia = Match.createFootballMatch(new Team("Argentina"), new Team("Australia"), scoreboard);
        germanyFrance = Match.createFootballMatch(new Team("Germany"), new Team("France"), scoreboard);
    }

    @Test
    public void shouldAddNewMatch() {
        var newMatch = mock(Match.class);

        when(newMatch.getResult())
                .thenReturn(new Match.Result(
                        new Team("Home Team"),
                        new Team("Away Team"), 0, 0));

        Assertions.assertDoesNotThrow(() -> scoreboard.onBegin(newMatch));
    }


    @Test
    public void shouldThrowWhenMatchExist() throws NotModifalbleMatchException {
        uruguayItaly.begin();

        Assertions.assertThrows(NotModifalbleMatchException.class, () -> uruguayItaly.begin());
    }

    @Test
    public void shouldThrowWhenTeamExist() throws NotModifalbleMatchException {
        uruguayItaly.begin();

        Assertions.assertThrows(AssertionError.class, () -> Match.createFootballMatch(new Team("Uruguay"), new Team("US"), scoreboard).begin());
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
