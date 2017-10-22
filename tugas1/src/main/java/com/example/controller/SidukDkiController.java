package com.example.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.model.AlamatModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.KecamatanService;
import com.example.service.KeluargaService;
import com.example.service.KelurahanService;
import com.example.service.KotaService;
import com.example.service.PendudukService;
import com.example.service.SidukDkiService;

@Controller
public class SidukDkiController {
	@Autowired
	KelurahanService kelurahanDAO;
	@Autowired
	KecamatanService kecamatanDAO;
	@Autowired
	KotaService kotaDAO;
	@Autowired
	PendudukService pendudukDAO;
	@Autowired
	KeluargaService keluargaDAO;
	@Autowired
	SidukDkiService sidukDAO;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/penduduk")
	public String addSubmitKependudukan(Model model, @RequestParam(value = "nik", required = false) String nik) {
		PendudukModel penduduk = pendudukDAO.selectKependudukan(nik);

		if (penduduk != null) {
			KeluargaModel keluarga = keluargaDAO.selectKeluargaByNik(penduduk.getId_keluarga());
			AlamatModel alamat = sidukDAO.selectAlamatByIdKelurahan(keluarga.getId_kelurahan());
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("alamat", alamat);
			return "viewpenduduk";
		} else {
			model.addAttribute("status", "caripenduduk");
			model.addAttribute("id", nik);
			return "not-found";
		}
	}

	@RequestMapping("/keluarga")
	public String addSubmitKeluarga(Model model, @RequestParam(value = "nkk", required = false) String nkk) {
		KeluargaModel keluarga = keluargaDAO.selectKeluargaByNkk(nkk);

		if (keluarga != null) {
			AlamatModel alamat = sidukDAO.selectAlamatByIdKelurahan(keluarga.getId_kelurahan());
			List<PendudukModel> anggota = sidukDAO.selectPendudukByIdKeluarga(keluarga.getId());
			model.addAttribute("nkk", nkk);
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("alamat", alamat);
			model.addAttribute("anggota", anggota);
			return "viewkeluarga";
		} else {
			model.addAttribute("status", "carikeluarga");
			model.addAttribute("id", nkk);
			return "not-found";
		}
	}

	@RequestMapping("/penduduk/tambah")
	public String tambahPenduduk(Model model) {
		PendudukModel penduduk = new PendudukModel();
		model.addAttribute("penduduk", penduduk);
		return "tambahpenduduk";
	}

	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
	public String tambahPendudukSubmit(Model model, @ModelAttribute PendudukModel penduduk) {
		KeluargaModel keluarga = keluargaDAO.selectKeluargaByNik(penduduk.getId_keluarga());

		if (keluarga == null) {
			model.addAttribute("status", "tambahpenduduk");
			model.addAttribute("id", penduduk.getId_keluarga());
			return "not-found";
		} else {
			AlamatModel alamat = sidukDAO.selectAlamatByIdKelurahan(keluarga.getId_kelurahan());
			String kode_kecamatan = alamat.getKode_kecamatan();
			kode_kecamatan = kode_kecamatan.substring(0, kode_kecamatan.length() - 1);
			String nik = "";
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			String tanggal_lahir = df.format(penduduk.getTanggal_lahir());
			String yy_tanggal_lahir = tanggal_lahir.substring(2, 4);
			String mm_tanggal_lahir = tanggal_lahir.substring(5, 7);
			String dd_tanggal_lahir = tanggal_lahir.substring(8, 10);

			if (penduduk.getJenis_kelamin() == 1) {
				int int_dd_tanggal_lahir = Integer.parseInt(dd_tanggal_lahir);
				dd_tanggal_lahir = "" + (int_dd_tanggal_lahir + 40);
			}

			nik = kode_kecamatan + dd_tanggal_lahir + mm_tanggal_lahir + yy_tanggal_lahir;

			int i = 1;
			String kesamaan = "000" + i;
			PendudukModel cek_kesamaan_nik = pendudukDAO.selectKependudukan(nik + kesamaan);
			while (cek_kesamaan_nik != null) {
				i++;
				if (i <= 9) {
					kesamaan = "000" + i;
				} else if (i > 9 && i <= 99) {
					kesamaan = "00" + i;
				} else if (i > 99 && i <= 999) {
					kesamaan = "0" + i;
				} else {
					kesamaan = "" + i;
				}
				cek_kesamaan_nik = pendudukDAO.selectKependudukan(nik + kesamaan);
			}

			List<PendudukModel> jumlah_penduduk = pendudukDAO.selectAllPenduduk();
			penduduk.setId(jumlah_penduduk.size() + 1);

			nik = nik + kesamaan;
			penduduk.setNik(nik);
			pendudukDAO.tambahPenduduk(penduduk);

			model.addAttribute("status", "penduduk");
			model.addAttribute("penduduk", nik + kesamaan);
			return "success";
		}
	}

