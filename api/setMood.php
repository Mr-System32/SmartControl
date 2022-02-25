<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");


//Creating Array for JSON response

// Include data base connect class
$filepath = realpath (dirname(__FILE__));
require_once($filepath."/db_connect.php");

 // Connecting to database 
$db = new DB_CONNECT(); 
 if (isset($_GET["User_ID"]) && isset($_GET["Mood_ID"])) {

    $User_ID = $_GET['User_ID'];
    $Mood_ID = $_GET['Mood_ID'];
 // Fire SQL query to get all data from weather
$result = mysql_query("UPDATE `Device` d
set d.D_status = CASE
   WHEN   d.Id IN (select Device_Mood.Device_ID from Device_Mood WHERE Device_Mood.Mood_ID = '$Mood_ID') THEN 1
   ELSE 0
END") or die(mysql_error());
 
  $response["success"] = 1;
    $response["message"] = "Mood Is Set ";
 
    // echoing JSON response
    echo json_encode($response);
}
else {
    // If required parameter is missing
    $response["success"] = 0;
    $response["message"] = "Parameter(s) are missing. Please check the request";
 
    // echoing JSON response
    echo json_encode($response);
}


?>