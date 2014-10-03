package br.com.cruz.jamal.common.to;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import lombok.NoArgsConstructor;
import br.com.cruz.jamal.common.exception.JamalException;
import br.com.cruz.jamal.common.exception.UnableToCompleteOperationException;
import br.com.cruz.jamal.common.helper.ReflectionHelper;

@NoArgsConstructor
public abstract class JamalTO<T extends JamalTO<T>> implements Serializable {

	private static final long serialVersionUID = 2161281162506571180L;
	
	
	public final List<Field> getPublicFieldList() throws JamalException {
		try {
			return ReflectionHelper.getPublicFieldList(this.getClass());
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getPublicFieldList", e);
		}
	}
	
	public final <V> V get(String fieldName, Class<V> fieldType) throws JamalException {
		try {
			return ReflectionHelper.get(this, fieldName, fieldType);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("get", e);
		}
	}
	
	public final <V> void set(String fieldName, V fieldValue) throws JamalException {
		try {
			ReflectionHelper.set(this, fieldName, fieldValue);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("set", e);
		}
	}
	
	
	@Override
	public String toString() {
		try {
			return super.toString();
		} catch (Exception e) {
//			JamalTO.log.error(e.getMessage(), e);
		}
		return super.toString();
	}
}
