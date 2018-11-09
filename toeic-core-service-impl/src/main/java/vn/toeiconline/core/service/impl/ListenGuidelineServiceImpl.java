package vn.toeiconline.core.service.impl;

import vn.toeiconline.core.dao.ListenGuidelineDao;
import vn.toeiconline.core.daoimpl.ListenGuidelineDaoImpl;
import vn.toeiconline.core.dto.ListenGuidelineDTO;
import vn.toeiconline.core.persistence.entity.ListenGuidelineEntity;
import vn.toeiconline.core.service.ListenGuidelineService;
import vn.toeiconline.core.utils.ListenGuidelineBeanUtil;

import java.util.ArrayList;
import java.util.List;

public class ListenGuidelineServiceImpl implements ListenGuidelineService {
    public Object[] findListenGuidelineByProperties(String property, String value, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        List<ListenGuidelineDTO> result = new ArrayList<ListenGuidelineDTO>();
        ListenGuidelineDao listenGuidelineDao = new ListenGuidelineDaoImpl();
        Object[] objects = listenGuidelineDao.findByProperty(property, value, sortExpression, sortDirection, offset, limit);
        for (ListenGuidelineEntity item: (List<ListenGuidelineEntity>)objects[1]) {
            ListenGuidelineDTO dto = ListenGuidelineBeanUtil.entity2Dto(item);
            result.add(dto);
        }
        objects[1] = result;
        return objects;
    }
}
