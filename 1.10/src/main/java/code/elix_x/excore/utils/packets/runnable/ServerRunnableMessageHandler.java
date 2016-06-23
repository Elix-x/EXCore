package code.elix_x.excore.utils.packets.runnable;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Function;

import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerRunnableMessageHandler<REQ extends IMessage, REPLY extends IMessage> extends RunnableMessageHandler<REQ, REPLY> {

	public ServerRunnableMessageHandler(Function<Pair<REQ, MessageContext>, Pair<Runnable, REPLY>> run){
		super(run);
	}

	@Override
	public IThreadListener getThreadListener(MessageContext ctx){
		return ctx.getServerHandler().playerEntity.mcServer;
	}

}
