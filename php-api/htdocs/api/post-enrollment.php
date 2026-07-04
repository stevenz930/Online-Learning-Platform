<?php
header("Content-Type: application/json");
require_once("config.php");

$id = $_POST["id"];
$course_id = $_POST["course_id"];

if ($id != null && $course_id != null) {
    // sign up
    $sql = "INSERT INTO enrollment (enrolled_at, is_completed, course_id, user_id)
        VALUES (NOW(), 1, ?, ?);";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ss", $course_id, $id);
    $stmt->execute();

    $sql2 = "DELETE FROM cart_item
        WHERE course_id = ? AND user_id = ?;";
    $stmt2 = $conn->prepare($sql2);
    $stmt2->bind_param("ss", $course_id, $id);
    $stmt2->execute();


    echo json_encode(["status" => "success", "message" => "enroll successful"]);
} else {
    echo json_encode(["status" => "error", "message" => "enroll failed"]);
}
?>