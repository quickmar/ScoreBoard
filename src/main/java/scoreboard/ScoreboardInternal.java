package scoreboard;

import scoreboards.NotModifalbleMatchException;
import match.Team;
import scoreboards.Match;
import scoreboards.Scoreboard;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreboardInternal implements Scoreboard {
    private record MatchWithIndex(int index, Match match) {
    }

    private final Set<Team> teams;
    private final List<Match> matches;

    public ScoreboardInternal() {
        teams = new HashSet<>();
        matches = new LinkedList<>();
    }

    @Override
    public List<Match.Result> getSummary() {
        var sequence = new AtomicInteger();
        return matches.stream()
                .map((match -> new MatchWithIndex(sequence.getAndIncrement(), match)))
                .sorted(Comparator
                        .comparing(ScoreboardInternal::getTotalScore)
                        .thenComparing(MatchWithIndex::index)
                        .reversed())
                .map(MatchWithIndex::match)
                .map(Match::getResult)
                .toList();
    }

    @Override
    public void onBegin(Match match) {
        try {
            var summary = match.getResult();
            addTeam(summary.homeTeam());
            addTeam(summary.awayTeam());
            addMatch(match);
        } catch (NotModifalbleMatchException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFinish(Match match) {
        var result = match.getResult();
        teams.remove(result.homeTeam());
        teams.remove(result.awayTeam());
        matches.remove(match);
    }

    private void addTeam(Team team) {
        if (teams.contains(team)) throw new AssertionError("Team " + team.name() + " already exists");
        teams.add(team);
    }

    private void addMatch(Match match) throws NotModifalbleMatchException {
        matches.addLast(match);
    }

    private static int getTotalScore(MatchWithIndex matchWithIndex) {
        return matchWithIndex.match().getResult().totalScore();
    }
}
