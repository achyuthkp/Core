package iamcore.services.match.impl;

import iamcore.datamodel.Identity;
import iamcore.services.match.Matcher;

public class EqualsIdentityMatcher implements Matcher<Identity> {

	@Override
	public boolean match(Identity criteria, Identity toBeChecked) {
		return toBeChecked.getDisplayName().equals(criteria.getDisplayName())
				|| toBeChecked.getEmailAddress().equals(
						criteria.getEmailAddress());
	}

}
