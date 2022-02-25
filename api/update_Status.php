<?php


header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

//Creating Array for JSON response
$response = array();
 
// Check if we got the field from the user
if (isset($_GET['ID']) && isset($_GET['D_status'])) {
 
    $ID = $_GET['ID'];
    $D_status = $_GET['D_status'];

    
 
    // Include data base connect class
	$filepath = realpath (dirname(__FILE__));
	require_once($filepath."/db_connect.php");

	// Connecting to database
    $db = new DB_CONNECT();
    if ($D_status==1){
      date_default_timezone_set('Asia/Baghdad');  
      $time=date("Y-m-d H:i:s");
	// Fire SQL query to update Device data by id
	
    $result = mysql_query("UPDATE Device SET D_status='$D_status' , D_DateON='$time' WHERE ID = '$ID' ");}
    else{
        
    $result = mysql_query("UPDATE Device SET D_status='$D_status'  WHERE ID = '$ID' ");}

 
    // Check for succesfull execution of query and no results found
    if ($result) {
        // successfully updation of temp (temperature)
        $response["success"] = 1;
        $response["message"] = "Device Status successfully updated.";
 
        // Show JSON response
        echo json_encode($response);
    } else {
 
    }
} else {
    // If required parameter is missing
    $response["success"] = 0;
    $response["message"] = "Parameter(s) are missing. Please check the request";
 
    // Show JSON response
    echo json_encode($response);
}
?>