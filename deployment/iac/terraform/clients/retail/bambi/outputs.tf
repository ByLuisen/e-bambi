output "realm_admin_credentials" {
  value = {
    username = keycloak_user.realm_admin.username
    password = var.realm_admin_password
  }
  sensitive = true
}