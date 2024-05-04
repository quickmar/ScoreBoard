package match;

public class FootballMatch implements Match {
    @Override
    public void finish() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void updateScore(int homeTeamScore, int awayTeamScore) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Result getResult() {
        return null;
    }

    @Override
    public Status getStatus() {
        return null;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
