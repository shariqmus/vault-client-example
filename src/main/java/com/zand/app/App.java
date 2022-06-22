package com.zand.app;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;

public class App {   

    public static void main(String args[]) throws VaultException {        
        
        String VAULT_SERVER_PATH = "http://127.0.0.1:8200";

        //
        // This section for login via static Token
        //
        // String TOKEN = "s.32sN7GkLm2AZ84Qf5vXEYxwu";
        // final VaultConfig vaultConfig = new VaultConfig().address(VAULT_SERVER_PATH).token(TOKEN).build();
        // final Vault vault = new Vault(vaultConfig, 1);

        // // Read Data
        // final String userName = vault.logical().read("kv/platformx").getData().get("dbusername");
        // final String password = vault.logical().read("kv/platformx").getData().get("dbpassword");

        // System.out.println("username: " + userName);
        // System.out.println("password: " + password);

        //
        // This section for login via AppRole
        //       

        String appRoleId = "8b334292-3d0f-7f70-cb65-9730578e5b4f";
        String secretId  = "fc46b702-df2a-560c-7c8c-2a19df3ef44e";

        final VaultConfig vaultConfig = new VaultConfig().address(VAULT_SERVER_PATH).build();
        final Vault vault = new Vault(vaultConfig, 1); // Version 2

        final String path = "approle";
        final String token = vault.auth().loginByAppRole(path, appRoleId, secretId).getAuthClientToken();

        vaultConfig.token(token).build();

        // Read Data
        final String userName = vault.logical().read("kv/platformx/dev").getData().get("dbusername");
        final String password = vault.logical().read("kv/platformx/dev").getData().get("dbpassword");

        System.out.println("username: " + userName);
        System.out.println("password: " + password);

    }
}
