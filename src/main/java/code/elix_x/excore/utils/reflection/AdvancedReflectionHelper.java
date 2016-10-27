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

	static{
		try{
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
		} else{
			return original & (~modifier);
		}
	}

	public static class AClass<C> {

		protected final Class<C> clas;

		public AClass(Class<C> clas){
			this.clas = clas;
		}

		public AClass(String clas){
			try{
				this.clas = (Class<C>) Class.forName(clas);
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
		}

		public boolean isInterface(){
			return clas.isInterface();
		}

		public AInterface<C> toInterface(){
			return new AInterface<>(clas);
		}

		public boolean isEnum(){
			return clas.isEnum();
		}

		public <E extends Enum> AEnum<E> toEnum(){
			return new AEnum<E>((Class<E>) clas);
		}

		public boolean isAnnotation(){
			return clas.isAnnotation();
		}

		public AAnnotation<C> toAnnotation(){
			return new AAnnotation<>(clas);
		}

		public AConstructor<C> getDeclaredConstructor(Class... args){
			return new AConstructor(clas, args);
		}

		public <T> AField<C, T> getDeclaredField(String... names){
			return new AField(clas, names);
		}

		public <T> AMethod<C, T> getDeclaredMethod(String[] names, Class... args){
			return new AMethod(clas, names, args);
		}

		public static class AInterface<C> extends AClass<C> {

			private AInterface(Class<C> clas){
				super(clas);
			}

		}

		public static class AEnum<C extends Enum> extends AClass<C> {

			private AEnum(Class<C> clas){
				super(clas);
			}

			public C getEnum(int ordinal){
				return clas.getEnumConstants()[ordinal];
			}

			public C getEnum(String name){
				return (C) Enum.valueOf(clas, name);
			}

		}

		public static class AAnnotation<C> extends AClass<C> {

			private AAnnotation(Class<C> clas){
				super(clas);
			}

		}

	}

	private static abstract class ReflectionObject<C, T> {

		private final AClass<C> clas;

		public ReflectionObject(Class<C> clas){
			this.clas = new AClass<C>(clas);
		}

		public ReflectionObject(String clas){
			try{
				this.clas = new AClass(Class.forName(clas));
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
		}

		public AClass<C> clas(){
			return clas;
		}

		public abstract <R extends ReflectionObject<C, T>> R set(int modifier, boolean on);

		public abstract <R extends ReflectionObject<C, T>> R setAccessible(boolean accessible);

		public abstract T get();

	}

	public static class AConstructor<C> extends ReflectionObject<C, Constructor<C>> {

		private final Constructor<C> constructor;

		public AConstructor(Constructor<C> constructor){
			super(constructor.getDeclaringClass());
			this.constructor = constructor;
		}

		public AConstructor(Class<C> clas, Class... args){
			super(clas);
			try{
				this.constructor = clas.getDeclaredConstructor(args);
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
		}

		public AConstructor<C> set(int modifier, boolean on){
			try{
				fieldConstructorModifiers.setInt(constructor, AdvancedReflectionHelper.set(constructor.getModifiers(), modifier, on));
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
			return this;
		}

		public AConstructor<C> setAccessible(boolean accessible){
			constructor.setAccessible(accessible);
			return this;
		}

		public Constructor get(){
			return constructor;
		}

		public C newInstance(Object... args){
			try{
				return constructor.newInstance(args);
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
		}

	}

	public static class AField<C, T> extends ReflectionObject<C, Field> {

		private final Field field;

		public AField(Field field){
			super((Class<C>) field.getDeclaringClass());
			this.field = field;
		}

		public AField(Class<C> clas, String... names){
			this(ReflectionHelper.findField(clas, names));
		}

		public AField<C, T> set(int modifier, boolean on){
			try{
				fieldFieldModifiers.setInt(field, AdvancedReflectionHelper.set(field.getModifiers(), modifier, on));
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
			return this;
		}

		public AField<C, T> setAccessible(boolean accessible){
			field.setAccessible(accessible);
			return this;
		}

		public AField<C, T> setFinal(boolean finall){
			return set(Modifier.FINAL, finall);
		}

		public Field get(){
			return field;
		}

		public <I extends C> T get(I instance){
			try{
				return (T) field.get(instance);
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
		}

		public <I extends C> void set(I instance, T t){
			try{
				field.set(instance, t);
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
		}

	}

	public static class AMethod<C, T> extends ReflectionObject<C, Method> {

		private final Method method;

		public AMethod(Method method){
			super((Class<C>) method.getDeclaringClass());
			this.method = method;
		}

		public AMethod(Class<C> clas, String[] names, Class... args){
			this(ReflectionHelper.findMethod(clas, null, names, args));
		}

		public AMethod<C, T> set(int modifier, boolean on){
			try{
				fieldMethodModifiers.setInt(method, AdvancedReflectionHelper.set(method.getModifiers(), modifier, on));
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
			return this;
		}

		public AMethod<C, T> setAccessible(boolean accessible){
			method.setAccessible(accessible);
			return this;
		}

		public AMethod<C, T> setFinal(boolean finall){
			return set(Modifier.FINAL, finall);
		}

		public Method get(){
			return method;
		}

		public <I extends C> T invoke(I instance, Object... args){
			try{
				return (T) method.invoke(instance, args);
			} catch(Exception e){
				throw Throwables.propagate(e);
			}
		}

	}

}
