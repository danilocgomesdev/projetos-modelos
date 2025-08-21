import Keycloak from "keycloak-js";
import keycloakDevConfig from "../App/auth/keycloak-dev.json";
import keycloakProdConfig from "../App/auth/keycloak-prod.json";

const env = import.meta.env;
const keycloakConfig = env.MODE === "production" ? keycloakProdConfig : keycloakDevConfig;

export const keycloak = Keycloak(keycloakConfig);
