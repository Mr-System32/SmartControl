<?php
 
header('content-type: application/json; charset=utf-8');
header("access-control-allow-origin: *");

//Creating Array for JSON response
$response = array();
 
// Check if we got the field from the user
if (isset($_GET['Device_ID']) && isset($_GET["User_ID"]) && isset($_GET["Room_ID"])) {

    $User_ID = $_GET['User_ID'];
    $Room_ID = $_GET['Room_ID'];
    $Device_ID = $_GET['Device_ID'];
 
    // Include data base connect class
    $filepath = realpath (dirname(__FILE__));
	require_once($filepath."/db_connect.php");
 
    // Connecting to database 
    $db = new DB_CONNECT();
 
    // Fire SQL query to delete weather data by id
    $result = mysql_query("DELETE FROM Device_Room WHERE Device_Room.Device_ID = $Device_ID and Device_Room.Room_ID = $Room_ID and Device_Room.User_ID = $User_ID   ");
 
    // Check for succesfull execution of query
    if (mysql_affected_rows() > 0) {
        // successfully deleted
        $response["success"] = 1;
        $response["message"] = "Data successfully deleted";
 
        // Show JSON response
        echo json_encode($response);
    } else {
        // no matched id found
        $response["success"] = 0;
        $response["message"] = "No device data found by given id";
 
        // Echo the failed response
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