	@RequestMapping("/keluarga/tambah")
	public String tambahKeluarga(Model model) {
		KeluargaModel keluarga = new KeluargaModel();
		model.addAttribute("keluarga", keluarga);
		List<KotaModel> kota = kotaDAO.selectAllKota();
		model.addAttribute("kota", kota);
		List<KecamatanModel> kecamatan = kecamatanDAO.selectAllKecamatan();
		model.addAttribute("kecamatan", kecamatan);
		List<KelurahanModel> kelurahan = kelurahanDAO.selectAllKelurahan();
		model.addAttribute("kelurahan", kelurahan);
		return "tambahkeluarga";
	}

	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
	public String tambahKeluargaSubmit(Model model, @ModelAttribute KeluargaModel keluarga) {
		String nkk = "";
		String kode = keluarga.getKode_kecamatan();
		String kode_provinsi = kode.substring(0, 2);
		String kode_kota = kode.substring(2, 4);
		String kode_kecamatan = kode.substring(4, 6);
		nkk = kode_provinsi + kode_kota + kode_kecamatan;

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String format_date = format.format(date);
		String dd_tanggal_lahir = format_date.substring(0, 2);
		String mm_tanggal_lahir = format_date.substring(3, 5);
		String yy_tanggal_lahir = format_date.substring(8, 10);
		nkk = nkk + dd_tanggal_lahir + mm_tanggal_lahir + yy_tanggal_lahir;

		int i = 1;
		String kesamaan = "000" + i;
		KeluargaModel cek_kesamaan_nkk = keluargaDAO.selectKeluargaByNkk(nkk + kesamaan);
		while (cek_kesamaan_nkk != null) {
			i++;
			if (i <= 9) {
				kesamaan = "000" + i;
			} else if (i > 9 && i <= 99) {
				kesamaan = "00" + i;
			} else if (i > 99 && i <= 999) {
				kesamaan = "0" + i;
			} else {
				kesamaan = "" + i;
			}
			cek_kesamaan_nkk = keluargaDAO.selectKeluargaByNkk(nkk + kesamaan);
		}

		List<KeluargaModel> jumlah_keluarga = keluargaDAO.selectAllKeluarga();
		keluarga.setId(jumlah_keluarga.size() + 1);

		nkk = nkk + kesamaan;
		keluarga.setNomor_kk(nkk);
		keluarga.setId_kelurahan(keluarga.getKode_kelurahan());
		keluarga.setIs_tidak_berlaku(0);

		keluargaDAO.tambahKeluarga(keluarga);

		model.addAttribute("status", "keluarga");
		model.addAttribute("keluarga", nkk + kesamaan);
		return "success";
	}

	@RequestMapping("/data/ubah")
	public String ubahPenduduk(Model model) {
		return "ubahdata";
	}
	
	@RequestMapping("/penduduk/ubah/{nik}")
	public String ubahPenduduk(Model model, @PathVariable(value = "nik") String nik) {
		PendudukModel penduduk = pendudukDAO.selectKependudukan(nik);

		if (penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			return "ubahpenduduk";
		} else {
			model.addAttribute("status", "caripenduduk");
			model.addAttribute("id", nik);
			return "not-found";
		}
	}

