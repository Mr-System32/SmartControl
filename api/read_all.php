<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");


//Creating Array for JSON response

// Include data base connect class
$filepath = realpath (dirname(__FILE__));
require_once($filepath."/db_connect.php");

 // Connecting to database 
$db = new DB_CONNECT(); 
 if (isset($_GET["User_ID"])) {
    $User_ID = $_GET['User_ID'];
 // Fire SQL query to get all data from weather
$result = mysql_query("SELECT * FROM Device where User_ID='$User_ID' order by ID desc") or die(mysql_error());
 
// Check for succesfull execution of query and no results found
if (mysql_num_rows($result) > 0) {
    
    // Storing the returned array in response
   
    // While loop to store all the returned response in variable
    while ($row = mysql_fetch_assoc($result)) {

if ($row["D_status"] ==1){
    //calculate houres number 
     date_default_timezone_set('Asia/Baghdad');  

        $ID=$row["ID"];
        $date=date("Y-m-d H:i:s");
    $newTime=time_ago($row["D_DateON"]) + $row["D_Hours"];
    $result3 = mysql_query("Update Device set D_Hours='$newTime' ,D_DateON='$date' where ID='$ID'") or die(mysql_error());
};

if ($row["D_Hours"]>720){
    //rest houres number monthly
    $ID=$row["ID"];
    $result2 = mysql_query("Update Device set D_Hours=0 where ID='$ID'") or die(mysql_error());
};



        // temperoary user array
   //   $Device[]=$row;
          $Device[]=array(
         'ID'=>$row["ID"],
         'D_Name'=>$row["D_Name"],
         'D_status'=>$row["D_status"],
         'D_Power'=>$row["D_Power"],
         'D_Port'=>$row["D_Port"],
         'D_Hours'=>$row["D_Hours"],
         'D_DateON'=>$row["D_DateON"],
           'D_Photo'=>$row["D_Photo"]
         
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

 date_default_timezone_set('Asia/Baghdad');  
// echo facebook_time_ago('2016-03-11 04:58:00');  
 function time_ago($timestamp)  
 {  
  date_default_timezone_set('Asia/Baghdad');  
// Declare two dates 
$start_time =strtotime($timestamp) ; 
$end_date = time() ; 

// Get the difference and divide into 
// total no. seconds 60/60/24 to get 
// number of days 

      $time_difference =(int) (($end_date - $start_time)/60/60/24*24); 
     
      return $time_difference;
} 

?>