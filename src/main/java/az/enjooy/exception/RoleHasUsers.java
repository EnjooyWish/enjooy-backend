package az.enjooy.exception;

public final class RoleHasUsers extends RuntimeException {
    public RoleHasUsers() {
        super();
    }

    public RoleHasUsers(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RoleHasUsers(final String message) {
        super(message);
    }

    public RoleHasUsers(final Throwable cause) {
        super(cause);
    }
}
