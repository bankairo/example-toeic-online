package vn.toeiconline.core.service.impl;

import vn.toeiconline.core.dto.ExaminationDTO;
import vn.toeiconline.core.persistence.entity.ExaminationEntity;
import vn.toeiconline.core.service.ExaminationService;
import vn.toeiconline.core.service.utils.SingletonDaoUtil;
import vn.toeiconline.core.utils.ExaminationBeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExaminationServiceImpl implements ExaminationService {
    public Object[] findExaminationByProperties(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        List<ExaminationDTO> dtos = new ArrayList<ExaminationDTO>();
        Object[] objects = SingletonDaoUtil.getExaminationDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit, null);
        for (ExaminationEntity item: (List<ExaminationEntity>)objects[1]) {
            ExaminationDTO dto = ExaminationBeanUtil.entity2Dto(item);
            dtos.add(dto);
        }
        objects[1] = dtos;
        return objects;
    }
}
