import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.nervos.ckb.address.Network;
import org.nervos.ckb.crypto.Hash;
import org.nervos.ckb.crypto.secp256k1.ECKeyPair;
import org.nervos.ckb.service.Api;
import org.nervos.ckb.type.Script;
import org.nervos.ckb.type.Witness;
import org.nervos.ckb.type.transaction.Transaction;
import org.nervos.ckb.utils.Numeric;
import org.nervos.ckb.utils.address.AddressGenerator;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Example {

    public static void main(String[] args) throws IOException {
        // Generate CKB address from private key
        String EXAMPLE_PRIVATE_KEY = "d52a6cb37ce90aed79a96ea9976668fbdbe16d6eac3611d5b15de388168da2c3";
        BigInteger publicKey = ECKeyPair.publicKeyFromPrivate(Numeric.toBigInt(EXAMPLE_PRIVATE_KEY));
        String lockArgs = Hash.blake160(Numeric.toHexStringWithPrefix(publicKey));
        String SECP256K1_BLAKE160_CODE_HASH = "0x9bd7e06f3ecf4be0f2fcd2188b23f1b9fcc88e5d4b65a8637b17723bbda3cce8";
        Script typeFullScript = new Script(SECP256K1_BLAKE160_CODE_HASH, lockArgs, Script.TYPE);
        String address = AddressGenerator.generateFullAddress(Network.TESTNET, typeFullScript);
        System.out.println("Address: " + address);

        // Generate transaction object from unsigned raw tx json
        String EXAMPLE_UNSIGNED_RAW_TX = "{'unsigned_tx': {'version': '0x0', 'cell_deps': [{'out_point': {'tx_hash': '0xbd262c87a84c08ea3bc141700cf55c1a285009de0e22c247a8d9597b4fc491e6', 'index': '0x2'}, 'dep_type': 'code'}, {'out_point': {'tx_hash': '0xd346695aa3293a84e9f985448668e9692892c959e7e83d6d8042e59c08b8cf5c', 'index': '0x0'}, 'dep_type': 'code'}, {'out_point': {'tx_hash': '0x03dd2a5594ed2d79196b396c83534e050ba0ad07fa5c1cd61a7094f9fb60a592', 'index': '0x0'}, 'dep_type': 'code'}, {'out_point': {'tx_hash': '0xf8de3bb47d055cdf460d93a2a6e1b05f7432f9777c8c474abf4eec1d4aee5d37', 'index': '0x0'}, 'dep_type': 'dep_group'}], 'header_deps': [], 'inputs': [{'previous_output': {'tx_hash': '0x8bca8b2fcc1d811788b7957242c1e598c8ce66900f1c2307c3ccb6bf592f9c46', 'index': '0x1'}, 'since': '0x0', 'capacity': '0x31eb3c600', 'lock': {'code_hash': '0x9bd7e06f3ecf4be0f2fcd2188b23f1b9fcc88e5d4b65a8637b17723bbda3cce8', 'args': '0xa112e3a303ad9430a706222ef063c9379107140a', 'hash_type': 'type'}, 'type': {'code_hash': '0xb1837b5ad01a88558731953062d1f5cb547adf89ece01e8934a9f0aeed2d959f', 'args': '0xf90f9c38b0ea0815156bbc340c910d0a21ee57cf0000002b00000002', 'hash_type': 'type'}}], 'outputs': [{'capacity': '0x31eb3c002', 'lock': {'code_hash': '0x58c5f491aba6d61678b7cf7edf4910b1f5e00ec0cde2f42e0abb4fd9aff25a63', 'args': '0x009871fde1b88fe71d00c2e5c2f3d49ec009e629', 'hash_type': 'type'}, 'type': {'code_hash': '0xb1837b5ad01a88558731953062d1f5cb547adf89ece01e8934a9f0aeed2d959f', 'args': '0xf90f9c38b0ea0815156bbc340c910d0a21ee57cf0000002b00000002', 'hash_type': 'type'}}], 'outputs_data': ['0x000000000000000000c000'], 'witnesses': ['0x']}}";
        OpenApiUnsignedTx openApiUnsignedTx = new Gson().fromJson(EXAMPLE_UNSIGNED_RAW_TX, OpenApiUnsignedTx.class);
        Transaction tx = openApiUnsignedTx.unsignedTx;
        List witnesses = new ArrayList<>();
        for (int i = 0; i < tx.inputs.size(); i++) {
            witnesses.add(i == 0 ? new Witness(Witness.SIGNATURE_PLACEHOLDER) : "0x");
        }
        tx.witnesses = witnesses;

        // Sign tx with private key
        Transaction signedTx = tx.sign(Numeric.toBigInt(EXAMPLE_PRIVATE_KEY));

        // Send tx to CKB blockchain and print the tx hash
        String NODE_URL = "http://localhost:8114";
        Api api = new Api(NODE_URL, false);
        String hash = api.sendTransaction(signedTx);
        System.out.println(hash);
    }

    static class OpenApiUnsignedTx {
        @SerializedName("unsigned_tx")
        public Transaction unsignedTx;
    }
}
