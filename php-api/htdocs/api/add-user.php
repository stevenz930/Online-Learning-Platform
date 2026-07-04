<?php
header("Content-Type: application/json");
require_once("config.php");

$input = json_decode(file_get_contents("php://input"), true);
if (!isset($input)) {
    echo json_encode(["error" => "Empty Input"]);
    exit();
}

$insert_sql = "INSERT INTO user (password, is_superuser, 
    first_name, last_name, avatar, bio, email, 
    username, is_instructor, facebook_url, instagram_url,
    linkedin_url, twitter_url, is_active) 
    values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
$insert_stmt = $conn->prepare($insert_sql);
$insert_stmt->bind_param("sissssssissssi", $input['password'], $input['is_superuser'], 
    $input['first_name'], $input['last_name'], $input['avatar'], $input['bio'], $input['email'],
    $input['username'], $input['is_instructor'],$input['facebook_url'],$input['instagram_url'],
    $input['linkedin_url'], $input['twitter_url'], $input['is_active']);
$insert_stmt->execute();

echo json_encode(["status" => "success", "data" => $insert_stmt]);

?>