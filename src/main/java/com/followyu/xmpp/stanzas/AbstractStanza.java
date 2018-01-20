package com.followyu.xmpp.stanzas;

import com.followyu.entities.Account;
import com.followyu.xml.Element;
import com.followyu.xmpp.jid.Jid;

public class AbstractStanza extends Element {

	protected AbstractStanza(final String name) {
		super(name);
	}

	public Jid getTo() {
		return getAttributeAsJid("to");
	}

	public Jid getFrom() {
		return getAttributeAsJid("from");
	}

	public String getId() {
		return this.getAttribute("id");
	}

	public void setTo(final Jid to) {
		if (to != null) {
			setAttribute("to", to.toString());
		}
	}

	public void setFrom(final Jid from) {
		if (from != null) {
			setAttribute("from", from.toString());
		}
	}

	public void setId(final String id) {
		setAttribute("id", id);
	}

	public boolean fromServer(final Account account) {
		return getFrom() == null
			|| getFrom().equals(account.getServer())
			|| getFrom().equals(account.getJid().toBareJid())
			|| getFrom().equals(account.getJid());
	}

	public boolean toServer(final Account account) {
		return getTo() == null
			|| getTo().equals(account.getServer())
			|| getTo().equals(account.getJid().toBareJid())
			|| getTo().equals(account.getJid());
	}
}
