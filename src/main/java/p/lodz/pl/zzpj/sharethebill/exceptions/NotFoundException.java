package p.lodz.pl.zzpj.sharethebill.exceptions;

public abstract class NotFoundException extends Exception{


    private static final String USER_NOT_FOUND = "User with id: %o not found";
    private static final String GROUP_NOT_FOUND = "Group with id: %o not found";

    private NotFoundException(String message) {
        super(message);
    }
    public static UserNotFoundException createUserNotFoundException(Long id){
        return new UserNotFoundException(String.format(USER_NOT_FOUND,id));
    }

    public static GroupNotFoundException createGroupNotFoundException(Long id){
        return new GroupNotFoundException(String.format(GROUP_NOT_FOUND,id));
    }

    private static class UserNotFoundException extends NotFoundException{
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    private static class GroupNotFoundException extends NotFoundException{
        private GroupNotFoundException(String message) {
            super(message);
        }
    }
}
