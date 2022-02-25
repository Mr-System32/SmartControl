<?php


header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

//Creating Array for JSON response
$response = array();
 
// Check if we got the field from the user
if (isset($_GET['ID']) && isset($_GET['D_Name']) && isset($_GET['D_status']) && isset($_GET['D_Power']) && isset($_GET['D_Port'])) {
 
    $ID = $_GET['ID'];
    $D_Name = $_GET['D_Name'];
    $D_status = $_GET['D_status'];
    $D_Power = $_GET['D_Power'];
    $D_Port= $_GET['D_Port'];
    
 
    // Include data base connect class
	$filepath = realpath (dirname(__FILE__));
	require_once($filepath."/db_connect.php");

	// Connecting to database
    $db = new DB_CONNECT();
 
	// Fire SQL query to update weather data by id
    $result = mysql_query("UPDATE Device SET D_Name = '$D_Name' ,D_status ='$D_status', D_Power ='$D_Power ',D_Port='$D_Port' WHERE ID = '$ID'");
 
    // Check for succesfull execution of query and no results found
    if ($result) {
        // successfully updation of temp (temperature)
        $response["success"] = 1;
        $response["message"] = " Data successfully updated.";
 
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