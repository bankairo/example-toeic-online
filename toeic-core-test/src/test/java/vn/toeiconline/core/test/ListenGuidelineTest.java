package vn.toeiconline.core.test;


import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import vn.toeiconline.core.dao.ListenGuidelineDao;
import vn.toeiconline.core.daoimpl.ListenGuidelineDaoImpl;

import java.util.HashMap;
import java.util.Map;

public class ListenGuidelineTest {
    ListenGuidelineDao listenGuidelineDao;

    @BeforeTest
    public void initData() {
        listenGuidelineDao = new ListenGuidelineDaoImpl();
    }

    @Test
    public void checkFindByProperties() {
        Map<String, Object> property = new HashMap<String, Object>();
        property.put("title", "Bai hd 8");
        property.put("content", "Noi dung bai hd 8");
        Object[] result = listenGuidelineDao.findByProperty(property, null, null, null, null);
    }
}
