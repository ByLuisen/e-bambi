terraform {
  required_providers {
    keycloak = {
      source = "keycloak/keycloak"
      version = ">= 5.0.0"
    }
  }
}

module "bambi_realm" {
  source = "../../../modules/realm"
  realm_id   = "bambi"
  realm_name = "Bambi"
}

module "e-bambi" {
  source   = "./e-bambi"
  realm_id = module.bambi_realm.realm_id
}

# -------------------------------
# bambi Realm administrator user
# -------------------------------
resource "keycloak_user" "realm_admin" {
  realm_id = module.bambi_realm.realm_id
  username = "realm-admin"
  enabled  = true

  initial_password {
    value     = var.realm_admin_password
    temporary = false
  }

  email      = "realm-admin@your-domain.com"
  first_name = "Realm"
  last_name  = "Administrator"
}

# -----------------------------------------------
# Retrieve Keycloak real-admin and default roles
# -----------------------------------------------
data "keycloak_openid_client" "realm_management" {
  realm_id = module.bambi_realm.realm_id
  client_id = "realm-management"
}

data "keycloak_role" "realm_admin" {
  realm_id    = module.bambi_realm.realm_id
  client_id   = data.keycloak_openid_client.realm_management.id
  name        = "realm-admin"
}

data "keycloak_role" "default_roles_bambi" {
  realm_id    = module.bambi_realm.realm_id
  name        = "default-roles-bambi"
}

# -------------------------------
# Set roles to Realm Admin user
# -------------------------------
resource "keycloak_user_roles" "realm_admin_roles" {
  realm_id = module.bambi_realm.realm_id
  user_id  = keycloak_user.realm_admin.id

  role_ids = [
    data.keycloak_role.default_roles_bambi.id,
    data.keycloak_role.realm_admin.id,
  ]
}

# -------------------------------
# Create bambi ADMIN role
# -------------------------------
resource "keycloak_role" "admin" {
  name      = "ADMIN"
  realm_id  = module.bambi_realm.realm_id
}

# -------------------------------
# Create bambi admin user
# -------------------------------
resource "keycloak_user" "e_bambi_admin" {
  realm_id = module.bambi_realm.realm_id
  username = "e-bambi-admin"
  enabled  = true

  initial_password {
    value     = var.admin_password
    temporary = false
  }

  email      = "e-bambi-admin@your-domain.com"
  first_name = "Bambi"
  last_name  = "Administrator"
}

resource "keycloak_user_roles" "admin_roles" {
  realm_id = module.bambi_realm.realm_id
  user_id  = keycloak_user.e_bambi_admin.id

  role_ids = [
    keycloak_role.admin.id,
  ]
}

# -------------------------------
# Create bambi normal user
# -------------------------------
resource "keycloak_user" "e_bambi_user" {
  realm_id = module.bambi_realm.realm_id
  username = "e-bambi-user"
  enabled  = true

  initial_password {
    value     = var.user_password
    temporary = false
  }

  email      = "e-bambi-user@your-domain.com"
  first_name = "User"
  last_name  = "Bambi"
}