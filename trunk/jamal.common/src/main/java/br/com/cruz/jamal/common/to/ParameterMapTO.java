package br.com.cruz.jamal.common.to;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import br.com.cruz.jamal.common.enumeration.Restriction;
import br.com.cruz.jamal.common.exception.JamalException;
import br.com.cruz.jamal.common.exception.UnableToCompleteOperationException;
import br.com.cruz.jamal.common.helper.MapHelper;
import br.com.cruz.jamal.common.helper.ValidationHelper;

@NoArgsConstructor
public class ParameterMapTO extends JamalTO<ParameterMapTO> {

	private static final long serialVersionUID = 2974050412574989278L;

	
	// FIELDS
	
	@Setter(AccessLevel.PRIVATE)
	private Map<ParameterTO<?>, Restriction> map;

	
	//GETTERS
	
	private Map<ParameterTO<?>, Restriction> getMap() {
		if (this.map == null) {
			this.setMap(new HashMap<>());
		}
		return this.map;
	}
	
	
	public int getSize() {
		return this.getMap().size();
	}
	
	
	// METHODS
	
	
	public boolean isEmpty() {
		return MapHelper.isNullOrEmpty(this.getMap());
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> void addParameter(String parameterName, Restriction restriction, T parameterValue) throws JamalException{
		
		try {
		
			ValidationHelper.notNull(parameterName);
			ValidationHelper.notNull(restriction);
			ValidationHelper.notNull(parameterValue);
			
			this.addParameter(new ParameterTO<T>((Class<T>)parameterValue.getClass(), parameterName, parameterValue), restriction);
		
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("addParameter", e);
		}
		
	}
	
	public void addParameter(String parameterName, Restriction restriction) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(parameterName);
			ValidationHelper.notNull(restriction);
			
			this.addParameter(new ParameterTO<>(parameterName), restriction);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("addParameter", e);
		}
		
	}
	
	private void addParameter(@NotNull ParameterTO<?> parameter, @NotNull Restriction restriction) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(parameter);
			ValidationHelper.notNull(restriction);
			
			this.getMap().put(parameter, restriction);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("addParameter", e);
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> T getParameter(String parameterName, Class<T> clazz) throws JamalException {
		
		try {
		
			ValidationHelper.notNull(parameterName);
			ValidationHelper.notNull(clazz);
			
			if (parameterName.isEmpty()) {
				return null;
			}
			
			ParameterTO<T> parameter = new ParameterTO<>(parameterName);
			
			if (!this.getMap().containsKey(parameter)) {
				return null;
			}
			
			List<ParameterTO<?>> parameterList = new ArrayList<>(this.getMap().keySet());
			
			return (T) parameterList.get(parameterList.indexOf(parameter));
		
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getParameter", e);
		}
		
	}
	
	
	public Restriction getRestriction(String parameterName) throws JamalException {
		
		try {
		
			ValidationHelper.notNull(parameterName);
			
			if (parameterName.isEmpty()) {
				return null;
			}
			
			ParameterTO<?> parameter = new ParameterTO<>(parameterName);
			
			if (!this.getMap().containsKey(parameter)) {
				return null;
			}
			
			return this.getMap().get(parameter);
		
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getRestriction", e);
		}
		
	}
	
}
