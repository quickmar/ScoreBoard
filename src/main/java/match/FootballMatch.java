package match;

public class FootballMatch implements Match {
    private Status status;

    public FootballMatch() {
        this.status = Status.RUNNING;
    }

    @Override
    public void finish() {
        status = Status.FINISHED;
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
        return status;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
