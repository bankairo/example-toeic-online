package vn.toeiconline.core.web.utils;

import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import vn.toeiconline.core.web.command.AbstractCommand;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
    public static void initSearchBean(HttpServletRequest request, AbstractCommand bean) {
        String sortExpression = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_SORT));
        String sortDirection = request.getParameter(new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
        String pageStr = request.getParameter((new ParamEncoder(bean.getTableId()).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

        int page = 1;
        if (StringUtils.isNotBlank(pageStr)) {
            try {
                page = Integer.valueOf(pageStr);
            } catch (Exception e) {

            }
        }

        bean.setPage(page);
        bean.setSortExpression(sortExpression);
        bean.setSortDirection(sortDirection);
        bean.setFirstItem((page - 1) * bean.getMaxPageItems());
    }

    public static void initSearchBeanManual(AbstractCommand command) {
        int page = 1;
        if (command != null) {
            if (command.getPage() > 0) {
                page = command.getPage();
            }
            command.setPage(page);
            command.setFirstItem((page - 1) * command.getMaxPageItems());
        }
    }
}
