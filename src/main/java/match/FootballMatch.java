package match;

public class FootballMatch implements Match {
    private Status status;
    private final ResultBar resultBar;
    private MatchChangeHandler handler;

    public FootballMatch(ResultBar resultBar, MatchChangeHandler handler) {
        this.status = Status.CREATED;
        this.resultBar = resultBar;
        this.handler = handler;
    }

    @Override
    public void begin() throws NotModifalbleMatchException {
        if (!status.equals(Status.CREATED)) throw new NotModifalbleMatchException(this);
        this.status = Status.RUNNING;
        this.handler.onBegin(this);
    }

    @Override
    public void finish() throws NotModifalbleMatchException {
        if (!status.equals(Status.RUNNING)) throw new NotModifalbleMatchException(this);
        this.handler.finalize(this);
        status = Status.FINISHED;
        this.handler = null;
    }

    @Override
    public void updateScore(int homeTeamScore, int awayTeamScore) throws NotModifalbleMatchException {
        if (!status.equals(Status.RUNNING)) {
            throw new NotModifalbleMatchException(this);
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
        var summary = getResult();
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
