package scoreboard;

import match.Match;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Scoreboard {
    private static final AtomicInteger matchSequence = new AtomicInteger();
    private final Set<Match.Team> teams;
    private final List<Match> matches;

    public Scoreboard() {
        teams = new HashSet<>();
        matches = new LinkedList<>();
    }

    public void newMatch(Match match) {
        try {
            var summary = match.getResult();
            addTeam(summary.homeTeam());
            addTeam(summary.awayTeam());
            addMatch(match);
        } catch (Match.NotModifalbleMatchException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Match.Result> getSummary() {
        return matches.stream()
                .sorted(totalScoreThenSeqenceNoComparator)
                .map(Match::getResult)
                .toList();
    }

    private void addTeam(Match.Team team) {
        if (teams.contains(team)) throw new AssertionError("Team " + team.name() + " already exists");
        teams.add(team);
    }

    private void addMatch(Match match) throws Match.NotModifalbleMatchException {
        match.begin(matchSequence.getAndIncrement());
        matches.add(match);
    }

    private static int getTotalScore(Match match) {
        return match.getResult().totalScore();
    }

    private static final Comparator<Match> totalScoreThenSeqenceNoComparator = (Match m1, Match m2) -> {
        var m1TotalScore = getTotalScore(m1);
        var m2TotalScore = getTotalScore(m2);
        var m1SequenceNumber = m1.getSequenceNumber();
        var m2SequenceNumber = m2.getSequenceNumber();

        if (m1TotalScore != m2TotalScore) {
            return m2TotalScore - m1TotalScore;
        } else {
            return Long.compare(m2SequenceNumber, m1SequenceNumber);
        }
    };


}
