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
  default   = "http://keycloak.local:8081" // Minikube
}