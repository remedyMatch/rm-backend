package io.remedymatch.registrierung.keycloak;

class KeycloakAttribute {
	private KeycloakAttribute() {
	}

	static final String KEYCLOAK_GRUPPE_FREIGEGEBEN = "freigegeben";
	static final String KEYCLOAK_GRUPPE_USER = "user";
	
	static final String KEYCLOAK_USER_ATTRIBUT_COMPANY = "company";
	static final String KEYCLOAK_USER_ATTRIBUT_COMPANY_TYPE = "company-type";

	static final String KEYCLOAK_USER_ATTRIBUT_PHONE = "phone";

	static final String KEYCLOAK_USER_ATTRIBUT_STREET = "street";
	static final String KEYCLOAK_USER_ATTRIBUT_HOUSENUMBER = "housenumber";
	static final String KEYCLOAK_USER_ATTRIBUT_ZIPCODE = "zipcode";
	static final String KEYCLOAK_USER_ATTRIBUT_CITY = "city";
	static final String KEYCLOAK_USER_ATTRIBUT_COUNTRY = "country";

	static final String KEYCLOAK_USER_ATTRIBUT_STATUS = "status";

	static final String KEYCLOAK_USER_STATUS_FREIGEGEBEN = "FREIGEGEBEN";
	static final String KEYCLOAK_USER_STATUS_AKTIVIERT = "AKTIVIERT";
}
