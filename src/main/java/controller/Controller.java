package controller;

import dispatcher.HttpWrapper;

/**
 * Interface of po process user options.
 *
 * @author Yaroslav Baranov
 */
public interface Controller {

    /**
     * Method to process certain business option.
     *
     * @param httpWrapper holder of http request and response
     */
    public void execute(HttpWrapper httpWrapper);
}
