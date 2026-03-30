package swalayan;
//Muhammad Faiz Najmuudin
//255150707111013
import java.util.Scanner;

public class Mainswalayan {

    static Scanner sc = new Scanner(System.in);

    
    static TransaksiPelanggan[] daftarPelanggan = {
        // Constructor 1 — dengan saldo awal (BAB 2)
        new TransaksiPelanggan("3812345678", "Andi Silver",    500_000, "1234"),
        new TransaksiPelanggan("5687654321", "Budi Gold",    1_500_000, "5678"),
        new TransaksiPelanggan("7411223344", "Cici Platinum", 2_000_000, "9999"),
        // Constructor 2 — tanpa saldo awal (BAB 3 Overloading)
        new TransaksiPelanggan("3899998888", "Deni Silver Baru", "4321")
    };

    public static void main(String[] args) {
    
        System.out.println("║  SELAMAT DATANG DI SWALAYAN TINY ║");
        

        while (true) {
            System.out.println("\n===== MENU UTAMA =====");
            System.out.println("1. Login & Transaksi");
            System.out.println("2. Lihat Info Akun");
            System.out.println("3. Lihat Semua Pelanggan (Ringkas)");
            System.out.println("4. Top Up Admin (Tanpa PIN)");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            int menu = sc.nextInt();

            if (menu == 0) {
                System.out.println("Terima kasih telah berbelanja di Swalayan Tiny!");
                break;
            }

           
            if (menu == 3) {
                System.out.println("\n=== DAFTAR SEMUA PELANGGAN ===");
                for (TransaksiPelanggan p : daftarPelanggan) {
                    p.cetakInfo(true);
                }
                continue;
            }

           
            if (menu == 4) {
                System.out.print("\nNomor Pelanggan : ");
                String nomorAdmin = sc.next();
                if (nomorAdmin.length() != 10 || !nomorAdmin.matches("\\d{10}")) {
                    System.out.println("Format nomor tidak valid!");
                    continue;
                }
                TransaksiPelanggan target = cariPelanggan(nomorAdmin);
                if (target == null) { System.out.println("Pelanggan tidak ditemukan!"); continue; }
                System.out.print("Jumlah Top Up Admin: Rp");
                double jmlAdmin = sc.nextDouble();
                target.topUp(jmlAdmin);
                continue;
            }

    
            System.out.print("\nMasukkan Nomor Pelanggan (10 digit): ");
            String nomor = sc.next();

            if (nomor.length() != 10 || !nomor.matches("\\d{10}")) {
                System.out.println("Format nomor tidak valid! Harus tepat 10 digit angka.");
                continue;
            }

            TransaksiPelanggan p = cariPelanggan(nomor);
            if (p == null) {
                System.out.println("Nomor pelanggan tidak ditemukan!");
                continue;
            }

          
            if (p.isTerblokir()) {
                System.out.println("Akun ini telah DIBLOKIR! Hubungi customer service.");
                continue;
            }

            
            boolean loginBerhasil = false;
            for (int percobaan = 1; percobaan <= 3; percobaan++) {
                System.out.print("Masukkan PIN (percobaan ke-" + percobaan + "): ");
                String pin = sc.next();
                if (p.autentikasi(nomor, pin)) {
                    loginBerhasil = true;
                    break;
                }
                if (p.isTerblokir()) break;
            }

            if (!loginBerhasil) continue;

            
            if (menu == 2) {
                p.cetakInfo();
                continue;
            }

          
            boolean lanjut = true;
            while (lanjut) {
                System.out.println("\n--- MENU TRANSAKSI ---");
                System.out.println("1. Pembelian");
                System.out.println("2. Top Up Saldo");
                System.out.println("3. Lihat Info Akun");
                System.out.println("0. Kembali");
                System.out.print("Pilih: ");
                int transaksi = sc.nextInt();

                switch (transaksi) {
                    case 1:
                        System.out.printf("Saldo saat ini: Rp%.2f%n", p.getSaldo());
                        System.out.print("Masukkan jumlah pembelian: Rp");
                        double beli = sc.nextDouble();
                        p.beli(beli); // sudah login, tidak perlu autentikasi ulang
                        break;
                    case 2:
                        System.out.print("Masukkan jumlah top up: Rp");
                        double topup = sc.nextDouble();
                        p.topUp(topup); // sudah login, tidak perlu autentikasi ulang
                        break;
                    case 3:
                        p.cetakInfo();
                        break;
                    case 0:
                        lanjut = false;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            }
        }
        sc.close();
    }

    private static TransaksiPelanggan cariPelanggan(String nomor) {
        for (TransaksiPelanggan p : daftarPelanggan) {
            if (p.getNomorPelanggan().equals(nomor)) return p;
        }
        return null;
    }
}
