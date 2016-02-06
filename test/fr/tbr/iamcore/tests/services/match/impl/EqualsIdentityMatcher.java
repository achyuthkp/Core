package fr.tbr.iamcore.tests.services.match.impl;

import fr.tbr.iamcore.tests.services.match.Matcher;
import iamcore.datamodel.Identity;

public class EqualsIdentityMatcher implements Matcher<Identity> {

	@Override
	public boolean match(Identity criteria, Identity toBeChecked) {
		return toBeChecked.getDisplayName().equals(criteria.getDisplayName())
				|| toBeChecked.getEmailAddress().equals(
						criteria.getEmailAddress());
	}

}
