<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");


//Creating Array for JSON response

// Include data base connect class
$filepath = realpath (dirname(__FILE__));
require_once($filepath."/db_connect.php");

 // Connecting to database 
$db = new DB_CONNECT(); 
 if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $User_ID = $_POST['User_ID'];
    $Photo = $_POST['Photo'];

     $path = "Users_Photo/".time()."$User_ID.jpeg";
    $finalPath = "https://ashrefabd.000webhostapp.com/api/".$path;

$result = mysql_query("Update Users set Photo ='$finalPath' where User_ID='$User_ID' " ) or die(mysql_error());
 
// Check for succesfull execution of query and no results found
   if ( $result ) {
       
    file_put_contents( $path, base64_decode($Photo) );
    $response["success"] = "1";
    $response["message"] = "Uploade are Done ...";
 
    // echoing JSON response
    echo json_encode($response);

        }else
         {
    // If required parameter is missing
    $response["success"] = "0";
    $response["message"] = "Uploade are missing. Please check the request";
 
    // echoing JSON response
    echo json_encode($response);
}
}
else {
    // If required parameter is missing
    $response["success"] = 0;
    $response["message"] = "Parameter(s) are missing. Please check the request";
 
    // echoing JSON response
    echo json_encode($response);
}


?>