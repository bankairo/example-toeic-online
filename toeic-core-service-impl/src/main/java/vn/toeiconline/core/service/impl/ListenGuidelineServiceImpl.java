package vn.toeiconline.core.service.impl;

import org.hibernate.exception.ConstraintViolationException;
import vn.toeiconline.core.dto.ListenGuidelineDTO;
import vn.toeiconline.core.persistence.entity.ListenGuidelineEntity;
import vn.toeiconline.core.service.ListenGuidelineService;
import vn.toeiconline.core.service.utils.SingletonDaoUtil;
import vn.toeiconline.core.utils.ListenGuidelineBeanUtil;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListenGuidelineServiceImpl implements ListenGuidelineService {
    public Object[] findListenGuidelineByProperties(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        List<ListenGuidelineDTO> result = new ArrayList<ListenGuidelineDTO>();
        Object[] objects = SingletonDaoUtil.getListenGuidelineDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit);
        for (ListenGuidelineEntity item: (List<ListenGuidelineEntity>)objects[1]) {
            ListenGuidelineDTO dto = ListenGuidelineBeanUtil.entity2Dto(item);
            result.add(dto);
        }
        objects[1] = result;
        return objects;
    }

    public ListenGuidelineDTO findListenGuidelineById(String property, Integer id) {
        ListenGuidelineEntity entity = SingletonDaoUtil.getListenGuidelineDaoInstance().findEqualUnique(property, id);
        ListenGuidelineDTO dto = ListenGuidelineBeanUtil.entity2Dto(entity);
        return dto;
    }

    public void saveListenGuideline(ListenGuidelineDTO dto) throws ConstraintViolationException {
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        dto.setCreatedDate(createdDate);
        ListenGuidelineEntity entity = ListenGuidelineBeanUtil.dto2Entity(dto);
        SingletonDaoUtil.getListenGuidelineDaoInstance().save(entity);
    }

    public ListenGuidelineDTO updateListenGuideline(ListenGuidelineDTO dto) {
        Timestamp modifiedDate = new Timestamp(System.currentTimeMillis());
        dto.setModifiedDate(modifiedDate);
        ListenGuidelineEntity entity = ListenGuidelineBeanUtil.dto2Entity(dto);
        entity = SingletonDaoUtil.getListenGuidelineDaoInstance().update(entity);
        dto = ListenGuidelineBeanUtil.entity2Dto(entity);
        return dto;
    }

    public Integer delete(List<Integer> ids) {
        Integer result = SingletonDaoUtil.getListenGuidelineDaoInstance().delete(ids);
        return result;
    }
}
