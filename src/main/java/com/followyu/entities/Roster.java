package com.followyu.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.followyu.xmpp.jid.Jid;

public class Roster {
	final Account account;
	final HashMap<String, Contact> contacts = new HashMap<>();
	private String version = null;

	public Roster(Account account) {
		this.account = account;
	}

	public Contact getContactFromRoster(Jid jid) {
		if (jid == null) {
			return null;
		}
		synchronized (this.contacts) {
			Contact contact = contacts.get(jid.toBareJid().toString());
			if (contact != null && contact.showInRoster()) {
				return contact;
			} else {
				return null;
			}
		}
	}

	public Contact getContact(final Jid jid) {
		synchronized (this.contacts) {
			final Jid bareJid = jid.toBareJid();
			if (contacts.containsKey(bareJid.toString())) {
				return contacts.get(bareJid.toString());
			} else {
				Contact contact = new Contact(bareJid);
				contact.setAccount(account);
				contacts.put(bareJid.toString(), contact);
				return contact;
			}
		}
	}

    public boolean isContactExit(final Jid jid) {
        synchronized (this.contacts) {
            final Jid bareJid = jid.toBareJid();
            if (contacts.containsKey(bareJid.toString())) {
                return true;
            } else {
                return false;
            }
        }
    }

	public void clearPresences() {
		for (Contact contact : getContacts()) {
			contact.clearPresences();
		}
	}

	public void markAllAsNotInRoster() {
		for (Contact contact : getContacts()) {
			contact.resetOption(Contact.Options.IN_ROSTER);
		}
	}

	public void clearSystemAccounts() {
		for (Contact contact : getContacts()) {
			contact.setPhotoUri(null);
			contact.setSystemName(null);
			contact.setSystemAccount(null);
		}
	}

	public List<Contact> getContacts() {
		synchronized (this.contacts) {
			return new ArrayList<>(this.contacts.values());
		}
	}

	public void initContact(final Contact contact) {
		contact.setAccount(account);
		contact.setOption(Contact.Options.IN_ROSTER);
		synchronized (this.contacts) {
			contacts.put(contact.getJid().toBareJid().toString(), contact);
		}
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return this.version;
	}

	public Account getAccount() {
		return this.account;
	}
}
