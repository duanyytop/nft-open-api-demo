import com.google.gson.Gson;
import org.nervos.ckb.type.transaction.Transaction;

public class Main {

    private static String UnsignedRawTx = "{\"version\":\"0x0\",\"cell_deps\":[{\"out_point\":{\"tx_hash\":\"0xaf35eb9ba88d0b159ba450cfcc9089796cc401bc4089a43de018c12f990909a5\",\"index\":\"0x0\"},\"dep_type\":\"code\"},{\"out_point\":{\"tx_hash\":\"0x1196caaf9e45f1959ea3583f92914ee8306d42e27152f7068f9eeb52ac23eeae\",\"index\":\"0x0\"},\"dep_type\":\"code\"},{\"out_point\":{\"tx_hash\":\"0x21089f7c91115214e031b28b599e85e5df7fefbadecb9fae01118f40be8efab2\",\"index\":\"0x0\"},\"dep_type\":\"code\"}],\"header_deps\":[],\"inputs\":[{\"previous_output\":{\"tx_hash\":\"0x29281072412004a2f4974d3fc5d8cc0aa6788403845d8c263719c2abdce9a6be\",\"index\":\"0x1\"},\"since\":\"0x0\"}],\"outputs\":[{\"capacity\":\"0x31eb3c027\",\"lock\":{\"code_hash\":\"0x614d40a86e1b29a8f4d8d93b9f3b390bf740803fa19a69f1c95716e029ea09b3\",\"args\":\"0xcdd657eb9111cddf4442e4338646ce9ff3533fe6\",\"hash_type\":\"type\"},\"type\":{\"code_hash\":\"0x2b24f0d644ccbdd77bbf86b27c8cca02efa0ad051e447c212636d9ee7acaaec9\",\"args\":\"0x0ccbff2f1e8e5167a46ffe6f795d14efd1cbfcbd0000000000000002\",\"hash_type\":\"type\"}}],\"outputs_data\":[\"0x000000000000000000c000\"],\"witnesses\":[\"0x0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\"],\"hash\":null}";

    public static void main(String[] args) {
        Transaction tx = new Gson().fromJson(UnsignedRawTx, Transaction.class);
        System.out.println(tx.outputs.get(0).capacity);
    }
}
