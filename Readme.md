The code in this repository demonstrates fetching key-value secret from Hashicorp vault.

Code is under `src/` folder

# Vault Sample Setup 

```
vault server -dev

export VAULT_ADDR='http://127.0.0.1:8200'
export VAULT_TOKEN=<TOKEN>

vault secrets enable kv
vault secrets list # Check

vault write kv/platformx/dev dbusername=admin dbpassword=pass123

vault auth enable approle
vault auth list # Check

vault write auth/approle/role/platformx-role \
    secret_id_ttl=10m \
    token_num_uses=10 \
    token_ttl=20m \
    token_max_ttl=30m \
    secret_id_num_uses=40

vault policy write platformx-policy - << EOF
    path "kv/platformx/*" {
        capabilities = ["create", "update", "read"]
    }
EOF

vault write auth/approle/role/platformx-role policies=platformx-policy
vault read  auth/approle/role/platformx-role/role-id
vault write -f auth/approle/role/platformx-role/secret-id
```

# Build and Run the application
```
cd vault-client-example
mvn package -DskipTests=true
mvn exec:java
```
