<?php

class DB_Connect {
  private $conn;

  //connecting Database
  public function connect(){
    require_once 'include/Config.php';

    //connecting to mysql Database
    $this->conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);

    //return database dba_handlers
    return this->conn;
  }
}

 ?>
