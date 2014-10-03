package br.com.cruz.jamal.common.to;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ParameterTO<T> extends JamalTO<ParameterTO<T>> {

	private static final long serialVersionUID = -8010945720505954827L;

	
	// FIELD
	
	@Getter
	@Setter
	private Class<T> type;
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private T value;
	
	
	//CONSTRUCTOR
	
	public ParameterTO(@NotNull String name) {
		super();
		this.setName(name);
	}
	
	
	// METHOD
	
	@Override
	public boolean equals(Object object) {
		try {
			return EqualsBuilder.reflectionEquals(this, object, "type", "value");
		} catch (Exception e) {
			ParameterTO.log.error(e.getMessage(), e);
		}
		return super.equals(object);
	}
	
	@Override
	public int hashCode() {
		try {
			return HashCodeBuilder.reflectionHashCode(this, "type", "value");
		} catch (Exception e) {
			ParameterTO.log.error(e.getMessage(), e);
		}
		return super.hashCode();
	}
}
