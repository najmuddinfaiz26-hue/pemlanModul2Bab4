package swalayan;
//Muhammad Faiz Najmuddin
//255150707111013
public class TransaksiPelanggan extends Pelanggan { 

    public TransaksiPelanggan(String nomorPelanggan, String nama, double saldo, String pin) {
        super(nomorPelanggan, nama, saldo, pin); 
    }

    
    public TransaksiPelanggan(String nomorPelanggan, String nama, String pin) {
        super(nomorPelanggan, nama, pin); 
    }

   
    public boolean autentikasi(String inputNomor, String inputPin) {
        
        if (terblokir) {
            System.out.println("Akun Anda telah diblokir! Hubungi customer service.");
            return false;
        }
        
        if (inputNomor.equals(nomorPelanggan) && inputPin.equals(pin)) {
            jumlahSalah = 0;
            return true;
        }
       
        jumlahSalah++;
        int sisa = MAKS_PERCOBAAN - jumlahSalah;
        if (jumlahSalah >= MAKS_PERCOBAAN) {
            terblokir = true;
            System.out.println("Autentikasi gagal! Akun DIBLOKIR karena 3x kesalahan.");
        } else {
            System.out.println("Autentikasi gagal! Sisa percobaan: " + sisa + "x");
        }
        return false;
    }

    
    public boolean beli(double jumlah) {
        String kode           = nomorPelanggan.substring(0, 2);
        double cashbackPotong = 0;
        double cashbackSaldo  = 0;

        
        switch (kode) {
            case "38": // Silver
                if (jumlah > 1_000_000) cashbackPotong = jumlah * 0.05;
                break;
            case "56": // Gold
                if (jumlah > 1_000_000) cashbackPotong = jumlah * 0.07;
                else                    cashbackSaldo   = jumlah * 0.02;
                break;
            case "74": // Platinum
                if (jumlah > 1_000_000) cashbackPotong = jumlah * 0.10;
                else                    cashbackSaldo   = jumlah * 0.05;
                break;
        }

        double totalBayar   = jumlah - cashbackPotong;
        double saldoSetelah = saldo - totalBayar + cashbackSaldo;

        
        if (saldoSetelah < SALDO_MINIMAL) {
            System.out.println("Transaksi GAGAL! Saldo tidak mencukupi (min. Rp10.000 harus tersisa).");
            return false;
        }

        saldo = saldoSetelah;
        System.out.println("\n--- Struk Pembelian ---");
        System.out.println("Nama            : " + nama);
        System.out.println("Jenis Rekening  : " + getJenisRekening());
        System.out.printf ("Harga Belanja   : Rp%.2f%n", jumlah);
        if (cashbackPotong > 0)
            System.out.printf("Cashback Potong : Rp%.2f%n", cashbackPotong);
        if (cashbackSaldo > 0)
            System.out.printf("Cashback Saldo  : Rp%.2f (masuk ke saldo)%n", cashbackSaldo);
        System.out.printf ("Total Dibayar   : Rp%.2f%n", totalBayar);
        System.out.printf ("Saldo Sekarang  : Rp%.2f%n", saldo);
        System.out.println("Status          : BERHASIL");
        System.out.println("----------------------");
        return true;
    }

   
    public boolean beli(String inputNomor, String inputPin, double jumlah) {
        if (!autentikasi(inputNomor, inputPin)) return false;
        return beli(jumlah); 
    }

    
    public boolean topUp(double jumlah) {
        if (jumlah <= 0) {
            System.out.println("Jumlah top up tidak valid!");
            return false;
        }
        saldo += jumlah;
        System.out.printf("[ADMIN] Top Up Rp%.2f BERHASIL! Saldo sekarang: Rp%.2f%n", jumlah, saldo);
        return true;
    }

  
    public boolean topUp(String inputNomor, String inputPin, double jumlah) {
        if (!autentikasi(inputNomor, inputPin)) return false;
        return topUp(jumlah); 
    }
}
