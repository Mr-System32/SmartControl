<?php


header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

//Creating Array for JSON response
$response = array();
 
// Check if we got the field from the user
if (isset($_GET['User_ID']) && isset($_GET['S_Type'])) {
 
    $User_ID = $_GET['User_ID'];
    $D_status = $_GET['S_Type'];

    
 
    // Include data base connect class
	$filepath = realpath (dirname(__FILE__));
	require_once($filepath."/db_connect.php");

	// Connecting to database
    $db = new DB_CONNECT();
 
	// Fire SQL query to update weather data by id
    $result = mysql_query("UPDATE Users SET S_Type ='$S_Type'  WHERE User_ID = '$User_ID' ");
 
    // Check for succesfull execution of query and no results found
    if ($result) {
        // successfully updation of temp (temperature)
        $response["success"] = 1;
        $response["message"] = "S_Type successfully updated.";
 
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