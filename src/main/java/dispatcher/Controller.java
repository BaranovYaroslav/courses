package dispatcher;

import constants.MessagesConstants;
import org.apache.log4j.Logger;
import service.NotificationService;

/**
 * Created by Ярослав on 11.04.2017.
 */
public interface Controller {
    public void execute(HttpWrapper httpWrapper);
}
