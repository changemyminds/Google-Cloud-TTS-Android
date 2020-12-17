package darren.gcptts.model;

/**
 * Author: Changemyminds.
 * Date: 2018/6/24.
 * Description:
 * Reference:
 */
public interface Speech {
    void start(String text);

    void resume();

    void pause();

    void stop();

    void exit();
}
