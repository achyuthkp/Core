package fr.tbr.iamcore.tests.services.match.impl;

import fr.tbr.iamcore.tests.services.match.Matcher;
import iamcore.datamodel.Identity;

public class StartsWithIdentityMatchStrategy implements Matcher<Identity> {

	@Override
	public boolean match(Identity criteria, Identity toBeChecked) {
		if(criteria == null){
			return true;
		}

		return toBeChecked.getDisplayName().startsWith(criteria.getDisplayName())
				|| toBeChecked.getEmailAddress().startsWith(criteria.getEmailAddress());
	}

}
