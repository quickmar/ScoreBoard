import match.Match;
import match.NotModifalbleMatchException;
import match.Team;
import scoreboard.Scoreboard;


public class Main {
    public static void main(String[] args) throws NotModifalbleMatchException {
        var scoreboard = new Scoreboard();

        var uruguayItaly = Match.createFootballMatch(new Team("Uruguay"), new Team("Italy"), scoreboard);
        var spainBrazil = Match.createFootballMatch(new Team("Spain"), new Team("Brazil"), scoreboard);
        var mexicoCanada = Match.createFootballMatch(new Team("Mexico"), new Team("Canada"), scoreboard);
        var argentinaAustralia = Match.createFootballMatch(new Team("Argentina"), new Team("Australia"), scoreboard);
        var germanyFrance = Match.createFootballMatch(new Team("Germany"), new Team("France"), scoreboard);

        mexicoCanada.begin();
        spainBrazil.begin();
        germanyFrance.begin();
        uruguayItaly.begin();
        argentinaAustralia.begin();


        uruguayItaly.updateScore(6, 6);
        spainBrazil.updateScore(10, 2);
        mexicoCanada.updateScore(0, 5);
        argentinaAustralia.updateScore(3, 1);
        germanyFrance.updateScore(2, 2);

        scoreboard.getSummary().stream().map(Match.Result::description).forEach(System.out::println);
    }
}