<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

//Creating Array for JSON response
$response = array();
 
// Check if we got the field from the user
if ( isset($_GET['UserName']) && isset($_GET['Password']) && isset($_GET['Email']) && isset($_GET['Phone']) ) {
 
    $UserName = $_GET['UserName'];
    $Password=  $_GET['Password'];
    $Email = $_GET['Email'];
    $Phone = $_GET['Phone'];
   
 
    // Include data base connect class
    $filepath = realpath (dirname(__FILE__));
	require_once($filepath."/db_connect.php");

 
    // Connecting to database 
    $db = new DB_CONNECT();
 
    //check email 
       $CheckEmail = mysql_query("Select Email from Users where Email='$Email'");
       
       if (mysql_num_rows($CheckEmail) > 0) {
            
            $response["success"] = 0;
            $response["message"] = "Email Alredy Use";
              // Show JSON response
        echo json_encode($response);
           
       }   else{
          
       
 
 
    // Fire SQL query to insert data in weather
    $result = mysql_query("INSERT INTO Users (UserName,Password,Email,Phone) VALUES('$UserName','$Password','$Email','$Phone')");
 
    // Check for succesfull execution of query
    if ($result) {
        // successfully inserted 
        $response["success"] = 1;
        $response["message"] = "Account successfully created.";
 
        // Show JSON response
        echo json_encode($response);
    } else {
        // Failed to insert data in database
        $response["success"] = 0;
        $response["message"] = "Something has been wrong";
 
        // Show JSON response
        echo json_encode($response);
    }
           
       }

    
} else {
    // If required parameter is missing
    $response["success"] = 0;
    $response["message"] = "Parameter(s) are missing. Please check the request";
 
    // Show JSON response
    echo json_encode($response);
}
?>