<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

//Creating Array for JSON response
$response = array();
 
// Check if we got the field from the user
if ( isset($_POST['D_Name']) && isset($_POST['D_status']) && isset($_POST['D_Power']) && isset($_POST['D_Port'])&& isset($_POST['User_ID']) && isset($_POST['D_Photo'])) {
 
    $D_Name = $_POST['D_Name'];
    $D_status=$_POST['D_status'];
    $D_Power = $_POST['D_Power'];
    $D_Port= $_POST['D_Port'];
     $User_ID= $_POST['User_ID'];
    $D_Photo= $_POST['D_Photo'];
    
      $path = "Device_Photo/".time()."$User_ID.jpeg";
    $finalPath = "https://dentalflutter.000webhostapp.com/".$path;
    
    // Include data base connect class
    $filepath = realpath (dirname(__FILE__));
	require_once($filepath."/db_connect.php");

 
    // Connecting to database 
    $db = new DB_CONNECT();
 
    // Fire SQL query to insert data in weather
    $result = mysql_query("INSERT INTO `Device`(`D_Name`, `D_status`, `D_Power`, `D_Port`, `User_ID`, `D_Photo`, `D_Hours`, `D_DateON`) VALUES ('$D_Name','$D_status','$D_Power','$D_Port','$User_ID','$finalPath','','')");
 
 
    // Check for succesfull execution of query
    if ($result) {
        // successfully inserted 
        file_put_contents( $path, base64_decode($D_Photo) );
        $response["success"] = 1;
        $response["message"] = "Device successfully created.";
 
        // Show JSON response
        echo json_encode($response);
    } else {
        // Failed to insert data in database
        $response["success"] = 0;
        $response["message"] = "Something has been wrong";
 
        // Show JSON response
        echo json_encode($response);
    }
} else {
    // If required parameter is missing
    $response["success"] = 0;
    $response["message"] = "Parameter(s) are missing. Please check the request";
 
    // Show JSON response
    echo json_encode($response);
}
?>