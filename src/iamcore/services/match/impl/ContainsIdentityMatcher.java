package iamcore.services.match.impl;

import iamcore.datamodel.Identity;
import iamcore.services.match.Matcher;

public class ContainsIdentityMatcher implements Matcher<Identity> {

	@Override
	public boolean match(Identity criteria, Identity toBeChecked) {
		return toBeChecked.getDisplayName().contains(criteria.getDisplayName())
				|| toBeChecked.getEmailAddress().contains(criteria.getEmailAddress());
	}

}
