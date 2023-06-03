package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	
	private List<Builder<T>> builders;
	private List<JSONObject> factoryElements;
	
	public BuilderBasedFactory (List<Builder<T>> builders) {
		this.builders = new ArrayList<>(builders);
		this.factoryElements = new ArrayList<>();
		for(Builder<T> b: this.builders) {
			this.factoryElements.add(b.getBuilderInfo());
		}
		
	}

	@Override
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		int pos = 0;
		boolean ok = false;
		if(info == null) throw new IllegalArgumentException("Invalid value for create instance: null");
		else {
			while (pos < this.builders.size() && !ok) {
				if(this.builders.get(pos).createInstance(info) != null) ok = true;
				else pos++;
			}
			if(ok) return this.builders.get(pos).createInstance(info);
			else throw new IllegalArgumentException("Invalid value for create instance");
		}
	}

	@Override
	public List<JSONObject> getInfo() {
		return this.factoryElements;
	}

}
