package com.followyu.xmpp.jingle.stanzas;

import com.followyu.xml.Element;

public class Reason extends Element {
	private Reason(String name) {
		super(name);
	}

	public Reason() {
		super("reason");
	}
}
