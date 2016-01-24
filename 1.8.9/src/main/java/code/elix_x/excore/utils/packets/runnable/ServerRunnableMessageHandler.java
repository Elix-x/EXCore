package code.elix_x.excore.utils.packets.runnable;

import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerRunnableMessageHandler<REQ extends IMessage, REPLY extends IMessage> extends RunnableMessageHandler<REQ, REPLY> {

	public ServerRunnableMessageHandler() {
		super(null);
	}

	@Override
	public IThreadListener getListener(MessageContext ctx) {
		return null;
	}

}