	@RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
	public String ubahPendudukSubmit(Model model, @ModelAttribute PendudukModel penduduk) {
		String nik_current = penduduk.getNik();
		KeluargaModel keluarga = keluargaDAO.selectKeluargaByNik(penduduk.getId_keluarga());

		if (keluarga == null) {
			model.addAttribute("status", "ubahpenduduk");
			model.addAttribute("id", penduduk.getId_keluarga());
			return "not-found";
		} else {
			AlamatModel alamat = sidukDAO.selectAlamatByIdKelurahan(keluarga.getId_kelurahan());
			String kode_kecamatan = alamat.getKode_kecamatan();
			kode_kecamatan = kode_kecamatan.substring(0, kode_kecamatan.length() - 1);
			String nik = "";
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			String tanggal_lahir = df.format(penduduk.getTanggal_lahir());
			String yy_tanggal_lahir = tanggal_lahir.substring(2, 4);
			String mm_tanggal_lahir = tanggal_lahir.substring(5, 7);
			String dd_tanggal_lahir = tanggal_lahir.substring(8, 10);

			if (penduduk.getJenis_kelamin() == 1) {
				int int_dd_tanggal_lahir = Integer.parseInt(dd_tanggal_lahir);
				dd_tanggal_lahir = "" + (int_dd_tanggal_lahir + 40);
			}

			nik = kode_kecamatan + dd_tanggal_lahir + mm_tanggal_lahir + yy_tanggal_lahir;

			int i = 1;
			String kesamaan = "000" + i;
			PendudukModel cek_kesamaan_nik = pendudukDAO.selectKependudukan(nik + kesamaan);
			while (cek_kesamaan_nik != null) {
				i++;
				if (i <= 9) {
					kesamaan = "000" + i;
				} else if (i > 9 && i <= 99) {
					kesamaan = "00" + i;
				} else if (i > 99 && i <= 999) {
					kesamaan = "0" + i;
				} else {
					kesamaan = "" + i;
				}
				cek_kesamaan_nik = pendudukDAO.selectKependudukan(nik + kesamaan);
			}

			penduduk.setId(penduduk.getId());

			nik = nik + kesamaan;
			penduduk.setNik(nik);
			pendudukDAO.ubahPenduduk(penduduk);

			model.addAttribute("status", "ubahpenduduk");
			model.addAttribute("penduduk", nik_current);
			model.addAttribute("new_nik", nik);
			return "success";
		}
	}

	@RequestMapping("/keluarga/ubah/{nkk}")
	public String ubahKeluarga(Model model, @PathVariable(value = "nkk") String nkk) {
		KeluargaModel keluarga = keluargaDAO.selectKeluargaByNkk(nkk);

		if (keluarga != null) {
			KelurahanModel kelurahan = kelurahanDAO.selectKelurahan(Integer.parseInt(keluarga.getId_kelurahan()));
			KecamatanModel kecamatan = kecamatanDAO.selectKecamatan(Integer.parseInt(kelurahan.getId_kecamatan()));
			KotaModel kota = kotaDAO.selectKota(Integer.parseInt(kecamatan.getId_kota()));
			keluarga.setKode_kecamatan(kecamatan.getKode_kecamatan());
			keluarga.setKode_kota("" + kota.getId());
			model.addAttribute("keluarga", keluarga);
			return "ubahkeluarga";
		} else {
			model.addAttribute("status", "caripenduduk");
			model.addAttribute("id", nkk);
			return "not-found";
		}
	}

	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
	public String ubahKeluargaSubmit(Model model, @ModelAttribute KeluargaModel keluarga) {
		String nomor_kk_current = keluarga.getNomor_kk();
		String nkk = "";
		String kode = keluarga.getKode_kecamatan();
		String kode_provinsi = kode.substring(0, 2);
		String kode_kota = kode.substring(2, 4);
		String kode_kecamatan = kode.substring(4, 6);
		nkk = kode_provinsi + kode_kota + kode_kecamatan;

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String format_date = format.format(date);
		String dd_tanggal_lahir = format_date.substring(0, 2);
		String mm_tanggal_lahir = format_date.substring(3, 5);
		String yy_tanggal_lahir = format_date.substring(8, 10);
		nkk = nkk + dd_tanggal_lahir + mm_tanggal_lahir + yy_tanggal_lahir;

		int i = 1;
		String kesamaan = "000" + i;
		KeluargaModel cek_kesamaan_nkk = keluargaDAO.selectKeluargaByNkk(nkk + kesamaan);
		while (cek_kesamaan_nkk != null) {
			i++;
			if (i <= 9) {
				kesamaan = "000" + i;
			} else if (i > 9 && i <= 99) {
				kesamaan = "00" + i;
			} else if (i > 99 && i <= 999) {
				kesamaan = "0" + i;
			} else {
				kesamaan = "" + i;
			}
			cek_kesamaan_nkk = keluargaDAO.selectKeluargaByNkk(nkk + kesamaan);
		}

		nkk = nkk + kesamaan;
		keluarga.setNomor_kk(nkk);
		keluarga.setId_kelurahan(keluarga.getId_kelurahan());
		keluarga.setIs_tidak_berlaku(0);

		keluargaDAO.ubahKeluarga(keluarga);

		model.addAttribute("status", "ubahkeluarga");
		model.addAttribute("keluarga", nomor_kk_current);
		model.addAttribute("new_nkk", nkk);
		return "success";
	}

