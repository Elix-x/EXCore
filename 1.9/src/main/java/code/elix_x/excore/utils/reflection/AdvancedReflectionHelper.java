package code.elix_x.excore.utils.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.google.common.base.Throwables;

import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class AdvancedReflectionHelper {

	private static final Field fieldConstructorModifiers;
	private static final Field fieldFieldModifiers;
	private static final Field fieldMethodModifiers;

	static {
		try {
			fieldConstructorModifiers = Constructor.class.getDeclaredField("modifiers");
			fieldConstructorModifiers.setAccessible(true);
			fieldFieldModifiers = Field.class.getDeclaredField("modifiers");
			fieldFieldModifiers.setAccessible(true);
			fieldMethodModifiers = Method.class.getDeclaredField("modifiers");
			fieldMethodModifiers.setAccessible(true);
		} catch(Exception e){
			throw Throwables.propagate(e);
		}
	}

	private static int set(int original, int modifier, boolean on){
		if(on){
			return original | modifier;
		} else {
			return original & (~modifier);
		}
	}

	public static class AClass<T> {

		private final Class<T> clas;

		public AClass(Class<T> clas){
			this.clas = clas;
		}

		public AClass(String clas){
			try {
				this.clas = (Class<T>) Class.forName(clas);
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
		}

		public AConstructor<T> getDeclaredConstructor(Class... args){
			return new AConstructor(clas, args);
		}

		public <FT> AField<FT> getDeclaredField(String... names){
			return new AField(clas, names);
		}

		public <MT> AMethod<MT> getDeclaredMethod(String[] names, Class... args){
			return new AMethod(clas, names, args);
		}

	}

	public static class AConstructor<T> {

		private final Constructor<T> constructor;

		public AConstructor(Constructor<T> constructor){
			this.constructor = constructor;
		}

		public AConstructor(Class<T> clas, Class... args){
			try {
				this.constructor = clas.getDeclaredConstructor(args);
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
		}

		public AConstructor set(int modifier, boolean on){
			try {
				fieldConstructorModifiers.setInt(constructor, AdvancedReflectionHelper.set(constructor.getModifiers(), modifier, on));
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
			return this;
		}

		public AConstructor setAccessible(boolean accessible){
			constructor.setAccessible(accessible);
			return this;
		}

		public Constructor constructor(){
			return constructor;
		}

		public T newInstance(Object... args){
			try {
				return constructor.newInstance(args);
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
		}

	}

	public static class AField<T> {

		private final Field field;

		public AField(Field field){
			this.field = field;
		}

		public AField(Class<?> clas, String... names){
			this(ReflectionHelper.findField(clas, names));
		}

		public AField set(int modifier, boolean on){
			try {
				fieldFieldModifiers.setInt(field, AdvancedReflectionHelper.set(field.getModifiers(), modifier, on));
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
			return this;
		}

		public AField setAccessible(boolean accessible){
			field.setAccessible(accessible);
			return this;
		}

		public AField setFinal(boolean finall){
			return set(Modifier.FINAL, finall);
		}

		public Field field(){
			return field;
		}

		public T get(Object instance){
			try {
				return (T) field.get(instance);
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
		}

		public void set(Object instance, T t){
			try {
				field.set(instance, t);
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
		}

	}

	public static class AMethod<T> {

		private final Method method;

		public AMethod(Method method){
			this.method = method;
		}

		public AMethod(Class clas, String[] names, Class... args){
			this(ReflectionHelper.findMethod(clas, null, names, args));
		}

		public AMethod set(int modifier, boolean on){
			try {
				fieldMethodModifiers.setInt(method, AdvancedReflectionHelper.set(method.getModifiers(), modifier, on));
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
			return this;
		}

		public AMethod setAccessible(boolean accessible){
			method.setAccessible(accessible);
			return this;
		}

		public AMethod setFinal(boolean finall){
			return set(Modifier.FINAL, finall);
		}

		public Method method(){
			return method;
		}

		public T invoke(Object instance, Object... args){
			try {
				return (T) method.invoke(instance, args);
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
		}

	}

}
