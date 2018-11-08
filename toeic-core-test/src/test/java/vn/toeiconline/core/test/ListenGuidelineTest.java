package vn.toeiconline.core.test;

import org.junit.Test;
import vn.toeiconline.core.dao.ListenGuidelineDao;
import vn.toeiconline.core.daoimpl.ListenGuidelineDaoImpl;

import java.util.List;

public class ListenGuidelineTest {

    @Test
    public void checkFindByProperties() {
        ListenGuidelineDao listenGuidelineDao = new ListenGuidelineDaoImpl();
        Object[] result = listenGuidelineDao.findByProperty(null, null, null, null, 2, 2);
    }
}
