<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");


//Creating Array for JSON response

// Include data base connect class
$filepath = realpath (dirname(__FILE__));
require_once($filepath."/db_connect.php");

 // Connecting to database 
$db = new DB_CONNECT(); 
 if (isset($_GET["User_ID"])&&isset($_GET["Room_ID"])) {

    $User_ID = $_GET['User_ID'];
    $Room_ID = $_GET['Room_ID'];
 // Fire SQL query to get all data from weather
 if ($Room_ID == 00){
      $result = mysql_query("SELECT * FROM `Device` WHERE Device.User_ID='$User_ID'") or die(mysql_error());
 }else{
    $result = mysql_query("SELECT * FROM `Device` ,`Device_Room` WHERE Device.ID=Device_Room.Device_ID and Device_Room.Room_ID='$Room_ID' and Device_Room.User_ID='$User_ID'") or die(mysql_error());}
 
// Check for succesfull execution of query and no results found
if (mysql_num_rows($result) > 0) {
    
    // Storing the returned array in response
   
    // While loop to store all the returned response in variable
    while ($row = mysql_fetch_assoc($result)) {
        // temperoary user array
   //   $Device[]=$row;
          $Device[]=array(
         'ID'=>$row["ID"],
         'D_Name'=>$row["D_Name"],
         'D_Port'=>$row["D_Port"],
         'D_status'=>$row["D_status"],
         'D_Power'=>$row["D_Power"],
         'D_Photo'=>$row["D_Photo"],
         'D_Hours'=>$row["D_Hours"],

   
         
         );
    }
    // On success
    //$Device["success"] = 1;
    // Show JSON response
    echo json_encode($Device )  ;
}   
else 
{
    // If no data is found
    $Device["success"] = 0;
    $Device["message"] = "No data on Device found";
 
    // Show JSON response
    echo json_encode($Device);

}}
else {
    // If required parameter is missing
    $response["success"] = 0;
    $response["message"] = "Parameter(s) are missing. Please check the request";
 
    // echoing JSON response
    echo json_encode($response);
}


?>