package code.elix_x.excore.utils.packets;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import code.elix_x.excore.utils.packets.runnable.RunnableMessageHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;

public class SmartNetworkWrapper {

	private final String name;

	private SimpleNetworkWrapper channel;

	private int nextPacketId = 0;

	private Map<Class<? extends IMessage>, Side> packetsReceivingSide = new HashMap<Class<? extends IMessage>, Side>();

	public SmartNetworkWrapper(String name) {
		this.name = name;
		channel = NetworkRegistry.INSTANCE.newSimpleChannel(name);
	}
	
	/*
	 * Getters
	 */

	public String getName() {
		return name;
	}

	public SimpleNetworkWrapper getChannel() {
		return channel;
	}

	public int getNextPacketId() {
		return nextPacketId;
	}
	
	public Side getPacketReceivingSide(Class<? extends IMessage> clazz){
		return packetsReceivingSide.get(clazz);
	}
	
	/*
	 * Register
	 */

	public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side) {
		channel.registerMessage(messageHandler, requestMessageType, nextPacketId, side);
		packetsReceivingSide.put(requestMessageType, side);
		nextPacketId++;
	}

	public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(IMessageHandler<? super REQ, ? extends REPLY> messageHandler, Class<REQ> requestMessageType, Side side) {
		channel.registerMessage(messageHandler, requestMessageType, nextPacketId, side);
		packetsReceivingSide.put(requestMessageType, side);
		nextPacketId++;
	}
	
	/*
	 * Runnable
	 */
	
	public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Function<Pair<REQ, MessageContext>, Pair<Runnable, REPLY>> onReceive, Class<REQ> requestMessageType, Side side) {
		registerMessage(new RunnableMessageHandler(onReceive), requestMessageType, side);
	}
	
	public <REQ extends IMessage> void registerMessage1(final Function<Pair<REQ, MessageContext>, Runnable> onReceive, Class<REQ> requestMessageType, Side side) {
		registerMessage(new Function<Pair<REQ, MessageContext>, Pair<Runnable, IMessage>>(){

			@Override
			public Pair<Runnable, IMessage> apply(Pair<REQ, MessageContext> t) {
				return new ImmutablePair<Runnable, IMessage>(onReceive.apply(t), null);
			}

		}, requestMessageType, side);
	}

	public <REQ extends IMessage, REPLY extends IMessage> void registerMessage2(final Function<REQ, Pair<Runnable, REPLY>> onReceive, Class<REQ> requestMessageType, Side side) {
		registerMessage(new Function<Pair<REQ, MessageContext>, Pair<Runnable, REPLY>>(){

			@Override
			public Pair<Runnable, REPLY> apply(Pair<REQ, MessageContext> t) {
				return onReceive.apply(t.getKey());
			}

		}, requestMessageType, side);
	}

	public <REQ extends IMessage> void registerMessage3(final Function<REQ, Runnable> onReceive, Class<REQ> requestMessageType, Side side) {
		registerMessage(new Function<Pair<REQ, MessageContext>, Pair<Runnable, IMessage>>(){

			@Override
			public Pair<Runnable, IMessage> apply(Pair<REQ, MessageContext> t) {
				return new ImmutablePair<Runnable, IMessage>(onReceive.apply(t.getKey()), null);
			}

		}, requestMessageType, side);
	}

	public <REQ extends IMessage> void registerMessage(final Runnable onReceive, Class<REQ> requestMessageType, Side side) {
		registerMessage(new Function<Pair<REQ, MessageContext>, Pair<Runnable, IMessage>>() {
			
			@Override
			public Pair<Runnable, IMessage> apply(Pair<REQ, MessageContext> t) {
				return new ImmutablePair<Runnable, IMessage>(onReceive, null);
			}
			
		}, requestMessageType, side);
	}
	
	/*
	 * Sending
	 */
	
	public void sendToAll(IMessage message) {
		if(packetsReceivingSide.get(message.getClass()) == Side.CLIENT){
			channel.sendToAll(message);
		}
	}

	public void sendTo(IMessage message, EntityPlayerMP player) {
		if(packetsReceivingSide.get(message.getClass()) == Side.CLIENT){
			channel.sendTo(message, player);
		}
	}

	public void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
		if(packetsReceivingSide.get(message.getClass()) == Side.CLIENT){
			channel.sendToAllAround(message, point);
		}
	}

	public void sendToDimension(IMessage message, int dimensionId) {
		if(packetsReceivingSide.get(message.getClass()) == Side.CLIENT){
			channel.sendToDimension(message, dimensionId);
		}
	}

	public void sendToServer(IMessage message) {
		if(packetsReceivingSide.get(message.getClass()) == Side.SERVER && FMLCommonHandler.instance().getSide() == Side.CLIENT){
			channel.sendToServer(message);
		}
	}

}
