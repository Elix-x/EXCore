package code.elix_x.excore.utils.packets;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Function;

import code.elix_x.excore.utils.packets.runnable.RunnableMessageHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleChannelHandlerWrapper;
import cpw.mods.fml.common.network.simpleimpl.SimpleIndexedCodec;
import cpw.mods.fml.relauncher.Side;
import io.netty.channel.ChannelFutureListener;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

public class SmartNetworkWrapper {

	private final String name;

	private EnumMap<Side, FMLEmbeddedChannel> channels;
	private SimpleIndexedCodec packetCodec;

	private int nextPacketId = 0;

	private Map<Class<? extends IMessage>, Side> packetsReceivingSide = new HashMap<Class<? extends IMessage>, Side>();

	public SmartNetworkWrapper(String name){
		this.name = name;
		packetCodec = new SimpleIndexedCodec();
		channels = NetworkRegistry.INSTANCE.newChannel(name, packetCodec);
	}

	/*
	 * Getters
	 */

	public String getName(){
		return name;
	}

	public int getNextPacketId(){
		return nextPacketId;
	}

	public Side getPacketReceivingSide(Class<? extends IMessage> clazz){
		return packetsReceivingSide.get(clazz);
	}

	/*
	 * From Simple Network Wrapper
	 */

	/**
	 * Construct a minecraft packet from the supplied message. Can be used where minecraft packets are required, such as
	 * {@link TileEntity#getDescriptionPacket}.
	 *
	 * @param message The message to translate into packet form
	 * @return A minecraft {@link Packet} suitable for use in minecraft APIs
	 */
	public Packet getPacketFrom(IMessage message){
		return channels.get(Side.SERVER).generatePacketFrom(message);
	}

	/*
	 * Register
	 */

	public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(IMessageHandler<? super REQ, ? extends REPLY> messageHandler, Class<REQ> requestMessageType, Side side){
		packetCodec.addDiscriminator(nextPacketId, requestMessageType);
		FMLEmbeddedChannel channel = channels.get(side);
		String type = channel.findChannelHandlerNameForType(SimpleIndexedCodec.class);
		if(side == Side.SERVER){
			addServerHandlerAfter(channel, type, messageHandler, requestMessageType);
		} else {
			addClientHandlerAfter(channel, type, messageHandler, requestMessageType);
		}

		packetsReceivingSide.put(requestMessageType, side);
		nextPacketId++;
	}

	private <REQ extends IMessage, REPLY extends IMessage, NH extends INetHandler> void addServerHandlerAfter(FMLEmbeddedChannel channel, String type, IMessageHandler<? super REQ, ? extends REPLY> messageHandler, Class<REQ> requestType){
		SimpleChannelHandlerWrapper<REQ, REPLY> handler = getHandlerWrapper(messageHandler, Side.SERVER, requestType);
		channel.pipeline().addAfter(type, messageHandler.getClass().getName() + "#" + nextPacketId, handler);
	}

	private <REQ extends IMessage, REPLY extends IMessage, NH extends INetHandler> void addClientHandlerAfter(FMLEmbeddedChannel channel, String type, IMessageHandler<? super REQ, ? extends REPLY> messageHandler, Class<REQ> requestType){
		SimpleChannelHandlerWrapper<REQ, REPLY> handler = getHandlerWrapper(messageHandler, Side.CLIENT, requestType);
		channel.pipeline().addAfter(type, messageHandler.getClass().getName() + "#" + nextPacketId, handler);
	}

	private <REPLY extends IMessage, REQ extends IMessage> SimpleChannelHandlerWrapper<REQ, REPLY> getHandlerWrapper(IMessageHandler<? super REQ, ? extends REPLY> messageHandler, Side side, Class<REQ> requestType){
		return new SimpleChannelHandlerWrapper<REQ, REPLY>(messageHandler, side, requestType);
	}

	/*
	 * Runnable
	 */

