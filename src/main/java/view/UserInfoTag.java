package view;

import constants.RequestAttribute;
import service.ServiceLoader;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Custom jsp tag to provide information about current user.
 */
public class UserInfoTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.print(getUserLogin());
    }

    private String getUserLogin() {
        return ((PageContext)this.getJspContext()).getSession().getAttribute(RequestAttribute.USER).toString();
    }
}
