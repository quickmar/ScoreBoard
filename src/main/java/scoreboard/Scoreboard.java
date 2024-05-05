package scoreboard;

import match.Match;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Scoreboard implements Match.MatchChangeHandler {
    private record MatchWithIndex(int index, Match match) {
    }

    private final Set<Match.Team> teams;
    private final List<Match> matches;

    public Scoreboard() {
        teams = new HashSet<>();
        matches = new LinkedList<>();
    }

    public List<Match.Result> getSummary() {
        var sequence = new AtomicInteger();
        return matches.stream()
                .map((match -> new MatchWithIndex(sequence.getAndIncrement(), match)))
                .sorted(totalScoreThenSeqenceNoComparator)
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
        } catch (Match.NotModifalbleMatchException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void finalize(Match match) {
        var result = match.getResult();
        teams.remove(result.homeTeam());
        teams.remove(result.awayTeam());
        matches.remove(match);
    }

    private void addTeam(Match.Team team) {
        if (teams.contains(team)) throw new AssertionError("Team " + team.name() + " already exists");
        teams.add(team);
    }

    private void addMatch(Match match) throws Match.NotModifalbleMatchException {
        matches.addLast(match);
    }

    private static int getTotalScore(Match match) {
        return match.getResult().totalScore();
    }

    private static final Comparator<MatchWithIndex> totalScoreThenSeqenceNoComparator = (MatchWithIndex m1, MatchWithIndex m2) -> {
        var m1TotalScore = getTotalScore(m1.match());
        var m2TotalScore = getTotalScore(m2.match());
        var m1SequenceNumber = m1.index();
        var m2SequenceNumber = m2.index();

        if (m1TotalScore != m2TotalScore) {
            return m2TotalScore - m1TotalScore;
        } else {
            return Long.compare(m2SequenceNumber, m1SequenceNumber);
        }
    };
}
