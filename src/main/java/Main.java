import match.Match;
import match.NotModifalbleMatchException;
import match.Team;
import scoreboard.Scoreboard;
import scoreboards.Scoreboards;


public class Main {
    public static void main(String[] args) throws NotModifalbleMatchException {
        var factory = Scoreboards.createFactory();

        var uruguayItaly = factory.createFootballMatch("Uruguay", "Italy");
        var spainBrazil = factory.createFootballMatch("Spain", "Brazil");
        var mexicoCanada = factory.createFootballMatch("Mexico", "Canada");
        var argentinaAustralia = factory.createFootballMatch("Argentina", "Australia");
        var germanyFrance = factory.createFootballMatch("Germany", "France");

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

        factory.getScoreboard().getSummary().stream().map(Match.Result::description).forEach(System.out::println);
    }
}