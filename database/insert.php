<?php
	include "koneksi.php";
	
	$nama 	= $_POST['nama'];
	$jenis = $_POST['jenis'];
	
	class emp{}
	
	if (empty($nama) || empty($jenis)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Kolom isian tidak boleh kosong"; 
		die(json_encode($response));
	} else {
		$query_kategori = mysql_query("INSERT INTO kategori (id,nama,jenis) VALUES(0,'".$nama."','".$jenis."')");
		
		if ($query_kategori) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Data berhasil di simpan";
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error simpan Data";
			die(json_encode($response)); 
		}	
	}
?>