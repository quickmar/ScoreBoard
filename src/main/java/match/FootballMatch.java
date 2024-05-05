package match;

public class FootballMatch implements Match {
    private Status status;
    private final ResultBar resultBar;

    public FootballMatch(ResultBar resultBar) {
        this.status = Status.RUNNING;
        this.resultBar = resultBar;
    }

    @Override
    public void finish() {
        status = Status.FINISHED;
    }

    @Override
    public void updateScore(int homeTeamScore, int awayTeamScore) throws NotModifalbleAfterFinishException {
        if (status.equals(Status.FINISHED)) {
            throw new NotModifalbleAfterFinishException(this);
        }
        this.resultBar.setHomeTeamScore(homeTeamScore);
        this.resultBar.setAwayTeamScore(awayTeamScore);
    }

    @Override
    public Result getResult() {
        return resultBar.getResultSummary();
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String getDescription() {
        var summary = resultBar.getResultSummary();
        return new StringBuilder()
                .append(summary.homeTeam().name())
                .append(" ")
                .append(summary.homeTeamScore())
                .append(" - ")
                .append(summary.awayTeam().name())
                .append(" ")
                .append(summary.awayTeamScore())
                .toString();
    }
}
