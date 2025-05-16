terraform {
  required_providers {
    keycloak = {
      source = "keycloak/keycloak"
      version = ">= 5.0.0"
    }
  }
}

resource "keycloak_realm" "this" {
  realm        = var.realm_id
  enabled      = true
  display_name = var.realm_name

  registration_allowed         = true
  reset_password_allowed       = true
  remember_me                  = true
  # verify_email                 = true

  ssl_required    = "external"
  password_policy = "length(12) and digits(1) and upperCase(1) and lowerCase(1) and specialChars(1) and notUsername"

  internationalization {
    supported_locales = [
      "fr",
      "en",
      "ca",
      "es"
    ]
    default_locale = "es"
  }

  default_signature_algorithm = "RS256"
  revoke_refresh_token = true

  sso_session_idle_timeout        = "720h"
  sso_session_max_lifespan        = "720h"
  offline_session_idle_timeout    = "720h"
  offline_session_max_lifespan    = "8760h"
  offline_session_max_lifespan_enabled = true
  oauth2_device_code_lifespan = "10m"

  security_defenses {
    brute_force_detection {
      max_login_failures         = 5
      wait_increment_seconds     = 60
      max_failure_wait_seconds   = 900
    }
  }

}