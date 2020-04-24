package io.remedymatch.usercontext;

import io.remedymatch.person.domain.model.Person;

public class TestUserContext {
	
	public static void setContextUser(final Person person) {
		UserContext.setContextUser(person);
	}

	public static void clear() {
		UserContext.clear();
	}
}
