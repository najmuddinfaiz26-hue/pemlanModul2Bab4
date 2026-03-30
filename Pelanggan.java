package swalayan;
//Muhammad Faiz Najmuddin
//255150707111013
public class Pelanggan {

   
    protected String  nomorPelanggan; 
    protected String  nama;           
    protected double  saldo;          
    protected String  pin;            
    protected boolean terblokir;      
    protected int     jumlahSalah;    

    
    protected static final double SALDO_MINIMAL  = 10_000; 
    protected static final int    MAKS_PERCOBAAN = 3;      

    
    
    public Pelanggan(String nomorPelanggan, String nama, double saldo, String pin) {
        if (nomorPelanggan.length() != 10)
            throw new IllegalArgumentException("Nomor pelanggan harus 10 digit!");
        this.nomorPelanggan = nomorPelanggan;
        this.nama           = nama;
        this.saldo          = saldo;
        this.pin            = pin;
        this.terblokir      = false;
        this.jumlahSalah    = 0;
    }

    
    
    public Pelanggan(String nomorPelanggan, String nama, String pin) {
        this(nomorPelanggan, nama, 0, pin);
    }

    
    public String  getNomorPelanggan() { return nomorPelanggan; }
    public String  getNama()           { return nama; }
    public double  getSaldo()          { return saldo; }
    public boolean isTerblokir()       { return terblokir; }

    
    public String getJenisRekening() {
        String kode = nomorPelanggan.substring(0, 2);
        switch (kode) {
            case "38": return "Silver";
            case "56": return "Gold";
            case "74": return "Platinum";
            default:   return "Tidak Dikenal";
        }
    }

    
    
    public void cetakInfo() {
        System.out.println("\n=== INFO AKUN PELANGGAN ===");
        System.out.println("Nama            : " + nama);
        System.out.println("No. Pelanggan   : " + nomorPelanggan);
        System.out.println("Jenis Rekening  : " + getJenisRekening());
        System.out.printf ("Saldo           : Rp%.2f%n", saldo);
        System.out.println("Status Akun     : " + (terblokir ? "DIBLOKIR" : "Aktif"));
        System.out.println("===========================");
    }

    
    public void cetakInfo(boolean ringkas) {
        if (ringkas) {
            System.out.printf("[%s] %s | Saldo: Rp%.2f | Status: %s%n",
                getJenisRekening(), nama, saldo, terblokir ? "DIBLOKIR" : "Aktif");
        } else {
            cetakInfo();
        }
    }
}z
