package code.elix_x.excore.utils.nbt.mbt.encoders;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.base.Throwables;

import code.elix_x.excore.utils.nbt.mbt.MBT;
import code.elix_x.excore.utils.nbt.mbt.MBTIgnore;
import code.elix_x.excore.utils.nbt.mbt.NBTEncoder;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class NBTClassEncoder implements NBTEncoder<Object, NBTTagCompound> {

	public static final String CLASS = "#CLASS";

	public boolean encodeClass;
	public boolean encodeFinal;
	public boolean encodeStatic;
	public boolean encodeSuper;

	public NBTClassEncoder(boolean encodeClass, boolean encodeFinal, boolean encodeStatic, boolean encodeSuper){
		this.encodeClass = encodeClass;
		this.encodeFinal = encodeFinal;
		this.encodeStatic = encodeStatic;
		this.encodeSuper = encodeSuper;
	}

	@Override
	public boolean canEncode(Object o){
		return !o.getClass().isAnnotationPresent(MBTIgnore.class);
	}

	@Override
	public boolean canDecode(NBTBase nbt, Class clazz){
		return nbt instanceof NBTTagCompound && !clazz.isAnnotationPresent(MBTIgnore.class);
	}

	@Override
	public NBTTagCompound toNBT(MBT mbt, Object t){
		NBTTagCompound nbt = new NBTTagCompound();
		Class clazz = t.getClass();
		if(encodeClass) nbt.setString(CLASS, clazz.getName());
		while(clazz != null && clazz != Object.class){
			if(!clazz.isAnnotationPresent(MBTIgnore.class)){
				for(Field field : clazz.getDeclaredFields()){
					if(!field.isAnnotationPresent(MBTIgnore.class)){
						field.setAccessible(true);
						if((encodeFinal || !Modifier.isFinal(field.getModifiers())) && (encodeStatic || !Modifier.isStatic(field.getModifiers()))){
							try{
								nbt.setTag(field.getName(), mbt.toNBT(field.get(t)));
							} catch(IllegalArgumentException e){
								Throwables.propagate(e);
							} catch(IllegalAccessException e){
								Throwables.propagate(e);
							}
						}
					}
				}
			}
			clazz = encodeSuper ? clazz.getSuperclass() : Object.class;
		}
		return nbt;
	}

	@Override
	public Object fromNBT(MBT mbt, NBTTagCompound nbt, Class clazz, Class... tsclasses){
		try{
			Constructor constructor;
			if(encodeClass) clazz = Class.forName(nbt.getString(CLASS));
			try{
				constructor = clazz.getConstructor();
			} catch(NoSuchMethodException e){
				constructor = clazz.getDeclaredConstructor();
			}
			constructor.setAccessible(true);
			Object o = constructor.newInstance();
			populate(mbt, nbt, o);
			return o;
		} catch(InstantiationException e){
			throw Throwables.propagate(e);
		} catch(IllegalAccessException e){
			throw Throwables.propagate(e);
		} catch(NoSuchMethodException e){
			throw Throwables.propagate(e);
		} catch(SecurityException e){
			throw Throwables.propagate(e);
		} catch(IllegalArgumentException e){
			throw Throwables.propagate(e);
		} catch(InvocationTargetException e){
			throw Throwables.propagate(e);
		} catch(ClassNotFoundException e){
			throw Throwables.propagate(e);
		}
	}

	public void populate(MBT mbt, NBTTagCompound nbt, Object o){
		Class clazz = o.getClass();
		try{
			while(clazz != null && clazz != Object.class){
				if(!clazz.isAnnotationPresent(MBTIgnore.class)){
					for(Field field : clazz.getDeclaredFields()){
						if(!field.isAnnotationPresent(MBTIgnore.class)){
							field.setAccessible(true);
							if(nbt.hasKey(field.getName())){
								if(encodeStatic || !Modifier.isStatic(field.getModifiers())){
									if(Modifier.isFinal(field.getModifiers())){
										if(encodeFinal){
											Field modifiers = Field.class.getDeclaredField("modifiers");
											modifiers.setAccessible(true);
											modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

											if(field.getGenericType() instanceof ParameterizedType){
												Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
												Class[] clas = new Class[]{};
												for(Type type : types){
													if(type instanceof Class){
														clas = ArrayUtils.add(clas, (Class) type);
													}
												}
												field.set(o, mbt.fromNBT(nbt.getTag(field.getName()), field.getType(), clas));
											} else{
												field.set(o, mbt.fromNBT(nbt.getTag(field.getName()), field.getType()));
											}
										}
									} else{
										if(field.getGenericType() instanceof ParameterizedType){
											Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
											Class[] clas = new Class[]{};
											for(Type type : types){
												if(type instanceof Class){
													clas = ArrayUtils.add(clas, (Class) type);
												}
											}
											field.set(o, mbt.fromNBT(nbt.getTag(field.getName()), field.getType(), clas));
										} else{
											field.set(o, mbt.fromNBT(nbt.getTag(field.getName()), field.getType()));
										}
									}
								}
							}
						}
					}
				}
				clazz = encodeSuper ? clazz.getSuperclass() : Object.class;
			}
		} catch(IllegalArgumentException e){
			Throwables.propagate(e);
		} catch(IllegalAccessException e){
			Throwables.propagate(e);
		} catch(NoSuchFieldException e){
			Throwables.propagate(e);
		} catch(SecurityException e){
			Throwables.propagate(e);
		}
	}

}
