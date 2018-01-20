package com.followyu.parser;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.followyu.entities.Account;
import com.followyu.entities.Contact;
import com.followyu.services.XmppConnectionService;
import com.followyu.xml.Element;
import com.followyu.xmpp.jid.Jid;

public abstract class AbstractParser {

	protected XmppConnectionService mXmppConnectionService;

	protected AbstractParser(XmppConnectionService service) {
		this.mXmppConnectionService = service;
	}

	protected long getTimestamp(Element packet) {
		long now = System.currentTimeMillis();
		Element delay = packet.findChild("delay");
		if (delay == null) {
			return now;
		}
		String stamp = delay.getAttribute("stamp");
		if (stamp == null) {
			return now;
		}
		try {
			long time = parseTimestamp(stamp).getTime();
			return now < time ? now : time;
		} catch (ParseException e) {
			return now;
		}
	}

	public static Date parseTimestamp(String timestamp) throws ParseException {
		timestamp = timestamp.replace("Z", "+0000");
		SimpleDateFormat dateFormat;
		timestamp = timestamp.substring(0,19)+timestamp.substring(timestamp.length() -5,timestamp.length());
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",Locale.US);
		return dateFormat.parse(timestamp);
	}

	protected void updateLastseen(final Element packet, final Account account,
			final boolean presenceOverwrite) {
		final Jid from = packet.getAttributeAsJid("from");
		updateLastseen(packet, account, from, presenceOverwrite);
	}

	protected void updateLastseen(final Element packet, final Account account, final Jid from,
								  final boolean presenceOverwrite) {
		final String presence = from == null || from.isBareJid() ? "" : from.getResourcepart();
		final Contact contact = account.getRoster().getContact(from);
		final long timestamp = getTimestamp(packet);
		if (timestamp >= contact.lastseen.time) {
			contact.lastseen.time = timestamp;
			if (!presence.isEmpty() && presenceOverwrite) {
				contact.lastseen.presence = presence;
			}
		}
	}

	protected String avatarData(Element items) {
		Element item = items.findChild("item");
		if (item == null) {
			return null;
		}
		Element data = item.findChild("data", "urn:xmpp:avatar:data");
		if (data == null) {
			return null;
		}
		return data.getContent();
	}
}
