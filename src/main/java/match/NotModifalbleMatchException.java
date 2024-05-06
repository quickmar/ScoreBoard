package match;

/**
 * Exception that is thrown whenever state of {@link Match} is changed after it is over.
 */
public class NotModifalbleMatchException extends Exception {
    public NotModifalbleMatchException(Match match) {
        super("The match: " + match.getResult().description() + " is in status: " + match.getStatus() + " and can not be modified.");
    }
}
