package vn.toeiconline.core.service;

public interface ListenGuidelineService {
    Object[] findListenGuidelineByProperties(String property, String value, String sortExpression, String sortDirection, Integer offset, Integer limit);
}
