package com.followyu.xmpp;

import com.followyu.entities.Account;
import com.followyu.xmpp.stanzas.PresencePacket;

public interface OnPresencePacketReceived extends PacketReceived {
	public void onPresencePacketReceived(Account account, PresencePacket packet);
}
