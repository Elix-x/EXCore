package code.elix_x.excore.utils.packets.runnable;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Function;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientRunnableMessageHandler<REQ extends IMessage, REPLY extends IMessage> extends RunnableMessageHandler<REQ, REPLY> {

	public ClientRunnableMessageHandler(Function<Pair<REQ, MessageContext>, Pair<Runnable, REPLY>> run){
		super(run);
	}

	@Override
	public IThreadListener getThreadListener(MessageContext ctx){
		return Minecraft.getMinecraft();
	}

}
