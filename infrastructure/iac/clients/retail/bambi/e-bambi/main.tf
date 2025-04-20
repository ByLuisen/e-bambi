terraform {
  required_providers {
    keycloak = {
      source = "keycloak/keycloak"
      version = ">= 5.0.0"
    }
  }
}

variable "realm_id" {
  type = string
}

resource "keycloak_openid_client" "e_bambi" {
  realm_id                     = var.realm_id
  client_id                    = "e-bambi"
  name                         = "e-bambi"
  enabled                      = true
  login_theme                  = "keywind"
  access_type                  = "CONFIDENTIAL"
  standard_flow_enabled        = true
  direct_access_grants_enabled = false
  service_accounts_enabled     = false
  implicit_flow_enabled        = false

  valid_redirect_uris   = [
    "http://localhost:8181/*",
    "http://localhost:8182/*"
  ]

  web_origins           = [
    "*"
  ]

  consent_required            = false
  frontchannel_logout_enabled = true
}

# -------------------------------
# JWT Token Configuration
# -------------------------------
resource "keycloak_openid_client_default_scopes" "client_default_scopes" {
  realm_id  = var.realm_id
  client_id = keycloak_openid_client.e_bambi.id

  default_scopes = [
    "profile",
    "email",
    "basic",
    "acr",
    "roles"
  ]
}