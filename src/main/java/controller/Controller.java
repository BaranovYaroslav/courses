package controller;

import dispatcher.HttpWrapper;

/**
 * Created by Ярослав on 11.04.2017.
 */
public interface Controller {
    public void execute(HttpWrapper httpWrapper);
}
