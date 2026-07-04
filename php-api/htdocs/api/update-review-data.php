<?php
header("Content-Type: application/json");
require_once("config.php");

$input = json_decode(file_get_contents("php://input"), true);
if (!isset($input['id'])) {
    echo json_encode(["error" => "Unknown id"]);
    exit();
}

$update_sql = "UPDATE course_review SET rating = ? , comment = ? WHERE id = ?";
$update_stmt = $conn->prepare($update_sql);
$update_stmt->bind_param("isi", $input['rating'], $input['comment'], $input['id']);
$update_stmt->execute();

echo json_encode(["status" => "success", "data" => $update_stmt]);

?>