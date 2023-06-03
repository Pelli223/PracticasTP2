package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator> {
	
	public EpsilonEqualStatesBuilder() {
		super("epseq", "EpsilonEqualStates comparator");
	}

	@Override
	protected StateComparator createTheInstant(JSONObject jsonObject) {
		double eps = jsonObject.has("eps") ? jsonObject.getDouble("eps") : 0.0;
		return new EpsilonEqualStates(eps);
	}
	
	protected JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("eps", 0.1);
		return data;
	}

}
