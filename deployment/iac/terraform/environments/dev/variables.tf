variable "keycloak_admin_username" {
  type      = string
  sensitive = true
  default   = "admin"
}

variable "keycloak_admin_password" {
  type      = string
  sensitive = true
  default   = "admin"
}

variable "keycloak_url" {
  type      = string
  default   = "http://localhost:8081" // Local
}