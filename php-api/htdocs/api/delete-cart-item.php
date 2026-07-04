<?php
header("Content-Type: application/json");
require_once("config.php");

$id = $_POST["id"];
$course_id = $_POST["course_id"];

if ($id != null && $course_id != null) {
    $sql = "DELETE FROM cart_item
        WHERE course_id = ? AND user_id = ?;";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ss", $course_id, $id);
    
    $stmt->execute();

    echo json_encode(["status" => "success", "message" => "delete cart item successful"]);
} else {
    echo json_encode(["status" => "error", "message" => "Invalid user id"]);
}
?>