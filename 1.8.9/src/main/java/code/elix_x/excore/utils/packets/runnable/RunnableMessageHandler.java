package code.elix_x.excore.utils.packets.runnable;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Function;

import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class RunnableMessageHandler<REQ extends IMessage, REPLY extends IMessage> implements IMessageHandler<REQ, REPLY> {

	public Function<Pair<REQ, MessageContext>, Pair<Runnable, REPLY>> run;

	public RunnableMessageHandler(Function<Pair<REQ, MessageContext>, Pair<Runnable, REPLY>> run){
		this.run = run;
	}

	@Override
	public REPLY onMessage(REQ message, MessageContext ctx){
		Pair<Runnable, REPLY> pair = run.apply(new ImmutablePair<REQ, MessageContext>(message, ctx));
		getThreadListener(ctx).addScheduledTask(pair.getKey());
		return pair.getValue();
	}

	public abstract IThreadListener getThreadListener(MessageContext ctx);

}
