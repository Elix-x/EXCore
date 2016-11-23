package code.elix_x.excore.utils.mod;

import code.elix_x.excore.utils.proxy.IProxy;

public interface IMod<M extends IMod<M, P>, P extends IProxy<M>> extends IProxy {

	public P getProxy();

}
