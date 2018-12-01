<?php
	
	$server		= "104.250.107.126"; 
	$user		= "teluboti_domma"; 
	$password	= "impalkuy"; 
	$database	= "teluboti_domma"; 
	
	$connect = mysqli_connect($server, $user, $password) or die ("Koneksi gagal!");
	$find_db = mysqli_select_db($database) or die ("Database belum siap!");

	if ($find_db) {
		echo "Database  Ada";
	}else {
		echo "Database Tidak Ada";
	}
?>