package com.followyu.xmpp;

import com.followyu.entities.Account;
import com.followyu.xmpp.stanzas.IqPacket;

public interface OnIqPacketReceived extends PacketReceived {
	public void onIqPacketReceived(Account account, IqPacket packet);
}
