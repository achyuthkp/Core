package iamcore.services.match.impl;

import iamcore.datamodel.Identity;
import iamcore.services.match.Matcher;

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
