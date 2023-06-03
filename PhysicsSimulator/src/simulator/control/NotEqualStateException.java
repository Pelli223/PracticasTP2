package simulator.control;

import org.json.JSONObject;

public class NotEqualStateException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public NotEqualStateException(JSONObject expectedState, JSONObject actualState, JSONObject expBody, JSONObject actBody, int step) {
		super("No equal states error\nExpected: " + expectedState + "\nActual: " + actualState + "\nExpected Body: " + expBody+"\nActual Body: "+ actBody +"\nat step " + step);
	}

}
