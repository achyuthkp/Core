package proj.iamcore.tests.services.match.impl;

import iamcore.datamodel.Identity;
import proj.iamcore.tests.services.match.Matcher;

public class EqualsIdentityMatcher implements Matcher<Identity> {

	@Override
	public boolean match(Identity criteria, Identity toBeChecked) {
		return toBeChecked.getDisplayName().equals(criteria.getDisplayName())
				|| toBeChecked.getEmailAddress().equals(
						criteria.getEmailAddress());
	}

}
