package scoreboard;

import match.Match;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Scoreboard {
    private static final AtomicInteger matchSequence = new AtomicInteger();
    private final Set<Match.Team> teams;
    private final Set<Match> matches;

    public Scoreboard() {
        teams = new HashSet<>();
        matches = new HashSet<>();
    }

    public void newMatch(Match match) {
        try {
            addMatch(match);
            var summary = match.getResult();
            addTeam(summary.homeTeam());
            addTeam(summary.awayTeam());
        } catch (Match.NotModifalbleMatchException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Match.Result> getSummary() {
        return matches.stream()
                .sorted(Comparator.comparing(Match::getSequenceNumber))
                .map(Match::getResult)
                .toList();
    }

    private void addTeam(Match.Team team) {
        if (teams.contains(team)) throw new AssertionError("Team " + team.name() + " already exists");
        teams.add(team);
    }

    private void addMatch(Match match) throws Match.NotModifalbleMatchException {
        if (matches.contains(match)) throw new AssertionError("Team " + match.getDescription() + " already exists");
        match.begin(matchSequence.getAndIncrement());
        matches.add(match);
    }
}
