package p.lodz.pl.zzpj.sharethebill.exceptions;

public abstract class NotFoundException extends Exception{


    private static final String USER_NOT_FOUND = "User with id: %o not found";
    private static final String GROUP_NOT_FOUND = "Group with id: %o not found";
    private static final String USER_NOT_IN_GROUP = "User with id: %o is not in group";

    private NotFoundException(String message) {
        super(message);
    }
    public static UserNotFoundException createUserNotFoundException(Long id){
        return new UserNotFoundException(String.format(USER_NOT_FOUND,id));
    }

    public static GroupNotFoundException createGroupNotFoundException(Long id){
        return new GroupNotFoundException(String.format(GROUP_NOT_FOUND,id));
    }

    public static UserIsNotInGroupException createUserIsNotInGroupException(Long id) {
        return new UserIsNotInGroupException(String.format(USER_NOT_IN_GROUP,id));
    }

    public static class UserNotFoundException extends NotFoundException{
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class GroupNotFoundException extends NotFoundException{
        private GroupNotFoundException(String message) {
            super(message);
        }
    }

    private static class UserIsNotInGroupException extends NotFoundException{
        private UserIsNotInGroupException(String message) {
            super(message);
        }
    }
}
