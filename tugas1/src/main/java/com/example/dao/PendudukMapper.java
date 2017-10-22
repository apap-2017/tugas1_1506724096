package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	@Select("select * from penduduk where nik = #{nik}")
	PendudukModel selectKependudukan(@Param("nik") String nik);

	@Insert("INSERT INTO penduduk (id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) "
			+ "VALUES (#{id}, #{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
	void tambahPenduduk(PendudukModel penduduk);
	
	@Select("SELECT * FROM penduduk")
	List<PendudukModel> selectAllPenduduk();
	
	@Update("UPDATE penduduk SET nik=#{nik}, nama=#{nama}, tempat_lahir=#{tempat_lahir}, tanggal_lahir=#{tanggal_lahir}, jenis_kelamin=#{jenis_kelamin}, is_wni=#{is_wni}, id_keluarga=#{id_keluarga}, agama=#{agama}, pekerjaan=#{pekerjaan}, status_perkawinan=#{status_perkawinan}, status_dalam_keluarga=#{status_dalam_keluarga}, golongan_darah=#{golongan_darah}, is_wafat=#{is_wafat} WHERE id=#{id}")
	void ubahPenduduk(PendudukModel penduduk);
	
	@Select("select * from penduduk as p, keluarga as k where p.id_keluarga = k.id AND k.id_kelurahan = #{id_kelurahan}")
	List<PendudukModel> selectPendudukByKelurahan(@Param("id_kelurahan") int id_kelurahan);
}