	public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Function<Pair<REQ, MessageContext>, Pair<Runnable, REPLY>> onReceive, Class<REQ> requestMessageType, Side side){
		registerMessage(new RunnableMessageHandler(onReceive), requestMessageType, side);
	}

	public <REQ extends IMessage> void registerMessage1(final Function<Pair<REQ, MessageContext>, Runnable> onReceive, Class<REQ> requestMessageType, Side side){
		registerMessage(new Function<Pair<REQ, MessageContext>, Pair<Runnable, IMessage>>(){

			@Override
			public Pair<Runnable, IMessage> apply(Pair<REQ, MessageContext> t){
				return new ImmutablePair<Runnable, IMessage>(onReceive.apply(t), null);
			}

		}, requestMessageType, side);
	}

	public <REQ extends IMessage, REPLY extends IMessage> void registerMessage2(final Function<REQ, Pair<Runnable, REPLY>> onReceive, Class<REQ> requestMessageType, Side side){
		registerMessage(new Function<Pair<REQ, MessageContext>, Pair<Runnable, REPLY>>(){

			@Override
			public Pair<Runnable, REPLY> apply(Pair<REQ, MessageContext> t){
				return onReceive.apply(t.getKey());
			}

		}, requestMessageType, side);
	}

	public <REQ extends IMessage> void registerMessage3(final Function<REQ, Runnable> onReceive, Class<REQ> requestMessageType, Side side){
		registerMessage(new Function<Pair<REQ, MessageContext>, Pair<Runnable, IMessage>>(){

			@Override
			public Pair<Runnable, IMessage> apply(Pair<REQ, MessageContext> t){
				return new ImmutablePair<Runnable, IMessage>(onReceive.apply(t.getKey()), null);
			}

		}, requestMessageType, side);
	}

	public <REQ extends IMessage> void registerMessage(final Runnable onReceive, Class<REQ> requestMessageType, Side side){
		registerMessage(new Function<Pair<REQ, MessageContext>, Pair<Runnable, IMessage>>(){

			@Override
			public Pair<Runnable, IMessage> apply(Pair<REQ, MessageContext> t){
				return new ImmutablePair<Runnable, IMessage>(onReceive, null);
			}

		}, requestMessageType, side);
	}

	/*
	 * Sending
	 */

	/**
	 * Send this message to everyone.
	 * The {@link IMessageHandler} for this message type should be on the CLIENT side.
	 *
	 * @param message The message to send
	 */
	public void sendToAll(IMessage message){
		if(packetsReceivingSide.get(message.getClass()) == Side.CLIENT){
			channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
			channels.get(Side.SERVER).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
		}
	}

	/**
	 * Send this message to the specified player.
	 * The {@link IMessageHandler} for this message type should be on the CLIENT side.
	 *
	 * @param message The message to send
	 * @param player The player to send it to
	 */
	public void sendTo(IMessage message, EntityPlayerMP player){
		if(packetsReceivingSide.get(message.getClass()) == Side.CLIENT){
			channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
			channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
			channels.get(Side.SERVER).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
		}
	}

	/**
	 * Send this message to everyone within a certain range of a point.
	 * The {@link IMessageHandler} for this message type should be on the CLIENT side.
	 *
	 * @param message The message to send
	 * @param point The {@link TargetPoint} around which to send
	 */
	public void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point){
		if(packetsReceivingSide.get(message.getClass()) == Side.CLIENT){
			channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
			channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
			channels.get(Side.SERVER).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
		}
	}

	/**
	 * Send this message to everyone within the supplied dimension.
	 * The {@link IMessageHandler} for this message type should be on the CLIENT side.
	 *
	 * @param message The message to send
	 * @param dimensionId The dimension id to target
	 */
	public void sendToDimension(IMessage message, int dimensionId){
		if(packetsReceivingSide.get(message.getClass()) == Side.CLIENT){
			channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
			channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
			channels.get(Side.SERVER).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
		}
	}

	/**
	 * Send this message to the server.
	 * The {@link IMessageHandler} for this message type should be on the SERVER side.
	 *
	 * @param message The message to send
	 */
	public void sendToServer(IMessage message){
		if(packetsReceivingSide.get(message.getClass()) == Side.SERVER && FMLCommonHandler.instance().getSide() == Side.CLIENT){
			channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
			channels.get(Side.CLIENT).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
		}
	}

}
