package com.followyu.xmpp.jingle;

public interface OnPrimaryCandidateFound {
	public void onPrimaryCandidateFound(boolean success,
			JingleCandidate canditate);
}
