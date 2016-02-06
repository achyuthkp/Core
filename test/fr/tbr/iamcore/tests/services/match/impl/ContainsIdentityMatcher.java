package fr.tbr.iamcore.tests.services.match.impl;

import fr.tbr.iamcore.tests.services.match.Matcher;
import iamcore.datamodel.Identity;

public class ContainsIdentityMatcher implements Matcher<Identity> {

	@Override
	public boolean match(Identity criteria, Identity toBeChecked) {
		return toBeChecked.getDisplayName().contains(criteria.getDisplayName())
				|| toBeChecked.getEmailAddress().contains(criteria.getEmailAddress());
	}

}
