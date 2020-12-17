package darren.googlecloudtts.exception;

/**
 * Author: Changemyminds.
 * Date: 2020/12/17.
 * Description:
 * Reference:
 */
public class ApiResponseFailException extends RuntimeException {
    public ApiResponseFailException(String message) {
        super(message);
    }
}
