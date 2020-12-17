package darren.googlecloudtts.api;

import darren.googlecloudtts.request.SynthesizeRequest;
import darren.googlecloudtts.response.SynthesizeResponse;

/**
 * Author: Changemyminds.
 * Date: 2020/12/17.
 * Description:
 * Reference:
 */
public interface SynthesizeApi {
    SynthesizeResponse get(SynthesizeRequest request);
}
