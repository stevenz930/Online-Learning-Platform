<?php
header("Content-Type: application/json");
require_once("config.php");

$input = json_decode(file_get_contents("php://input"), true);
//echo json_encode(["status" => "test", "message" => $input['id']]);
if (!isset($input['id'])) {
    echo json_encode(["error" => "Missing id"]);
    exit();
}

$delete_sql = "DELETE FROM course_review
    WHERE id = ?;";
$delete_stmt = $conn->prepare($delete_sql);
$delete_stmt->bind_param("i", $input['id']);
$delete_stmt->execute();

echo json_encode(["status" => "success", "message" => $input['id']]);

?>