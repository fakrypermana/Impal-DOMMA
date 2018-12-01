<?php 
	include "koneksi.php";
	
	$query_kategori = mysql_query("SELECT * FROM kategori ORDER BY nama ASC");
	
	$json = array();
	
	while($row = mysql_fetch_assoc($query)){
		$json[] = $row;
	}
	
	echo json_encode($json);
	
	mysql_close($connect);
	
?>