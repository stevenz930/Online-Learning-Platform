<?php
header("Content-Type: application/json");
require_once("config.php");

$input = json_decode(file_get_contents("php://input"), true);
if (!isset($input['id'])) {
    echo json_encode(["error" => "Unknown id"]);
    exit();
}

$update_sql = "UPDATE course 
    SET title = ?, description = ?, price = ?, 
        level = ?, thumbnail = ?, user_id = ?, 
        tags = ?, subject = ?, category = ?
    WHERE id = ?;";
$update_stmt = $conn->prepare($update_sql);
$update_stmt->bind_param("ssdssisssi", 
    $input['title'], 
    $input['description'], 
    $input['price'], 
    $input['level'], 
    $input['thumbnail'], 
    $input['user_id'], 
    $input['tags'], 
    $input['subject'], 
    $input['category'], 
    $input['id']
);
$update_stmt->execute();

echo json_encode(["status" => "success", "data" => $update_stmt]);

?>