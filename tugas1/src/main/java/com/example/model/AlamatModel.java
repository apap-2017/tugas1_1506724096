package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlamatModel {
	public int id_kelurahan;
	public String kode_kelurahan;
	public String nama_kelurahan;
	public int id_kecamatan;
	public String kode_kecamatan;
	public String nama_kecamatan;
	public int id_kota;
	public String kode_kota;
	public String nama_kota;
}
