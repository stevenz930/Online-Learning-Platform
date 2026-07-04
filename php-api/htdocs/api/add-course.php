<?php
header("Content-Type: application/json");
require_once("config.php");

$input = json_decode(file_get_contents("php://input"), true);
//$input = $_POST;
if (!isset($input['title'])) {
    echo json_encode(["error" => "Empty Input", "input data" => $input['title']]);
    exit();
}

$insert_sql = "INSERT INTO course (
        title, description, price, 
        level, thumbnail, user_id,
        tags, subject, category) 
    values (?,?,?,
        ?,?,?,
        ?,?,?)";
$insert_stmt = $conn->prepare($insert_sql);
$insert_stmt->bind_param("ssdssisss", 
    $input['title'], 
    $input['description'], 
    $input['price'], 
    $input['level'], 
    $input['thumbnail'], 
    $input['user_id'], 
    $input['tags'], 
    $input['subject'], 
    $input['category']
);
$insert_stmt->execute();

echo json_encode(["status" => "success", "data" => $input['title']]);

?>