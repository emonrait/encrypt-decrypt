package com.cbl.encryptdycript.service;

import com.cbl.encryptdycript.response.ResponseDto;
import com.cbl.encryptdycript.util.Decrypt;
import com.cbl.encryptdycript.util.Encrypt;
import com.cbl.encryptdycript.util.JWTDecryptor;
import com.cbl.encryptdycript.util.JWTEncryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EncryptionDecryptionService {

    public ResponseDto dataEncryption(String data) throws Exception {
        ResponseDto outModel = new ResponseDto();

        if (data == null || data.isEmpty() || data.isBlank()) {
            throw new Exception("Data is Required.");
        }

        final Encrypt encrypt = new Encrypt();
        String path = "/Users/raihanmahamud/Desktop/key/";
        encrypt.setSignPublicKeyPath(path + "CBL_public.pem");
        encrypt.setSignPrivateKeyPath(path + "CBL_private.pem");
        encrypt.setEncryptPublicKeyPath(path + "BBL_public.pem");
        final JWTEncryptor jwtEncryptor = new JWTEncryptor(encrypt);


        // String plainMessage = "{\"TTReferenceNo\":\"1234567890\",\"BeneficiaryName\":\"John Doe\",\"BeneficiaryPhoneNo\":\"01737902799\"}";
        String value = jwtEncryptor.signandEncryptMessage(data);
        outModel.setResponseData(value);

        return outModel;

    }

    public ResponseDto dataDecryption(String data) throws Exception {
        ResponseDto outModel = new ResponseDto();

        if (data == null || data.isEmpty() || data.isBlank()) {
            throw new Exception("Data is Required.");
        }

        final Decrypt decrypt = new Decrypt();
        String path = "/Users/raihanmahamud/Desktop/key/";
        decrypt.setDecryptPublicKeyPath(path + "BBL_public.pem");
        decrypt.setDecryptPrivateKeyPath(path + "CBL_private.pem");
        decrypt.setVerifyPublicKeyPath(path + "BBL_public.pem");

        final JWTDecryptor jwtDecryptor = new JWTDecryptor(decrypt);

        //String encryptedMessage="eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2Q0JDLUhTNTEyIiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.BCKrEBBJ2uLJhgj9-epfs05xK_O9oq6tX9pVfKr6AHjj_J-3gu_C6hqvQaZynMlfjNBxk5B97bQfmgJTIGNOZh8F0I1BkG-EqfvsT-1pIz8SdRTkRgW0-cQuLUYGLsnU_a9TOl9Nr5VS-E-A3N9cnw7zN6XVqoHjYo2GhWfxipSuZ7yidWH0LdV_Pl6Q6jbgbqCmGvY9J_G2FuJW2JMNVzV9BlwgfXIGv3h5iivCIzVyg8NwuytIN6NTGmM3Dc_KQyfY6y7FqFdA_pE7brMLTwGWq_vyRjnJCzjnQYvvWagnX2HO2UZ9lFAqQ1iWkXCYIUg4kuKQTLivKb536SoFKg.6QN7gMrSOf2RDcCiYEX9lw.lXdru38_01oKGLhNE6285MGnwL9X-dxdPzT0GDb1jZaOTy2PounsM35bGhHw8qxgVfbAd1EK8HiJZ5TRxqzUvZdS54aK-cYd-m_gj3-4CumX97GWSkgkHzWtaKo2vKiATbS9h9y5Qd50U8VfCtmo_2I6yw57pxf73OEtN7AV21NRrIIiuqIpjngzkWlB22vIYZT1RJ6o9PqsyURfQKjZaz6LRZcnmfIFBsZDTGlVsdQ1AcVba49rqIwGnaIIeY6gM9SdUAKnXycWfrHg-MLOhHqqAMX4BO1JmTiMVU4fptNB_UDjrn2Q1DWZqh-XRE2zs-1073RXWXp7G0PqhvUtIdUvRjeN11QX_TB6yHRNPCCtANWEmfk4z_G_uiZEKKzi1ERydUv9PFoHNunQPU6Pdd66Xz5_RdG7SLuOe7snsch83M63j4ly_aWh5AfcG6AC8Y7V1YcPG4tZabo-hTkZOp6GLeVLyR7HIQsQLUAxKPVFfQp1aE8lDPPl1m_cayLTX1sn4d-OBoYFkUgTPFU9YnCf5ade8n9-741w7TsM2FAnvDjj9RtZm8AS0wFJ7OFUd1eFsUIdaiYB8b40DCztZiNwccpaUE7O9yTaNcelEImMjzIG9uupKYXF6V3yKjoBqaDKhPx0FaHpmobulMC_-P5QnpV5UEtfiAne4z1HFIuYGtHzPjd0xUWkJZ50kmEQ_UqOzSnBujEQJKTp3uVg71etrDj2mA4iVN6LjzAjnZxGfBYnrGAt06potHe0fHm7e_ZswfCrS0zaASQWjvrZtSCPu39vYLl-T3qfuhqc5WI.vWGb9MI8Jd9fDtOWEQzSurlm-lhhFJSo06X998kCYl0";
        //  String encryptedMessage = "eyJhbGciOiJSU0EtT0FFUCIsImVuYyI6IkEyNTZDQkMtSFM1MTIifQ.mVjI0kKy8dzhvI5a9fNgphRc7KonBJucz8PHOr_hm3Q85aWkTI8ip70dntG7czN4P34NUkn7yoN4UEcEtdBbfuLptEyrnplGAAReNBF45K83VpUBHn-me-Tq2TRT4PMlwBlrWi4B3KUfRX-Li2aGhQz9DZ9A_o7js8-QA6VmqJQ-yPP9Wl-uxLDuVqxgwxTW1G3pVdXEqnbcr6XlRupE3f_cChw974D1UQgih-Kh9LqiQVmg5bG-Wn8u2fnvGvquqOF0SaqcJkTSnwwQur56iYDKYq6nBqtNK7VgWOn4MHNE2xapjhjCnPOBbGx3Q9VdQUWXttXzcUpZnUXV9mBlbw.QBIbBVbmcZsQdx_K4GUdcA.DA1FgK58FSWYIYdINV88QVYIghWzG2NxGlCPVQhslFjQoibf5VCIWPpc3ALPa9O-JyYE35H4vAUJV2Qx24zCJ5k_ByOLCrKc6DHfZWq5w6i6WJgYecaQsgu_EWbhw67HPWpj_6L_6yjgMSGY3IFC-GMRn_Dl09G06M7YY92WwvLNv116J-sT3rROf5kKIsmq1zCl7eZk0IyE9mKN-AIIp0dhZOKsXCIWKC8qOxPUTghDpxWysQQ4H5pfpWlnR7kw3GI02Y-6kcDbk7DxNKbBsWkK5dpcJ6Y76wz2qZRJVahxjMS4WXREOr3ziIyGBBr1SDMtdjMS4qeD0Mn_oYIpUE3uyVDGIT7Uz4v4dcr3JXu09djNQ6GoEq6tEoAX1mIaIeN2jX3vOW4BG2U4LP79nY14BmnBXatY671vscKIwpjGftK1ToiZRzxRylbWsKvg7AaBaWvC_LsMHFQcVF1cknqmji9ew5PukKm7yq9Hp-RccArXNvi1AhlINm8BB__t7GX4Glc8PvQd2-q7SdSCYeog6mqtpPv4CUT8XPYIgHF70Ps3VHPKOvf_sihcKNB_la_GLxXKgLW-UGS0V1NDuQ.XNG5BC5_OiiKXfzEsuBL_EyqOD1cQlsLsdLHvmwY2rw";
        String value = jwtDecryptor.decryptandVerifyMessage(data);
        outModel.setResponseData(value);

        return outModel;
    }


}
