package com.followyu.xmpp;

import com.followyu.entities.Account;
import com.followyu.xmpp.stanzas.MessagePacket;

public interface OnMessagePacketReceived extends PacketReceived {
	public void onMessagePacketReceived(Account account, MessagePacket packet);
}
