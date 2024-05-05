import match.Match;
import scoreboard.Scoreboard;


public class Main {
    public static void main(String[] args) throws Match.NotModifalbleMatchException {
        var scoreboard = new Scoreboard();

        var uruguayItaly = Match.createFootballMatch(new Match.Team("Uruguay"), new Match.Team("Italy"), scoreboard);
        var spainBrazil = Match.createFootballMatch(new Match.Team("Spain"), new Match.Team("Brazil"), scoreboard);
        var mexicoCanada = Match.createFootballMatch(new Match.Team("Mexico"), new Match.Team("Canada"), scoreboard);
        var argentinaAustralia = Match.createFootballMatch(new Match.Team("Argentina"), new Match.Team("Australia"), scoreboard);
        var germanyFrance = Match.createFootballMatch(new Match.Team("Germany"), new Match.Team("France"), scoreboard);

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