package vn.toeiconline.core.common.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Map;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Object[] buildPropertiesToAppend(Map<String, Object> property, String whereClause) {
        StringBuilder strReturn = new StringBuilder();
        String[] params = null;
        Object[] values = null;
        if (whereClause != null) {
            strReturn.append(whereClause);
        }
        if (property != null && property.size() > 0) {
            params = new String[property.size()];
            values = new Object[property.size()];
            int i = 0;
            for (Map.Entry<String, Object> item: property.entrySet()) {
                params[i] = item.getKey();
                values[i] = item.getValue();
                i++;
            }
            for (int i1 = 0; i1 < property.size(); i1++) {
                strReturn.append(" AND ").append("LOWER(" + params[i1] + ") LIKE '%' || :" + params[i1] + " || '%' ");
            }
        }
        return new Object[]{strReturn.toString(), params, values};
    }
}
