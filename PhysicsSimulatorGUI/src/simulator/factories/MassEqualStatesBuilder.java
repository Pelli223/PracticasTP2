package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualState;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder extends Builder<StateComparator> {
	
	public MassEqualStatesBuilder() {
		super ("masseq", "MassEqualsStates comparator");
	}

	@Override
	protected StateComparator createTheInstant(JSONObject jsonObject) {
		return new MassEqualState();
	}

}
