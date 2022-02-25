<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

 
// Include data base connect class
$filepath = realpath (dirname(__FILE__));
require_once($filepath."/db_connect.php");
$message="";
if ( isset($_GET['Email']) && isset($_GET['Password'])  ) {
 
    $Email = $_GET['Email'];
    $Password=  $_GET['Password'];
 
 // Connecting to database 
$db = new DB_CONNECT();	
 
 // Fire SQL query to get all data from weather
$result = mysql_query("SELECT * FROM Users where Email='$Email' and Password='$Password' ") or die(mysql_error());
 
// Check for succesfull execution of query and no results found
if (mysql_num_rows($result) > 0) {

    $row = mysql_fetch_assoc($result);



$myObj->User_ID = $row["User_ID"];
$myObj->UserName = $row["UserName"];
$myObj->Email = $row["Email"];
$myObj->S_Type = $row["S_Type"];
$myObj->Photo = $row["Photo"];
$myObj->message="Success";

$myJSON = json_encode($myObj);

echo $myJSON;
     
        //  $User_ID=$row["User_ID"];
        //  $UserName=$row["UserName"];
        //  $Email=$row["Email"];
        //  $S_Type=$row["S_Type"];
        //  $message="Success";
         

//echo json_encode($User_ID,$UserName,$Email,$S_Type,$message);
//echo json_encode ($User_ID);
    
	// Storing the returned array in response
}
else
{
        // If required parameter is missing
    $response["success"] = 0;
    $response["message"] = "Parameter(s) are missing. Please check the request";
 
    // Show JSON response
    echo json_encode($response);
}
}
?>