	@RequestMapping(value = "/penduduk/mati", method = RequestMethod.POST)
	public String submitStatusKematian(Model model, @RequestParam(value = "nik", required = false) String nik,
			@RequestParam(value = "is_wafat", required = false) int is_wafat) {
		PendudukModel penduduk = pendudukDAO.selectKependudukan(nik);
		int jml_wafat = 0;

		if (is_wafat == 0) {
			penduduk.setIs_wafat(1);
		}

		pendudukDAO.ubahPenduduk(penduduk);
		List<PendudukModel> anggota = sidukDAO.selectPendudukByIdKeluarga(penduduk.getId_keluarga());
		KeluargaModel keluarga = keluargaDAO.selectKeluargaByNik(penduduk.getId_keluarga());

		for (PendudukModel p : anggota) {
			if (p.getIs_wafat() == 1) {
				jml_wafat++;
			}
		}

		if (anggota.size() == jml_wafat) {
			keluarga.setIs_tidak_berlaku(1);
		}
		keluargaDAO.ubahKeluarga(keluarga);

		model.addAttribute("status", "ubahkematian");
		model.addAttribute("nik", nik);
		return "success";
	}

	@RequestMapping(value = "/penduduk/cari")
	public String cariPenduduk(Model model, @RequestParam(value = "id_kota", required = false, defaultValue = "0" ) int id_kota,
			@RequestParam(value = "id_kecamatan", required = false, defaultValue = "0") int id_kecamatan,
			@RequestParam(value = "id_kelurahan", required = false, defaultValue = "0") int id_kelurahan) throws ParseException {
		List<KotaModel> kota = kotaDAO.selectAllKota();
		model.addAttribute("kota", kota);
		boolean hide_kecamatan = false;
		boolean hide_kelurahan = false;
		boolean cari_submit = true;
		
		if (id_kelurahan != 0) {
			List<PendudukModel> penduduk = pendudukDAO.selectPendudukByKelurahan(id_kelurahan);
			model.addAttribute("penduduk", penduduk);
			
			String nama_kelurahan = kelurahanDAO.selectKelurahan(id_kelurahan).getNama_kelurahan();
			model.addAttribute("id_kelurahan", id_kelurahan);
			model.addAttribute("nama_kelurahan", nama_kelurahan);
			
			String nama_kecamatan = kecamatanDAO.selectKecamatan(id_kecamatan).getNama_kecamatan();
			model.addAttribute("id_kecamatan", id_kecamatan);
			model.addAttribute("nama_kecamatan", nama_kecamatan);
			
			String nama_kota = kotaDAO.selectKota(id_kota).getNama_kota();
			model.addAttribute("id_kota", id_kota);
			model.addAttribute("nama_kota", nama_kota);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			Date date_max = df.parse("1800/01/01");
			Date date_min = new Date();
			PendudukModel penduduk_tua = new PendudukModel();
			PendudukModel penduduk_muda = new PendudukModel();
			
			for (int i = 0; i < penduduk.size(); i++) {
				Date current = penduduk.get(i).getTanggal_lahir();
				if (current.after(date_max)) {
					date_max = current;
					penduduk_tua = penduduk.get(i);
				}
				if (current.before(date_min)) {
					date_min = current;
					penduduk_muda = penduduk.get(i);
				}
			}
			model.addAttribute("penduduk_tua", penduduk_tua);
			model.addAttribute("penduduk_muda", penduduk_muda);
			
			hide_kecamatan = true;
			hide_kelurahan = true;
			cari_submit = false;
		} else if (id_kecamatan != 0) {
			List<KelurahanModel> kelurahan = kelurahanDAO.selectKelurahanByKecamatan(id_kecamatan);
			model.addAttribute("kelurahan", kelurahan);
			
			String nama_kecamatan = kecamatanDAO.selectKecamatan(id_kecamatan).getNama_kecamatan();
			model.addAttribute("id_kecamatan", id_kecamatan);
			model.addAttribute("nama_kecamatan", nama_kecamatan);
			
			String nama_kota = kotaDAO.selectKota(id_kota).getNama_kota();
			model.addAttribute("id_kota", id_kota);
			model.addAttribute("nama_kota", nama_kota);
			
			
			hide_kecamatan = true;
			hide_kelurahan = true;
		} else if (id_kota != 0) {
			List<KecamatanModel> kecamatan = kecamatanDAO.selectKecamatanByKota(id_kota);
			model.addAttribute("kecamatan", kecamatan);
			
			String nama_kota = kotaDAO.selectKota(id_kota).getNama_kota();
			model.addAttribute("id_kota", id_kota);
			model.addAttribute("nama_kota", nama_kota);
			
			hide_kecamatan = true;
		}
		
		model.addAttribute("hide_kecamatan", hide_kecamatan);
		model.addAttribute("hide_kelurahan", hide_kelurahan);
		model.addAttribute("cari_submit", cari_submit);
		return "caripenduduk";
	}
}
