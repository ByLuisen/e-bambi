# terraform init
# terraform init -upgrade
# terraform plan -out=keycloak.plan
# terraform apply keycloak.plan
# terraform destroy

module "bambi" {
  source = "../../clients/retail/bambi"
}