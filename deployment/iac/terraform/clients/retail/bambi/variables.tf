variable "realm_admin_password" {
  type      = string
  sensitive = true
  default   = "admin!1Aadmin!1A"
}

variable "admin_password" {
  type      = string
  sensitive = true
  default   = "admin!1Aadmin!1A"
}

variable "user_password" {
  type      = string
  sensitive = true
  default   = "usera!1Aadmin!1A"
}