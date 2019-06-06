package snlogic;

import java.util.HashMap;

public class EventTypes {
    public enum EventKey {
        LOG_IN,
        LOG_IN_CHALLENGE,
        LOG_IN_FAILURE,
        LOG_OUT,
        SIGN_UP,
        AUTH_CHALLENGE,
        AUTH_CHALLENGE_SUCCESS,
        AUTH_CHALLENGE_FAILURE,
        TWO_FACTOR_DISABLE,
        EMAIL_UPDATE,
        PASSWORD_RESET,
        PASSWORD_RESET_SUCCESS,
        PASSWORD_UPDATE,
        PASSWORD_RESET_FAILURE,
        USER_INVITE,
        ROLE_UPDATE,
        PROFILE_UPDATE,
        PAGE_VIEW,
        VERIFY,
    }

    public static final HashMap<EventKey, String> types = new HashMap<EventKey, String>(){{
        put( EventKey.LOG_IN , "sn.user.login");
        put( EventKey.LOG_IN_CHALLENGE , "sn.user.login.challenge");
        put( EventKey.LOG_IN_FAILURE , "sn.user.login.failure");
        put( EventKey.LOG_OUT , "sn.user.logout");
        put( EventKey.SIGN_UP , "sn.user.signup");
        put( EventKey.AUTH_CHALLENGE , "sn.user.auth.challange");
        put( EventKey.AUTH_CHALLENGE_SUCCESS , "sn.user.auth.challange.success");
        put( EventKey.AUTH_CHALLENGE_FAILURE , "sn.user.auth.challange.failure");
        put( EventKey.TWO_FACTOR_DISABLE , "sn.user.2fa.disable");
        put( EventKey.EMAIL_UPDATE ,"sn.user.email.update" );
        put( EventKey.PASSWORD_RESET , "sn.user.password.reset");
        put( EventKey.PASSWORD_RESET_SUCCESS , "sn.user.password.reset.success");
        put( EventKey.PASSWORD_UPDATE , "sn.user.password.update");
        put( EventKey.PASSWORD_RESET_FAILURE , "sn.user.password.reset.failure");
        put( EventKey.USER_INVITE , "sn.user.invite");
        put( EventKey.ROLE_UPDATE , "sn.user.role.update");
        put( EventKey.PROFILE_UPDATE , "sn.user.profile.update");
        put( EventKey.PAGE_VIEW , "sn.user.page.view");
        put (EventKey.VERIFY , "sn.verify");
    }};
}
