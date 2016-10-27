package code.elix_x.excore.utils.data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.google.common.base.Throwables;

public class DataCopier {

	public static <O> O copyData(O from, O to){
		return copyData(from, to, false);
	}

	public static <O> O copyData(O from, O to, boolean propagateExceptions){
		Class clazz = from.getClass();
		for(Field field : clazz.getDeclaredFields()){
			if(!Modifier.isStatic(field.getModifiers())){
				field.setAccessible(true);
				try{
					field.set(to, field.get(from));
				} catch(IllegalArgumentException e){
					if(propagateExceptions) Throwables.propagate(e);
				} catch(IllegalAccessException e){
					if(propagateExceptions) Throwables.propagate(e);
				}
			}
		}
		return to;
	}

}
