package darren.googlecloudtts.parameter;

import darren.googlecloudtts.exception.OutOfScopeException;

/**
 * Author: Changemyminds.
 * Date: 2018/6/23.
 * Description:
 * Reference:
 */
public class SynthesisInput {
    private String text;
//    private String ssml;

    public SynthesisInput(String text) {
        this(text, "");
    }

    public SynthesisInput(String text, String ssml) {
        setText(text);
//        setSsml(ssml);
    }

    public String getText() {
        return text;
    }

//    public String getSsml() {
//        return ssml;
//    }

    public void setText(String text) {
        if (text.length() > 5000) {
            throw  new OutOfScopeException("The input size is limited to 5000 characters");
        }

        this.text = text;
    }

//    public void setSsml(String ssml) {
//        this.ssml = ssml;
//    }
}
