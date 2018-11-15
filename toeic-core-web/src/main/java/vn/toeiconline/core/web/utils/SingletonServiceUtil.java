package vn.toeiconline.core.web.utils;

import vn.toeiconline.core.service.impl.ListenGuidelineServiceImpl;
import vn.toeiconline.core.service.impl.RoleServiceImpl;
import vn.toeiconline.core.service.impl.UserServiceImpl;

public class SingletonServiceUtil {
    private static UserServiceImpl userServiceImpl = null;
    private static RoleServiceImpl roleServiceImpl = null;
    private static ListenGuidelineServiceImpl listenGuidelineServiceImpl = null;

    public static UserServiceImpl getUserServiceInstance() {
        if (userServiceImpl == null) {
            userServiceImpl = new UserServiceImpl();
        }
        return userServiceImpl;
    }
    public static RoleServiceImpl getRoleServiceInstance() {
        if (roleServiceImpl == null) {
            roleServiceImpl = new RoleServiceImpl();
        }
        return roleServiceImpl;
    }
    public static ListenGuidelineServiceImpl getListenGuidelineServiceInstance() {
        if (listenGuidelineServiceImpl == null) {
            listenGuidelineServiceImpl = new ListenGuidelineServiceImpl();
        }
        return listenGuidelineServiceImpl;
    }
}
