package p.lodz.pl.zzpj.sharethebill.exceptions;

public abstract class UniqueConstaintException extends Exception{

    private static final String LOGIN_TAKEN = "Login %s is taken";
    private static final String EMAIL_TAKEN = "Email %s is taken";
    private static final String USER_IN_GROUP = "User is already a member of group";

    public UniqueConstaintException(String message) {
        super(message);
    }

    public static LoginTakenException createLoginTakenException(String login){
        return new LoginTakenException(String.format(LOGIN_TAKEN,login));
    }

    public static EmailTakenException createEmailTakenException(String email) {
        return new EmailTakenException(String.format(EMAIL_TAKEN,email));
    }

    public static UserAlreadyInGroupExcepiton createUserAlreadyInGroupException() {
        return new UserAlreadyInGroupExcepiton(USER_IN_GROUP);
    }

    private static class LoginTakenException extends UniqueConstaintException {
        public LoginTakenException(String message) {
            super(message);
        }
    }

    private static class EmailTakenException extends UniqueConstaintException{
        public EmailTakenException(String message) {
            super(message);
        }
    }

    private static class UserAlreadyInGroupExcepiton extends UniqueConstaintException{
        public UserAlreadyInGroupExcepiton(String message) {
            super(message);
        }
    }
}
