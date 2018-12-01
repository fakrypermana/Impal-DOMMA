<?php
	include "koneksi.php";
	
	$id 	= $_POST['id'];
	$nama 	= $_POST['nama'];
	$jenis = $_POST['jenis'];
	
	class emp{}
	
	if (empty($id) || empty($nama) || empty($jenis)) { 
		$response = new emp();
		$response->success = 0;
		$response->message = "Kolom isian tidak boleh kosong"; 
		die(json_encode($response));
	} else {
		$query = mysql_query("UPDATE biodata SET nama='".$nama."', jenis='".$jenis."' WHERE id='".$id."'");
		
		if ($query) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Data berhasil di update";
			die(json_encode($response));
		} else{ 
			$response = new emp();
			$response->success = 0;
			$response->message = "Error update Data";
			die(json_encode($response)); 
		}	
	}
?>