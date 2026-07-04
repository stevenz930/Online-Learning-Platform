<?php
header("Content-Type: application/json");
require_once("config.php");

$input = json_decode(file_get_contents("php://input"), true);
//echo json_encode(["status" => "test", "message" => $input['id']]);
if (!isset($input['id'])) {
    echo json_encode(["error" => "Missing id"]);
    exit();
}

$update_sql = "UPDATE user SET is_active = 0 WHERE id = ?";
$update_stmt = $conn->prepare($update_sql);
$update_stmt->bind_param("i", $input['id']);
$update_stmt->execute();

echo json_encode(["status" => "success", "message" => $input['id']]);

?>