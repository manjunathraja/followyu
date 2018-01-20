package com.followyu.xmpp.jingle;

import com.followyu.entities.Account;
import com.followyu.xmpp.PacketReceived;
import com.followyu.xmpp.jingle.stanzas.JinglePacket;

public interface OnJinglePacketReceived extends PacketReceived {
	public void onJinglePacketReceived(Account account, JinglePacket packet);
}
