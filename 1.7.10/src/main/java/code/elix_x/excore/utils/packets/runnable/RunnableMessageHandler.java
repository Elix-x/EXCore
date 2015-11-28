package code.elix_x.excore.utils.packets.runnable;

import java.util.function.Function;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class RunnableMessageHandler<REQ extends IMessage, REPLY extends IMessage> implements IMessageHandler<REQ, REPLY> {
	
	public Function<Pair<REQ, MessageContext>, Pair<Runnable, REPLY>> run;

	public RunnableMessageHandler(Function<Pair<REQ, MessageContext>, Pair<Runnable, REPLY>> run) {
		this.run = run;
	}

	@Override
	public REPLY onMessage(REQ message, MessageContext ctx) {
		Pair<Runnable, REPLY> pair = run.apply(new ImmutablePair<REQ, MessageContext>(message, ctx));
		pair.getKey().run();
		return pair.getValue();
	}
	
}
