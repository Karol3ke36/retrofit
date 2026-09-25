package ph.me.testonlajn;

import com.google.gson.annotations.SerializedName;

public class Pytanie {
    @SerializedName("zapytanie")
    public String trescPytania;
    @SerializedName("odp_a")
    public String odpA;
    @SerializedName("odp_b")
    public String odpB;
    @SerializedName("odp_c")
    public String odpC;
    @SerializedName("poprawna")
    public int odpPoprawna;

    public Pytanie(String trescPytania, String odpA, String odpB, String odpC, int odpPoprawna) {
        this.trescPytania = trescPytania;
        this.odpA = odpA;
        this.odpB = odpB;
        this.odpC = odpC;
        this.odpPoprawna = odpPoprawna;
    }
}

